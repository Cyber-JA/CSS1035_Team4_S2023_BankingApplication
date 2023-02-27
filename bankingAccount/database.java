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
 * different information are specified: 1.UserID, unique for each user 2.Balance
 * 3.Account type (e.g. checking or savings) 4.Withdrawals available if savings
 * type 5.overdraft counter if checking type 6.hash of the password inserted by
 * the user, used into the login procedure.
 * 
 * SECURE PRACTICE: In the database, is not stored the password itself, but an hash of it.
 * Then, after the user insert the password, the hash of this password is computed and compared
 * to the stored one.
 * Future implementation will provide a salt to harden the security and avoid some categories of attacks.
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
   * 
   * 
   * @param password
   * Password inserted by the user in the login phase.
   * 
   * @throws UnsupportedEncodingException
   * Exception thrown when the password inserted by the user is not supported by the encoding used into the program.
   * This is unlikely, since the UTF-8 Encoding is adopted.
   * 
   * @throws NoSuchAlgorithmException
   * Exception thrown in case the algorithm used to perform the digest into performing the login is not a valid one.
   * 
   * @return hashtext
   * Hash of the password inserted by the user. N.B. The stored value is never exposed.
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
   * SECURE PRACTICE: A prepared statement is used into quering the database, to avoid SQLInjection attacks.
   * 
   * @param username
   * Username inserted by the user into the login phase.
   * 
   * @throws SQLException
   * Exception thrown when the database is queried with a malformed statement.
   * 
   * @return result
   * Value stored into the db
   * 
   * @see SQLException
   * 
   * @see PreparedStatement
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
   * 
   * @param username
   * Username inserted by the user into the login phase.
   * 
   * @param password
   * Password inserted by the user into the login phase.
   * 
   * @return int
   * Binary value indicating if the login procedure proceeded correctly or not.
   * 
   * @throws UnsupportedEncodingException
   * Exception thrown when the password inserted by the user is not supported by the encoding used into the program.
   * This is unlikely, since the UTF-8 Encoding is adopted.
   * 
   * @throws NoSuchAlgorithmException
   * Exception thrown in case the algorithm used to perform the digest into performing the login is not a valid one.
   * 
   * @throws SQLException
   * Exception thrown in case of malformed SQL statement.
   * 
   * @see SQLException
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
   * 
   * SECURE PRACTICE: A prepared statement is used into quering the database, to avoid SQLInjection attacks.
   * 
   * @param username
   * Username inserted by the user into the login phase.
   * 
   * @return result
   * Data retrieved from the database.
   * 
   * @throws SQLException
   * Exception thrown in case of a malformed SQL request.
   * 
   * @see SQLException
   * 
   * @see PreparedStatement
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
   * 
   * SECURE PRACTICE: A prepared statement is used into quering the database, to avoid SQLInjection attacks.
   * 
   * @param username
   * Username inserted by the user into the login phase.
   * 
   * @return result
   * Result of the database query.
   * 
   * @throws SQLException
   * Exception thrown in case of a malformed SQL request.
   * 
   * @see SQLException
   * 
   * @see PreparedStatement
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
   * 
   * SECURE PRACTICE: A prepared statement is used into quering the database, to avoid SQLInjection attacks.
   * 
   * @param username
   * Username inserted by the user into the login phase.
   *
   * @throws SQLException
   * Exception thrown in case of a malformed SQL request.
   * 
   * @return result
   * REsult of the query to the database.
   * 
   * @see SQLException
   * 
   * @see PreparedStatement
   */
  public static int checkBalance(String username, String AccountType) throws SQLException {
    int result = 0;
    ResultSet resultSet = null;

    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

      // Create and execute a SELECT SQL statement.
    	String selectSql= null;
    	if(AccountType.equalsIgnoreCase("Checking")) {
    	 selectSql = "SELECT CheckingBalance FROM Userpass WHERE username = ?";
    	}
    	if(AccountType.equalsIgnoreCase("Savings")) {
       	 selectSql = "SELECT SavingsBalance FROM Userpass WHERE username = ?";
    	}
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
   * 
   * SECURE PRACTICE: A prepared statement is used into quering the database, to avoid SQLInjection attacks.
   * 
   * @param username
   * Username of the user currently logged in.
   * 
   * @param balance
   * Future balance of the user that will be inserted into the database.
   * 
   * @throws SQLException
   * Exception thrown in case of a malformed SQL request.
   * 
   * @see SQLException
   * 
   * @see PreparedStatement
   */
  public static void updateSQLBalance(String username, double balance, String AccountType) throws SQLException {
String selectSql = null;
    try (Connection connection = DriverManager.getConnection(connectionUrl); 
        Statement statement = connection.createStatement();) {

    	if(AccountType.equalsIgnoreCase("Checking")) {
       	 selectSql = "SELECT CheckingBalance FROM Userpass WHERE username = ?";
       	}
       	if(AccountType.equalsIgnoreCase("Savings")) {
          	 selectSql = "SELECT SavingsBalance FROM Userpass WHERE username = ?";
          	}
      PreparedStatement stmt = connection.prepareStatement(selectSql);
      stmt.setString(1, username);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}