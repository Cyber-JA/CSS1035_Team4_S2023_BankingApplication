
import java.io.UnsupportedEncodingException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.apache.logging.log4j.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * Class implementing the connection with the database and it's used
 * functionalities The Database contains a table named Userpass, in which
 * different information are specified: 1.UserID, unique for each user 2.Balance
 * 3.Account type (e.g. checking or savings) 4.Withdrawals available if savings
 * type 5.overdraft counter if checking type 6.hash of the password inserted by
 * the user, used into the login procedure.
 * 
 * SECURE PRACTICE: In the database, is not stored the password itself, but an
 * hash of it. Then, after the user insert the password, the hash of this
 * password is computed and compared to the stored one. Future implementation
 * will provide a salt to harden the security and avoid some categories of
 * attacks.
 */
public class database {
	static final Logger logger = LogManager.getLogger(BankLogin.class);
	private static String connectionUrl;

	/**
	 * function used to compute the MD5 hash over the password. This is then used to
	 * check into login function with the data contained into the database
	 * 
	 * 
	 * @param password Password inserted by the user in the login phase.
	 * 
	 * @throws UnsupportedEncodingException Exception thrown when the password
	 *                                      inserted by the user is not supported by
	 *                                      the encoding used into the program. This
	 *                                      is unlikely, since the UTF-8 Encoding is
	 *                                      adopted.
	 * 
	 * @throws NoSuchAlgorithmException     Exception thrown in case the algorithm
	 *                                      used to perform the digest into
	 *                                      performing the login is not a valid one.
	 * 
	 * @return hashtext Hash of the password inserted by the user. N.B. The stored
	 *         value is never exposed.
	 */

	public static void setConnectionUrl() {
		String password = CryptographicServices.decryptAndRetrievePasswordToAccessDatabase();
		connectionUrl = "jdbc:sqlserver://sjubank.database.windows.net:1433;" + "database=SJUbank;"
				+ "user=sjubank@sjubank;" + "password=" + password + ";" + "encrypt=true;"
				+ "trustServerCertificate=false;" + "loginTimeout=30;";
	}

