/**
 * CS2212 Assignment 4
 * Group: 32
 * @author  Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: this class represents the analysis UI, which is the client in our system. It connect with most classes,
 * receive choices call analysis server to validate and base on the validation & selection, call analysis sever
 * to retrieve results and call rendering server to display various results to the UI.
 */
package FinalVersion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AnalysisUI implements AUI, ActionListener {
	private static AnalysisUI instance = new AnalysisUI();
	private boolean clicked = false;
	int earliest = 1980, latest = 2018;
	private UserSpecification usp = new UserSpecification("2018", "2018", "can", 1, 1);
	private JComboBox<String> countriesList;
	private JComboBox<String> fromList;
	private JComboBox<String> toList;
	private JButton recalculate;
	JComboBox<String> viewsList;
	private JButton addView;
	private JButton removeView;
	JComboBox<String> methodsList;
	private JPanel east;
	private JPanel west;
	private JPanel north;

	/**
	 * implement of the singleton design pattern
	 * 
	 * @return AnalysisUI instance
	 */
	public static AnalysisUI getinstance() {
		return instance;
	}

	/**
	 * initial the UI with several default values
	 */
	public void initial() {
		JFrame f = new JFrame("Country Statistics");

		f.setSize(1200, 900);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
//set the country selections
		JLabel chooseCountryLabel = new JLabel("Choose a country: ");
		Vector<String> countriesNames = new Vector<String>();
		countriesNames.add("USA");
		countriesNames.add("Canada");
		countriesNames.add("France");
		countriesNames.add("China");
		countriesNames.add("Russia");
		countriesNames.sort(null);
		countriesList = new JComboBox<String>(countriesNames);
		countriesList.addActionListener(instance);
		countriesList.setActionCommand("countries");
//set the period selections
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		to.setName("toText");
		Vector<String> years = new Vector<String>();
		for (int i = 2018; i >= 1980; i--) {
			years.add("" + i);
		}
//set from
		fromList = new JComboBox<String>(years);
		fromList.addActionListener(instance);
		fromList.setActionCommand("start");
//set to
		toList = new JComboBox<String>(years);
		toList.addActionListener(instance);
		toList.setActionCommand("end");
//add period, country selections to north area
		north = new JPanel();
		north.add(chooseCountryLabel);
		north.add(countriesList);
		north.add(from);
		north.add(fromList);
		north.add(to);
		north.add(toList);
		f.add(north, BorderLayout.NORTH);
//set recalculate button
		recalculate = new JButton("Recalculate");
		recalculate.addActionListener(instance);
		recalculate.setActionCommand("do");
//set the viewers
		JLabel viewsLabel = new JLabel("Available Views: ");
		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Pie Chart");
		viewsNames.add("Report");
		viewsList = new JComboBox<String>(viewsNames);
		viewsList.addActionListener(instance);
		viewsList.setActionCommand("view");
//set the add/remove buttons
		addView = new JButton("+");
		addView.addActionListener(instance);
		addView.setActionCommand("add");
		removeView = new JButton("-");
		removeView.addActionListener(instance);
		removeView.setActionCommand("remove");
//set analysis types
		JLabel methodLabel = new JLabel("        Choose analysis method: ");

		Vector<String> methodsNames = new Vector<String>();
		methodsNames.add("Total population vs School enrollment");
		methodsNames.add("Total population vs Mortality rate, under5");
		methodsNames.add("School enrollment vs Mortality rate, under5");
		methodsNames.add("GDP per capita vs School enrollment, secondary ");
		methodsNames.add("GDP per capita vs Mortality rate, under5)");
		methodsNames.add("GDP per capita vs total population");
		methodsNames.add("Total population");
		methodsNames.add("Agriculture, value added (% of GDP)");

		methodsList = new JComboBox<String>(methodsNames);
		methodsList.addActionListener(instance);
		methodsList.setActionCommand("type");
//add viewers and analysis types to the south area
		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);
		f.add(south, BorderLayout.SOUTH);
		east = new JPanel();
		east.setLayout(new GridLayout(1, 1));

// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));
		f.add(east, BorderLayout.EAST);
		f.add(west, BorderLayout.WEST);

		f.setVisible(true);

	}

	/**
	 * create pop-up window whenever an invalid selection occurs
	 */
	public void popup(String message) {
		JPanel p = new JPanel();
		JFrame f1 = new JFrame("Oops...");
		f1.setSize(400, 200);
		f1.setLocationRelativeTo(null);
		f1.add(p);
		p.setLayout(null);
		JLabel t = new JLabel(message);
		t.setBounds(10, 10, 390, 100);
		p.add(t);
		f1.setVisible(true);

	}

	/**
	 * react to various user events
	 */
	public void actionPerformed(ActionEvent e) {
//TYPE changes, determine whether clean all viewers
		if (e.getActionCommand().equals("type")) {
			int currentT = usp.getType();
			int nextT = methodsList.getSelectedIndex() + 1;
			if (currentT == nextT) {
				return;
			} else {
				clicked = false;
				usp.setType(nextT);
				String message = AnalysisServer.validate(usp);
				if (!message.equals("valid")) {
					if (message.startsWith("###")) {
						resetYear(message);
					}else {
					popup(message);
					}
				}
				west.removeAll();
				east.removeAll();
				east.repaint();
				west.repaint();
				// remove all viewer
			}
		}
//COUNTRY changes, determine whether the country is valid for specific analysis
		if (e.getActionCommand().equals("countries")) {
			clicked = false;
			String[] s = { "can", "chn", "fra", "rus", "usa" };
			usp.setCountry(s[countriesList.getSelectedIndex()]);
			String message = AnalysisServer.validate(usp);
			if (!message.equals("valid")) {
				if (message.startsWith("###")) {
					resetYear(message);
					return;
				}
				popup(message);
			}
//STARTYEAR changes, determine whether the selected year is valid for specific analysis in specific country
		} else if (e.getActionCommand().equals("start")) {
			clicked = false;
			usp.setStart((String) fromList.getSelectedItem());
			String message = AnalysisServer.validate(usp);
			if (!message.equals("valid")) {
				if (message.startsWith("###")) {
					return;
				}
				popup(message);
			}
//ENDYEAR same as above
		} else if (e.getActionCommand().equals("end")) {
			clicked = false;
			usp.setEnd((String) toList.getSelectedItem());
			String message = AnalysisServer.validate(usp);
			if (!message.equals("valid")) {
				if (message.startsWith("###")) {
					return;
				}
				popup(message);
			}
//VIEWER
		} else if (e.getActionCommand().equals("view")) {
			usp.setStrategy(viewsList.getSelectedIndex() + 1);
			String message = AnalysisServer.validate(usp);
			if (!message.equals("valid")) {
				if (message.startsWith("###")) {
					return;
				}
				popup(message);
			}
//RECALCULATE			
		} else if (e.getActionCommand().equals("do")) {
			int currentT = usp.getType();
			int nextT = methodsList.getSelectedIndex() + 1;
			if (currentT == nextT && clicked == true) {
				return;
			}
			usp.setType(nextT);
			String message = AnalysisServer.validate(usp);
			if (!message.equals("valid")) {
				if (message.startsWith("###")) {
				}else {
					popup(message);
					return;
				}
			}
			AnalysisServer.differentiate(usp);
			addViewer(usp.strategy);
			clicked = true;
		}
//ADD_VIEWER
		else if (e.getActionCommand().equals("add")) {
			if (clicked == false) {
				popup("<html>set everything then press recalculate,<br/> then you can add whatever viewer you want!<html>");
			} else {
				Component[] eastc = east.getComponents();
				for (int i = 0; i < eastc.length; i++) {
					if (Integer.parseInt(eastc[i].getName()) == usp.getStrategy()) {
						return;
					}
				}
				Component[] c = west.getComponents();
				for (int i = 0; i < c.length; i++) {
					if (Integer.parseInt(c[i].getName()) == usp.getStrategy()) {
						return;
					}
				}
				String valid = AnalysisServer.validate(usp);
				if (!valid.equals("valid")) {
					if (valid.startsWith("###")) {
						addViewer(usp.strategy);
						return;
					}
					popup(valid);
					return;
				}
				
			}
			// if check viewer applicable, if it is, add a graph and update boolean array
//REMOVE_VIEWER
		} else if (e.getActionCommand().equals("remove")) {
			if (usp.getStrategy() == 4) {
				east.removeAll();
				east.repaint();
				return;
			}
			Component[] c = west.getComponents();
			for (int i = 0; i < c.length; i++) {
				if (Integer.parseInt(c[i].getName()) == usp.getStrategy()) {

					west.remove(c[i]);
					west.repaint();
					return;
				}
			}
		}
	}

	/**
	 * the method calls rendering server to display the results
	 */
	private void addViewer(int view) {
		boolean dual;

		if (usp.getType() == 7 || usp.getType() == 8) {
			dual = false;
		} else {
			dual = true;
		}
		Rendering rd = new Rendering();
		if (usp.getStrategy() == 4) {
			rd.creatReport(east);
		}
		rd.createCharts(west, view, dual);
	}

	private void resetYear(String message) {
		message = message.substring(3);
		String[] ft = message.split(",");
		int from = Integer.parseInt(ft[0]);
		int to = Integer.parseInt(ft[1]);
		if (from == earliest && to == latest) {
			return;
		}
		earliest = from;
		latest = to;
		Vector<String> years = new Vector<String>();
		for (int i = to; i >= from; i--) {
			years.add("" + i);
		}
		north.remove(fromList);
		north.remove(toList);
		fromList = new JComboBox<String>(years);
		fromList.addActionListener(instance);
		fromList.setActionCommand("start");
		toList = new JComboBox<String>(years);
		toList.addActionListener(instance);
		toList.setActionCommand("end");
		Component[] c = north.getComponents();
		for (int i = 0; i < c.length; i++) {
			if (c[i].getName()=="toText") {
				c[0] = c[i];
				break;
			}
		}
		north.remove(c[0]);
		north.add(fromList);
		north.add(c[0]);
		north.add(toList);
		north.revalidate();
		usp.setEnd("" + to);
		usp.setStart("" + to);
	}
}
