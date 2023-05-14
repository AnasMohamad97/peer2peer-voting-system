import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

//person , voter . etc.


public class Wallet implements java.io.Serializable {
	public String id;
	public PrivateKey priv_k;
	public PublicKey  pub_k ;
	
	
	public Wallet(String id ) {
		this.id = id;
		generate_Keys();
	}
	
	public void generate_Keys() //this method is it uses Java.security.KeyPairGenerator to 
	// generate an Elliptic Curve KeyPair. This methods makes and sets our Public and Private keys
	{
	
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
			keyGen.initialize(512,random);
        	KeyPair keyPair = keyGen.generateKeyPair();
        	priv_k = keyPair.getPrivate();
        	pub_k = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	public String getSK() {
		return Base64.getEncoder().encodeToString(priv_k.getEncoded());
	}
	public String getPK() {
		return Base64.getEncoder().encodeToString(priv_k.getEncoded());
	}
}
