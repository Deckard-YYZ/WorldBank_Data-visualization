/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * 
 * Purpose: this class represents the user specification object with multiple elements selected by the user.
 */
package FinalVersion;

public class UserSpecification 
{
	// A list of the attributes that user can select.
	String start;
	String end;
	String country;
	int type;
	int strategy;
	
	/**
	 * Constructor
	 * @param start
	 * @param end
	 * @param country
	 * @param type
	 * @param strategy
	 * 
	 */
	
	public UserSpecification(String start, String end, String country, int type, int strategy)
	{
		this.start = start;
		this.end = end;
		this.country = country;
		this.type = type;
		this.strategy = strategy;
	};
	
	/**
	 * modifier
	 * @param start
	 */
	public void setStart(String start)
	{
		this.start = start;
	}
	
	/**
	 * modifier
	 * @param end
	 */
	public void setEnd(String end)
	{
		this.end = end;
	}
	
	/**
	 * modifier
	 * @param country
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	/**
	 * modifier
	 * @param type
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	
	/**
	 * modifier
	 * @param strategy
	 */
	public void setStrategy(int strategy)
	{
		this.strategy = strategy;
	}
	
	/**
	 * accessor
	 * @return start time
	 */
	public String getStart()
	{
		return start;
	}
	
	/**
	 * accessor
	 * @return end time
	 */
	public String getEnd()
	{
		return end;
	}
	
	/**
	 * accessor
	 * @return country
	 */
	public String getCountry()
	{
		return country;
	}
	
	/**
	 * accessor
	 * @return type of analysis
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * accessor
	 * @return strategy
	 */
	public int getStrategy()
	{
		return strategy;
	}
}
