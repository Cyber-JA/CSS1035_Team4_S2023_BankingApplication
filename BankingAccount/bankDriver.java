package BankingAccount;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class bankDriver {

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
		//selectData("user");
		login("admin","letmei");
		   
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
public static void login(String Username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
	String Uname = selectData(Username);
	String Pwd = MD5hash(password);
	if(Uname.contains(Pwd)) {
		System.out.println("success");
	}
	else {
		System.out.println("wrong password");
		System.out.println(Uname);
		System.out.println(Pwd);
		
	}
}

}
