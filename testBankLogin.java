import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class testBankLogin {

	@Test
	public void testValidateAndEncode() {
		System.out.println("-----Testing validation and encoding -----");
		try {
			String val1 = BankLogin.validate("input example", "input example");
			assertEquals("input example", val1);
			String val2 = BankLogin.validate("#<>", "#<>");
			assertNotEquals("#<>", val2);
			System.out.println("Test Succeed");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----Testing validating and encoding-----");
	}

	@Test
	public void testNormalizer() {
		System.out.println("-----Testing normalization-----");
		try {
			System.out.println("Input value: 雞apcWPlT@ù");
			String ret = BankLogin.normalize("雞apcWPlT@ù");
			System.out.println("Normalized value in UTF-8 Encoding: " + ret);
			assertEquals(ret, "雞apcWPlT@ù");
		} catch (Exception e) {
			fail();
		}
		System.out.println("-----Testing normalization-----");
	}

	@Test
	public void testOpenBankLogin() {
		try {
			System.out.println("-----Testing Open method-----");
			BankLogin test = new BankLogin();
			test.open();
			System.out.println("Success opening Login.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Open method input-----");
	}

	@Test
	public void testCreateContentsBankLogin() {
		try {
			System.out.println("-----Testing Create Contents method-----");
			BankLogin test = new BankLogin();
			test.createContents();
			System.out.println("Success creating contents in Login.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Create Contents method test-----");
	}
}