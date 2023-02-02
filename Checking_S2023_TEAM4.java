
public class Checking_S2023_TEAM4 extends Account_S2023_TEAM4 {
	/*Starting Balance of the Checking account*/
	//thanks to the superclass, balance is available //private double Balance;
	/*Overdraft is possible but only once. To perform a new one it is needed to have a positive balance*/
	private double overdraftcounter;
	/*A fee is applied when overdraft*/
	private double overdraftFee;
	/*ATM withdrawal fee*/
	private double WithdrawalsFee;

	/*Constructors*/
	Checking_S2023_TEAM4() {
		this.setBalance(0);
		this.setOverdraftcounter(0);
		this.setOverdraftFee(2);
		this.setWithdrawalsFee(1);
		System.out.println("Checking account created. Current balance: " + this.getBalance());
	}

	Checking_S2023_TEAM4(double balance) {
		this.setBalance(balance);
		this.setOverdraftcounter(0);
		this.setOverdraftFee(2);
		this.setWithdrawalsFee(1);
		System.out.println("Checking account created. Current balance: " + this.getBalance());
	}
	/*************/
	
	/*overdraftFee Getter and Setter*/
	public double getOverdraftFee() {
		return overdraftFee;
	}

	public void setOverdraftFee(double overdraftFee) {
		this.overdraftFee = overdraftFee;
	}
	/*********************************/
	
	/*Overdraftcounter getter and setter*/
	public double getOverdraftcounter() {
		return overdraftcounter;
	}

	public void setOverdraftcounter(double overdraftcounter) {
		this.overdraftcounter = overdraftcounter;
	}
	/***********************************/
	 
	/*WithdrawalsFee getter and setter*/  
	public double getWithdrawalsFee() {
		return WithdrawalsFee;
	}

	public void setWithdrawalsFee(double withdrawalsFee) {
		WithdrawalsFee = withdrawalsFee;
	}
	/**********************************/
	
	/*Method to apply withdrawals fee*/
	public void applyWithdrawalsFee() {
		 System.out.println("Applying withdrawals fee (" + this.getOverdraftFee() + "$).");
		 Balance = Balance - this.getOverdraftFee();
		 System.out.println("Current balance after withdrawal: " + this.getBalance());
	}
	
	/*Method to apply overdraft fee*/
	public void applyOverdraftFee() {
		 System.out.println("Applying overdraft fee (" + this.getWithdrawalsFee() + "$).");
		 Balance = Balance - this.getWithdrawalsFee();
		 System.out.println("Current balance after overdraft: " + this.getBalance());
	}


  /** Withdraw method*/
  @Override
 public void withdraw(double amount) {
	  System.out.println("Withdrawing...");
	 if(Balance-amount<0 && overdraftcounter==0) {  // checking if account will be overdrawn by withdrawal
		 
		 //final double fee = 2; //fee imposed to the first overdraft transaction  
		 System.out.println("Insufficient funds, a two dollar fee will be levied.");
		 Balance = Balance - amount; // first overdraft transaction is allowed but a two dollar fee is imposed.
		 this.applyWithdrawalsFee();
		 this.applyOverdraftFee();
		 overdraftcounter++; //mark the overdraft
		  
	  }
	 
	  else if(Balance-amount>=0 && overdraftcounter==0) { //if not overdraft, proceed with the withdrawal
		  System.out.println("Withdrawn amount: " + amount);
		  Balance = Balance - amount;
		  this.applyWithdrawalsFee();
	  
	  }
	  else if(overdraftcounter!=0) { //last possible case, if overdraft, cannot proceed with withdrawal
		  
		  System.out.println("Your account is overdrafted. Please restore your account to a positive balance to make another withdrawal.");
	  
	  }
}
 /** Deposit method*/
  @Override
 public void deposit(double amount) {
	 System.out.println("Depositing...");
	 Balance = Balance + amount;
	 System.out.println("Deposited amount: " + amount);
	 if(Balance>=0) { //
		 if(this.getOverdraftcounter() > 0)
			 System.out.println("Overdraft restored.");
		 this.setOverdraftcounter(0);
	 }
 
 }
  
  /** Method to perform payment with checking account*/
  public void makePayment(double amount) {
	  
	if(this.Balance - amount < 0 && this.getOverdraftcounter() != 0) {
		System.out.println("Cannot process payment. Not enough founds:" + this.getBalance() + ". Amount to pay: " + amount);
		return;
	}
	else if(this.Balance - amount < 0 && this.getOverdraftcounter() == 0) {
		this.Balance -= amount;
		System.out.println("Insufficient funds, a two dollar fee will be levied.");
		this.applyOverdraftFee();
		overdraftcounter++; //mark the overdraft
		System.out.println("Payment completed. New balance: " + this.getBalance());
		return;
	}
	else {
		this.Balance -= amount;
		System.out.println("Payment completed. New balance: " + this.getBalance());
	}
  }
  
}
