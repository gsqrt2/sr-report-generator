import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.ws.api.Component;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class DataRetriever {
	public DataRetriever(JFrame parentFrame){
		appFrame = parentFrame;
		dataRetrieverDialog = new JDialog();
		islandToTtlpHash = new HashMap<String,String>();
		additionalLocationsHash = new HashMap<String,String>();
		
		readStructure();
		readAdditinalLocations();
		readTaskTypes();
	}
	
	private void openDialog()
	{
		dialogFrame = new JDialog(appFrame, true);
		dialogFrame.setLocation((appFrame.getLocation().x +350), (appFrame.getLocation().y + 200));
		dialogFrame.setUndecorated(true);
		JPanel dialogPanel = new JPanel();
		Border margin = new EmptyBorder(10,10,10,10);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		dialogPanel.setBorder(new CompoundBorder(border, margin));
	
		parserProgressBar = new JProgressBar();
		parserProgressBar.setPreferredSize(new Dimension(300,20));
		parserProgressBar.setStringPainted(true);
		
		dialogPanel.add(parserProgressBar);
		dialogFrame.add(dialogPanel);
		dialogFrame.pack();
	
		parserThread = new Thread(new Runnable(){
			public void run(){
				readXlsxData();
			}
		});
		parserThread.start();
		dialogFrame.setVisible(true);
		
		System.out.println("before dispose");
		
		
		
	}
	
	public void retrieveData(File xlsxFile)
	{
		dataFile = xlsxFile;
		openDialog();
		
	}
	
	private void readXlsxData()
	{
		try{
			
            parserProgressBar.setValue(0);
			parserProgressBar.setString("Ανάγνωση αρχείου δεδομένων...");
			FileInputStream fis = new FileInputStream(dataFile);
			XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            int currentRow = 1;
            int allRows = sheet.getLastRowNum()+1;
            parserProgressBar.setMinimum(0);
            parserProgressBar.setMaximum(allRows);
            //System.out.println("sheet size: "+sheet.getLastRowNum());
            
            Iterator<Row> itr = sheet.iterator();
          
            while (itr.hasNext() && validXlsxFormat) {
            	Row row = itr.next();

                if(headersRow)
                {
                	parserProgressBar.setValue(currentRow);
                	parserProgressBar.setString("Έλεγχος κεφαλίδων...");
                	checkXlsxHeaders(row);
                	headersRow = false;
                }
                else
                {   
                	parserProgressBar.setValue(currentRow);
                	parserProgressBar.setString("Επεξεργασία εγγραφής "+currentRow+" από "+allRows);
                	/*ta mdf kai ta pedia metrane gia mia i gia dyo tasks? 
                	 * xreiazomai paradeigma apo alloy eidoys douleies opws syndyastika rantevou, ipvpn kai kalwdiakes an metrane*/
                	
                	/* CHECK LOCATION AND HANDLE IF UNRECOGNIZED*/
                	
                	String currentIsland = (String) row.getCell(islandCol).toString();
                	
                	
                	if(islandToTtlpHash.get(currentIsland) != null)
                	{
                		
                	}
                	else
                	{
                		//System.out.println("not a known location: "+currentIsland);
                		if(additionalLocationsHash.get(currentIsland) != null)
                		{
                			System.out.println(currentIsland+" found in "+additionalLocationsHash.get(currentIsland));
                		}
                		else
                		{
                			System.out.println("handle uknown location");
                		}
                		
                	}
                	
                			
                	/* CHECK TASK TYPE AND HANDLE IF UNRECOGNIZED*/		
                	String currentTaskTypeDescription = (String) row.getCell(taskTypeCol).toString();
                	String currentTaskType = (String) taskTypesHash.get(currentTaskTypeDescription);
                	
                	if(currentTaskType.equals("connection"))
                	{
                		//System.out.println("new connection");
                	}
                	else
                	if(currentTaskType.equals("service"))
                	{
                		//System.out.println("new service");
                	}
                	else
                	if(currentTaskType.equals("ignore"))
                	{
                		//System.out.println("IGNORED!!!!");
                	}
                	else
                	{
                		System.out.println("Unrecognized task type: '"+currentTaskType+"', at row "+currentRow);
                	}			
                }
                
                currentRow++;
            }
            book.close();
            dialogFrame.dispose();
        }
		catch(IOException ioe)
		{
			System.out.println("IOException trying to create fis from xlsx file.");
		}
	}
	
	private void readStructure()
	{
		try{
			 Node structureRootNode = getRootNodeFromXml("structure.xml");
	         NodeList ttlps = structureRootNode.getChildNodes();
	         
	         for(int i=0;i<ttlps.getLength();i++)
	         {
	        	 Element currentTtlpElement = (Element) ttlps.item(i);
	        	 //System.out.println(currentTtlpElement.getAttribute("name"));
	        	 String ttlpName = currentTtlpElement.getAttribute("name");
	        	 NodeList tys = currentTtlpElement.getChildNodes();
	        	 for(int j=0;j<tys.getLength();j++)
	        	 {
	        		 Node currentTyElement = tys.item(j);
	        		 //System.out.println("-> "+currentTyElement.getFirstChild().getTextContent());
	        		 String islandName = currentTyElement.getFirstChild().getTextContent();
	        		 islandToTtlpHash.put(islandName, ttlpName);
	        	 }
	        	 
	         }
	         //System.out.println(islandToTtlp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception reading structure: "+e);
		}
	}
	
	private void readTaskTypes()
	{
		Node taskTypesRootNode = getRootNodeFromXml("taskTypes.xml");
		NodeList taskTypeNodes = taskTypesRootNode.getChildNodes();
		taskTypesHash = new HashMap<String, String>();
		for(int i=0;i<taskTypeNodes.getLength();i++)
		{
			Element currentTaskTypeElement = (Element) taskTypeNodes.item(i);
			//System.out.println(currentTaskTypeElement.getAttribute("name"));
			taskTypesHash.put(currentTaskTypeElement.getAttribute("name"), currentTaskTypeElement.getAttribute("type"));
		}	
	}
	
	private void readAdditinalLocations()
	{
		try
		{
			
			 Node additionalLocationRootNode = getRootNodeFromXml("additional.xml");
	         NodeList locations = additionalLocationRootNode.getChildNodes();
	         for(int i=0;i<locations.getLength();i++)
	         {
	        	 Element currentElement = (Element) locations.item(i);
	        	 System.out.println(currentElement.getAttribute("name")+" -> "+currentElement.getAttribute("parent"));
	        	 additionalLocationsHash.put(currentElement.getAttribute("name"), currentElement.getAttribute("parent"));
	         }
			
		}
		catch(Exception e)
		{
			System.out.println("Exception reading additional locaions: "+e);
		}
	}
	
	
	
	private Node getRootNodeFromXml(String xmlFileName)
	{
		try
		{
			File inputFile = new File("data/"+xmlFileName);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         Element rootElement = doc.getDocumentElement();
	         Node cleanRootElement = clean(rootElement);
	         
	         return cleanRootElement;
		}
		catch(Exception e)
		{
			System.out.println("Exception reading xml file "+xmlFileName+": "+e);
			return null;
		}
	}
	
	private void checkXlsxHeaders(Row headersRow)
	{
		Iterator<Cell> cellIterator = headersRow.cellIterator();
		int cellNo = 0;
		while(cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String currentColumnTitle = cell.getStringCellValue();
			switch(currentColumnTitle)
			{
				case "ΝΗΣΙ": 
					islandCol = cellNo;
					break;
					
				case "ΗΜ/ΝΙΑ ΡΑΝΤΕΒΟΥ":
					appointmentDateCol = cellNo;
					break;
					
				case "Εργασία":
					taskTypeCol = cellNo;
					break;
				
				case "Κατάσταση":
					statusCol = cellNo;
					break;
			}
			cellNo++;
		}
		//System.out.println(""+islandCol+"-"+appointmentDateCol+"-"+taskTypeCol+"-"+statusCol);
		if((islandCol == null) || (appointmentDateCol == null) || (taskTypeCol == null) || (statusCol == null))
		{
			validXlsxFormat = false;
			System.out.println("Xlsx headers not recognized.");
		}
	}
	
	
	private Node clean(Node node)
	 {
	   NodeList childNodes = node.getChildNodes();

	   for (int n = childNodes.getLength() - 1; n >= 0; n--)
	   {
	      Node child = childNodes.item(n);
	      short nodeType = child.getNodeType();

	      if (nodeType == Node.ELEMENT_NODE)
	         clean(child);
	      else if (nodeType == Node.TEXT_NODE)
	      {
	         String trimmedNodeVal = child.getNodeValue().trim();
	         if (trimmedNodeVal.length() == 0)
	            node.removeChild(child);
	         else
	            child.setNodeValue(trimmedNodeVal);
	      }
	      else if (nodeType == Node.COMMENT_NODE)
	         node.removeChild(child);
	   }
	   
	   return node;
	 }
	
	private JLabel dialogText;
	private JDialog dialogFrame;
	private JFrame appFrame;
	private JDialog dataRetrieverDialog;
	private File dataFile, structureXmlFile;
	private boolean headersRow = true, validXlsxFormat = true;
	private NodeList ttlpList;
	private HashMap islandToTtlpHash, additionalLocationsHash, taskTypesHash;
	private Integer islandCol, appointmentDateCol, taskTypeCol, statusCol;
	private JProgressBar parserProgressBar;
	private Thread parserThread;

}
