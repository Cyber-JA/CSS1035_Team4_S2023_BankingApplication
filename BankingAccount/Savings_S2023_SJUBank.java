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
	/**Constructor methods
	 * @throws InvalidAmountException */
	Savings_S2023_SJUBank() {
		try {
			this.setBalance(0);
		} catch (InvalidAmountException e) {
			this.Balance = 0;
		}
		this.setWithdrawalsAvailable(2); //should be retrieved by the db
		this.setInterestRate(0.01);
		System.out.printf("Savings account created. Current balance: (%.2f)\n", this.getBalance());//to be removed
	}

	Savings_S2023_SJUBank(double balance) {
		try {
			this.setBalance(balance);
		} catch (InvalidAmountException e) {
			System.out.println("Exception: " + e);
		}
		this.setWithdrawalsAvailable(2); //should be retrieved by the db
		this.setInterestRate(0.01);
		System.out.printf("Savings account created. Current balance: (%.2f)\n", this.getBalance());//to be removed
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
	
	/** Withdraw method
	 * @throws WithdrawalsAvailableException */
	@Override
	public void withdraw(double amount) throws InvalidAmountException, WithdrawalsAvailableException {
		if(amount <= 0 ) {
			throw new InvalidAmountException(amount);
		  }
		System.out.printf("Withdrawning amount: %.2f ...\n",  amount);
		if(Balance-amount<0) {
		  System.out.println("Insufficient funds");
		}
		else if(this.getWithdrawalsAvailable() == 0 ) {
			throw new WithdrawalsAvailableException(this.getWithdrawalsAvailable());
		 }
		
		else {
			Balance = Balance - amount;
			System.out.printf("Current Balance: (%.2f)\n", getBalance());
			this.WithdrawalsAvailable--;
			System.out.printf("Withdrawn amount: %.2f\n",  amount);
			System.out.println("Withdrawals Available after operation: "+ getWithdrawalsAvailable());
		}
	}
	
	/** Deposit method*/
	@Override
	public void deposit(double amount) throws InvalidAmountException {
		if(amount <= 0 ) {
			throw new InvalidAmountException(amount);
		  }
		System.out.println("Depositing...");
		Balance = Balance + amount;	
		System.out.printf("Deposited amount: %.2f\n",  amount);
		System.out.printf("Current Balance: %.2f\n", getBalance());
	}
	
	/*Accrued interest method*/
	public void computeAccruedInterest() {
		System.out.println("Accrued interest forecast: " + this.getBalance() * this.getInterestRate());
	}
	
}
//TODO excpetion for not enough withdraws available
class WithdrawalsAvailableException extends Exception {
	int withdrawalsAvailable;
	
	 WithdrawalsAvailableException(int withdrawalsAvailable) {
		System.out.println("Withdrawals available: " + withdrawalsAvailable);
		this.withdrawalsAvailable = withdrawalsAvailable;
	}
}