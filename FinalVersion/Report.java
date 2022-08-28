package FinalVersion;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * CS2212 Assignment 4
 * Group: 32
 * @author  Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: the class for creating Report by reading the data from file.
 */
public class Report {
	/**
	 * the class for creating Report by reading the data from file
	 * 
	 * @author Jason Xie
	 *
	 */
	
	public static JScrollPane createReport() throws IOException {
		String title1 = "";
		String title2 = "";
		String dataset = "";
		String data = "";
		String dataset2 = "";
		String data2 = "";

		AnalysisServer read = new AnalysisServer();
		BufferedReader in = new BufferedReader(new FileReader("Data")); // insert the file name into fileReader.

		title1 = in.readLine();
		while (!(data = in.readLine()).equals("")) {
			dataset = dataset + data + "\n";
		}

		if ((title2 = in.readLine()) != null) {
			while ((data2 = in.readLine()) != null) {
				dataset2 = dataset2 + data2 + "\n";
			}
		}

		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setPreferredSize(new Dimension(400, 300));
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		String reportMessage = "";

		String[] set;
		String[] set2;
		if (title1 != null && title2 != null && !title2.contains("percentage") && !title2.contains("Agriculture")) {
			reportMessage = title1 + " vs " + title2 + "\n==============================\n";

			set = dataset.split("\n");
			set2 = dataset2.split("\n");

			for (int i = 0; i < set.length; i++) {
				String[] temp = set[i].split(": ");
				reportMessage = reportMessage + "Year " + temp[0] + ":\n" + " " + title1 + " => " + temp[1] + "\n";
				String[] temp2 = set2[i].split(": ");

				reportMessage = reportMessage + " " + title2 + " => " + temp2[1] + "\n";
			}
		} else if (title1 != null && (title2.contains("percentage") || title2.contains("Agriculture"))) {
			reportMessage = title1 + "\n==============================\n";
			set = dataset.split("\n");
			for (int i = 0; i < set.length; i++) {
				String[] value = set[i].split(": ");
				reportMessage = reportMessage + "Year " + value[0] + ":\n" + " " + title1 + " => " + value[1] + "\n";
			}
		}

		report.setText(reportMessage);
		JScrollPane outputScrollPane = new JScrollPane(report);

		in.close();
		return outputScrollPane;
	}
}