package BankingAccount;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class bankDriver {

	public static void main(String[] args) {
		selectData("user");
		
	        }
public static void selectData(String Username) {
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

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
}
}
