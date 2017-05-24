import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.DefaultComboBoxModel;


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
			initLocationGui();		
		}
		else
		if(dataType == "taskType")
		{
			this.setTitle("’γνωστος τύπος έργασίας: '"+dataString+"'");		
			//initGui();
		}
	}
	
	private void initLocationGui()
	{
		this.setResizable(false);
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		chooseLocationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		Ttlp dummyTtlp = new Ttlp("Επιλέξτε ΤΤΛΠ");
		Ttlp.IslandTy dummyTy = dummyTtlp.addTy("Επιλέξτε Τ/Υ");
		Ttlp[] ttlpArray = parentDataRetriever.getTtplStructure();
		
		Ttlp[] finalTtlpArray = new Ttlp[ttlpArray.length+1];
		finalTtlpArray[0] = dummyTtlp;
		for(int i=1;i<finalTtlpArray.length;i++)
			finalTtlpArray[i] = ttlpArray[i-1];

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		textPanel.add(new JLabel("Παρακαλώ καθορίστε σε ποιά Τ/Υ ανήκει η άγνωστη περιοχή '"+dataString+"':"));
		
		ttlpComboBox = new JComboBox(finalTtlpArray);
		chooseLocationPanel.add(ttlpComboBox);


	
		
		okButton = new JButton("Ok");
		buttonPanel.add(okButton);
		mainPanel.add(textPanel);
		mainPanel.add(chooseLocationPanel);
		//mainPanel.add(buttonPanel);
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
	private JPanel textPanel, mainPanel, buttonPanel, chooseLocationPanel;
	private JButton okButton;
	private DataRetriever parentDataRetriever;

	private JComboBox<Ttlp> ttlpComboBox;
	private JComboBox<Ttlp.IslandTy> tyComboBox;

}
