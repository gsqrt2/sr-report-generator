import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import  java.awt.Dimension;

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
					  
					  if(dataRetriever == null)
					  {
						  dataRetriever = new DataRetriever(frame);
					  }
					  
					  statusLabel.setText("Ανάγνωση αρχείου δεδομένων...");
					  dataRetriever.retrieveData(fileToParse);
					  statusLabel.setText("Status");
					  
				  }
			  } 
			} );
		filePanel.add(filePathTextField);
		filePanel.add(chooseFileButton);
		
		/*init resultPanel*/
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(1000, 500));
		JLabel dummyResultLabel = new JLabel("result table here");
		resultPanel.add(dummyResultLabel);
		resultPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		
		/*init statusPanel*/
		statusPanel = new JPanel();
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusLabel = new JLabel("Status");
		statusPanel.add(statusLabel);
		
		/*Assemble main panel*/
		mainPanel.add(filePanel,  BorderLayout.PAGE_START);
		mainPanel.add(resultPanel, BorderLayout.CENTER);
		mainPanel.add(statusPanel, BorderLayout.PAGE_END);
		
		
		/*Show GUI*/
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			frame.getContentPane().add(mainPanel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setLocation(150, 100);
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println("Exception trying to show GUI: "+e);
		}
		
	}
	 
	 private void sendReport()
	 {
		 String htmlText = "<table border='1'>"
		 		+ "<tr>"
				 	+"<td>h1 <h1>text</h1></td>"
		 		+"</tr>"
		 		+ "</table>";
		 if(sendMailSSL == null)
		 {	 
			 sendMailSSL = new SendMailSSL();
		 }

		 sendMailSSL.sendMail(htmlText, "gsqrt2@gmail.com");
	 }

	 private JFrame frame;
	 private JPanel mainPanel, filePanel, resultPanel, statusPanel;
	 private JButton chooseFileButton;
	 private JTextField filePathTextField;
	 private JFileChooser fileChooser;
	 private File fileToParse;
	 private DataRetriever dataRetriever;
	 private SendMailSSL sendMailSSL;
	 private JLabel statusLabel;
}
