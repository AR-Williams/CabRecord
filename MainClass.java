package edu.tridenttech.cpt237.williams;

import java.io.IOException;
import java.util.Collections;
/**
 * 
 * @author Alexis Williams 
 *
 */

public class MainClass 
{
	private static Controller c = new Controller();
	private static MultiCabUI m;
	
	public static void main(String[] args) throws IOException
	{				
		c.addCabs();
		
		for(ExtendedCab b : c.getCabList())
		{
			c.getCabName().add(b.getName());
		}
		
		Collections.sort(c.getCabName());	
			
		m = new MultiCabUI(c.getCabName(), c);
		c.setUI(m);
		m.show();
	}
	
}
