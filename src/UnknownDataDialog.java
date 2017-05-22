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
		addLocationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		ignoreAlwaysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ignoreOncePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		textPanel.add(new JLabel("Τί να κάνω με την άγνωστη τοποθεσία;"));
		

		buttonGroup = new ButtonGroup();
	
		String[] emptyString = { "                               " };
		addRadio = new JRadioButton();
		addLocationLabel1 = new JLabel("Η περιοχή ανήκει στο ΤΤΛΠ: ");
		
		PopupMenuListener popupMenuListener = new PopupMenuListener(){
			public void popupMenuWillBecomeVisible(PopupMenuEvent pme){
				addRadio.setSelected(true);
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent pme){
				
			}
			public void popupMenuCanceled(PopupMenuEvent pme){
	
			}
		};
		ttlpComboBox = new JComboBox<Ttlp>(parentDataRetriever.getTtplStructure());
		ttlpComboBox.addPopupMenuListener(popupMenuListener);
		ttlpComboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(parentDataRetriever.getTyofTtlp(ttlpComboBox.getSelectedItem().toString()));
				//tyComboBox.setModel(model);
				Ttlp selectedTtlp = (Ttlp) ttlpComboBox.getSelectedItem();
				Ttlp.IslandTy[] tyArray = new Ttlp.IslandTy[selectedTtlp.getListOfTys().size()];
				selectedTtlp.getListOfTys().toArray(tyArray);
				System.out.println(tyArray.length);
				for(int i=0;i<tyArray.length;i++)
					System.out.println(tyArray[i]);
				
				DefaultComboBoxModel<Ttlp.IslandTy> newModel = new DefaultComboBoxModel<Ttlp.IslandTy>(tyArray);
				tyComboBox.setModel(newModel);
			}
		});
		tyComboBox = new JComboBox<Ttlp.IslandTy>();
		tyComboBox.addPopupMenuListener(popupMenuListener);
		
		ignoreOnceRadio = new JRadioButton();
		ignoreAlwaysRadio = new JRadioButton();
		
		
		buttonGroup.add(addRadio);
		buttonGroup.add(ignoreAlwaysRadio);
		buttonGroup.add(ignoreOnceRadio);
		
		addLocationPanel.add(addRadio);
		addLocationPanel.add(addLocationLabel1);
		addLocationPanel.add(ttlpComboBox);
		addLocationLabel2 = new JLabel(", ΤΥ: ");
		addLocationPanel.add(addLocationLabel2);
		addLocationPanel.add(tyComboBox);
		addLocationPanel.addMouseListener(new MouseListener(){
				public void mousePressed(MouseEvent e) {
			    }

			    public void mouseReleased(MouseEvent e) {
			    }

			    public void mouseEntered(MouseEvent e) {
			    }

			    public void mouseExited(MouseEvent e) {
			    }

			    public void mouseClicked(MouseEvent e) {
			    	addRadio.setSelected(true);
			    }
		});
		
		ignoreAlwaysPanel.add(ignoreAlwaysRadio);
		ignoreAlwaysLabel = new JLabel("Να αγνοούνται πάντα εγγραφές με αυτή την ένδειξη τοποθεσίας.");
		ignoreAlwaysPanel.add(ignoreAlwaysLabel);
		ignoreAlwaysPanel.addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e) {
		    }

		    public void mouseReleased(MouseEvent e) {
		    }

		    public void mouseEntered(MouseEvent e) {
		    }

		    public void mouseExited(MouseEvent e) {
		    }

		    public void mouseClicked(MouseEvent e) {
		    	ignoreAlwaysRadio.setSelected(true);
		    }
		});
		//ignoreAlwaysPanel.add(new JLabel());
		
		ignoreOncePanel.add(ignoreOnceRadio);
		ignoreOnceLabel = new JLabel("Να αγνοηθεί μόνο αυτή τη φορά.");
		ignoreOncePanel.add(ignoreOnceLabel);
		ignoreOncePanel.addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e) {
		    }

		    public void mouseReleased(MouseEvent e) {
		    }

		    public void mouseEntered(MouseEvent e) {
		    }

		    public void mouseExited(MouseEvent e) {
		    }

		    public void mouseClicked(MouseEvent e) {
		    	ignoreOnceRadio.setSelected(true);
		    }
		});
		
		okButton = new JButton("Ok");
		buttonPanel.add(okButton);
		mainPanel.add(textPanel);
		mainPanel.add(addLocationPanel);
		mainPanel.add(ignoreAlwaysPanel);
		mainPanel.add(ignoreOncePanel);
		mainPanel.add(buttonPanel);
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
	//private JComboBox<String> ttlpComboBox, tyComboBox;
	private JComboBox<Ttlp> ttlpComboBox;
	private JComboBox<Ttlp.IslandTy> tyComboBox;
	private JLabel addLocationLabel1, addLocationLabel2, ignoreAlwaysLabel, ignoreOnceLabel;
}
