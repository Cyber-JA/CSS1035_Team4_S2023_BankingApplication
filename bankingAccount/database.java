package bankingAccount;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class implementing the connection with the database and it's used
 * functionalities The Database contains a table named Userpass, in which
 * different information are specified: -UserID, unique for each user -Balance
 * -Account type (e.g. checking or savings) -Withdrawals available if savings
 * type -overdraft counter if checking type -hash of the password inserted by
 * the user, used into the login procedure
 */
public class database {

  private static String connectionUrl = "jdbc:sqlserver://sjubank.database.windows.net:1433;" 
      + "database=SJUbank;" 
      + "user=sjubank@sjubank;" 
      + "password=vERY@iNSECURE@pASSWORD;" 
      + "encrypt=true;" 
        + "trustServerCertificate=false;" 
          + "loginTimeout=30;";

  /**
   * function used to compute the MD5 hash over the password. This is then used to
   * check into login function with the data contained into the database
   */
  public static String md5hash(String password) 
      throws UnsupportedEncodingException, NoSuchAlgorithmException {
    String x = password;
    byte[] bytesOfMessage = x.getBytes("UTF-8");
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] messageDigest = md.digest(bytesOfMessage);
    BigInteger no = new BigInteger(1, messageDigest);
    String hashtext = no.toString(16);
    while (hashtext.length() < 32) {
      hashtext = "0" + hashtext;
    }
    return hashtext;
  }
  
  /**
   * Method used to select hash in db. 
   */
  public static String selectData(String username) throws SQLException {
    String result = null;
    ResultSet resultSet = null;

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
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

  /**
   * This function is used to perform the login, checking the data inserted by the
   * user with the one contained into the database. The MD5Hash is used to perform
   * this computation.
   */
  public static int login(String username, String password)
      throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
    String uname = selectData(username);
    String pwd = md5hash(password);
    if (!uname.isEmpty() && uname.contains(pwd)) {
      return 1;
    } else {
      System.out.println("wrong password");
      return 0;
    }
  }

  /**
   * This method is used to retrieve the UserID of the user whose username is
   * being specified.
   */
  public static int checkUID(String username) throws SQLException {
    int result = 0;
    ResultSet resultSet = null;

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
      String selectSql = "SELECT UserID FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      resultSet.next();
      result = resultSet.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * This method is used to check which kind of account type is contained into the
   * db associated with the user specified as parameter.
   */
  public static String checkAccountType(String username) throws SQLException {
    String result = null;
    ResultSet resultSet = null;

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
      String selectSql = "SELECT Accounttype FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      resultSet.next();
      result = resultSet.getString(1);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Method used to retrieve the balance of the user whose username is specified
   * as parameter.
   */
  public static int checkBalance(String username) throws SQLException {
    int result = 0;
    ResultSet resultSet = null;

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
      String selectSql = "SELECT Balance FROM Userpass WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      resultSet.next();
      result = resultSet.getInt(1);
      // Print results from select statement

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * This method is used to update the balance column of the specified user into
   * the database.
   */
  public static void updateSQLBalance(String username, double balance) throws SQLException {

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
      String selectSql = "UPDATE Userpass SET Balance = " + balance + " WHERE username = ?";
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}