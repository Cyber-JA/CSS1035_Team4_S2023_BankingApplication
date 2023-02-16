package BankingAccount;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class testDatabase {
	
	private static String connectionUrl =
            "jdbc:sqlserver://sjubank.database.windows.net:1433;"
                    + "database=SJUbank;"
                    + "user=sjubank@sjubank;"
                    + "password=vERY@iNSECURE@pASSWORD;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";

	@Test
	void testSelectData() {
		System.out.println("-----Testing SelectData database-----");
		fail("Not yet implemented");
		System.out.println("-----End SelectData database test-----");
	}
	
	@Test
	void testMD5hash() {
		System.out.println("-----Testing MD5 hash-----");
		String x = "password";
		try {
			System.out.println("String to hash: " + x);
			String hash = database.MD5hash(x);
			System.out.println("Result: " + hash);
			System.out.println("Expected hash: 5f4dcc3b5aa765d61d8327deb882cf99");
			assertEquals(hash, "5f4dcc3b5aa765d61d8327deb882cf99");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("-----End MD5 hash test-----");
	}
	
	@Test
	void testLogin() {
		System.out.println("-----Testing Login database-----");
		fail("Not yet implemented");
		System.out.println("-----End Login database test-----");
	}
	
	@Test
	void testCheckUID() {
		System.out.println("-----Testing Checking UID database-----");
		fail("Not yet implemented");
		System.out.println("-----End Checking UID database test-----");
	}
	
	@Test
	void testCheckAccountType() {
		System.out.println("-----Testing Checking Account Type database-----");
		fail("Not yet implemented");
		System.out.println("-----End Checking Account Type database test-----");
	}
	
	@Test
	void testCheckBalance() {
		System.out.println("-----Testing Checking Balance database-----");
		fail("Not yet implemented");
		System.out.println("-----End Checking Balance  database test-----");
	}
	
	@Test
	void testUpdateSQLBalance() {
		System.out.println("-----Testing Update Balance database-----");
		fail("Not yet implemented");
		System.out.println("-----End Update Balance database test-----");
	}
}
