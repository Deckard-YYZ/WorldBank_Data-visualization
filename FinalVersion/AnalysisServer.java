/**
 * CS2212 Assignment 4
 * Group: 32
 * @author  Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang
 * 
 * Purpose: this class represents the analysis server that validates user specification and and fetch data.
 */
package FinalVersion;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

public class AnalysisServer 
{	
	/**
	 * Validate the user specification.
	 * @param us an object that groups together all user specifications.
	 * @return a string indicating whether if the selection is valid.
	 */
	public static String validate(UserSpecification us) 
	{
		// Specify the database file and convert user specification elements.
		File validate = new File("validate");
		int analysis = us.getType();
		int start = Integer.parseInt(us.getStart());
		int end = Integer.parseInt(us.getEnd());
		int strategy = us.getStrategy();
		
		// The data cannot span more than 10 years or less than 0 years.
		if (end - start > 10 || end - start < 0)
		{
			return ("<html>Sorry, the time frame must be between 0 and 10 years.<html>");
		}
		
		else if (strategy == 3 && end != start)
		{
			return ("<html>Sorry, pie chart is only available for single year analysis.<html>");
		}
		// The pie chart is only available for the Agriculture GDP and population analysis.
		else if(analysis != 7 && analysis != 8 && strategy == 3)
		{
			return("<html>Sorry, pie chart is only avilable for 'Agriculture GDP' and 'Population distribution between genders' analyses.<html>");
		}
		
		// If nothing above is wrong, compare the data time frame with local database.
		else
		{
			try 
			{
				Scanner myReader = new Scanner(validate);
				while (myReader.hasNextLine()) 
				{
					String data = myReader.nextLine();
					String[] element = data.split(",");
					if (element[0].equals(us.getCountry()))
					{
						return("###"+element[analysis*2-1]+","+element[analysis*2]);
						/**
						if (start < Integer.parseInt(element[analysis*2-1]))
						{
							return("The earliest record of this analysis is year " + element[analysis*2-1]);
						}
						if (end > Integer.parseInt(element[analysis*2]))
						{
							return("The latest record of this analysis is year " + element[analysis*2]);
						}
						**/
					}
				}
				myReader.close();
			}
			
			catch (Exception e) 
			{
			      System.out.println("An error occurred in the validation stage.");
			      e.printStackTrace();
			}
		}
		
		// Return valid if the user specification is valid.
		return ("valid");
	}
	
	/**
	 * Differentiate the analysis types.
	 * @param us user selected elements.
	 */
	public static void differentiate(UserSpecification us)
	{	
		try 
		{
			FileWriter data = new FileWriter("Data");
			switch (us.getType())
			{
				// Total population vs School enrollment, secondary (% gross) 
				case 1:
				{
				    data.write("Total Population");
				    writeResults("SP.POP.TOTL", us, data, "year");
				    
				    data.write("\n\nSchool enrollment, secondary (% gross)");
				    writeResults("SE.SEC.ENRR", us, data, "year");
				    data.close();
					break;
				}
				
				// Total population vs Mortality rate, under-5 (per 1,000 live births)
				case 2:
				{
					data.write("Total Population");
					writeResults("SP.POP.TOTL", us, data, "year");
					
					data.write("\n\nMortality rate, under-5 (per 1,000 live births)");
					writeResults("SH.DYN.MORT", us, data, "year"); 
					data.close();
					break;
				}
				
				// School enrollment, secondary (% gross) vs Mortality rate, under-5 (per 1,000 live births)
				case 3:
				{
					data.write("School enrollment, secondary (% gross) ");
					writeResults("SE.SEC.ENRR", us, data, "year");
					
					data.write("\n\nMortality rate, under-5 (per 1,000 live births)");
					writeResults("SH.DYN.MORT", us, data, "year");
					data.close();
					break;
				}
				
				// GDP per capita vs School enrollment, secondary (% gross) 
				case 4:
				{
					data.write("GDP per capita (current US$)");
					writeResults("NY.GDP.PCAP.CD", us, data, "year");
					
					data.write("\n\nSchool enrollment, secondary (% gross) ");
					writeResults("SE.SEC.ENRR", us, data, "year");
					data.close();
					break;
				}
					
				// GDP per capita vs Mortality rate, under-5 (per 1,000 live births)
				case 5:
				{
					data.write("GDP per capita (current US$)");
					writeResults("NY.GDP.PCAP.CD", us, data, "year");
					
					data.write("\n\nMortality rate, under-5 (per 1,000 live births)");
					writeResults("SH.DYN.MORT", us, data, "year");
					data.close();
					break;
				}
				
				// GDP per capita vs total population
				case 6:
				{
					data.write("GDP per capita (current US$)");
					writeResults("NY.GDP.PCAP.CD", us, data, "year");
					
					data.write("\n\nTotal Population");
				    writeResults("SP.POP.TOTL", us, data, "year");
				    data.close();
					break;
				}
				
				// Total population 
				case 7:
				{
					data.write("Total population");
				    writeResults("SP.POP.TOTL", us, data, "year");
				    
				    data.write("\n\nFemale population percentage");
				    writeResults("SP.POP.TOTL.FE.ZS", us, data, "female");
				    data.close();
					break;
				}
				
				// GDP Agriculture, value added (% of GDP)
				case 8:
				{
					data.write("GDP (current US$)");
					writeResults("NY.GDP.MKTP.CD", us, data, "year");
					
					data.write("\n\nAgriculture, value added (% of GDP) ");
				    writeResults("NV.AGR.TOTL.ZS", us, data, "agriculture");
				    data.close();
					break;
				}
			}
		}
		
		// Anticipate for any error that might occur.
		catch (IOException e) 
		{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	/**
	 * write data to a file.
	 * @param indicator indicator of the analysis.
	 * @param us user selected elements.
	 * @param writer file to write data to.
	 * @param title line title in the file.
	 */
	public static void writeResults(String indicator, UserSpecification us, FileWriter writer, String title)
	{
		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/" + indicator + "?date=" + us.getStart() + ":" + us.getEnd() + "&format=json", us.getCountry());
		String singleYear = "";
		try 
		{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			// IF THE RESPONSE IS 200 OK GET THE LINE WITH THE RESULTS
			if (responsecode == 200) 
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) 
				{
					inline += sc.nextLine();
				}
				sc.close();
				// PROCESS THE JSON AS ONE LINE
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
				int year = 0;
				for (int i = 0; i < sizeOfResults; i++) 
				{
					// GET FOR EACH ENTRY THE YEAR FROM THE 鈥渄ate鈥� FIELD
					year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
					// CHECK IF THERE IS A VALUE FOR THE POPULATION FOR A GIVEN YEAR
					if(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
						singleYear = "";
					else
						// GET THE POPULATION FOR THE GIVEN YEAR FROM THE 鈥渧alue鈥� FIELD
						singleYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsString();
					if (singleYear != "")
					{
						if(title.equals("year"))
						{
							writer.write("\n" + year + ": " +  singleYear);
						}
						else 
						{
							writer.write("\n" + title + ": " +  singleYear);
						}
					}
					else 
					{
						if(title.equals("year"))
						{
							writer.write("\n" + year + ": " +  0);
						}
						else 
						{
							writer.write("\n" + title + ": " +  0);
						}
					}
				}
			}
		} 
		
		// Anticipate for any error that might occur.
		catch (IOException e) 
		{
		     System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
}