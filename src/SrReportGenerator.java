import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;


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
		filePathTextField = new JTextField("file path here");
		filePathTextField.setColumns(50);
		chooseFileButton = new JButton("Select File");
		filePanel.add(filePathTextField);
		filePanel.add(chooseFileButton);
		
		/*Assemble main panel*/
		mainPanel.add(filePanel,  BorderLayout.PAGE_START);
		
		
		/*Show GUI*/
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	 private JFrame frame;
	 private JPanel mainPanel, filePanel;
	 private JButton chooseFileButton;
	 private JTextField filePathTextField;
}
