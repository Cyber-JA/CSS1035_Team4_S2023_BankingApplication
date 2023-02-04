package BankingAccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BankTest {

	@Test
	void checkingTestWithdrawl() {
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		assertEquals(102,acc1.getBalance());
		acc1.withdraw(1);
		assertEquals(100,acc1.getBalance());
		
	}
	@Test
	void savingsTestWithdrawl() {
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		assertEquals(102, acc1.getBalance());
		acc1.withdraw(100);
		assertEquals(2, acc1.getBalance());
	}
	@Test
	void checkingTestDeposit() {
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		acc1.deposit(1);
		assertEquals(103,acc1.getBalance());
		
	}
	@Test
	void savingsTestDeposit() {
		Savings_S2023_SJUBank acc1 = new Savings_S2023_SJUBank(102);
		acc1.deposit(2);
		assertEquals(104, acc1.getBalance());
	}
	@Test
	void checkingTestOverdraftFee() {
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		acc1.withdraw(103);
		assertEquals(-4,acc1.getBalance());
		
	}
	


}
