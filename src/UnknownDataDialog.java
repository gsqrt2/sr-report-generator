import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.DefaultComboBoxModel;


public class UnknownDataDialog extends JDialog{
	public UnknownDataDialog(String dataString, String dataType, DataRetriever parentDataRetriever)
	{
		this.setModal(true);
		this.dataString = dataString;
		this.dataType = dataType;
		this.parentDataRetriever = parentDataRetriever;
		thisDialog = this;

		
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
		
		Ttlp dummyTtlp = new Ttlp("Επιλέξτε ΤΤΛΠ:");
		Ttlp ignoreTtlp = new Ttlp("Να αγνοείται πάντα αυτή η εγγραφή");
		Ttlp.IslandTy dummyTy = dummyTtlp.addTy("Επιλέξτε Τ/Υ:");
		Ttlp.IslandTy[] tyArray = new Ttlp.IslandTy[1];
		tyArray[0] = dummyTy;
		Ttlp[] ttlpArray = parentDataRetriever.getTtplStructure();
		
		Ttlp[] finalTtlpArray = new Ttlp[ttlpArray.length+2];
		finalTtlpArray[0] = dummyTtlp;
		finalTtlpArray[1] = ignoreTtlp;
		for(int i=2;i<finalTtlpArray.length;i++)
			finalTtlpArray[i] = ttlpArray[i-2];

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		textPanel.add(new JLabel("Παρακαλώ καθορίστε σε ποιά Τ/Υ ανήκει η άγνωστη περιοχή '"+dataString+"':"));
		
		ttlpComboBox = new JComboBox<Ttlp>(finalTtlpArray);
		ttlpComboBox.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    	
                    	Ttlp.IslandTy dummyTy = dummyTtlp.addTy("Επιλέξτε Τ/Υ");
                    	Ttlp.IslandTy[] tyArray = new Ttlp.IslandTy[1];
                		tyArray[0] = dummyTy;
                		DefaultComboBoxModel<Ttlp.IslandTy> dummyModel = new DefaultComboBoxModel<Ttlp.IslandTy>(tyArray);
                    		
                        if(ttlpComboBox.getSelectedIndex() == 0)
                        {
                        	tyComboBox.setModel(dummyModel);
                        	tyComboBox.setEnabled(false);
                        	okButton.setEnabled(false);
                        }
                        else
                        if(ttlpComboBox.getSelectedIndex() == 1)
                        {
                        	tyComboBox.setModel(dummyModel);
                        	tyComboBox.setEnabled(false);
                        	okButton.setEnabled(true);
                        }
                        else
                        {
                        	Ttlp selectedTtlp = (Ttlp) ttlpComboBox.getSelectedItem();
                        	System.out.println(selectedTtlp.toString());
                        	DefaultComboBoxModel model = new DefaultComboBoxModel(selectedTtlp.getListOfTys().toArray());
                        	tyComboBox.setModel(model);
                        	tyComboBox.setEnabled(true);
                        	okButton.setEnabled(true);
                        }
                    }
                }            
        );
		chooseLocationPanel.add(ttlpComboBox);
		
		
		tyComboBox = new JComboBox<Ttlp.IslandTy>(tyArray);
		tyComboBox.setEnabled(false);
		chooseLocationPanel.add(tyComboBox);
	
		
		okButton = new JButton("Καταχώρηση");
		okButton.setEnabled(false);
		ignoreButton = new JButton("Όχι τώρα");
		ignoreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				parentDataRetriever.addUnknownLocation(dataString, "ignore", false);
				thisDialog.setVisible(false);
				thisDialog.dispose();
			}
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(ignoreButton);
		mainPanel.add(textPanel);
		mainPanel.add(chooseLocationPanel);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
		//mainPanel.setPreferredSize(new Dimension(550,250));

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	}
	
	private void addToLocations()
	{
		
	}
	
	
	
	private String dataString, dataType;
	private JPanel textPanel, mainPanel, buttonPanel, chooseLocationPanel;
	private JButton okButton, ignoreButton;
	private DataRetriever parentDataRetriever;
	private JComboBox<Ttlp> ttlpComboBox;
	private JComboBox<Ttlp.IslandTy> tyComboBox;
	private UnknownDataDialog thisDialog;


}
