import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;



public class ReportGenerator {
	public ReportGenerator(ArrayList<Ttlp> retrievedArraylist){
		this.retrievedArraylist = retrievedArraylist;
		reportContainer = new JPanel(new BorderLayout());
		JScrollPane testTable = retrievedArraylist.get(0).getTtlpReportTable();
		reportContainer.add(testTable, BorderLayout.CENTER);
	}
	
	public JPanel getResults(){
		return reportContainer;
	}
	
	
	private JPanel reportContainer;
	private ArrayList<Ttlp> retrievedArraylist;
}
