import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class DataRetriever {
	public DataRetriever(File xlsxFile){
		dataFile = xlsxFile;
		try{
			FileInputStream fis = new FileInputStream(dataFile);
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
}
