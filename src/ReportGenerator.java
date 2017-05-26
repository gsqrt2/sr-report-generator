import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;



public class ReportGenerator {
	public ReportGenerator(ArrayList<Ttlp> retrievedArraylist){
		this.retrievedArraylist = retrievedArraylist;
		
		for(Ttlp i : retrievedArraylist)
		{
			String ttlpName = i.toString();
			switch(ttlpName)
			{
				case "аяцосаяымийоу" :
					saronikosTable = i.getTtlpReportTable();
					break;
				case "яодоу":
					rodosTable = i.getTtlpReportTable();
					break;
					
				case "йы":
					kosTable = i.getTtlpReportTable();
					break;
					
				case "салоу":
					samosTable = i.getTtlpReportTable();
					break;
					
				case "виоу":
					xiosTable = i.getTtlpReportTable();
					break;
					
				case "йуйкадым":
					kykladesTable = i.getTtlpReportTable();
					break;
					
			}
		}
		//reportContainer = new JPanel(new BorderLayout());
		//reportContainer.add(kykladesTable, BorderLayout.CENTER);
		reportContainer = new JPanel(new GridBagLayout());
		reportContainer.setPreferredSize(new Dimension(500,500));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		reportContainer.add(kykladesTable, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		reportContainer.add(rodosTable, c);
		/*
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 2;
		reportContainer.add(kosTable, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		reportContainer.add(saronikosTable, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		reportContainer.add(samosTable, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		reportContainer.add(xiosTable, c);*/
		
		reportWrapper = new JPanel(new BorderLayout());
		reportWrapper.add(reportContainer, BorderLayout.CENTER);
	}
	
	public JPanel getResults(){
		return reportWrapper;
	}
	
	
	private JPanel reportContainer, reportWrapper;
	private ArrayList<Ttlp> retrievedArraylist;
	private JScrollPane kykladesTable, rodosTable, kosTable, saronikosTable, samosTable, xiosTable;
}
