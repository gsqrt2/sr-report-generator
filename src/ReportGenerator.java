import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JPanel;


public class ReportGenerator {
	public ReportGenerator(ArrayList<Ttlp> retrievedArrayList){
		this.retrievedArraylist = retrievedArrayList;
		Object[] columnNames = {"аяцосаяымийос",
                "сумеяцеиа",
                "бкабес",
                "йатасйеуес"};
		
		Object[][] data = {
			    {"аицима", "2", "5", "3"},
			    {"ацйистяи", "0", "0", "1"},
			};
		
		testTable = new JTable(data, columnNames);
		
		reportContainer = new JPanel();
		reportContainer.add(testTable);
		// for(int i=0;i<retrievedArraylist.size();i++)
		// {
			// System.out.println("ttlp no"+i+": "+retrievedArraylist.get(i));
			 Ttlp currentTtlp = retrievedArraylist.get(0);
			 ArrayList<Ttlp.IslandTy> tysOfTtlpArraylist = currentTtlp.getListOfTys();
			// System.out.println(tysOfTtlpArraylist.size());
			 for(int j=0;j<tysOfTtlpArraylist.size();j++)
			 {
				 Ttlp.IslandTy currentTy = tysOfTtlpArraylist.get(j);
				if(currentTtlp.toString().equals("аяцосаяымийоу"))
				 System.out.println("Ty "+currentTy.toString()+": kataskeyes:"+currentTy.getNumOfConnections()+", vlaves"+currentTy.getNumOfServices());
			 }
		// }
		 
		
	}
	
	public JTable getResults(){
		return testTable;
	}
	
	private JPanel reportContainer;
	private ArrayList<Ttlp> retrievedArraylist;
	private JTable testTable;
}
