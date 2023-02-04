package BankingAccount;
public class Checking_S2023_SJUBank extends Account_S2023_SJUBank {
	/*Starting Balance of the Checking account*/
	//thanks to the superclass, balance is available //private double Balance;
	/*Overdraft is possible but only once. To perform a new one it is needed to have a positive balance*/
	private double overdraftcounter;
	/*A fee is applied when overdraft*/
	private double overdraftFee;
	/*ATM withdrawal fee*/
	private double WithdrawalsFee;

	/*Constructors*/
	Checking_S2023_SJUBank() {
		this.setBalance(0);
		this.setOverdraftcounter(0);
		this.setOverdraftFee(2);
		this.setWithdrawalsFee(1);
		System.out.printf("Checking account created. Current balance: (%.2f)\n", this.getBalance());
	}

	Checking_S2023_SJUBank(double balance) {
		this.setBalance(balance);
		this.setOverdraftcounter(0);
		this.setOverdraftFee(2);
		this.setWithdrawalsFee(1);
		System.out.printf("Checking account created. Current balance: (%.2f)\n", this.getBalance());
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
		System.out.println("Applying withdrawal fee (" + this.getWithdrawalsFee() + "$)");
		 Balance = Balance - this.getOverdraftFee();
		 System.out.printf("Current balance after withdrawal: %.2f\n", this.getBalance());
	}
	
	/*Method to apply overdraft fee*/
	public void applyOverdraftFee() {
		 System.out.println("Applying overdraft fee (" + this.getWithdrawalsFee() + "$)");
		 Balance = Balance - this.getWithdrawalsFee();
		 System.out.printf("Current balance after overdraft: %.2f\n",  this.getBalance());
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
		  if(this.getBalance() < 0) {
			  this.applyOverdraftFee();
			  this.overdraftcounter++;
			  
		  }
	  
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
		System.out.printf("Cannot process payment. Not enough founds: %.2f. Amount to pay: %.2f\n", this.getBalance(), amount);
		return;
	}
	else if(this.Balance - amount < 0 && this.getOverdraftcounter() == 0) {
		this.Balance -= amount;
		System.out.println("Insufficient funds, a two dollar fee will be levied.");
		this.applyOverdraftFee();
		overdraftcounter++; //mark the overdraft
		System.out.printf("Payment completed. New balance: %.2f\n", this.getBalance());
		return;
	}
	else {
		this.Balance -= amount;
		System.out.printf("Payment completed. New balance: %.2f\n", this.getBalance());
	}
  }
  
}
