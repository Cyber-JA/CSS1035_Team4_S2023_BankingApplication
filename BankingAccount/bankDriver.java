package BankingAccount;

public class bankDriver {

	public static void main(String[] args) {
		
		/********************CHECKING ACCOUNT TESTING********************/
		TestCheckingWithdrawl();
		TestCheckingDeposit();
		TestCheckingChargeFees();
		TestCheckingWithdrawlWhenOverdraft();
		TestCheckingRestoringOverdraft();
		/****************END CHECKING ACCOUNT TESTING******************/
		
		/********************SAVING ACCOUNT TESTING********************/
		TestSavingsWithdrawl();
		TestSavingsDeposit();
		TestSavingsWithdrawlWithNoFunds();
		TestSavingsExceedWithdrawlsAvailable();
		/****************END SAVING ACCOUNT TESTING******************/
	}

	
	/********************CHECKING ACCOUNT TESTS********************/
	public static void TestCheckingWithdrawl() {
		
		System.out.println("-----Testing Checking Withdrawl-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 1$. A fee (1$) is applied for withdrawals*/
		acc1.withdraw(1);
		/*expected amount is 100$*/
		if(acc1.getBalance() != 100) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		System.out.println("-----End Withdrawl Test-----\n");
	}
	
	public static void TestCheckingDeposit() {
		
		System.out.println("-----Testing Checking Deposit-----");
		/*account with initial 0$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(0);
		/*attempt to deposit 100$.*/
		acc1.deposit(100);
		/*expected amount is 100$*/
		if(acc1.getBalance() != 100) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		System.out.println("-----End Deposit Test-----\n");
	}
	
	public static void TestCheckingChargeFees() {
		
		System.out.println("-----Testing Checking Fees Charge-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$.*/
		acc1.withdraw(103);
		/*expected amount is -4$*/
		if(acc1.getBalance() != -4) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), -4.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), -4.0);
		}
		System.out.println("-----End Fees Charge Test-----\n");
	}
	
	public static void TestCheckingWithdrawlWhenOverdraft() {
		
		System.out.println("-----Testing Checking Withdrawl when overdraft-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$.*/
		acc1.withdraw(103);
		/*attemp to withdraw when overdraft should not succeed*/
		/*attempt to withdraw 1$.*/
		acc1.withdraw(1);
		if(acc1.getBalance() != -4) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), -4.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), -4.0);
		}
		System.out.println("-----End Withdrawl when overdraft Test-----\n");
	}
	
	public static void TestCheckingRestoringOverdraft() {
		
		System.out.println("-----Testing Checking Restoring Overdraft-----");
		/*account with initial 102$ dollar is created*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		/*attempt to withdraw 103$. This will overdraft*/
		acc1.withdraw(103);
		/*attempt to restore the overdraft*/
		acc1.deposit(5);
		/*expected amount is 1$*/
		if(acc1.getBalance() != 1) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 1.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 1.0);
		}
		System.out.println("-----End Restoring Overdraft Test-----\n");
	}
	
	/********************SAVINGS ACCOUNT TESTS********************/
	
	public static void TestSavingsWithdrawl() {
			
			System.out.println("-----Testing Savings Withdrawl-----");
			/*account with initial 102$ dollar is created*/
			Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
			/*attempt to withdraw 1$. No fee is applied for withdrawals*/
			acc1.withdraw(1);
			/*expected amount is 101$*/
			if(acc1.getBalance() != 101) {
				System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 101.0);
			}
			else {
				System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 101.0);
			}
			System.out.println("-----End Withdrawl Test-----\n");
		}
	
	public static void TestSavingsDeposit() {
			
			System.out.println("-----Testing Savings Deposit-----");
			/*account with initial 0$ dollar is created*/
			Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(0);
			/*attempt to deposit 100$.*/
			acc1.deposit(100);
			/*expected amount is 100$*/
			if(acc1.getBalance() != 100) {
				System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
			}
			else {
				System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
			}
			System.out.println("-----End Deposit Test-----\n");
		}
	
	public static void TestSavingsWithdrawlWithNoFunds() {
		
		System.out.println("-----Testing Savings Withdrawl with no Funds-----");
		/*account with initial 102$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		/*attempt to withdraw 103$. No fee is applied for withdrawals*/
		acc1.withdraw(103);
		/*expected amount is 102$*/
		if(acc1.getBalance() != 102) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 102.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 102.0);
		}
		System.out.println("-----End Withdrawl Test-----\n");
	}
	
	public static void TestSavingsExceedWithdrawlsAvailable() {
		
		System.out.println("-----Testing Savings Exceed Withdrawls Available-----");
		/*account with initial 102$ dollar is created*/
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		/*attempt to withdraw 1$. No fee is applied for withdrawals*/
		acc1.withdraw(1);
		/*attempt to withdraw 1$. No fee is applied for withdrawals*/
		acc1.withdraw(1);
		/*attempt to withdraw 1$. No fee is applied for withdrawals*/
		acc1.withdraw(1);
		/*expected amount is 100$*/
		if(acc1.getBalance() != 100) {
			System.out.printf("FAIL! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		else {
			System.out.printf("PASS! Actual balance: %.2f$. Expected balance: %.2f$\n", acc1.getBalance(), 100.0);
		}
		System.out.println("-----End Exceed Withdrawls Available-----\n");
	}
}
