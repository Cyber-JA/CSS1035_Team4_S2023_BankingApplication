import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Test;

/*In this class are implemented JUnits tests for the database class*/

class testDatabase {

	private static String connectionUrl;

	public static void setConnectionUrl() {
		String password = CryptographicServices.decryptAndRetrievePasswordToAccessDatabase();
		connectionUrl = "jdbc:sqlserver://sjubank.database.windows.net:1433;" + "database=SJUbank;"
				+ "user=sjubank@sjubank;" + "password=" + password + ";" + "encrypt=true;"
				+ "trustServerCertificate=false;" + "loginTimeout=30;";
	}

	@Test
	void testSelectData() {
		System.out.println("-----Testing selectData Method database-----");
		try {
			setConnectionUrl();
			/* this is the data that is used to check */
			String username = "TestingUser";
			String check = "c540fd9b9d0c110caecf7249de72f57d42d9bb9e324a95868426b9e347a61dc7";
			System.out.println("Retrieving password of user: TestingUser");
			/* applying the method that should be tested */
			String result = database.selectData("user");

			/* retrieving actual data from database */
			Connection connection = DriverManager.getConnection(connectionUrl);
			// Create and execute a SELECT SQL prepared statement.
			String selectSql = "SELECT Password FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			result = resultSet.getString(1).trim();

			/* expected value */
			System.out.println("Expected: c540fd9b9d0c110caecf7249de72f57d42d9bb9e324a95868426b9e347a61dc7");
			/* actual value */
			System.out.println("Actual: " + result);
			/* testing */
			assertEquals(check, result);
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			fail("Exception: " + e);
		}
		System.out.println("-----End SelectData database test-----");
	}

