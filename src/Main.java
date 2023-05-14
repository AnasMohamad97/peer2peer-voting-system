import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, ClassNotFoundException, IOException {

		
		Wallet v1 = new Wallet("123");
		Wallet v2 = new Wallet("456");
		Wallet v3 = new Wallet("789");
		
		Peer2Peer p1 = new Peer2Peer(v1, 5000);
		Peer2Peer p2 = new Peer2Peer(v2, 3000);
		Peer2Peer miner = new Peer2Peer(v3, 6666);
		
		Vote s1= new Vote("Savatage",v1,"x");
		Vote s2 = new Vote("Opeth",v2,"xx");

		
		

	
		p1.connect();
		p2.connect();
		miner.connect();
		
		
		p1.BroadcastVote(s1);
		miner.ReciveVote();
		p1.receiveBlock();
		
		p1.disconnet();
		p2.disconnet();
		miner.disconnet();
        
		
		p1.connect();
		p2.connect();
		miner.connect();
        
		p1.BroadcastVote(s2);
		miner.ReciveVote();
		p2.receiveBlock();
		




		
	}

}
