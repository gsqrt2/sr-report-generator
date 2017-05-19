import javax.swing.*;
import javax.swing.BoxLayout;
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
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		testLabel1 = new JLabel("proti grammi");
		testLabel2 = new JLabel("deyteri grammi");
		testLabel3 = new JLabel("triti grammi");
		okButton = new JButton("Ok");
		mainPanel.add(testLabel1);
		mainPanel.add(testLabel2);
		mainPanel.add(testLabel3);
		mainPanel.add(okButton);
		this.add(mainPanel);
		//mainPanel.setPreferredSize(new Dimension(550,250));
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void addToLocations()
	{
		
	}
	
	private String dataString, dataType;
	private JPanel mainPanel, addLocationPanel, ignoreAlwaysPanel, ignoreOncePanel;
	private JLabel testLabel1, testLabel2, testLabel3;
	private JButton okButton;
	private DataRetriever parentDataRetriever;
}
