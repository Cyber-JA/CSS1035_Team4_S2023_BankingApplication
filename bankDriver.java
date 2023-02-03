
public class bankDriver {

	public static void main(String[] args) {
		
		/********************CHECKING ACCOUNT TESTING********************/
		/*a checking account is created with an initial deposit of 102$*/
		Checking_S2023_SJUBank acc1 = new Checking_S2023_SJUBank(102);
		System.out.println();
		
		/*attempt to withdraw 100$, this should succeed. A fee (2$) is applied for withdrawals*/
		acc1.withdraw(100);  //current balance should be: 0$
		System.out.println();
		
		/*attempt to withdraw 100$, this should succeed but overdraft the account. A fee is applied for 
		 * withdrawals and for overdraft*/
		acc1.withdraw(100);  //current balance should be: -103$
		System.out.println();
		
		/*attempt to withdraw 1$, this should not succeed because of overdraft*/
		acc1.withdraw(1);  
		System.out.println();
		
		/*testing deposit method*/
		acc1.deposit(1000); //this should restore the overdraft as well
		System.out.println();
		
		/*testing deposit method, without overdraft*/
		acc1.deposit(10);
		System.out.println();
		
		/*testing makePayment method*/
		acc1.makePayment(100); //new balance should be 807$
		acc1.makePayment(807); //new balance should be 0$
		/*attempting to overdraft*/
		acc1.makePayment(1); //overdraft and payment are charged
		
		System.out.println("\n");
		/****************END CHECKING ACCOUNT TESTING******************/
		
		/********************SAVING ACCOUNT TESTING********************/
		
		/*a saving account is created with an initial deposit of 0$*/
		Savings_S2023_SJUBank saving = new Savings_S2023_SJUBank(0);
		System.out.println();
		
		/*try to withdraw, it fails because can't overdraft*/
		saving.withdraw(100);
		System.out.println();
		
		//depositing
		saving.deposit(400);
		System.out.println();
		
		/*testing computeAccruedInterest method, it shows a forecast interest with current balance amount*/
		saving.computeAccruedInterest();
		System.out.println();
		System.out.println("Withdrawals available:" + saving.getWithdrawalsAvailable());
		
		//with this account, only a limited number of withdrawals is available
		/*running out the withdrawals available*/
		saving.withdraw(10);
		System.out.println();
		saving.withdraw(10);
		System.out.println();
		saving.withdraw(10); //this should not be permitted
		System.out.println();
		
		/****************END SAVING ACCOUNT TESTING******************/
	}

}
