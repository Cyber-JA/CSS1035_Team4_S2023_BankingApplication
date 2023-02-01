public class checkingAccount extends bankAccount {
  private double Balance;
 private double overdraftcounter;

  public checkingAccount() {
  }

  public checkingAccount(double startingBalance) {
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

 public void withdraw(double amount) {
	 if(Balance-amount<=0 && overdraftcounter==0) {  // checking if account will be overdrawn by withdrawl
		  System.out.println("Insuffcient funds, a two dollar fee has been levied.");
		  Balance = Balance - amount; // first overdraft transaction is allowed but a two dollar fee is imposed.
		  overdraftcounter++; //mark the overdraft
		  
	  }
	 
	  else if(Balance-amount>=0 && overdraftcounter==0) {
   Balance = Balance - amount;
	  }
	  else if(overdraftcounter!=0) {
		  System.out.println("Your account is overdrafted please restore your account to a positive balance to make another withdrawl.");
	  }
	
}
 public void deposit(double amount) {
	 Balance = Balance + amount;
	 if(Balance>=0) {
		 overdraftcounter = 0;
	 }
 }





  

}
