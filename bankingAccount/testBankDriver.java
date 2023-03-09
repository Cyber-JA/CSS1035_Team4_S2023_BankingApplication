package bankingAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class testBankDriver{
	
	@Test
	public void testValidateFloatInputFailingCase() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("-----Testing validating wrong float input -----");
			System.out.println("Insert value:");
			Object input = bankDriver.validateFloatInput(in);
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Float");
		} catch (Exception e){
			System.out.println(e);
			fail();
		}
		System.out.println("-----End validating wrong float input-----");
	}
	
	@Test
	public void testValidateFloatInputSuccedingCase() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("-----Testing validating correct float input-----");
			System.out.println("Insert value:");
			Object input = bankDriver.validateFloatInput(in);
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Float");
		} catch (Exception e){
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating correct float input-----");
	}

	@Test
	public void testValidateIntInputSuccedingCase() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("-----Testing validating correct int input-----");
			System.out.println("Insert value:");
			Object input = bankDriver.validateIntInput(in);
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Integer");
		} catch (Exception e){
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating correct int input-----");
	}
	
	@Test
	public void testValidateIntInputFailingCase() {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("-----Testing validating wrong int input-----");
			System.out.println("Insert value:");
			Object input = bankDriver.validateIntInput(in);
			String val = input.getClass().getName();
			assertEquals(val, "java.lang.Integer");
		} catch (Exception e){
			System.out.println(e);
			fail();
		}
		System.out.println("-----Testing validating wrong int input-----");
	}
	
	@Test
	public void testValidateAndEncode() {
		System.out.println("-----Testing validating validating and encoding -----");
		try {
			String val1 = bankDriver.validate("input example", "input example");
			assertEquals("input example", val1);
			String val2 = bankDriver.validate("#<>", "#<>");
			assertNotEquals("#<>", val2);
			System.out.println("Test Succeed");
		} catch (Exception e){
			fail();
		}
		System.out.println("-----Testing validating and encoding-----");
	}
	
	@Test
	public void testNormalizer() {
		System.out.println("-----Testing normalization-----");
		try {
			System.out.println("Input value: 雞apcWPlT@ù");
			String ret = bankDriver.normalize("雞apcWPlT@ù");
			System.out.println("Normalized value in UTF-8 Encoding: " + ret);
			assertEquals(ret, "雞apcWPlT@ù");
		} catch (Exception e){
			fail();
		}
		System.out.println("-----Testing normalization-----");
	}
}