	/**
	 * Method used to select hash in db. SECURE PRACTICE: A prepared statement is
	 * used into quering the database, to avoid SQLInjection attacks.
	 * 
	 * @param username Username inserted by the user into the login phase.
	 * 
	 * @throws SQLException Exception thrown when the database is queried with a
	 *                      malformed statement.
	 * 
	 * @return result Value stored into the db
	 * 
	 * @see SQLException
	 * 
	 * @see PreparedStatement
	 */
	public static String selectData(String username) throws SQLException {
		String result = null;
		ResultSet resultSet = null;
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			/*
			 * Create and execute a SELECT SQL statement. This is a sensitive component
			 * since, if not implemented properly, would allow to perform SQL injection.
			 * According to the CMU SEI: "The JDBC library provides an API for building SQL
			 * commands that sanitize untrusted data. The java.sql.PreparedStatement class
			 * properly escapes input strings, preventing SQL injection when used correctly.
			 * Use the set*() methods of the PreparedStatement class to enforce strong type
			 * checking. This technique mitigates the SQL injection vulnerability because
			 * the input is properly escaped by automatic entrapment within double quotes.
			 * Note that prepared statements must be used even with queries that insert data
			 * into the database." RISK ASSESSMENT: "Failure to sanitize user input before
			 * processing or storing it can result in injection attacks. Rule: IDS00-J
			 * Severity: High Likelihood Probable Remediation Cost: Medium Priority: P12
			 * Level: L1
			 */
			String selectSql = "SELECT Password FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			resultSet = stmt.executeQuery();
			resultSet.next();
			result = resultSet.getString(1);

		} catch (SQLException e) {
			System.out.println("Cannot find a result");
			return "";
		}
		return result;
	}

	public static String checkSalt(String username) throws SQLException {
		String result = null;
		ResultSet resultSet = null;

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Create and execute a SELECT SQL statement.
			String selectSql = "SELECT salt FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			resultSet = stmt.executeQuery();
			if (resultSet.next() == false) {
				System.out.println("ResultSet in empty in Java");

			} else {
				result = resultSet.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This function is used to perform the login, checking the data inserted by the
	 * user with the one contained into the database. The MD5Hash is used to perform
	 * this computation.
	 * 
	 * @param username Username inserted by the user into the login phase.
	 * 
	 * @param password Password inserted by the user into the login phase.
	 * 
	 * @return int Binary value indicating if the login procedure proceeded
	 *         correctly or not.
	 * 
	 * @throws UnsupportedEncodingException Exception thrown when the password
	 *                                      inserted by the user is not supported by
	 *                                      the encoding used into the program. This
	 *                                      is unlikely, since the UTF-8 Encoding is
	 *                                      adopted.
	 * 
	 * @throws NoSuchAlgorithmException     Exception thrown in case the algorithm
	 *                                      used to perform the digest into
	 *                                      performing the login is not a valid one.
	 * 
	 * @throws SQLException                 Exception thrown in case of malformed
	 *                                      SQL statement.
	 * 
	 * @see SQLException
	 */

	public static String getSecurePassword(String password, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static int login(String username, String password, Shell shell)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
		setConnectionUrl();
		String storedHashedPassword = selectData(username);
		String salt = checkSalt(username).trim();
		String hashedEnteredPassword = getSecurePassword(password, salt);
		/*
		 * This is a sensitive part in which, using unsanitized user input in format
		 * string, would cause DoS or information leak. According to the CMU SEI: "The
		 * standard library implementations throw an exception when any conversion
		 * argument fails to match the corresponding format specifier. Although throwing
		 * an exception helps mitigate against exploits, if untrusted data is
		 * incorporated into a format string, it can result in an information leak or
		 * allow a denial-of-service attack. Consequently, unsanitized input from an
		 * untrusted source must never be incorporated into format strings." The input
		 * used within the following function, is sanitized prior the method call. RISK
		 * ASSESSMENT: Incorporating untrusted data in a format string may result in
		 * information leaks or allow a denial-of-service attack. Rule: IDS06-J
		 * Severity: Medium Likelihood Unlikely Remediation Cost: Medium Priority: P4
		 * Level: L3
		 */
		if (!storedHashedPassword.isEmpty() && storedHashedPassword.trim().equals(hashedEnteredPassword.trim())) {
			return 1;
		} else {
			MessageBox box = new MessageBox(shell, SWT.OK);
			box.setText("Error");
			box.setMessage("Your username/password is invalid.");
			box.open();
			return 0;
		}
	}

	/**
	 * This method is used to retrieve the UserID of the user whose username is
	 * being specified.
	 * 
	 * SECURE PRACTICE: A prepared statement is used into quering the database, to
	 * avoid SQLInjection attacks.
	 * 
	 * @param username Username inserted by the user into the login phase.
	 * 
	 * @return result Data retrieved from the database.
	 * 
	 * @throws SQLException Exception thrown in case of a malformed SQL request.
	 * 
	 * @see SQLException
	 * 
	 * @see PreparedStatement
	 */
	public static int checkUID(String username) throws SQLException {
		int result = 0;
		ResultSet resultSet = null;
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			/*
			 * Create and execute a SELECT SQL statement. This is a sensitive component
			 * since, if not implemented properly, would allow to perform SQL injection.
			 * According to the CMU SEI: "The JDBC library provides an API for building SQL
			 * commands that sanitize untrusted data. The java.sql.PreparedStatement class
			 * properly escapes input strings, preventing SQL injection when used correctly.
			 * Use the set*() methods of the PreparedStatement class to enforce strong type
			 * checking. This technique mitigates the SQL injection vulnerability because
			 * the input is properly escaped by automatic entrapment within double quotes.
			 * Note that prepared statements must be used even with queries that insert data
			 * into the database." RISK ASSESSMENT: "Failure to sanitize user input before
			 * processing or storing it can result in injection attacks. Rule: IDS00-J
			 * Severity: High Likelihood Probable Remediation Cost: Medium Priority: P12
			 * Level: L1
			 */
			String selectSql = "SELECT UserID FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			resultSet = stmt.executeQuery();
			if (resultSet.next() == false) {
				System.out.println("ResultSet in empty in Java");
				return -1;
			} else {
				result = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method used to retrieve the balance of the user whose username is specified
	 * as parameter.
	 * 
	 * SECURE PRACTICE: A prepared statement is used into quering the database, to
	 * avoid SQLInjection attacks.
	 * 
	 * @param username Username inserted by the user into the login phase.
	 *
	 * @throws SQLException Exception thrown in case of a malformed SQL request.
	 * 
	 * @return result REsult of the query to the database.
	 * 
	 * @see SQLException
	 * 
	 * @see PreparedStatement
	 */
	public static double checkBalance(String username, String AccountType) throws SQLException {
		double result = 0;
		ResultSet resultSet = null;
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			/*
			 * Create and execute a SELECT SQL statement. This is a sensitive component
			 * since, if not implemented properly, would allow to perform SQL injection.
			 * According to the CMU SEI: "The JDBC library provides an API for building SQL
			 * commands that sanitize untrusted data. The java.sql.PreparedStatement class
			 * properly escapes input strings, preventing SQL injection when used correctly.
			 * Use the set*() methods of the PreparedStatement class to enforce strong type
			 * checking. This technique mitigates the SQL injection vulnerability because
			 * the input is properly escaped by automatic entrapment within double quotes.
			 * Note that prepared statements must be used even with queries that insert data
			 * into the database." RISK ASSESSMENT: "Failure to sanitize user input before
			 * processing or storing it can result in injection attacks. Rule: IDS00-J
			 * Severity: High Likelihood Probable Remediation Cost: Medium Priority: P12
			 * Level: L1
			 */
			String selectSql = null;
			if (AccountType.equalsIgnoreCase("Checking")) {
				selectSql = "SELECT CheckingBalance FROM Userpass WHERE username = ?";
			}
			if (AccountType.equalsIgnoreCase("Savings")) {
				selectSql = "SELECT SavingsBalance FROM Userpass WHERE username = ?";
			}
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			resultSet = stmt.executeQuery();
			resultSet.next();
			result = resultSet.getDouble(1);
			// Print results from select statement

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method is used to update the balance column of the specified user into
	 * the database.
	 * 
	 * SECURE PRACTICE: A prepared statement is used into quering the database, to
	 * avoid SQLInjection attacks.
	 * 
	 * @param username Username of the user currently logged in.
	 * 
	 * @param balance  Future balance of the user that will be inserted into the
	 *                 database.
	 * 
	 * @throws SQLException Exception thrown in case of a malformed SQL request.
	 * 
	 * @see SQLException
	 * 
	 * @see PreparedStatement
	 */
	public static void updateSQLBalance(String username, double balance, String AccountType) throws SQLException {
		String selectSql = null;
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			if (AccountType.equalsIgnoreCase("Checking")) {
				selectSql = "UPDATE Userpass SET CheckingBalance = " + balance + " WHERE username = ?";
			}
			if (AccountType.equalsIgnoreCase("Savings")) {
				selectSql = "UPDATE Userpass SET SavingsBalance = " + balance + " WHERE username = ?";
			}
			/*
			 * Create and execute a SELECT SQL statement. This is a sensitive component
			 * since, if not implemented properly, would allow to perform SQL injection.
			 * According to the CMU SEI: "The JDBC library provides an API for building SQL
			 * commands that sanitize untrusted data. The java.sql.PreparedStatement class
			 * properly escapes input strings, preventing SQL injection when used correctly.
			 * Use the set*() methods of the PreparedStatement class to enforce strong type
			 * checking. This technique mitigates the SQL injection vulnerability because
			 * the input is properly escaped by automatic entrapment within double quotes.
			 * Note that prepared statements must be used even with queries that insert data
			 * into the database." RISK ASSESSMENT: "Failure to sanitize user input before
			 * processing or storing it can result in injection attacks. Rule: IDS00-J
			 * Severity: High Likelihood Probable Remediation Cost: Medium Priority: P12
			 * Level: L1
			 */
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Implementation of method with generic type on ArrayList
	 */
	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E[]> generateTransactionHistory(String UID) throws SQLException {
		setConnectionUrl();
		ArrayList<E[]> rowList = new ArrayList<E[]>();
		ResultSet resultSet = null;

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			PreparedStatement stmt = connection.prepareStatement(
					"SELECT TransactionDate, TransactionType, Amount, AccountType FROM transactionH1 WHERE UserID = ?");
			stmt.setString(1, UID);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				// ArrayList<String> rowList = new ArrayList<String>();
				String TransactionDate = resultSet.getString("TransactionDate");
				String TransactionType = resultSet.getString("TransactionType");
				float Amount = resultSet.getFloat("Amount");
				String AccountType = resultSet.getString("AccountType");
				rowList.add(
						(E[]) new String[] { TransactionDate, TransactionType, Double.toString(Amount), AccountType });

				// dataList.add(rowList);
			}
			stmt.close();
			// Print results from select statement

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowList;
	}

	public static void updateTransactionHistory(String TransactionType, double amount1, int UID, String accountType)
			throws SQLException {
		setConnectionUrl();
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date = dateObj.format(formatter);

		String selectSql = "INSERT INTO transactionH1 (TransactionDate, TransactionType, Amount, UserID, AccountType) "
				+ "VALUES (" + "'" + date + "'" + ", " + "'" + TransactionType + "'" + ", " + amount1 + ", " + UID
				+ ", " + "'" + accountType + "'" + ");";

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			statement.execute(selectSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getRowCount() throws SQLException {
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {
			String selectSql = "SELECT count(*) FROM Userpass";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}

	}

	public int createAccount(String UserName, String Password, double CheckingBalance, double SavingsBalance,
			Shell shell) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
		setConnectionUrl();
		if (passwordRequirements(Password) != 1) {
			return -1; // returns -1 if password does not meet requirements
		}
		if (checkUID(UserName) == -1) {
			/*
			 * Create and execute a SELECT SQL statement. This is a sensitive component
			 * since, if not implemented properly, would allow to perform SQL injection.
			 * According to the CMU SEI: "The JDBC library provides an API for building SQL
			 * commands that sanitize untrusted data. The java.sql.PreparedStatement class
			 * properly escapes input strings, preventing SQL injection when used correctly.
			 * Use the set*() methods of the PreparedStatement class to enforce strong type
			 * checking. This technique mitigates the SQL injection vulnerability because
			 * the input is properly escaped by automatic entrapment within double quotes.
			 * Note that prepared statements must be used even with queries that insert data
			 * into the database." RISK ASSESSMENT: "Failure to sanitize user input before
			 * processing or storing it can result in injection attacks. Rule: IDS00-J
			 * Severity: High Likelihood Probable Remediation Cost: Medium Priority: P12
			 * Level: L1
			 */
			try (Connection connection = DriverManager.getConnection(connectionUrl);
					Statement statement = connection.createStatement();) {
				String selectSql = "INSERT INTO Userpass (UserID, Username, Password,CheckingBalance,SavingsBalance,salt) "
						+ "VALUES (?, ?, ?, ?, ?, ?);";
				String salt = getSalt();
				PreparedStatement stmt = connection.prepareStatement(selectSql);
				stmt.setLong(1, getRowCount() + 1);
				stmt.setString(2, UserName);
			    stmt.setString(3, getSecurePassword(Password,salt).trim());
				stmt.setDouble(4, CheckingBalance);
				stmt.setDouble(5, SavingsBalance);
				stmt.setString(6, salt.trim());
				stmt.execute();
				MessageBox box = new MessageBox(shell, SWT.OK);
				box.setText("Success");
				box.setMessage("Account has been created please login.");
				box.open();
				return 1; // returns 1 on successful account creation
			}
		} else {
			MessageBox box = new MessageBox(shell, SWT.OK);
			box.setText("Error");
			box.setMessage("Account already exists.");
			box.open();
			return 0; // returns 0 if account exists
		}
	}

	protected static int passwordRequirements(String Password) {
		setConnectionUrl();
		String str = Password;
		int specials = 0, digits = 0, letters = 0, satisfied = 0;
		while (satisfied == 0) {
			for (int i = 0; i < str.length(); ++i) {
				char ch = str.charAt(i);
				if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isWhitespace(ch)) {
					++specials;
				} else if (Character.isDigit(ch)) {
					++digits;
				} else {
					++letters;
				}
			}
			if (letters >= 8 && digits >= 1 && specials >= 1) {
				satisfied = 1;
				return satisfied;
			} else {
				System.out.println("Password needs to respect the following criteria:");
				System.out.println("1. At least 8 letters");
				System.out.println("2. At least 1 digit");
				System.out.println("3. At least 1 special char");
				return satisfied;
			}
		}
		return satisfied;
	}

	public static void updateVlog(String ViolationType, String UID) throws SQLException {
		setConnectionUrl();
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date = dateObj.format(formatter);

		String selectSql = "INSERT INTO ViolationLog (UID, Date, Type) " + "VALUES (" + "'" + UID + "'" + ", " + "'"
				+ date + "'" + ", " + "'" + ViolationType + "'" + ");";

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			statement.execute(selectSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}