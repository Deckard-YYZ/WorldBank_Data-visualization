/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: this class represents the login user interface.
 */

package FinalVersion;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;

public class LoginUI implements ActionListener {
	private  JTextField uid;
	private  JTextField pwd;
	private  JFrame f;
	private static LoginUI instance = new LoginUI();

	private LoginUI() {
	}
	public String getID() {
		return uid.getText();
	}
	public String getPWD() {
		return pwd.getText();
	}

	public static LoginUI getInstance() {
		return instance;
	}

	public void make() {
		JPanel p = new JPanel();
		f = new JFrame("Login");
		f.setSize(300, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);

		f.add(p);
		p.setLayout(null);

		JLabel l = new JLabel("Username");
		l.setBounds(10, 20, 80, 25);
		p.add(l);

		uid = new JTextField(20);
		uid.setVisible(true);
		uid.setBounds(100, 20, 150, 25);
		p.add(uid);

		JLabel l1 = new JLabel("Password");
		l1.setBounds(10, 50, 80, 25);
		p.add(l1);

		pwd = new JTextField(20);
		pwd.setVisible(true);
		pwd.setBounds(100, 50, 150, 25);
		p.add(pwd);

		JButton b = new JButton("Login");
		b.setBounds(100, 90, 80, 27);
		b.addActionListener(instance);
		b.setActionCommand("Login");
		p.add(b);

		f.setVisible(true);
	}

	public void popup() {
		JPanel p = new JPanel();
		JFrame f1 = new JFrame("Error");
		f1.setSize(230, 100);
		f1.setLocationRelativeTo(null);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.add(p);
		p.setLayout(null);
		JLabel t = new JLabel("Invalid Credential, Terminates");
		t.setBounds(20, 10 , 200, 20);
		p.add(t);
		JButton b = new JButton("OK");
		b.setBounds(70, 30, 80, 30);
		b.addActionListener(instance);
		b.setActionCommand("OK");
		p.add(b);
		f.setVisible(false);
		f1.setVisible(true);
	}
	public void endLogin() {
		f.setVisible(false);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		AUI aui = new LoginServer();
		if (e.getActionCommand().equals("Login")) {
			aui.initial();
		}else {
		System.exit(0);
		}
	}
}










