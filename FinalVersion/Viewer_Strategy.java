/**
 * CS2212 Assignment 4
 * @author Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: this class represents the line chart and report viewers' interface.
 */

package FinalVersion;

import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;

/**
 * Interface
 */
public interface Viewer_Strategy {
public ChartPanel draw() throws IOException;

}
