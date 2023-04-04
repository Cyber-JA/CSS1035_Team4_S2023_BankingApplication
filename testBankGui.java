import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class testBankGui {

	@Test
	public void testValidateFloatInputFailingCase() {
		try {
			System.out.println("-----Testing validating wrong float input -----");
			Object input = BankGui.validateFloatInput("1,43");
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Float");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End validating wrong float input-----");
	}

	@Test
	public void testValidateFloatInputSuccedingCase() {
		try {
			System.out.println("-----Testing validating correct float input-----");
			Object input = BankGui.validateFloatInput("1.43");
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Float");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating correct float input-----");
	}

	@Test
	public void testValidateIntInputSuccedingCase() {
		try {
			System.out.println("-----Testing validating correct int input-----");
			Object input = BankGui.validateIntInput("1");
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Integer");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating correct int input-----");
	}

	@Test
	public void testValidateIntInputFailingCase() {
		try {
			System.out.println("-----Testing validating wrong int input-----");
			Object input = BankGui.validateIntInput("1.34");
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Integer");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating wrong int input-----");
	}

	@Test
	public void testSetObjectVariablesSavings() {
		try {
			System.out.println("-----Testing set Savings account from db-----");
			String username = "testing";
			System.out.println("Testing with account: " + username);
			BankGui test = new BankGui(username);
			test.setObjectVariablesSavings();
			System.out.println("Success.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End set Savings account from db test-----");
	}

	@Test
	public void testSetObjectVariablesChecking() {
		try {
			System.out.println("-----Testing set Checking account from db -----");
			String username = "testing";
			System.out.println("Testing with account: " + username);
			BankGui test = new BankGui(username);
			test.setObjectVariablesCheckings();
			System.out.println("Success.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End set Checking account from db test-----");
	}

	@Test
	public void testCreateContents() {
		try {
			System.out.println("-----Testing Create Contents method-----");
			String username = "testing";
			BankGui test = new BankGui(username);
			test.createContents();
			System.out.println("Create contents for user: " + username);
			System.out.println("Success creating contents in GUI.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Create Contents method test-----");
	}

	@Test
	public void testOpenBankGui() {
		try {
			String Username = "testing";
			BankGui window = new BankGui(Username);
			System.out.println("-----Testing Open method-----");
			window.open();
			System.out.println("Opening GUI for user: " + Username);
			System.out.println("Success opening GUI.");
		} catch (Exception e) {
			System.out.println(e);
			fail();
		}
		System.out.println("-----End Open method input-----");
	}
}