import java.security.MessageDigest;
import java.util.Date;
import com.google.gson.*;

public  class Block implements java.io.Serializable
{
	public String hash;
	public String prev_hash;
	private Vote data;
	private long timeStamp;
	private int nonce;
	
	public Block(Vote data , String prev_hash) {
		this.data = data;
		this.prev_hash = prev_hash;
		this.timeStamp = new Date().getTime();
		this.hash = calc_hash();
	}

	//Applies Sha256 to a string and returns the result. 
	public  String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}	
	public String calc_hash() {
		String calHash = applySha256(prev_hash + Long.toString(timeStamp) + data);
		return calHash;
	}
	
	public void mineBlock(int diff) {
		
		String Target = new String(new char[diff]).replace('\0', '0');
		while(!hash.substring(0,diff).equals(Target)) {
			nonce++;
			hash = calc_hash();
		}		
		System.out.println("Block Mined!!! : " + hash);

	}
	
	
	
}