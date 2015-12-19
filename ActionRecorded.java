package edu.tridenttech.cpt237.williams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionRecorded 
{	
	private static ActionRecorded recorder = null;
	private static String FILE_NAME = "CabRecords.csv";
	private File file;
	
	private ActionRecorded(String file){};
		
	public static ActionRecorded getRecorder()
	{
			if(recorder == null)
			{
				recorder = new ActionRecorded(FILE_NAME);
			}
			
		return recorder;
		
	}

	public void writeRecord(String cabID, RecordType type, double v1, double v2) throws IOException
	{
		Date day = new Date();
		SimpleDateFormat date = new SimpleDateFormat("YYYY/MM/dd");
		file = new File(FILE_NAME);
				
		try 
		{
			FileWriter author = new FileWriter(file, true);
			PrintWriter writer = new PrintWriter(author);
			writer.printf("%s,%s,%s,%.2f,%.2f\n", date.format(day), cabID, type, v1, v2);
			
			writer.flush();
		}
		catch (FileNotFoundException e) 
		{
			//creates new file in case it does not already exist
			file.createNewFile();
			
			FileWriter author = new FileWriter(file, true);
			PrintWriter writer = new PrintWriter(author);
			writer.printf("%s,%s,%s,%.2f,%.2f\n", date.format(day), cabID, type, v1, v2);
			
			writer.close();
		}				
	}
	
}
