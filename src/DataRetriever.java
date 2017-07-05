import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
//import com.sun.xml.internal.ws.api.Component;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DataRetriever {
	public DataRetriever(SrReportGenerator object){
		parentObject = object;
		islandToTtlpHash = new HashMap<String,Ttlp>();
		additionalLocationsHash = new HashMap<String,String>();
		ttlpHash = new HashMap<String,Ttlp>();
		ttlpArraylist = new ArrayList<Ttlp>();
		parentObject.setStatus("Εισαγωγή βασικών δομών...");
		
		readStructure();
		readAdditinalLocations();
		readTaskTypes();
		parentObject.setStatus("Status");
	}
	

	
	public void retrieveData(File xlsxFile)
	{
		dataFile = xlsxFile;

		Thread thread = new Thread(new Runnable(){
			 public void run()
			 {
				 parentObject.disableActions(true);
				 parentObject.loadParserProgress();
				 readXlsxData();
				 parentObject.disableActions(false);
				 parentObject.setStatus("Status");
				 if(success)
				 {
					 parentObject.handInRetrievedArraylist(ttlpArraylist);
					 parentObject.showReport();
				 }
				 else
				 {
					 parentObject.resetResultPanel();
				 }
				 success = true;
			 }
		 });
		 
		 thread.start();
		
	}
	
	private void readXlsxData()
	{
		try{
			parentObject.setProgressBarValue(0);
			parentObject.setProgressBarString("Καταμέτρηση...");
			headersRow = true;
			FileInputStream fis = new FileInputStream(dataFile);
			XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            int currentRow = 1;
            int allRows = sheet.getLastRowNum()+1;
            parentObject.setProgressBarIndeterminate(false);
            parentObject.setProgressBarMinimumValue(0);
            parentObject.setProgressBarMaximumValue(allRows);
            //System.out.println("sheet size: "+sheet.getLastRowNum());
            
            Iterator<Row> itr = sheet.iterator();
          
            do {
            	Row row = itr.next();
            	Thread.sleep(5);
                if(headersRow)
                {
                	parentObject.setProgressBarValue(currentRow);
                	parentObject.setProgressBarString("Έλεγχος κεφαλίδων...");
                	//System.out.println("checking headers");
                	checkXlsxHeaders(row);
                	//System.out.println("checked headers: "+validXlsxFormat);
                	headersRow = false;
                }
                else
                {   
                	ignoreRecord = false;
                	parentObject.setProgressBarValue(currentRow);
                	parentObject.setProgressBarString("Εγγραφή "+currentRow+" από "+allRows);
                	/*ta mdf kai ta pedia metrane gia mia i gia dyo tasks? 
                	 * xreiazomai paradeigma apo alloy eidoys douleies opws syndyastika rantevou, ipvpn kai kalwdiakes an metrane*/
                	
                	/* CHECK LOCATION AND HANDLE IF UNRECOGNIZED*/

                	String currentIsland = (String) row.getCell(islandCol).toString();
                	Ttlp currentTtlp = null;
                	String currentTy = "";
                	
                	
                	if(islandToTtlpHash.get(currentIsland) != null)
                	{
                		currentTtlp =  islandToTtlpHash.get(currentIsland);
                		currentTy = currentIsland;
                		//System.out.println("found ttlp from object:"+currentTtlp+", ty: "+currentIsland);
                	}
                	else
                	{
                		//System.out.println("not a known location: "+currentIsland);
                		if(additionalLocationsHash.get(currentIsland) != null)
                		{
                			//System.out.println(currentIsland+" found in "+additionalLocationsHash.get(currentIsland));
                			String currentTyFromAdditional = additionalLocationsHash.get(currentIsland);
                			if(additionalLocationsHash.get(currentIsland) != "ignore")
                			{
                				if(islandToTtlpHash.get(currentTyFromAdditional) != null)
                				{
                					currentTtlp = islandToTtlpHash.get(currentTyFromAdditional);
                					currentTy = currentTyFromAdditional;
                					//System.out.println("found ttlp from ADDITIONAL object:"+currentTtlp+", ty: "+currentTyFromAdditional);
                				}
                				else
                				{
                					/*to parent apo to additional location entry de vrethike sto main structure*/
                					ignoreRecord = true;
                					System.out.println("Η περιοχή '"+currentTyFromAdditional+"' δεν ανήκει στη βασική δομή.");
                				}
                			}
                			else
                			{
                				ignoreRecord = true;
                			}
                		}
                		else
                		{
                			//System.out.println("handle uknown location: "+currentIsland);
                			unknownDataDialog = new UnknownDataDialog(currentIsland, "location", this);
                			ignoreRecord = true;
                			/*NA TO VGALW OTAN FTIAKSW TO HANDLE*/
                		}
                		
                	}
                		//if(!ignoreRecord)
                			//System.out.println("checking task type for ttlp: "+currentTtlp.toString()+", ty: "+currentTy);
                	
                	/* CHECK TASK TYPE AND HANDLE IF UNRECOGNIZED*/
                	if(!ignoreRecord)
                	{
	                	String currentTaskTypeDescription = (String) row.getCell(taskTypeCol).toString();
	                	String currentTaskType = (String) taskTypesHash.get(currentTaskTypeDescription);
	                	
	                	if(currentTaskType.equals("connection"))
	                	{
	                		//System.out.println("new connection");
	                		//System.out.println("requesting ty object for :"+currentTy);
	                		Ttlp.IslandTy currentIslandTyObject = currentTtlp.getTyByName(currentTy);
	                		currentIslandTyObject.increaseConnections();
	                		if(currentTy == "ΠΟΡΟΣ - ΜΕΘΑΝΑ")
	                			System.out.println("poros methana at :"+currentRow);
	                	}
	                	else
	                	if(currentTaskType.equals("service"))
	                	{
	                		//System.out.println("new service");
	                		Ttlp.IslandTy currentIslandTyObject = currentTtlp.getTyByName(currentTy);
	                		currentIslandTyObject.increaseServices();
	                		//if(currentTy.toString().equals("ΠΟΡΟΣ - ΜΕΘΑΝΑ"))
	                			//System.out.println("poros - methana service at :"+currentRow);
	                	}
	                	else
	                	if(currentTaskType.equals("ignore"))
	                	{
	                		//System.out.println("IGNORED!!!!");
	                	}
	                	else
	                	{
	                		System.out.println("Unrecognized task type: '"+currentTaskType+"', at row "+currentRow);
	                		//new UnknownDataDialog(currentTaskType, "taskType", this);
	                	}
                	}
                }
                
                currentRow++;
            }while (itr.hasNext() && validXlsxFormat);
            book.close();
            
            if(!validXlsxFormat)
            {
            	JOptionPane.showMessageDialog(null, "Η δομή του αρχείου δεν είναι η αναμένόμενη, παρακαλώ επιλέξτε κατάλληλο αρχείο.");
            	success = false;
            	validXlsxFormat = true;
            }

        }
		catch(IOException ioe)
		{
			System.out.println("IOException trying to create fis from xlsx file.");
		}
		catch(InterruptedException e)
		{
			System.out.println("inerrupted exception");
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
	        	
	        	 Ttlp currentTtlp = new Ttlp(ttlpName);
	        	 ttlpArraylist.add(currentTtlp);
	        	 ttlpHash.put(ttlpName, currentTtlp);
	        	 NodeList tys = currentTtlpElement.getChildNodes();
	        	 for(int j=0;j<tys.getLength();j++)
	        	 {
	        		 Node currentTyElement = tys.item(j);
	        		 //System.out.println("-> "+currentTyElement.getFirstChild().getTextContent());
	        		 String islandName = currentTyElement.getFirstChild().getTextContent();
	        		 currentTtlp.addTy(islandName);
	        		 islandToTtlpHash.put(islandName, currentTtlp);
	        	 }
	        	 
	         }
	         //System.out.println(islandToTtlp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception reading structure: "+e);
			success = false;
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
	        	 //System.out.println(currentElement.getAttribute("name")+" -> "+currentElement.getAttribute("parent"));
	        	 additionalLocationsHash.put(currentElement.getAttribute("name"), currentElement.getAttribute("parent"));
	         }
			
		}
		catch(Exception e)
		{
			System.out.println("Exception reading additional locaions: "+e);
		}
	}
	
	public void addUnknownLocation(String newLocation, String parent, boolean permenant)
	{
		additionalLocationsHash.put(newLocation, parent);
		System.out.println("ignored "+newLocation);
		if(permenant)
		{
			Node additionalLocationRootNode = getRootNodeFromXml("additional.xml");
			Document doc = additionalLocationRootNode.getOwnerDocument();
			Element newLocationElement = doc.createElement("location");
			newLocationElement.setAttribute("name", newLocation);
			newLocationElement.setAttribute("parent", "ignore");
			additionalLocationRootNode.appendChild(newLocationElement);
			
			try{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("data/additional.xml"));
				transformer.transform(source, result);
			}catch(TransformerException tfe){System.out.println("Xml write TransformerException: "+tfe);}
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
			success = false;
			return null;
		}
	}
	
	private void checkXlsxHeaders(Row headersRow)
	{
		islandCol = null;
		appointmentDateCol = null;
		taskTypeCol = null;
		statusCol = null;
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
	

	public Ttlp[] getTtplStructure(){

		Ttlp[] ttlpArray = new Ttlp[ttlpArraylist.size()];
		ttlpArraylist.toArray(ttlpArray);
		
		return ttlpArray;
	}
	




	private SrReportGenerator parentObject;

	private File dataFile;
	private boolean headersRow = true, validXlsxFormat = true, ignoreRecord = false, success = true;
	private HashMap<String,String> additionalLocationsHash, taskTypesHash;
	private HashMap<String,Ttlp> islandToTtlpHash;
	private Integer islandCol, appointmentDateCol, taskTypeCol, statusCol;
	private ArrayList<Ttlp> ttlpArraylist;
	private HashMap<String, Ttlp> ttlpHash;
	private UnknownDataDialog unknownDataDialog;



}
