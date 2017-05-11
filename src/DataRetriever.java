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
import java.awt.Color;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
		openDialog();
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
		dialogFrame.setLocationRelativeTo(null);
		dialogFrame.setUndecorated(true);
		JPanel dialogPanel = new JPanel();
		dialogPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		dialogText = new JLabel("This is the dialog text");
		dialogPanel.add(dialogText);
		dialogFrame.add(dialogPanel);
		
		dialogFrame.pack();
		dialogFrame.setVisible(true);
		
	}
	
	public void retrieveData(File xlsxFile)
	{
		dataFile = xlsxFile;
		readXlsxData();
	}
	
	private void readXlsxData()
	{
		try{
			FileInputStream fis = new FileInputStream(dataFile);
			XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            int currentRow = 1;
            System.out.println("sheet size: "+sheet.getLastRowNum());
            
            
            Iterator<Row> itr = sheet.iterator();
            //System.out.println(sheet.getRow(0).getCell(0).toString());
            //Iterating over Excel file in Java
            while (itr.hasNext() && validXlsxFormat) {
            	Row row = itr.next();

                if(headersRow)
                {
                	checkXlsxHeaders(row);
                	headersRow = false;
                }
                else
                {                	
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
				case "ÍÇÓÉ": 
					islandCol = cellNo;
					break;
					
				case "ÇÌ/ÍÉÁ ÑÁÍÔÅÂÏÕ":
					appointmentDateCol = cellNo;
					break;
					
				case "Åñãáóßá":
					taskTypeCol = cellNo;
					break;
				
				case "ÊáôÜóôáóç":
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

}
