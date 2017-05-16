import java.util.ArrayList;
import java.util.HashMap;


public class Ttlp {
	public Ttlp(String name)
	{
		ttlpName = name;
		islandTyHashMap = new HashMap<String, IslandTy>();
		
		
	}
	
	public String toString()
	{
		return ttlpName;
	}
	
	
	public ArrayList<IslandTy> getListOfTys()
	{
		listOfTys = new ArrayList<IslandTy>(islandTyHashMap.values());
		return listOfTys;
	}
	
	public IslandTy addTy(String tyName)
	{
		IslandTy newTy = new IslandTy(tyName, this);
		islandTyHashMap.put(tyName, newTy);
		
		return newTy;
	}
	
	public IslandTy getTyByName(String tyName)
	{
		return islandTyHashMap.get(tyName);
	}
	
	class IslandTy{
		public IslandTy(String name, Ttlp ttlp)
		{
			tyName = name;
		}
		
		public Ttlp getParentTtlp()
		{
			return parentTtlp;
		}
		
		public int getNumOfConnections()
		{
			return numOfTyConnections;
		}
		
		public int getNumOfServices()
		{
			return numOfTyServices;
		}
		
		public String toString()
		{
			return tyName;
		}
		
		public void increaseConnections()
		{
			numOfTtlpConnections++;
			numOfTyConnections++;
		}
		
		public void increaseServices()
		{
			numOfTtlpServices++;
			numOfTyServices++;
		}
		
		private String tyName;
		private int numOfTyConnections=0, numOfTyServices=0;
		private Ttlp parentTtlp;
	}
	
	private String ttlpName;
	private int numOfTtlpConnections=0, numOfTtlpServices=0, numOfTtlpAdsl=0;
	private ArrayList<IslandTy> listOfTys;
	private HashMap<String,IslandTy> islandTyHashMap;
}
