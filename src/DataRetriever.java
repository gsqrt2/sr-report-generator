import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

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
	public DataRetriever(File xlsxFile){
		dataFile = xlsxFile;
		readStructure();
		//readXlsxData();
		
	}
	
	private void readXlsxData()
	{
		try{
			FileInputStream fis = new FileInputStream(dataFile);
			XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            
            Iterator<Row> itr = sheet.iterator();
            System.out.println(sheet.getRow(0).getCell(0).toString());
            //Iterating over Excel file in Java
          /*  while (itr.hasNext()) {
            	Row row = itr.next();
            	
                //Iterating over each column of Excel file
                Iterator<Cell> cellIterator = row.cellIterator();
                if(headersRow)
                {
                	System.out.println("Headers:");
                	while(cellIterator.hasNext()) {
	                    Cell cell = cellIterator.next();
	                    System.out.println(cell.toString());
                	}
                	headersRow = false;
                }
                else
                {
                	//System.out.println("Values:");
	                while(cellIterator.hasNext()) {
	                    Cell cell = cellIterator.next();
	                    //System.out.println(cell.toString()); 
	               }
                }
            }*/
        }
		catch(IOException ioe)
		{
			System.out.println("IOException trying to create fis from xlsx file.");
		}
	}
	
	private void readStructure()
	{
		try{
			File inputFile = new File("data/structure.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         Element rootElement = doc.getDocumentElement();
	         Node cleanRootElement = clean(rootElement);
	         NodeList ttlps = cleanRootElement.getChildNodes();
	         islandToTtlp = new HashMap<String,String>();
	         for(int i=0;i<ttlps.getLength();i++)
	         {
	        	 Element currentTtlpElement = (Element) ttlps.item(i);
	        	 System.out.println(currentTtlpElement.getAttribute("name"));
	        	 String ttlpName = currentTtlpElement.getAttribute("name");
	        	 NodeList tys = currentTtlpElement.getChildNodes();
	        	 for(int j=0;j<tys.getLength();j++)
	        	 {
	        		 Node currentTyElement = tys.item(j);
	        		 System.out.println("-> "+currentTyElement.getFirstChild().getTextContent());
	        		 String islandName = currentTyElement.getFirstChild().getTextContent();
	        		 islandToTtlp.put(islandName, ttlpName);
	        	 }
	        	 
	         }
	         System.out.println(islandToTtlp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception reading structure: "+e);
		}
	}
	
	private void readAdditinalLocations()
	{
		try
		{
			
			
		}
		catch(Exception e)
		{
			System.out.println("Exception reading additional locaions: "+e);
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
	
	private File dataFile, structureXmlFile;
	private boolean headersRow = true;
	private NodeList ttlpList;
	private HashMap islandToTtlp;
}
