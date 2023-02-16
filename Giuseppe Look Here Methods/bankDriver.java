package BankingAccount;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class bankDriver {
	static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
	static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
	static String Username = null;
	static String Password = null;
	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, InterruptedException {
		//selectData("user");
		//login("admin","letmei");
		
		//TODO implement methods from this code into the new SQL class file GIUSEPPE
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to SJU bank, please enter your username followed by the enter key then your password followed by the enter key");
		 Username = in.nextLine();  
		 Password = in.nextLine();
		 System.out.println(Username);
		 System.out.println(Password);
		 Scanner selection = new Scanner(System.in);
		 int choice = -1;
		 if(login(Username,Password)==1) {
			 System.out.println("Login successful");
			 if(checkAccountType(Username).contains("Savings")) {
				 System.out.println("Welcome to your account " + Username);
				 System.out.println("Please select a choice ranging from 1-2");
				 System.out.println("1. Withdraw");
				 System.out.println("2. Deposit");
				 setObjectVariablesSavings();
				 while(true) {
					 choice = selection.nextInt();
					 if(choice==1) {
						 System.out.println("How much would you like to withdraw?");
						 accountS.withdraw(selection.nextFloat());
						 updateSQLBalance(Username,accountS.getBalance());
						 //System.out.println(checkBalance(Username));
						 System.out.println("Please select a choice ranging from 1-2");
						 continue;
					 }
					 if(choice==2) {
						 System.out.println("How much would you like to deposit?");
						 accountS.deposit(selection.nextFloat());
						 updateSQLBalance(Username,accountS.getBalance());
						 System.out.println("Please select a choice ranging from 1-2");
						 continue;
						 
					 }
					 
				 }
			 }
			 else if(checkAccountType(Username).contains("Checking")) {
				 System.out.println("Welcome to your account " + Username);
				 System.out.println("Please select a choice ranging from 1-2");
				 System.out.println("1. Withdraw");
				 System.out.println("2. Deposit");
				 System.out.println("3. Make Payment");
				 setObjectVariablesCheckings();
				 while(true) {
					 choice = selection.nextInt();
					 if(choice==1) {
						 System.out.println("How much would you like to withdraw?");
						 accountC.withdraw(selection.nextFloat());
						 updateSQLBalance(Username,accountC.getBalance());
						 //System.out.println(checkBalance(Username));
						 System.out.println("Please select a choice ranging from 1-3");
						 continue;
					 }
					 if(choice==2) {
						 System.out.println("How much would you like to deposit?");
						 accountC.deposit(selection.nextFloat());
						 updateSQLBalance(Username,accountC.getBalance());
						 System.out.println("Please select a choice ranging from 1-3");
						 continue;
						 
					 }
					 if(choice==3) {
						 System.out.println("How much would you like to pay");
						 accountC.makePayment(selection.nextFloat());
						 updateSQLBalance(Username,accountC.getBalance());
						 System.out.println("Please select a choice ranging from 1-3");
						 continue;
						 
					 }
					 
				 }
			 }
		 }
		 else {
			 System.out.println("Login failed");
		 }
		 
		 
		 
	}
	public static String MD5hash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
	
	public static String checkAccountType(String Username) throws SQLException {
		String result = null;
		String connectionUrl =
	            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
	                    + "database=SJUbank;"
	                    + "user=sjubank@sjubank;"
	                    + "password=vERY@iNSECURE@pASSWORD;"
	                    + "encrypt=true;"
	                    + "trustServerCertificate=false;"
	                    + "loginTimeout=30;";
	 	ResultSet resultSet = null;
	    
	        try (Connection connection = DriverManager.getConnection(connectionUrl);
	                Statement statement = connection.createStatement();) {

	            // Create and execute a SELECT SQL statement.
	            String selectSql = "SELECT Accounttype FROM Userpass WHERE Username = ?";
	            PreparedStatement stmt = connection.prepareStatement(selectSql);
	            stmt.setString(1, Username);
	            resultSet = stmt.executeQuery();
	             resultSet.next();
	            result = resultSet.getString(1);
	            // Print results from select statement
	            
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
			return result;
	}
public static String selectData(String Username) throws SQLException {
	String result = null;
	String connectionUrl =
            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
                    + "database=SJUbank;"
                    + "user=sjubank@sjubank;"
                    + "password=vERY@iNSECURE@pASSWORD;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";
 	ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT Password FROM Userpass WHERE Username = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, Username);
            resultSet = stmt.executeQuery();
             resultSet.next();
            result = resultSet.getString(1);
            // Print results from select statement
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return result;
}
public static int login(String Username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
	String Uname = selectData(Username);
	String Pwd = MD5hash(password);
	if(Uname.contains(Pwd)) {
		return 1;
	}
	else {
		System.out.println("wrong password");
		return 0;
	}
}
public static int checkUID(String Username) throws SQLException {
	int result = 0;
	String connectionUrl =
            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
                    + "database=SJUbank;"
                    + "user=sjubank@sjubank;"
                    + "password=vERY@iNSECURE@pASSWORD;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";
 	ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT UserID FROM Userpass WHERE Username = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, Username);
            resultSet = stmt.executeQuery();
             resultSet.next();
            result = resultSet.getInt(1);
            // Print results from select statement
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return result;
}
public static int checkBalance(String Username) throws SQLException {
	int result = 0;
	String connectionUrl =
            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
                    + "database=SJUbank;"
                    + "user=sjubank@sjubank;"
                    + "password=vERY@iNSECURE@pASSWORD;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";
 	ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT Balance FROM Userpass WHERE Username = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, Username);
            resultSet = stmt.executeQuery();
             resultSet.next();
            result = resultSet.getInt(1);
            // Print results from select statement
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return result;
}
public static void setObjectVariablesSavings() throws SQLException {
	accountS.setBalance(checkBalance(Username));
	accountS.setUID(checkUID(Username));
}
public static void setObjectVariablesCheckings() throws SQLException {
	accountC.setBalance(checkBalance(Username));
	accountC.setUID(checkUID(Username));
}
public static void updateSQLBalance(String Username,double balance) throws SQLException {
	int result = 0;
	String connectionUrl =
            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
                    + "database=SJUbank;"
                    + "user=sjubank@sjubank;"
                    + "password=vERY@iNSECURE@pASSWORD;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";
 	ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "UPDATE Userpass SET Balance = "+ balance + " WHERE Username = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, Username);
            stmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		//return result;
}
}
