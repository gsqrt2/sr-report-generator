import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.Dimension;

import java.util.ArrayList;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

public class SrReportGenerator {

	public SrReportGenerator(){
		frameInit();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		srReportGenerator = new SrReportGenerator();
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
						  dataRetriever = new DataRetriever(srReportGenerator);
					  }

					  
					  dataRetriever.retrieveData(fileToParse);
				  }
			  } 
			} );
		filePanel.add(filePathTextField);
		filePanel.add(chooseFileButton);
		
		/*init resultPanel*/
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(1000, 500));
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
	 
	 public void loadParserProgress()
	 {

		 progressPanel = new JPanel();
		 
		 resultPanel.setLayout(new GridBagLayout());
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.anchor = GridBagConstraints.CENTER;
		 
		 Border margin = new EmptyBorder(10,10,10,10);
		 Border border = BorderFactory.createLineBorder(Color.BLACK);
		 progressPanel.setBorder(new CompoundBorder(border, margin));
		
		 parserProgressBar = new JProgressBar();
		 parserProgressBar.setPreferredSize(new Dimension(200, 20));
		 parserProgressBar.setStringPainted(true);
		 parserProgressBar.setIndeterminate(true);
		 progressPanel.add(parserProgressBar);
		 resultPanel.add(progressPanel);
		 resultPanel.revalidate();
	 }
	 
	 public void setProgressBarIndeterminate(Boolean indeterminate)
	 {
		 parserProgressBar.setIndeterminate(indeterminate);
	 }
	 
	 public void disableActions(Boolean disabled)
	 {
		 chooseFileButton.setEnabled(!disabled); 
	 }
	 
	 
	 public void setProgressBarString(String newString)
	 {
		parserProgressBar.setString(newString); 
	 }
	 
	 public void setProgressBarValue(int newValue)
	 {
		 parserProgressBar.setValue(newValue);
	 }
	 
	 public void setProgressBarMinimumValue(int newValue)
	 {
		 parserProgressBar.setMinimum(newValue);
	 }
	 
	 public void setProgressBarMaximumValue(int newValue)
	 {
		 parserProgressBar.setMaximum(newValue);
	 }
	 
	 public void setStatus(String newStatus)
	 {
		 statusLabel.setText(newStatus);
	 }
	 
	 public void showReport()
	 {
		 resultPanel.remove(progressPanel);
		 parserProgressBar = null;
		 progressPanel = null;
		 
		
		 for(int i=0;i<retrievedArraylist.size();i++)
		 {
			// System.out.println("ttlp no"+i+": "+retrievedArraylist.get(i));
			 Ttlp currentTtlp = retrievedArraylist.get(i);
			 ArrayList<Ttlp.IslandTy> tysOfTtlpArraylist = currentTtlp.getListOfTys();
			// System.out.println(tysOfTtlpArraylist.size());
			 for(int j=0;j<tysOfTtlpArraylist.size();j++)
			 {
				 Ttlp.IslandTy currentTy = tysOfTtlpArraylist.get(j);
				 System.out.println("Ty "+currentTy.toString()+": kataskeyes:"+currentTy.getNumOfConnections()+", vlaves"+currentTy.getNumOfServices());
			 }
		 }
		 
		 JLabel resultlabel = new JLabel("result here");
		 resultPanel.add(resultlabel);
		 resultPanel.updateUI();
	 }
	 
	 public void handInRetrievedArraylist(ArrayList<Ttlp> resultArraylist)
	 {
		 retrievedArraylist = new ArrayList<Ttlp>();
		 retrievedArraylist = resultArraylist;
	 }

	 
	 private JFrame frame;
	 private JPanel mainPanel, filePanel, resultPanel, statusPanel, progressPanel;
	 private JButton chooseFileButton;
	 private JTextField filePathTextField;
	 private JFileChooser fileChooser;
	 private File fileToParse;
	 private DataRetriever dataRetriever;
	 private SendMailSSL sendMailSSL;
	 private JLabel statusLabel;
	 private JProgressBar parserProgressBar;
	 private ArrayList<Ttlp> retrievedArraylist = null;
	 public static SrReportGenerator srReportGenerator;
}
