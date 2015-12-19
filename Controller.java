package edu.tridenttech.cpt237.williams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * 
 * @author Alexis Williams
 *
 */

public class Controller implements CabLoader
{	
	private ArrayList<ExtendedCab>cabList = new ArrayList<ExtendedCab>();
	private ArrayList<String> cabName = new ArrayList<String>();
		
	private MultiCabUI cabUI;
	
	private String filename = "cabs.csv";
	private File file;	
	
	private Scanner keyboard;
	private Scanner delimiter;
	
	private String nameOfCab;
	private double mpg;
	private double serviceCost;
	private double tankSize;
	
	/**
	 * setUI: accepts a UI and sets it for use inside the class.
	 * @param ui: the UI parameter value
	 */
	public void setUI(MultiCabUI ui)
	{
		cabUI = ui;
	}
	
	/**
	 * addCabs: adds all the ExtendedCab objects to the ArrayList and sorts them.
	 * @throws IOException 
	 */
	public void addCabs() throws IOException
	{
		try
		{
			file = new File(filename);
			keyboard = new Scanner(file);			
			
			while(keyboard.hasNext())
			{
				delimiter = new Scanner(keyboard.nextLine());
				delimiter.useDelimiter(",");
				nameOfCab = delimiter.next();
				mpg = delimiter.nextDouble();
				serviceCost = delimiter.nextDouble();
				tankSize = delimiter.nextDouble();
				
				ExtendedCab cab = new ExtendedCab(nameOfCab, mpg, serviceCost, tankSize);
				
				cabList.add(cab);				
			}//end while
			keyboard.close();
			delimiter.close();
		}//end try
		catch(FileNotFoundException e)
		{
			file.createNewFile();
		}		
		
		Collections.sort(getCabList(), ExtendedCab.NameSorter);		
	}
	
	public ArrayList<ExtendedCab> getCabList() 
	{
		return cabList;
	}

	public ArrayList<String> getCabName() 
	{
		return cabName;
	}


	/**
	 * loadCab: accepts the name of the Cab and goes through the arraylist of cabs to find the object the name matches with
	 * @param name: the name of the cab to be switched to.
	 */
	public void loadCab(String name) 
	{
		ExtendedCab wantedCab = null;
			
		for(ExtendedCab i : getCabList())
		{
			if(name.equals(i.getName()))
			{
				wantedCab = i;
			}
		}//end for loop
			
		cabUI.setCab(wantedCab);
	}
	
}
