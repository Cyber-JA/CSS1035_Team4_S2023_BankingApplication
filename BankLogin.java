import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.awt.Component;
import java.io.PrintStream;
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
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.apache.logging.log4j.*;

interface GlobalConstants {
	String uname = "Chilly Billy";
}

public class BankLogin {
	private static final Pattern pattern = Pattern.compile("^[\\s\\w\\W]{0,20}$");
	static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
	static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
	static final Logger logger = LogManager.getLogger(BankLogin.class);
	static String Username = null;
	static String Password = null;

	database a = new database();
	protected Shell shell;

	private Text usernameField;
	private Text passwordField;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	static BankLogin window = new BankLogin();

	public static void main(String[] args) {

		try {
			database.setConnectionUrl();
			setUTF8systemout();
			window.open();
		} catch (Exception e) {
			logger.error("Exception ", e);
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
				Username = usernameField.getText();
				Password = passwordField.getText();
				try {
					validate(Username, Username);
					validate(Password, Password);
					if (a.login(Username, Password, shell) == 1) {
						/*
						 * This is a sensitive component. According to the CMU SEI: "A log injection
						 * vulnerability arises when a log entry contains unsanitized user input. A
						 * malicious user can insert fake log data and consequently deceive system
						 * administrators as to the system's behavior. Log injection attacks can be
						 * prevented by sanitizing and validating any untrusted input sent to a log.
						 * Logging unsanitized user input can also result in leaking sensitive data
						 * across a trust boundary." The input is validated, prior the method's call.
						 * RISK ASSESSMENT: Allowing unvalidated user input to be logged can result in
						 * forging of log entries, leaking secure information, or storing sensitive data
						 * in a manner that violates a local law or regulation. Rule: IDS03-J Severity:
						 * Medium Likelihood Probable Remediation Cost: Medium Priority: P8 Level: L2
						 */
						logger.info("Login with ", Username, Password);
						BankGui window = new BankGui(Username);
						windowCloseLogin(shell);
						window.open();
					} else {
						/*
						 * This is a sensitive component. According to the CMU SEI: "A log injection
						 * vulnerability arises when a log entry contains unsanitized user input. A
						 * malicious user can insert fake log data and consequently deceive system
						 * administrators as to the system's behavior. Log injection attacks can be
						 * prevented by sanitizing and validating any untrusted input sent to a log.
						 * Logging unsanitized user input can also result in leaking sensitive data
						 * across a trust boundary." The input is validated, prior the method's call.
						 * RISK ASSESSMENT: Allowing unvalidated user input to be logged can result in
						 * forging of log entries, leaking secure information, or storing sensitive data
						 * in a manner that violates a local law or regulation. Rule: IDS03-J Severity:
						 * Medium Likelihood Probable Remediation Cost: Medium Priority: P8 Level: L2
						 */
						logger.error("Wrong login ", Username, Password);
					}
				} catch (UnsupportedEncodingException e1) {
					logger.error("Exception ", e1);
				} catch (NoSuchAlgorithmException e1) {
					logger.error("Exception ", e1);
				} catch (SQLException e1) {
					logger.error("Exception ", e1);
				}

			}
		});

		Button createAccountButton = new Button(shell, SWT.NONE);
		createAccountButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Username = usernameField.getText();
				Password = passwordField.getText();
				try {
					a.createAccount(Username, Password, 0.0, 0.0, shell);
				} catch (UnsupportedEncodingException e1) {
					logger.error("Exception ", e1);
				} catch (NoSuchAlgorithmException e1) {
					logger.error("Exception ", e1);
				} catch (SQLException e1) {
					logger.error("Exception ", e1);
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

	/**
	 * Wrapper method to set the UTF-8 Encoding.
	 * 
	 * @throws UnsupportedEncodingException Exception thrown in case of unsupported
	 *                                      encoding.
	 */
	public static void setUTF8systemout() throws UnsupportedEncodingException {
		PrintStream out = new PrintStream(System.out, true, "UTF-8"); // PrintStream object with UTF-8 encoding
		System.setOut(out); // set console printing to the new PrintStream object we declared.
	}

	/**
	 * Method used to normalize user input according to the specified Normalizer.
	 * {@link java.text.Normalizer.Form#NFKC}
	 * 
	 * @param input Untrusted input to normalize.
	 * 
	 * @return canonical returned normalized input.
	 */
	public static String normalize(String input) {
		String canonical = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFKC);
		return canonical;
	}

	/**
	 * Performing encoding of invalid characters before outputting it into stdout.
	 * 
	 * @param input Receiving normalized, then validated input.
	 * 
	 * @return encoded input.
	 */
	public static String HTMLEntityEncode(String input) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
				sb.append(ch);
			} else {
				sb.append("&#" + (int) ch + ";");
			}
		}
		return sb.toString();
	}

	/**
	 * In this method are performed both validation and normalization of user's
	 * input. Normalization is performed with the normalize method. In particular,
	 * related to the validation, the check is performed against a specified patter.
	 * At the end, output encoding for nonvalid characters is performed and the
	 * value returned.
	 * 
	 * @see normalize
	 * 
	 * 
	 * @param name  Input acquired by the thrown exception
	 * 
	 * @param input Input normalized and then validated.
	 * 
	 * @throws DataValidationException Exception thrown if, after normalizing, when
	 *                                 validating data, is not properly formatted.
	 * 
	 * @return canonical returned normalized, validated, HTML entity encoded input.
	 */
	public static String validate(String name, String input) throws DataValidationException {
		/*
		 * This is a sensitive part, in which normalization is performed. According to
		 * the CMU SEI guide: "Applications that accept untrusted input should normalize
		 * the input before validating it. Normalization is important because in
		 * Unicode, the same string can have many different representations." RISK
		 * ASSESSMENT: Validating input before normalization affords attackers the
		 * opportunity to bypass filters and other security mechanisms. It can result in
		 * the execution of arbitrary code. Rule: IDS01-J Severity: High Likelihood
		 * Probable Remediation Cost: Medium Priority: P12 Level: L1
		 */
		String canonical = normalize(input);
		// validating
		if (!pattern.matcher(canonical).matches()) {
			throw new DataValidationException("Improper format in " + name + " field");
		}

		// Performs output encoding for nonvalid characters
		canonical = HTMLEntityEncode(canonical);
		return canonical;
	}

}
