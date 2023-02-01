public class Checking_S2023_TEAM4 extends Account_S2023_TEAM4 {
  private double Balance;
 private double overdraftcounter;

  public Checking_S2023_TEAM4() {
  }

  public Checking_S2023_TEAM4(double startingBalance) {
    this.Balance = startingBalance;
  }

  /** Return balance */
  public double getBalance() {
    return Balance;
  }

  /** Set a new balance */
  public void setBalance(double Balance) {
    this.Balance = Balance;
  }

  /** Withdraw method*/
  @Override
 public void withdraw(double amount) {
	  
	 if(Balance-amount<0 && overdraftcounter==0) {  // checking if account will be overdrawn by withdrawal
		 
		 //final double fee = 2; //fee imposed to the first overdraft transaction  
		 System.out.println("Insufficient funds, a two dollar fee has been levied.");
		 Balance = Balance - amount; // first overdraft transaction is allowed but a two dollar fee is imposed.
		 //Balance -= fee; //2 dollar fee imposed to the balance on the first overdraft transaction
		 overdraftcounter++; //mark the overdraft
		  
	  }
	 
	  else if(Balance-amount>=0 && overdraftcounter==0) { //if not overdrawn and not overdraft, proceed with the withdraw
		  
		  Balance = Balance - amount;
	  
	  }
	  else if(overdraftcounter!=0) { //last possible case, if overdraft, cannot proceed with withdraw
		  
		  System.out.println("Your account is overdrafted. Please restore your account to a positive balance to make another withdrawal.");
	  
	  }
}
 /** Deposit method*/
  @Override
 public void deposit(double amount) {
	 
	 Balance = Balance + amount;
	 
	 if(Balance>=0) { //
		 
		 overdraftcounter = 0;
	 
	 }
 
 }

}
