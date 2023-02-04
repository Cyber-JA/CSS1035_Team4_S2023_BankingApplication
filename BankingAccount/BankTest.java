package BankingAccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BankTest {

	@Test
	void test() {
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		assertEquals(102,acc1.getBalance());
	}

}
