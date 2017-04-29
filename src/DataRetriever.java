import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	
	public long getLength(){
		return dataFile.length();
	}
	
	private File dataFile;
	private boolean headersRow = true;
}
