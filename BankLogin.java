import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.awt.Component;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
interface GlobalConstants
{
  String uname = "Chilly Billy";

}
public class BankLogin {
	private static final Pattern pattern = Pattern.compile("^[\\s\\w\\W]{0,20}$");
	  static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
	  static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
	  static String Username = null;
	  static String Password = null;
	  
	  database a = new database();
	protected Shell shell;
	
	
	private Text usernameField;
	private Text passwordField;

	/**
	 * Launch the application.
	 * @param args
	 */
	static BankLogin window = new BankLogin();
	public static void main(String[] args) {
		
			
			try {
				window.open();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Button loginButton = new Button(shell, SWT.NONE);
		loginButton.setBounds(214, 166, 142, 61);
		loginButton.setText("Login");
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Username= usernameField.getText();
				Password = passwordField.getText();
				try {
					if(a.login(Username, Password, shell)==1) {
						BankGui window = new BankGui(Username);
						windowCloseLogin(shell);
						
						window.open();
						
						
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		Button createAccountButton = new Button(shell, SWT.NONE);
		createAccountButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Username= usernameField.getText();
				Password = passwordField.getText();
				try {
					a.createAccount(Username, Password, 0.0, 0.0,shell);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		createAccountButton.setText("Create Account");
		createAccountButton.setBounds(49, 166, 142, 61);
		
		usernameField = new Text(shell, SWT.BORDER);
		usernameField.setBounds(115, 37, 172, 21);
		
		passwordField = new Text(shell, SWT.BORDER);
		passwordField.setEchoChar('*');
		passwordField.setBounds(115, 82, 172, 21);
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBounds(41, 43, 55, 15);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setBounds(41, 88, 55, 15);

	}

	private static void windowCloseLogin(Shell shell) {
		shell.close();
	}
}
