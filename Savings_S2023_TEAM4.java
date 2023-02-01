
public class savingsAccount extends bankAccount {
	private double Balance;

	@Override
	public void withdraw(double amount) {
if(Balance-amount<0) {
	System.out.println("Insufficient funds");
}
else
	Balance = Balance - amount;
		
	}

	@Override
	public void deposit(double amount) {
		Balance = Balance + amount;
		
	}
	  public double getBalance() {
		    return Balance;
		  }

		  /** Set a new balance */
		  public void setBalance(double Balance) {
		    this.Balance = Balance;
		  }

}
