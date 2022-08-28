/**
 * CS2212 Assignment 4
 * Group: 32
 * @author Avrilyn Li , Sha Liu , Jason Xie , Yunzhuo Zhang 
 * Purpose: this class represents the login server.
 */

package FinalVersion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

public class LoginServer implements AUI {
	private String id;
	private String pwd;
	/**
	 * Initialize a login session
	 * if valid user, initial the analysisUI
	 */
	public void initial() {
		LoginUI l = LoginUI.getInstance();
		boolean correct = false;
		try {
			correct = checkID(l.getID());
			if(!correct) {
				l.popup();
				return;
			}
			correct = checkPWD(l.getPWD());
			if(!correct) {
				l.popup();
				return;
			}
			AnalysisUI aui = AnalysisUI.getinstance();
			l.endLogin();
			aui.initial();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Check user ID
	 * @param input user input
	 * @return true or false
	 * @throws FileNotFoundException
	 */
	private boolean checkID(String input) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader("UserDB.txt"));
		try {
			String line;
			StringTokenizer st;
			while ((line = br.readLine()) != null) {
				st = new StringTokenizer(line);
				String currentID = st.nextToken();
				if (currentID.equals(input)) {
					return true;
				}
			}
		} catch (Exception e) {} 
		return false;
	}

	/**
	 * Check password
	 * @param input user input
	 * @return true or false
	 * @throws FileNotFoundException
	 */
	private boolean checkPWD(String input) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader("UserDB.txt"));
		try {
			String line;
			StringTokenizer st;
			while ((line = br.readLine()) != null) {
				st = new StringTokenizer(line);
				st.nextToken();
				String currentPWD = st.nextToken();
				if (currentPWD.equals(input)) {
					return true;
				}
			}
		} catch (Exception e) {
		} finally {
		}
		return false;
	}

	

}
