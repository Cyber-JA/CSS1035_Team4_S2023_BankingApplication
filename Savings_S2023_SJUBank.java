
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
		System.out.println("Saving account created. Current balance: " + this.getBalance());
	}

	Savings_S2023_SJUBank(double balance) {
		this.setBalance(balance);
		this.setWithdrawalsAvailable(2);
		this.setInterestRate(0.01);
		System.out.println("Saving account created. Current balance: " + this.getBalance());
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
		System.out.println("Withdrawing...");
		if(Balance-amount<0) {
		  System.out.println("Insufficient funds");
		}
		else if(this.getWithdrawalsAvailable() == 0) {
			System.out.println("Cannot proceed. Available withdraw: "+ getWithdrawalsAvailable());
		}
		
		else {
			Balance = Balance - amount;
			System.out.println("Current Balance:" + getBalance());
			this.WithdrawalsAvailable--;
			System.out.println("Withdrawn amount: " + amount);
			System.out.println("Withdrawals Available after operation: "+ getWithdrawalsAvailable());
		}
	}
	
	/** Deposit method*/
	@Override
	public void deposit(double amount) {
		System.out.println("Depositing...");
		Balance = Balance + amount;	
		System.out.println("Deposited amount: " + amount);
	}
	
	/*Accrued interest method*/
	public void computeAccruedInterest() {
		System.out.println("Accrued interest forecast: " + this.getBalance() * this.getInterestRate());
	}
	
}