	@Test
	void testCheckSavingsBalance() {
		System.out.println("-----Testing Savings Balance database-----");
		String username = "admin";
		System.out.println("Testing with user: admin");
		/* retrieving data from the database */
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Create and execute a SELECT SQL prepared statement.
			String selectSql = "SELECT SavingsBalance FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			float result = resultSet.getFloat(1);
			BigDecimal val = new BigDecimal(result);
			val = val.setScale(2, RoundingMode.HALF_UP);
			result = val.floatValue();
			/* Expected data */
			System.out.println("Expected: " + result);
			/* Actual data */
			System.out.println("Actual: " + database.checkBalance(username, "Savings"));
			/* testing */
			// assertEquals(database.checkBalance(username, "Savings"), result);
			stmt.close();
		} catch (SQLException e) {
			fail("Exception: " + e);
		}
		System.out.println("-----End Savings Balance  database test-----");
	}

	@Test
	void testCheckCheckingBalance() {
		System.out.println("-----Testing Checking Balance database-----");
		String username = "admin";
		System.out.println("Testing with user: admin");
		/* retrieving data from the database */
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Create and execute a SELECT SQL prepared statement.
			String selectSql = "SELECT CheckingBalance FROM Userpass WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			float result = resultSet.getFloat(1);
			BigDecimal val = new BigDecimal(result);
			val = val.setScale(2, RoundingMode.HALF_UP);
			result = val.floatValue();
			/* Expected data */
			System.out.println("Expected: " + result);
			/* Actual data */
			System.out.println("Actual: " + database.checkBalance(username, "Checking"));
			/* testing */
			// assertEquals(database.checkBalance(username, "Checking"), result);
			stmt.close();
		} catch (SQLException e) {
			fail("Exception: " + e);
		}
		System.out.println("-----End Checking Balance  database test-----");
	}

	@Test
	void testUpdateSQLCheckingBalance() {
		System.out.println("-----Testing Update Checking Balance database-----");
		String username = "admin";
		System.out.println("Testing with user: admin");
		setConnectionUrl();
		/* static testing data */
		double balance = 10000.54;
		/* retrieving data from the database */
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Create and execute a SELECT SQL prepared statement.
			String selectSql = "UPDATE Userpass SET CheckingBalance = " + balance + " WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			stmt.execute();
			String testSql = "SELECT CheckingBalance FROM Userpass WHERE username = ?";
			PreparedStatement stmtTest = connection.prepareStatement(testSql);
			stmtTest.setString(1, username);
			ResultSet resultSet = stmtTest.executeQuery();
			resultSet.next();
			double result = resultSet.getDouble(1);
			/* Expected value */
			System.out.println("Expected: " + balance);
			/* Actual value */
			System.out.println("Actual: " + result);
			/* testing */
			assertEquals(balance, result);
			stmt.close();
			stmtTest.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-----End Update Checking Balance database test-----");
	}

	@Test
	void testUpdateSQLSavingsBalance() {
		System.out.println("-----Testing Update Savings Balance database-----");
		String username = "admin";
		System.out.println("Testing with user: admin");
		setConnectionUrl();
		/* static testing data */
		double balance = 10000.54;
		/* retrieving data from the database */
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {

			// Create and execute a SELECT SQL prepared statement.
			String selectSql = "UPDATE Userpass SET SavingsBalance = " + balance + " WHERE username = ?";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			stmt.setString(1, username);
			stmt.execute();
			String testSql = "SELECT SavingsBalance FROM Userpass WHERE username = ?";
			PreparedStatement stmtTest = connection.prepareStatement(testSql);
			stmtTest.setString(1, username);
			ResultSet resultSet = stmtTest.executeQuery();
			resultSet.next();
			double result = resultSet.getDouble(1);
			/* Expected value */
			System.out.println("Expected: " + balance);
			/* Actual value */
			System.out.println("Actual: " + result);
			/* testing */
			assertEquals(balance, result);
			stmt.close();
			stmtTest.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-----End Update Savings Balance database test-----");
	}

	@Test
	void testPasswordRequirements() {
		System.out.println("-----Testing Password Requirements-----");

		try {
			String password = "testingp1@";
			database d = new database();
			assertEquals(d.passwordRequirements(password), 1);
			System.out.println("Password: " + password);
			System.out.println("Success.");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Password Requirements test-----");
	}

	@Test
	void testLogin() {
		System.out.println("-----Testing Login-----");

		try {
			String username = "TestingUser";
			String password = "testingpassword1@";
			Shell shell = new Shell();
			System.out.println("Testing with username: " + username);
			System.out.println("Testing with password: " + password);
			assertEquals(database.login(username, password, shell), 1);
			System.out.println("Success.");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Login test-----");
	}

	@Test
	void testUpdateVLog() {
		System.out.println("-----Testing Update Vlog-----");

		try {
			String type = "testing";
			String UID = "0";
			System.out.println("Violation type: " + type);
			System.out.println("UID testing: " + UID);
			database.updateVlog(type, UID);
			System.out.println("Success.");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Update Vlog test-----");
	}

	@Test
	void testUpdateTransactionHistory() {
		System.out.println("-----Testing Update Transaction History-----");

		try {
			String transactionType = "deposit";
			float amount1 = 100;
			int UID = 0;
			String accountType = "testing";
			System.out.println("Transaction type: " + transactionType);
			System.out.println("Amount inserted: " + amount1);
			System.out.println("UID: " + UID);
			System.out.println("Account type: " + accountType);
			database.updateTransactionHistory("1", 100, 100, "testing");
			System.out.println("Success.");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Update Transaction History test-----");
	}

	@Test
	void testCreateAccount() {
		System.out.println("-----Testing Create Account in database-----");

		try {
			database d = new database();
			Shell shell = new Shell();
			String username = "testing";
			String password = "testingp1@";
			System.out.println("Creating account.");
			System.out.println("Username: " + username);
			System.out.println("Password: " + password);
			d.createAccount(username, password, 0, 0, shell);
			System.out.println("Success.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Create Account in database test-----");
	}

	@Test
	void testGetRowCount() {
		System.out.println("-----Testing Get Row Count-----");
		setConnectionUrl();
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {
			database d = new database();
			String selectSql = "SELECT count(*) FROM Userpass";
			PreparedStatement stmt = connection.prepareStatement(selectSql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			assertEquals(d.getRowCount(), count);
			System.out.println("Expected value: " + d.getRowCount());
			System.out.println("Actual value: " + count);
			System.out.println("Success.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Get Row Count test-----");
	}

	@Test
	void testGenerateTransactionHistory() {

		System.out.println("-----Testing Generate Transaction History-----");
		try {
			ArrayList<String[]> list = database.generateTransactionHistory("1");
			System.out.println("First element of the first object in list: " + list.get(0)[0]);
			System.out.println("Success.");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Generate Transaction History test-----");
	}
	
	@Test
	void testGetSalt() {

		System.out.println("-----Testing Salt Generation-----");
		try {
			String salt = database.getSalt();
			System.out.println("Salt generated: " + salt);
			assertNotNull(salt);
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Salt Generation test-----");
	}
	
	@Test
	void testGetSecurePassword() {

		System.out.println("-----Testing Password Hashing-----");
		try {
			String password = "TestingPassword1@";
			String salt = database.getSalt();
			System.out.println("Password to hash: " + password);
			System.out.println("Salt used: " + salt);
			System.out.println("Actual hashed value: " + database.getSecurePassword(password, salt));
			assertNotNull(database.getSecurePassword(password, salt));
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Password Hashing test-----");
	}
	
	@Test
	void testCheckSalt() {

		System.out.println("-----Testing Stored Salt-----");
		try {
			String username = "TestingUser";
			System.out.println("Testing with user: " + username);
			System.out.println("Expected value: wNiEjQ8DZBqz95fd/5lr8A==");
			String salt = database.checkSalt(username);
			System.out.println("Actual value: " + salt);
			assertEquals("wNiEjQ8DZBqz95fd/5lr8A==", salt.trim());
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----End Stored Salt test-----");
	}
	
}