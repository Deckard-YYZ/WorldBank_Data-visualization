/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: the class for creating line Chart by reading the data from file.
 */
package FinalVersion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineViewer implements Viewer_Strategy {
	/**
	 * the method processed the data in the generated file, 
	 * and creates a line chart, placed back to the UI.
	 * 
	 * @return ChartPanel
	 * @throws IOException
	 */
	public ChartPanel draw() throws IOException {
		String title1 = "";
		String title2 = "";
		String data = "";

		AnalysisServer read = new AnalysisServer();
		BufferedReader in = new BufferedReader(new FileReader("Data")); // insert the file name into fileReader.
		title1 = in.readLine();
		XYSeries series1 = new XYSeries(title1);

		while ((!(data = in.readLine()).equals(""))) {

			String[] num = data.split(": ");
			series1.add(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
		}

		XYSeries series2 = null;
		if ((data.equals("")) && (title2 = in.readLine()) != null && !title2.contains("percentage")
				&& !title2.contains("Agriculture")) { // when
			// there
			// are
			// second
			// part
			// of data to compare with
			series2 = new XYSeries(title2);
			while ((data = in.readLine()) != null) {
				String[] num = data.split(": ");
				series2.add(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
			}
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		if (series2 != null) {
			dataset.addSeries(series2);

		}

		JFreeChart chart;
		if (series1 != null && series2 != null) {
			chart = ChartFactory.createXYLineChart(title1 + " vs " + title2, "Year", "", dataset,
					PlotOrientation.VERTICAL, true, true, false);
		} else {
			chart = ChartFactory.createXYLineChart(title1, "Year", "", dataset, PlotOrientation.VERTICAL, true, true,
					false);
		}

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		if (title1 != null && title2 != null) {
			chart.setTitle(new TextTitle(title1 + " vs " + title2, new Font("Serif", java.awt.Font.BOLD, 18)));
		} else {
			chart.setTitle(new TextTitle(title1, new Font("Serif", java.awt.Font.BOLD, 18)));

		}

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		in.close();
		return chartPanel;
	}
}
