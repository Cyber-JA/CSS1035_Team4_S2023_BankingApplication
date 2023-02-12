package BankingAccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testCheckingAccount {
	
	/********************CHECKING ACCOUNT TESTS********************/
	@Test
	void TestCheckingCreation() {
		
		System.out.println("-----Testing Checking Creation-----");
		/*account with initial 0$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank();
		/*expected amount is 0$*/
		assertEquals(acc1.getBalance(), 0);
		System.out.println("-----End Checking Creation Test-----\n");
	}
	@Test
	void TestCheckingCreationWithInitialAmount() {
		
		System.out.println("-----Testing Checking Creation With Initial Amount-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*expected amount is 102$*/
		assertEquals(acc1.getBalance(), 102);
		System.out.println("-----End Checking Creation With Initial Amount Test-----\n");
	}
	@Test
	void TestCheckingWithNegativeAmount() {
		
		System.out.println("-----Testing Checking Creation With Negative Amount-----");
		/*account with initial -1$ dollar is created. This is not allowed and the initial amount is set to 0$*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(-1);
		/*expected amount is 0$*/
		assertEquals(acc1.getBalance(), 0);
		System.out.println("-----End Checking Creation With Negative Amount Test-----\n");
	}
	@Test
	void TestCheckingWithdrawl() {
		
		System.out.println("-----Testing Checking Withdrawl-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 1$. A fee (1$) is applied for withdrawals*/
		acc1.withdraw(1);
		/*expected amount is 100$*/
		assertEquals(acc1.getBalance(), 100);
		System.out.println("-----End Withdrawl Test-----\n");
	}
	@Test
	void TestCheckingDeposit() {
		
		System.out.println("-----Testing Checking Deposit-----");
		/*account with initial 0$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(0);
		/*attempt to deposit 100$.*/
		acc1.deposit(100);
		/*expected amount is 100$*/
		assertEquals(acc1.getBalance(), 100);
		System.out.println("-----End Deposit Test-----\n");
	}
	@Test
	void TestCheckingChargeFees() {
		
		System.out.println("-----Testing Checking Fees Charge-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$.*/
		acc1.withdraw(103);
		/*fees are applied (1$ for withdrawing and 2$ for overdraft)*/
		/*expected amount is -4$*/
		assertEquals(acc1.getBalance(), -4);
		System.out.println("-----End Fees Charge Test-----\n");
	}
	@Test
	void TestCheckingWithdrawlWhenOverdraft() {
		
		System.out.println("-----Testing Checking Withdrawl when overdraft-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$.*/
		acc1.withdraw(103);
		/*attempt to withdraw when overdraft should not succeed*/
		/*attempt to withdraw 1$.*/
		acc1.withdraw(1);
		assertEquals(acc1.getBalance(), -4);
		System.out.println("-----End Withdrawl when overdraft Test-----\n");
	}
	@Test
	void TestCheckingRestoringOverdraft() {
		
		System.out.println("-----Testing Checking Restoring Overdraft-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$. This will overdraft*/
		acc1.withdraw(103);
		/*attempt to restore the overdraft*/
		acc1.deposit(5);
		/*expected amount is 1$*/
		assertEquals(acc1.getBalance(), 1);
		System.out.println("-----End Restoring Overdraft Test-----\n");
	}
}
