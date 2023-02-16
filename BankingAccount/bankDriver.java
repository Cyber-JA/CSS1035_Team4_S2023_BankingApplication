package BankingAccount;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class bankDriver {

	public static void main(String[] args){
		try {
			BankingAccount.database.login("admin", "letmein");
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | SQLException e) {
			System.out.println(e);
		}
	}
}