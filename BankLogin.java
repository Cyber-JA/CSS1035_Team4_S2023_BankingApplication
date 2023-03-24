package BankingAccount;

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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.SWTResourceManager;
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
	protected Shell shlSjuBankLogin;
	
	
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
		shlSjuBankLogin.open();
		shlSjuBankLogin.layout();
		while (!shlSjuBankLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSjuBankLogin = new Shell();
		shlSjuBankLogin.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\dckfa\\Downloads\\ui-ux-modernization-hero.png"));
		//shlSjuBankLogin.setImage(SWTResourceManager.getImage(BankLogin.class, "/org/eclipse/jface/fieldassist/images/errorqf_ovr@2x.png"));
		shlSjuBankLogin.setBackground(SWTResourceManager.getColor(192, 192, 192));
		shlSjuBankLogin.setSize(517, 384);
		shlSjuBankLogin.setText("SJU Bank Login");
		
		Button loginButton = new Button(shlSjuBankLogin, SWT.NONE);
		loginButton.setForeground(SWTResourceManager.getColor(94, 94, 221));
		loginButton.setImage(SWTResourceManager.getImage("C:\\Users\\dckfa\\Downloads\\button_login(1).png"));
		loginButton.setBounds(265, 234, 158, 65);
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Username= usernameField.getText();
				Password = passwordField.getText();
				try {
					if(a.login(Username, Password, shlSjuBankLogin)==1) {
						BankGui window = new BankGui(Username);
						windowCloseLogin(shlSjuBankLogin);
						
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
		
		Button createAccountButton = new Button(shlSjuBankLogin, SWT.NONE);
		createAccountButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		createAccountButton.setImage(SWTResourceManager.getImage("C:\\Users\\dckfa\\Downloads\\button_create-account.png"));
		createAccountButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Username= usernameField.getText();
				Password = passwordField.getText();
				try {
					a.createAccount(Username, Password, 0.0, 0.0,shlSjuBankLogin);
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
		createAccountButton.setBounds(45, 234, 214, 65);
		
		usernameField = new Text(shlSjuBankLogin, SWT.BORDER);
		usernameField.setForeground(SWTResourceManager.getColor(94, 94, 221));
		usernameField.setBackground(SWTResourceManager.getColor(192, 192, 192));
		usernameField.setBounds(189, 70, 124, 33);
		
		passwordField = new Text(shlSjuBankLogin, SWT.BORDER | SWT.PASSWORD);
		passwordField.setBackground(SWTResourceManager.getColor(192, 192, 192));
		passwordField.setEchoChar('*');
		passwordField.setBounds(189, 111, 124, 31);
		
		Label lblUsername = new Label(shlSjuBankLogin, SWT.NONE);
		lblUsername.setFont(SWTResourceManager.getFont("Microsoft Sans Serif", 12, SWT.BOLD));
		lblUsername.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblUsername.setForeground(SWTResourceManager.getColor(230, 230, 230));
		lblUsername.setImage(SWTResourceManager.getImage(BankLogin.class, "/icons/full/help.png"));
		lblUsername.setBounds(88, 72, 86, 31);
		lblUsername.setText("Username:");
		
		Label lblPassword = new Label(shlSjuBankLogin, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Microsoft Sans Serif", 12, SWT.BOLD));
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblPassword.setForeground(SWTResourceManager.getColor(230, 230, 230));
		lblPassword.setText("Password:");
		lblPassword.setBounds(88, 111, 86, 21);
		
		Button btnNewButton = new Button(shlSjuBankLogin, SWT.FLAT);
		btnNewButton.setForeground(SWTResourceManager.getColor(42, 42, 42));
		btnNewButton.setBackground(SWTResourceManager.getColor(42, 42, 42));
		btnNewButton.setImage(SWTResourceManager.getImage(BankLogin.class, "/icons/full/help.png"));
		btnNewButton.setBounds(319, 70, 25, 21);
		
		Button btnNewButton_1 = new Button(shlSjuBankLogin, SWT.FLAT);
		btnNewButton_1.setForeground(SWTResourceManager.getColor(42, 42, 42));
		btnNewButton_1.setBackground(SWTResourceManager.getColor(42, 42, 42));
		btnNewButton_1.setImage(SWTResourceManager.getImage(BankLogin.class, "/icons/full/help.png"));
		btnNewButton_1.setBounds(319, 111, 25, 21);

	}

	private static void windowCloseLogin(Shell shell) {
		shell.close();
	}
}
