
public class Savings_S2023_TEAM4 extends Account_S2023_TEAM4 {
	private double Balance;
	
	/** Withdraw method*/
	@Override
	public void withdraw(double amount) {
		
	  if(Balance-amount<0) {
		  System.out.println("Insufficient funds");
	  }
		else
			Balance = Balance - amount;		
	}
	
	/** Deposit method*/
	@Override
	public void deposit(double amount) {
		Balance = Balance + amount;	
	}
	
	/** Return balance */
	public double getBalance() {
	    return Balance;
	}

	/** Set a new balance */
	public void setBalance(double Balance) {
		this.Balance = Balance;  
	}

}
