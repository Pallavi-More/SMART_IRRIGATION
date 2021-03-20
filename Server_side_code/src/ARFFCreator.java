import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ARFFCreator 
{

	public static String generateARFFFile(String planttype, int age, String soiltype,
			float temp, float humidity, float moisture ) {

		try
		{			
		    String srcFilePath = "F://water-unknown-template.arff";
		    String destFilePath = "F://"+System.currentTimeMillis()+".arff";

		    File srcFile = new File(srcFilePath);
		    File destFile = new File(destFilePath);
		    
		    Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		    	    		    
		    FileWriter fw = new FileWriter(destFilePath,true); //the true will append the new data
		    
		    String newLine = planttype.toLowerCase() + "," 
		    			   + age + "," 
		    			   + soiltype.toLowerCase() + "," 
		    			   + temp + ","
		    			   + humidity + ","
		    			   + moisture + ",?";
		    			   
		    fw.write(newLine);//appends the string to the file
		    fw.close();
		    return destFilePath;
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
		return "";
		
	}
}


