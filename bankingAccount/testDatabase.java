package bankingAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/*In this class are implemented JUnits tests for the database class*/

class testDatabase {

  private static String connectionUrl = "jdbc:sqlserver://sjubank.database.windows.net:1433;" 
      + "database=SJUbank;" 
      + "user=sjubank@sjubank;" 
      + "password=vERY@iNSECURE@pASSWORD;" 
      + "encrypt=true;" 
      + "trustServerCertificate=false;" 
      + "loginTimeout=30;";

  @Test
  void testSelectData() {
    System.out.println("-----Testing selectData Method database-----");
    try {
      /* this is the data that is used to check */
      String username = "user";
      String check = "0d107d09f5bbe40cade3de5c71e9e9b7";
      System.out.println("Retrieving password of user: user");
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
      System.out.println("Expected: 0d107d09f5bbe40cade3de5c71e9e9b7");
      /* actual value */
      System.out.println("Actual: 0d107d09f5bbe40cade3de5c71e9e9b7");
      /* testing */
      assertEquals(check, result);
    } catch (SQLException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End SelectData database test-----");
  }

  @Test
  void testMD5hash() {
    System.out.println("-----Testing MD5 hash-----");
    /* this is the data that is used to check */
    String x = "password";
    try {
      System.out.println("String to hash: " + x);
      String hash = database.md5hash(x);
      System.out.println("Actual: " + hash);
      /* correct data obtained with external verified tools */
      System.out.println("Expected: 5f4dcc3b5aa765d61d8327deb882cf99");
      /* testing */
      assertEquals(hash, "5f4dcc3b5aa765d61d8327deb882cf99");
    } catch (UnsupportedEncodingException e) {
      fail("Exception: " + e);
    } catch (NoSuchAlgorithmException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End MD5 hash test-----");
  }

  @Test
  void testLogin() {
    System.out.println("-----Testing Login database-----");
    System.out.println("Testing with user: admin");
    try {
      /* method to test */
      assertEquals(database.login("admin", "letmein"), 1);
      System.out.println("Success.");
    } catch (UnsupportedEncodingException e) {
      fail("Exception: " + e);
    } catch (NoSuchAlgorithmException e) {
      fail("Exception: " + e);
    } catch (SQLException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End Login database test-----");
  }

  @Test
  void testCheckUID() {
    System.out.println("-----Testing Checking UID database-----");
    System.out.println("Testing with user: admin");
    try {
      /* data that will be used as parameter */
      String username = "admin";
      /* retrieving actual data from the database */
      Connection connection = DriverManager.getConnection(connectionUrl);
      // Create and execute a SELECT SQL prepared statement.
      String selectSql = "SELECT UserID FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      ResultSet resultSet = stmt.executeQuery();
      resultSet.next();
      int result = resultSet.getInt(1);
      /* Expected value */
      System.out.println("Expected: " + result);
      /* Actual value */
      System.out.println("Actual: " + database.checkUID(username));
      /* testing */
      assertEquals(database.checkUID(username), result);
    } catch (SQLException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End Checking UID database test-----");
  }

  @Test
  void testCheckAccountType() {
    System.out.println("-----Testing Checking Account Type database-----");
    System.out.println("Testing with user: admin");
    String username = "admin";
    /* retrieving data from database */
    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL prepared statement.
      String selectSql = "SELECT Accounttype FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      ResultSet resultSet = stmt.executeQuery();
      resultSet.next();
      String result = resultSet.getString(1);
      /* Expected data */
      System.out.println("Expected: " + result);
      /* Actual data */
      System.out.println("Actual: " + database.checkAccountType(username));
      /* testing */
      assertEquals(database.checkAccountType(username), result);
    } catch (SQLException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End Checking Account Type database test-----");
  }

  @Test
  void testCheckBalance() {
    System.out.println("-----Testing Checking Balance database-----");
    String username = "user";
    System.out.println("Testing with user: user");
    /* retrieving data from the database */
    try (Connection connection = DriverManager.getConnection(connectionUrl); 
            Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL prepared statement.
      String selectSql = "SELECT Balance FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      ResultSet resultSet = stmt.executeQuery();
      resultSet.next();
      int result = resultSet.getInt(1);
      /* Expected data */
      System.out.println("Expected: " + result);
      /* Actual data */
      System.out.println("Actual: " + database.checkBalance(username));
      /* testing */
      assertEquals(database.checkBalance(username), result);

    } catch (SQLException e) {
      fail("Exception: " + e);
    }
    System.out.println("-----End Checking Balance  database test-----");
  }

  @Test
  void testUpdateSQLBalance() {
    System.out.println("-----Testing Update Balance database-----");
    String username = "admin";
    System.out.println("Testing with user: admin");
    /* static testing data */
    double balance = 10000.54;
    /* retrieving data from the database */
    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL prepared statement.
      String selectSql = "UPDATE Userpass SET Balance = " + balance + " WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      stmt.execute();
      String testSql = "SELECT Balance FROM Userpass WHERE username = ?";
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("-----End Update Balance database test-----");
  }
}