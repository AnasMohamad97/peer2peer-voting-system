import java.util.ArrayList;

import com.google.gson.*;
public class BlockChain {
	
public static ArrayList<Block> Chain = new ArrayList<Block>();
	
public static int diff  = 5;

	public BlockChain( ) 
	{
		
	}
   
boolean	isChainValid(){
	
		Block cur;
        Block prev;
		boolean ok = true;
		String Hash_Target = new String (new char[diff]).replace('\0', '0');
		
        for(int i = 1 ;i<Chain.size();++i) 
        {
        	
        	cur =  Chain.get(i);
        	prev = Chain.get(i-1);
        	
        	
            // Any change to the blockchain’s blocks will cause this method to return false.

        	if(!cur.hash.equals(cur.calc_hash())) {
        		System.out.println("Chain Invalid (Current hash calculation is not correct)");
        		ok =  false;
        	}
        	if(!prev.hash.equals(cur.prev_hash)) {
        		System.out.println("Chain Invaild (previous not equal current)" );
        		ok =  false;

        	}
        	if(!cur.hash.substring(0, diff).equals(Hash_Target)) {
				System.out.println("This block hasn't been mined");
                ok  = false;
        	}
        }

		return ok;
		
	}


	public Block add_block(Vote data) 
	{
		int SIZE = Chain.size();
		
		if(SIZE == 0) 
		{
			Block b = new Block(data , "00000000000000000000000000000000000");
			Chain.add(b);
			return b;
		}
		
		else 
		{
			Block b = new Block(data , Chain.get(SIZE-1).hash);
			Chain.add(b);
			return b;
			

		} 
		
		
    }
	

	public void Print_The_Chain() 
	{
	  	String blockjason = new GsonBuilder().setPrettyPrinting().create().toJson(Chain);
	  	System.out.println(blockjason);
	}
	

}
