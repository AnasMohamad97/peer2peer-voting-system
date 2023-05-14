import java.io.UnsupportedEncodingException;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Vote implements java.io.Serializable{

	
	public String VoteId;
	
    public Wallet v; //voter
    
	public String choice;
	
	byte[] signature;
	
	Vote(String id , Wallet v , String choice ) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException{
		this.VoteId = id;
		this.choice = choice;
		this.v = v;
		this.signature = create_Signature();
	}
	private byte[] create_Signature() throws SignatureException,NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException
	{
		Signature sig = Signature.getInstance("SHA256WithDSA");
		SecureRandom secureRandom = new SecureRandom();
		sig.initSign(v.priv_k,secureRandom);
		byte[] data = choice.getBytes("UTF-8");
		sig.update(data);
		return sig.sign();
		
	}
	
	public boolean verify() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException
	{
		Signature sig = Signature.getInstance("SHA256WithDSA");
		sig.initVerify(v.pub_k);
		byte[] data = choice.getBytes("UTF-8");
		sig.update(data);
		return sig.verify(signature);
	}
}
