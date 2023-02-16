package BankingAccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testSavingsAccount {
	
	/********************SAVINGS ACCOUNT TESTS********************/
	
	@Test
	void TestSavingsCreation() {
		
		System.out.println("-----Testing Savings Creation-----");
		/*account with initial 0$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank();
		/*expected amount is 0*/
		assertEquals(acc1.getBalance(), 0);
		System.out.println("-----End Creation Test-----\n");
	}
	@Test
	void TestSavingsCreationWithInitialAmount() {
		
		System.out.println("-----Testing Savings Creation With Initial Amount-----");
		/*account with initial 102$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		/*expected amount is 102$*/
		assertEquals(acc1.getBalance(), 102);
		System.out.println("-----End Creation With Initial Amount Test-----\n");
	}
	@Test
	void TestSavingsCreationWithNegativeInitialAmount() {
		
		System.out.println("-----Testing Savings Creation With Negative Initial Amount-----");
		/*account with initial -1$ dollar is created. This should not be permitted and the amount is set to 0$*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(-1);
		/*expected amount is 0$*/
		assertEquals(acc1.getBalance(), 0);
		System.out.println("-----End Creation With Negative Initial Amount Test-----\n");
	}
	@Test
	void TestSavingsWithdrawl() {
		
		System.out.println("-----Testing Savings Withdrawl-----");
		/*account with initial 102$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		/*attempt to withdraw 1$. No fee is applied for withdrawals*/
		try {
			acc1.withdraw(1);
		} catch (InvalidAmountException e) {
			fail("Exception: " + e);
		} catch (WithdrawalsAvailableException e) {
			fail("Exception: " + e);
		}
		/*expected amount is 101$*/
		assertEquals(acc1.getBalance(), 101);
		System.out.println("-----End Withdrawl Test-----\n");
	}
	@Test
	void TestSavingsDeposit() {
		
		System.out.println("-----Testing Savings Deposit-----");
		/*account with initial 0$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(0);
		/*attempt to deposit 100$.*/
		try {
			acc1.deposit(100);
		} catch (InvalidAmountException e) {
			fail("Exception: " + e);
		}
		/*expected amount is 100$*/
		assertEquals(acc1.getBalance(), 100);
		System.out.println("-----End Deposit Test-----\n");
	}
	@Test
	void TestSavingsWithdrawlWithNoFunds() {
		
		System.out.println("-----Testing Savings Withdrawl with no Funds-----");
		/*account with initial 102$ dollars is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		/*attempt to withdraw 103$ that should not succeed. No fee is applied for withdrawals*/
		try {
			acc1.withdraw(103);
		} catch (InvalidAmountException e) {
			fail("Exception: " + e);
		} catch (WithdrawalsAvailableException e) {
			System.out.println("Exception: " + e);
		}
		/*expected amount is 102$*/
		assertEquals(acc1.getBalance(), 102);
		System.out.println("-----End Withdrawl Test-----\n");
	}
	@Test
	void TestSavingsExceedWithdrawlsAvailable() {
		
		System.out.println("-----Testing Savings Exceed Withdrawls Available-----");
		/*account with initial 102$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		try {
			/*attempt to withdraw 1$. No fee is applied for withdrawals*/
			acc1.withdraw(1);
			/*attempt to withdraw 1$. No fee is applied for withdrawals*/
			acc1.withdraw(1);
			/*attempt to withdraw 1$. No fee is applied for withdrawals*/
			acc1.withdraw(1);
		} catch (InvalidAmountException e) {
			fail("Exception: " + e);
		} catch (WithdrawalsAvailableException e) {
			/*expected amount is 100$*/
			System.out.println("Exception: " + e);
			assertEquals(acc1.getBalance(), 100);
		}
		System.out.println("-----End Exceed Withdrawls Available-----\n");
	}
}
