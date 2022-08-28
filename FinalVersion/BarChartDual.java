/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Sha Liu, Jason Xie , Yunzhuo Zhang, Avrilyn Li
 * Purpose: this class represents the algorithms to draw dual bar charts.
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
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartDual implements Viewer_Strategy
{
	/**
     * Create a bar chart data set for the left y axis.
     * @param filename file to reference.
     * @return dataset
     */
    private static CategoryDataset createDatasetLeftY(String filename){	
    	
    	//some local para need to be used later
    	File file = new File(filename);
    	BufferedReader reader = null;
    	String title1 = null;
    	String title2 = null;
    	String data;
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    	try {           
    		reader = new BufferedReader(new FileReader(file));
    		//get title
    		title1 = reader.readLine();        
    		//input data
    		data=reader.readLine();
    		while(!data.equals(""))
    		{
    			String[] s=data.split(": ");
    			//add the data found in file to dataset
    			dataset.addValue(Double.parseDouble(s[1]), title1, s[0]);
    			data=reader.readLine();
    		} 
    		title2 = reader.readLine();
    		data=reader.readLine();
    		while(data!=null)
    		{
    			String[] s=data.split(": ");
    			dataset.addValue(null, title2, s[0]);
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
     * Creates a bar chart dataset for the right y axis.
     * @param filename file to reference
     * @return dataset
     */
    private static CategoryDataset createDatasetRightY(String filename)
    {
    	//some local para need to be used later
    	File file = new File(filename);
    	BufferedReader reader = null;
    	String title1 = null;
    	String title2 = null;
    	String data;
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    	try {           
    		reader = new BufferedReader(new FileReader(file));
    		//get title
    		title1 = reader.readLine();        
    		//read data
    		data=reader.readLine();
    		while(!data.equals(""))
    		{
    			String[] s=data.split(": ");
    			//add the data found in file to dataset
    			dataset.addValue(null, title1, s[0]);
    			data=reader.readLine();
    		} 
    		title2 = reader.readLine();
    		data=reader.readLine();
    		while(data!=null)
    		{
    			String[] s=data.split(": ");
    			//add the data found in file to dataset
    			dataset.addValue(Double.parseDouble(s[1]), title2, s[0]);
    			data=reader.readLine();
    		} 
    		reader.close();        
    		} catch (IOException e) {e.printStackTrace();}
    		finally {
    			if (reader != null) {
    				try {reader.close();} catch (IOException e1) {}}
    			}
    			return dataset;
    		
    }
    
    /**
     * produce a bar chart
     **/
    private static JFreeChart createChart(CategoryDataset dataset, CategoryDataset dataset1,String filename)
    {
        //This huge code is for getting title
    	CategoryAxis categoryaxis = new CategoryAxis("Years");
        File file = new File(filename);
    	BufferedReader reader = null;
    	String title1 = null;
    	String title2 = null;
    	String data;   
    	try {           
    		reader = new BufferedReader(new FileReader(file));
    		title1 = reader.readLine();        
    		data=reader.readLine();    		
    		while(!data.equals(""))
    		{
    			data=reader.readLine();
    		} 
    		title2 = reader.readLine();
    		reader.close();        
    		} catch (IOException e) {e.printStackTrace();}
    		finally {
    			if (reader != null) {
    				try {reader.close();} catch (IOException e1) {}}
    		}
        //Left y-axis name
    	NumberAxis Laxis = new NumberAxis("Dollars");
    	if(title1.substring(0, 1).equalsIgnoreCase("T")) {Laxis=new NumberAxis("People");}
    	if(title1.substring(0, 1).equalsIgnoreCase("S")) {Laxis=new NumberAxis("% gross");}
        
        BarRenderer barrenderer = new BarRenderer();
        barrenderer.setItemMargin(0.00000001);
        
        /**
         * Produce a bar chart
         * @param dataset dataset referenced
         * @param dataset1 dataset referenced
         * @param filename file to referenced
         * @return the chart
         */
        CategoryPlot categoryplot = new CategoryPlot(dataset, categoryaxis, Laxis, barrenderer) {
        
            public LegendItemCollection getLegendItems()
            {
                LegendItemCollection legenditemcollection = new LegendItemCollection();
                CategoryDataset categorydataset2 = getDataset();
                if(categorydataset2 != null){
                    CategoryItemRenderer categoryitemrenderer = getRenderer();                    
                    if(categoryitemrenderer != null){
                        org.jfree.chart.LegendItem legenditem = categoryitemrenderer.getLegendItem(0, 0);
                        legenditemcollection.add(legenditem);
                    }
                }
                CategoryDataset categorydataset3 = getDataset(1);
                
                if(categorydataset3 != null){
                    CategoryItemRenderer categoryitemrenderer1 = getRenderer(1);
                    
                    if(categoryitemrenderer1 != null){
                        org.jfree.chart.LegendItem legenditem1 = categoryitemrenderer1.getLegendItem(1, 1);
                        legenditemcollection.add(legenditem1);
                    }                
                }                
                return legenditemcollection;
            }

        };
        
        String title=title1+" vs. "+title2;
        JFreeChart jfreechart = new JFreeChart(title, categoryplot);
        jfreechart.setBackgroundPaint(Color.white);
        categoryplot.setBackgroundPaint(new Color(238, 238, 255));
        categoryplot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        categoryplot.setDataset(1, dataset1);
        categoryplot.mapDatasetToRangeAxis(1, 1);
        
        //Right y-axis name
        NumberAxis Raxis = new NumberAxis("People");
        if(title2.substring(0, 1).equalsIgnoreCase("S")) {Raxis=new NumberAxis("% gross");}
        if(title2.substring(0, 1).equalsIgnoreCase("M")) {Raxis=new NumberAxis("Mortality rate");}
        categoryplot.setRangeAxis(1, Raxis);
        categoryplot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        BarRenderer barrenderer1 = new BarRenderer();
        barrenderer1.setItemMargin(0.00000001);

        categoryplot.setRenderer(1, barrenderer1);
        return jfreechart;
    }

    /**
     * Return a bar chart.
     * @return the chartPanel to be displayed.
     */
    public ChartPanel draw() throws IOException
    {
        String filename="Data";
    	JFreeChart jfreechart = createChart(createDatasetLeftY(filename), createDatasetRightY(filename), filename);
        ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		return chartPanel;
    }
}