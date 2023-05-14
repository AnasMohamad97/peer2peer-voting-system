import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import com.google.gson.GsonBuilder; 


   class Peer2Peer {
	   
	   BlockChain B = new BlockChain();
	   DatagramSocket DS = null;
	   InetAddress ip = null;
	   Wallet person ;
	   int port ;
	   
	   public static ArrayList<Peer2Peer> nodes = new ArrayList<Peer2Peer>(); //peers
	   public Peer2Peer(Wallet person , int p) 
	   {
		   this.person = person;
		   this.port = p;
		   nodes.add(this);
		   
	   }
	   public void connect() throws UnknownHostException, SocketException 
	   {
		   
			   this.ip = InetAddress.getByName("127.0.0.1");
			   this.DS = new DatagramSocket(this.port);
			   this.DS.setBroadcast(true);
			   this.DS.setSoTimeout(5000);
			   System.out.println(this.person.id + " Connected!" );
			
	   }
   

	   
	   
	   public void BroadcastVote(Vote v) throws IOException
	   {
		   ByteArrayOutputStream B = new ByteArrayOutputStream();
		   ObjectOutputStream O = new ObjectOutputStream(B);
		   
		   O.writeObject(v);
		   B.flush();
		   O.flush();
		   final byte[] data = B.toByteArray();
		   
		   for (int i = 0 ; i<nodes.size();++i)
		   {
			   DatagramPacket Pac = new DatagramPacket(data,data.length , this.ip , nodes.get(i).getPort());
			   DS.send(Pac);
			   
		   }
		   
		   
		   
	   }
	   
	   
	   
	public void ReciveVote() throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException
	   { // miners
		   byte[] buffer =  new byte[5000];
		   DatagramPacket pac = new DatagramPacket(buffer , 5000);
		   DS.receive(pac);
		   ByteArrayInputStream B = new ByteArrayInputStream(buffer);
		   ObjectInputStream O = new ObjectInputStream(new BufferedInputStream(B));
		   Vote v = (Vote)O.readObject();
		   B.close();
		   O.close();
		   
		   System.out.println(v.choice);
		   Block b = this.mine(v);
		   
		   for(int i = 0  ; i<nodes.size();++i)
		   {
			   nodes.get(i).disconnet();
			   nodes.get(i).connect();
		   }
		   BroadcastBlock(b);
		   
	   }

	public void BroadcastBlock(Block b) throws IOException {
		 ByteArrayOutputStream B = new ByteArrayOutputStream();
		   ObjectOutputStream O = new ObjectOutputStream(B);
		   
		   O.writeObject(b);
		   B.flush();
		   O.flush();
		   
		   final byte[] data = B.toByteArray();
		   for (int i = 0 ; i<nodes.size();++i)
		   {
			   DatagramPacket Pac = new DatagramPacket(data,data.length , this.ip , nodes.get(i).getPort());
			   DS.send(Pac);
			   
		   }
		
	}
	
	public void receiveBlock() throws IOException, ClassNotFoundException
	{
		byte[] buffer =  new byte[5000];
		   DatagramPacket pac = new DatagramPacket(buffer , 5000);
		   
		   DS.receive(pac);
		   ByteArrayInputStream B = new ByteArrayInputStream(buffer);
		   ObjectInputStream O = new ObjectInputStream(new BufferedInputStream(B));
		   Block b = (Block)O.readObject();
		   System.out.println(b.hash);
		   
	}
	
	public int getPort(){return this.port;}
	public void disconnet() {DS.close();}
	public void setBlock(BlockChain b) {this.B = b;}

	public Block mine(Vote v) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException 
	{
		
		 Block b  = null;
		 if(v.verify() && B.isChainValid() ) {
			 b = B.add_block(v);
			 return b;
		 }
		 else
		 {
			 System.err.println("not valid");
			 return b;
		 }
		 
	}
	
	
}
   
   
   
   
