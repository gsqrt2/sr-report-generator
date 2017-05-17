import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;


public class UnknownDataDialog extends JDialog{
	public UnknownDataDialog(String dataString, String dataType, DataRetriever parentDataRetriever)
	{
		this.setModal(true);
		this.dataString = dataString;
		this.dataType = dataType;
		this.parentDataRetriever = parentDataRetriever;
		
		if(dataType == "location")
		{
			this.setTitle("’γνωστη περιοχή: '"+dataString+"'");
			initGui();		
		}
		else
		if(dataType == "taskType")
		{
			this.setTitle("’γνωστος τύπος έργασίας: '"+dataString+"'");		
			initGui();
		}
	}
	
	private void initGui()
	{
		this.setResizable(false);
		mainPanel = new JPanel(new BorderLayout());
		testLabel = new JLabel("dialog to handle "+dataString+" as "+dataType);
		mainPanel.add(testLabel);
		this.add(mainPanel);
		mainPanel.setPreferredSize(new Dimension(550,250));
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void addToLocations()
	{
		
	}
	
	private String dataString, dataType;
	private JPanel mainPanel;
	private JLabel testLabel;
	private DataRetriever parentDataRetriever;
}
