/**
 * CS2212 Assignment 4
 * Group: 32
 * @author  Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: this class generate pie charts.
 */

package FinalVersion;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


public class PieChart implements Viewer_Strategy
{	
	/**
	 * Create a data set.
	 * @param filename
	 * @return dataset
	 */
    private static PieDataset createDataset(String filename)
    {
        //some local para need to be used later
        File file = new File(filename);
        BufferedReader reader = null;
        String useless =null;
        String title = null;
        String data =null;
        DefaultPieDataset piedataset = new DefaultPieDataset();

        
        try {           
            reader = new BufferedReader(new FileReader(file));
            useless=reader.readLine();
            while(!useless.equals("")) {
            	useless=reader.readLine();
            }
            
            //get title
            title = reader.readLine();            
            //read data
            data=reader.readLine();            
            String[] s=data.split(": ");                         
            //add the data found in file to dataset
            piedataset.setValue(s[0], Double.parseDouble(s[1]));
            //add the remain data to dataset
            piedataset.setValue("non-"+s[0], 100D-(Double.parseDouble(s[1])));
              
            reader.close();
        
        } catch (IOException e) {e.printStackTrace();}
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {}
            }
        }        
        return piedataset;
    }

    /**
     * Create a pie chart.
     * @param filename file to reference
     * @param dataset
     * @return pie chart
     */
    private static JFreeChart createChart(String filename,PieDataset dataset)
    {
    	//This huge code is for getting title
    	File file = new File(filename);
        BufferedReader reader = null;
        String title = null;                      
        try {           
            reader = new BufferedReader(new FileReader(file));
            
            title = reader.readLine();           
            reader.close();        
        } catch (IOException e) {e.printStackTrace();}
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {}
            }
        }
        
        //create chart
        JFreeChart piechart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot pieplot = (PiePlot)piechart.getPlot();
        
        //if no data set(use it when check the code)
        //pieplot.setNoDataMessage("No data available");
        
        //show piechart's percentage
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2}percent)"));
                
        return piechart;
    }

    /**
     * Return a pie chart.
     * @return ChartPanel
     */
    public ChartPanel draw()
    {
    	String filename="Data";
    	JFreeChart piechart = createChart(filename,createDataset(filename));
    	ChartPanel chartPanel = new ChartPanel(piechart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		return chartPanel;
    }
}
