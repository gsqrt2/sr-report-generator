import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.*;
import javax.swing.DefaultComboBoxModel;


public class UnknownDataDialog extends JDialog{
	public UnknownDataDialog(String dataString, String dataType, DataRetriever parentDataRetriever)
	{
		this.setModal(true);
		this.dataString = dataString;
		this.parentDataRetriever = parentDataRetriever;
		thisDialog = this;

		
		if(dataType == "location")
		{
			this.setTitle("������� �������: '"+dataString+"'");
			initLocationGui();		
		}
		else
		if(dataType == "taskType")
		{
			this.setTitle("�������� ����� ��������: '"+dataString+"'");		
			initTaskTypeGui();
		}
	}
	
	private void initTaskTypeGui()
	{
		this.setResizable(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		chooseTaskTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel unknownTaskTypeLabel = new JLabel("�������� ��������� ��� �������������� �� �������� ����� '"+dataString+"'");
		textPanel.add(unknownTaskTypeLabel);
		
		String[] taskTypeArray = {"�������� ���� ��������:", "�� ���������� ����� �� ��������", "���������", "�����"};
		taskTypeComboBox = new JComboBox<String>(taskTypeArray);
		taskTypeComboBox.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(taskTypeComboBox.getSelectedIndex() == 0)
                        {
                        	okButton.setEnabled(false);
                        }
                        else
                        {
                        	okButton.setEnabled(true);
                        }
                    }
                }            
        );
		chooseTaskTypePanel.add(taskTypeComboBox);
		
		mainPanel.add(textPanel);
		mainPanel.add(chooseTaskTypePanel);
		
		okButton = new JButton("����������");
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(taskTypeComboBox.getSelectedIndex() == 1)
				{
					parentDataRetriever.addUnknownTaskType(dataString, "ignore", true);
					System.out.println("always ignore");
				}
				else
				{
					parentDataRetriever.addUnknownTaskType(dataString, taskTypeComboBox.getSelectedItem().toString(), true);
					System.out.println("count as: "+taskTypeComboBox.getSelectedItem().toString());
				}
				
				//thisDialog.setVisible(false);
				//thisDialog.dispose();
			}
		});
		
		ignoreButton = new JButton("��� ����");
		ignoreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				parentDataRetriever.addUnknownTaskType(dataString, "ignore", false);
				//thisDialog.setVisible(false);
				//thisDialog.dispose();
				System.out.println("ignore once");
			}
		});
		
		buttonPanel.add(okButton);
		buttonPanel.add(ignoreButton);
		
		mainPanel.add(buttonPanel);

		this.add(mainPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void initLocationGui()
	{
		this.setResizable(false);
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		chooseLocationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		Ttlp dummyTtlp = new Ttlp("�������� ����:");
		Ttlp ignoreTtlp = new Ttlp("�� ��������� ����� ���� � �������");
		Ttlp.IslandTy dummyTy = dummyTtlp.addTy("�������� �/�:");
		Ttlp.IslandTy[] tyArray = new Ttlp.IslandTy[1];
		tyArray[0] = dummyTy;
		Ttlp[] ttlpArray = parentDataRetriever.getTtplStructure();
		
		Ttlp[] finalTtlpArray = new Ttlp[ttlpArray.length+2];
		finalTtlpArray[0] = dummyTtlp;
		finalTtlpArray[1] = ignoreTtlp;
		for(int i=2;i<finalTtlpArray.length;i++)
			finalTtlpArray[i] = ttlpArray[i-2];

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel unknownLocationLabel = new JLabel("�������� ��������� �� ���� �/� ������ � ������� ������� '"+dataString+"':");
		Border border = unknownLocationLabel.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		unknownLocationLabel.setBorder(new CompoundBorder(border, margin));
		textPanel.add(unknownLocationLabel);
		
		
		ttlpComboBox = new JComboBox<Ttlp>(finalTtlpArray);
		ttlpComboBox.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    	
                    	Ttlp.IslandTy dummyTy = dummyTtlp.addTy("�������� �/�");
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
                        	//System.out.println(selectedTtlp.toString());
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
	
		
		okButton = new JButton("����������");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(ttlpComboBox.getSelectedIndex() == 1)
				{
					parentDataRetriever.addUnknownLocation(dataString, "ignore", true);
				}
				else
				{
					parentDataRetriever.addUnknownLocation(dataString, tyComboBox.getSelectedItem().toString(), true);
				}
				//System.out.println(tyComboBox.getSelectedItem().toString());
				
				thisDialog.setVisible(false);
				thisDialog.dispose();
			}
		});
		okButton.setEnabled(false);
		ignoreButton = new JButton("��� ����");
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
	
	
	
	private String dataString;
	private JPanel textPanel, mainPanel, buttonPanel, chooseLocationPanel, chooseTaskTypePanel;
	private JButton okButton, ignoreButton;
	private DataRetriever parentDataRetriever;
	private JComboBox<Ttlp> ttlpComboBox;
	private JComboBox<Ttlp.IslandTy> tyComboBox;
	private JComboBox<String> taskTypeComboBox;
	private UnknownDataDialog thisDialog;


}
