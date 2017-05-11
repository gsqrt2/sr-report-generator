import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

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
					  sendReport();
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
	 
	 private void sendReport()
	 {
		 sendMailSSL = new SendMailSSL();
		 sendMailSSL.sendMail("geia sou re mastora", "gmarios@ote.gr");
	 }

	 private JFrame frame;
	 private JPanel mainPanel, filePanel;
	 private JButton chooseFileButton;
	 private JTextField filePathTextField;
	 private JFileChooser fileChooser;
	 private File fileToParse;
	 private DataRetriever dataRetriever;
	 private SendMailSSL sendMailSSL;
}
