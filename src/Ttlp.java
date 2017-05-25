import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;


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
	
	public JScrollPane getTtlpReportTable(){

		listOfTys = new ArrayList<IslandTy>(islandTyHashMap.values());
		String[] columnNames = {ttlpName,
                "сумеяцеиа",
                "бкабес",
                "йатасйеуес"};
		
		Object[][]  data = new Object[listOfTys.size()][];
		for(int i=0;i<listOfTys.size();i++)
		{
			Object[] currentObject = new Object[]{listOfTys.get(i).toString(), listOfTys.get(i).getNumOfTeams(), listOfTys.get(i).getNumOfServices(), listOfTys.get(i).getNumOfConnections()};
					
			data[i] = currentObject;
		}
		ttlpReportTable = new JTable(data, columnNames);
		ttlpReportTable.setColumnSelectionAllowed(false);
		ttlpReportTable.getTableHeader().setResizingAllowed(false);
		ttlpReportTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane tableScrollPane = new JScrollPane(ttlpReportTable);
		ttlpReportTable.setFillsViewportHeight(true);

		
		return tableScrollPane;
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
		
		public int getNumOfTeams(){
			return numOfTyTeams;
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
		private int numOfTyConnections=0, numOfTyServices=0, numOfTyTeams=0;
		private Ttlp parentTtlp;
	}
	
	private JTable ttlpReportTable;
	private String ttlpName;
	private int numOfTtlpConnections=0, numOfTtlpServices=0, numOfTtlpAdsl=0, numOfTtlpTeams=0;
	private ArrayList<IslandTy> listOfTys;
	private HashMap<String,IslandTy> islandTyHashMap;
}
