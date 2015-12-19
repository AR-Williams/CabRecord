package edu.tridenttech.cpt237.williams;

import java.util.Comparator;

/**
 * 
 * @author Alexis Williams 
 * 
 * Added modifications for the cab service that utilizes several methods of the Cab parent class.
 */

public class ExtendedCab extends Cab
{
	private final double SERVICE_COST;
	private double totalCosts = 0;
	private int milesSinceService = 0;
	private String name;
	
	/**
	 * ExtendedCab: the constructor for the ExtendedCab class. 
	 * Accepts the name, miles per gallon, and gas tank size of the cab
	 * passes the miles per gallon and gas tank size along to the Cab parent class for use.
	 */	
	public ExtendedCab(String n, double MPG, double serviceCost, double tank)
	{
		super(MPG, tank);
		name = n;
		SERVICE_COST = serviceCost;
	}	
	
	/**
	 * NameSorter: sorts the cab names of each ExtendedCab object
	 */
	public static Comparator<ExtendedCab> NameSorter = new Comparator<ExtendedCab>()
	{
		public int compare(ExtendedCab o1, ExtendedCab o2)
		{
			return o1.name.compareTo(o2.name); 
		}		
	};
	
	public String getName()
	{
		return name;
		
	}
	
	public double getService()
	{
		return SERVICE_COST;
	}
	
	/**
	 * getTotalCosts: returns the total maintenance costs done for the cab.
	 * @return: totalCosts 
	 */	
	public double getTotalCosts()
	{
		return totalCosts;
	}
	
	/**
	 * getNetEarnings: returns the net earnings that the cab has made.
	 * @return: netEarnings
	 */
	public double getNetEarnings()
	{		
		double netEarnings;
		
		netEarnings = super.getGrossEarnings() - totalCosts;
		
		return netEarnings;				
	}
	
	/**
	 * getMilesSinceService: returns the number of miles added since the last time the cab was serviced
	 * @return: milesSinceService
	 */
	
	public int getMilesSinceService()
	{
		return milesSinceService;
	}

	/**
	 * serviceCab: sets the milesSinceService value to zero and adds a service fee to the overall maintenance cost
	 */	
	public void serviceCab()
	{
		milesSinceService = 0;
		totalCosts += SERVICE_COST;
	}
	
	/**
	 * addGas: the addGas method for the ExtendedCab class. 
	 * adds the cost for gas to the maintenance cost and passes the gas value to the addGas of the Cab class to be added to the cab's gas tank  
	 * @param g: parameter to accepts the gas value as inputed by the user.
	 * @param c: parameter for the price per gallon of gas, the first parameter
	 */
	public double addGas(double g, double c)
	{										
		double addTank = super.addGas(g);		
		double gasTotal = 0;
		
			if(addTank != 0)
			{
				gasTotal = g * c; 
			
				totalCosts += gasTotal;
			}		
			else
			{
				gasTotal = 0;
			}
		
		return gasTotal;
	}
	
	/**
	 * recordTrip: records a trip and returns the fare for that trip.
	 */
	public double recordTrip(int m)
	{		
		double fare;
		
			if(gasAvailable <= 0)
			{
				fare = 0;
			}
			else
			{	
				fare = super.recordTrip(m);				
				milesSinceService += m;	
			}
					
		return fare;
	}
	
	/**
	 * reset: sets the maintenance costs to zero when called.
	 */
	public void reset()
	{		
		super.reset();
		totalCosts = 0;
	}

		
}
