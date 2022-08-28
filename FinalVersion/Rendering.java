/**
 * CS2212 Assignment 4
 * Group: 32
 * @author  Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: the class responsible for rendering all the charts when called by the UI..
 */
package FinalVersion;

import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;



public class Rendering {
	/**
	 * the method collects all the methods to create report and
	 * places it back to the UI.
	 * 
	 * @param JPanel east
	 */
	public void creatReport(JPanel east) {
		try {
			JScrollPane jp = Report.createReport();
			jp.setName("4");
			jp.setSize(600, 800);
			east.add(jp);
			east.revalidate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * the method collects all the methods to create different types of charts and
	 * places them back to the UI.
	 * 
	 * @param JPanel west, int viewer, boolean dualchart
	 */
	public void createCharts(JPanel west, int viewer, boolean dualchart) {
		int view = viewer;
		switch (view) {
		case 1: {
			Viewer_Strategy vs = new LineViewer();
			try {
				ChartPanel c = vs.draw();
				c.setName("1");
				west.add(c);
				west.revalidate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 2: {
			try {
				if (dualchart) {
					Viewer_Strategy vs = new BarChartDual();
					ChartPanel c = vs.draw();
					c.setName("2");
					west.add(c);
					west.revalidate();
				} else {
					Viewer_Strategy vs = new BarChartSingle();
					ChartPanel c = vs.draw();
					c.setName("2");
					west.add(c);
					west.revalidate();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
		case 3: {
			try {
				Viewer_Strategy vs = new PieChart();
				ChartPanel c = vs.draw();
				c.setName("3");
				west.add(c);
				west.revalidate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
		}
	}
}