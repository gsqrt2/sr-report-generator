import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;


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
		textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		addLocationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		ignoreAlwaysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ignoreOncePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		textPanel.add(new JLabel("Τί να κάνω με την άγνωστη τοποθεσία;"));
		
		buttonGroup = new ButtonGroup();
	
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
		addRadio = new JRadioButton("Η περιοχή ανήκει στο ΤΤΛΠ: ");
		ttlpComboBox = new JComboBox(petStrings);
		tyComboBox = new JComboBox(petStrings);
		
		ignoreOnceRadio = new JRadioButton("Αγνόησε μόνο αυτή τη φορά, να ερωτηθώ ξανα");
		ignoreAlwaysRadio = new JRadioButton("Να αγνοείται η συγκεκριμένη εγγραφή");
		
		
		buttonGroup.add(addRadio);
		buttonGroup.add(ignoreAlwaysRadio);
		buttonGroup.add(ignoreOnceRadio);
				
		addLocationPanel.add(addRadio);
		addLocationPanel.add(ttlpComboBox);
		addLocationPanel.add(new JLabel(", ΤΥ: "));
		addLocationPanel.add(tyComboBox);
		//addLocationPanel.add(new JLabel("Η περιοχή ανήκει στο: "));
		
		ignoreAlwaysPanel.add(ignoreAlwaysRadio);
		//ignoreAlwaysPanel.add(new JLabel());
		
		ignoreOncePanel.add(ignoreOnceRadio);
		//ignoreOncePanel.add(new JLabel());
		
		okButton = new JButton("Ok");
		mainPanel.add(textPanel);
		mainPanel.add(addLocationPanel);
		mainPanel.add(ignoreAlwaysPanel);
		mainPanel.add(ignoreOncePanel);
		mainPanel.add(okButton);
		this.add(mainPanel);
		//mainPanel.setPreferredSize(new Dimension(550,250));
		System.out.println("->"+buttonGroup.getButtonCount());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}
	
	private void addToLocations()
	{
		
	}
	
	private String dataString, dataType;
	private JPanel textPanel, mainPanel, addLocationPanel, ignoreAlwaysPanel, ignoreOncePanel, buttonPanel;
	private JButton okButton;
	private DataRetriever parentDataRetriever;
	private JRadioButton addRadio, ignoreOnceRadio, ignoreAlwaysRadio;
	private ButtonGroup buttonGroup;
	private JComboBox ttlpComboBox, tyComboBox;
}
