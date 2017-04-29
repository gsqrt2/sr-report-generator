import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;



public class SrReportGenerator {

	public SrReportGenerator(){
		frameInit();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new SrReportGenerator();
	}
	
	 private void frameInit(){
		frame = new JFrame("SR Report Generator");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		/* Build top panel*/
		filePanel = new JPanel(new FlowLayout());
		filePathTextField = new JTextField("Select xlsx file to parse");
		filePathTextField.setEnabled(false);
		filePathTextField.setColumns(50);
		chooseFileButton = new JButton("Select File");
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx files", "xlsx");
		fileChooser.setFileFilter(filter);
		chooseFileButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  int returnVal = fileChooser.showOpenDialog(frame);
				  if(returnVal == JFileChooser.APPROVE_OPTION) {
					  filePathTextField.setText(fileChooser.getSelectedFile().toString());
					  fileToParse = fileChooser.getSelectedFile();
					  dataRetriever = new DataRetriever(fileToParse);
					  System.out.println("length from dataRetriever: "+dataRetriever.getLength());
					  
				  }
			  } 
			} );
		filePanel.add(filePathTextField);
		filePanel.add(chooseFileButton);
		
		/*Assemble main panel*/
		mainPanel.add(filePanel,  BorderLayout.PAGE_START);
		
		
		/*Show GUI*/
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocation(250, 100);
		frame.setVisible(true);
		
	}
	 private JFrame frame;
	 private JPanel mainPanel, filePanel;
	 private JButton chooseFileButton;
	 private JTextField filePathTextField;
	 private JFileChooser fileChooser;
	 private File fileToParse;
	 private DataRetriever dataRetriever;
}
