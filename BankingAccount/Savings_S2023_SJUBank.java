package BankingAccount;
public class Savings_S2023_SJUBank extends Account_S2023_SJUBank {
	
	/*Starting Balance of the Savings account*/
	// thanks to the superclass, balance is available 
	// as well as Balance's getter and setter
	//private double Balance;
	/*Only a limited number of withdrawal per month is possible*/
	private int WithdrawalsAvailable;
	/*Annual interest rate*/
	private double InterestRate;
	
	/**Constructor methods*/
	Savings_S2023_SJUBank() {
		this.setBalance(0);
		this.setWithdrawalsAvailable(2);
		this.setInterestRate(0.01);
		System.out.printf("Savings account created. Current balance: (%.2f)\n", this.getBalance());
	}

	Savings_S2023_SJUBank(double balance) {
		if(balance < 0) {
			System.out.println("Invalid amount inserted. An account with 0$ will be created and a deposit can be executed.");
			this.setBalance(0);
		}
		else
			this.setBalance(balance);
		
		this.setWithdrawalsAvailable(2);
		this.setInterestRate(0.01);
		System.out.printf("Savings account created. Current balance: (%.2f)\n", this.getBalance());
	}
	
	/*Interest rate getter and setter*/
	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}
	/*********************************/
	
	/*WithdrawalsAvailable getter and setter*/
	public int getWithdrawalsAvailable() {
		return WithdrawalsAvailable;
	}

	public void setWithdrawalsAvailable(int withdrawalsAvailable) {
		this.WithdrawalsAvailable = withdrawalsAvailable;
	}
	/****************************************/
	
	/** Withdraw method*/
	@Override
	public void withdraw(double amount) {
		if(amount <= 0 ) {
			System.out.println("Error. Insert a valid amount.");  
			return;
		  }
		System.out.println("Withdrawing amount: " + amount + "...");
		if(Balance-amount<0) {
		  System.out.println("Insufficient funds");
		}
		else if(this.getWithdrawalsAvailable() == 0) {
			System.out.println("Cannot proceed. Available withdraw: "+ getWithdrawalsAvailable());
		}
		
		else {
			Balance = Balance - amount;
			System.out.printf("Current Balance: (%.2f)\n", getBalance());
			this.WithdrawalsAvailable--;
			System.out.println("Withdrawn amount: " + amount);
			System.out.println("Withdrawals Available after operation: "+ getWithdrawalsAvailable());
		}
	}
	
	/** Deposit method*/
	@Override
	public void deposit(double amount) {
		if(amount <= 0 ) {
			System.out.println("Error. Insert a valid amount.");  
			return;
		  }
		System.out.println("Depositing...");
		Balance = Balance + amount;	
		System.out.println("Deposited amount: " + amount);
		System.out.printf("Current Balance: (%.2f)\n", getBalance());
	}
	
	/*Accrued interest method*/
	public void computeAccruedInterest() {
		System.out.println("Accrued interest forecast: " + this.getBalance() * this.getInterestRate());
	}
	
}
