/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Sha Liu, Jason Xie , Yunzhuo Zhang, Avrilyn Li
 * Purpose: this class represents the algorithms to draw single bar charts.
 */

package FinalVersion;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class BarChartSingle implements Viewer_Strategy
{
	/**
     * Creates a dataset
     * @param filename file to reference
     * @return dataset
     */
    private static CategoryDataset createDataset(String filename){
    	
    	//some local para need to be used later
    	File file = new File(filename);
        BufferedReader reader = null;
        String title = null;
        String data;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();       
        try {           
            reader = new BufferedReader(new FileReader(file));
            //get title
            title = reader.readLine();
            
            //read data
            data=reader.readLine();
            while(!data.equals(""))
            {
              String[] s=data.split(": ");
              //add the data found in file to dataset
              dataset.addValue(Double.parseDouble(s[1]), title, s[0]);
              data=reader.readLine();
            }          
            reader.close();        
        } catch (IOException e) {e.printStackTrace();}
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {}
            }
        }        
        return dataset;
    }
    
    /**
     * Produce a single bar chart
     * @param filename file to reference
     * @param dataset dataset to reference
     * @return the chart
     */
    private static JFreeChart createChart(String filename,CategoryDataset dataset)
    {
    	File file = new File(filename);
        BufferedReader reader = null;
        String title=null;
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
        String y=null;
        //decide y axis
        if (title.substring(0, 1).equalsIgnoreCase("G")) {y="US$";}
        else {y="people";}
            	    	
    	//create a vertical bar chart
        JFreeChart jfreechart = ChartFactory.createBarChart(title, "year", y, dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
        categoryplot.setNoDataMessage("No data available");
        //vertical grid lines are visible
        categoryplot.setDomainGridlinesVisible(true);
        
        //Create axis
        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        //color bar
        GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(64, 0, 0));
             
        return jfreechart;
    }
    
    /**
     * Return a bar chart.
     * @return the ChartPanel to be displayed.
     */
    public ChartPanel draw()
    {
    	String filename="Data";
    	JFreeChart jfreechart = createChart(filename,createDataset(filename));
        ChartPanel chartPanel = new  ChartPanel(jfreechart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		return chartPanel;
    }    
}
