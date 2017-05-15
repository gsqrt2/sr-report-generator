import java.util.ArrayList;


public class Ttlp {
	public Ttlp(String name)
	{
		ttlpName = name;
		
	}
	
	public String toString()
	{
		return ttlpName;
	}
	
	
	public islandTy addTy(String tyName)
	{
		islandTy newTy = new islandTy(tyName, this);
		return newTy;
	}
	
	public islandTy getTyByName(String tyName)
	{
		
		return null;
	}
	
	class islandTy{
		public islandTy(String name, Ttlp ttlp)
		{
			tyName = name;
		}
		
		public Ttlp getParentTtlp()
		{
			return parentTtlp;
		}
		
		private String tyName;
		private int numOfTyConnections=0, numOfTyServices=0;
		private Ttlp parentTtlp;
	}
	
	private String ttlpName;
	private int numOfTtlpConnections=0, numOfTtlpServices=0, numOfTtlpAdsl=0;
	private ArrayList<islandTy> listOfTys;
}
