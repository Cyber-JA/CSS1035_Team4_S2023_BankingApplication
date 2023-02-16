package BankingAccount;
public abstract class Account_S2023_SJUBank {
 
    protected java.util.Date dateCreated;
  	protected double Balance;
  	private int UID;
 
	  protected Account_S2023_SJUBank() {
	    dateCreated = new java.util.Date();
	    this.Balance = 0;
	  }
	
	 protected Account_S2023_SJUBank(double balance) {
		this.dateCreated = new java.util.Date();
		Balance = balance;
	}

	public double getBalance() {
		return Balance;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public void setBalance(double balance) throws InvalidAmountException {
		if(balance < 0) {
			throw new InvalidAmountException(balance);
		}
		else
			Balance = balance;
	}

	/** Get dateCreated */
	  public java.util.Date getDateCreated() {
	    return dateCreated;
	  }
	
	  @Override
	  public String toString() {
	    return "created on " + dateCreated;
	  }
	
	  /** Abstract method withdraw 
	 * @throws WithdrawalsAvailableException 
	 * @throws OverdraftAccountException */
	  public abstract void withdraw(double amount) throws InvalidAmountException, WithdrawalsAvailableException, OverdraftAccountException;
	  
	  /** Abstract method deposit */
	  public abstract void deposit(double amount) throws InvalidAmountException;

}

class InvalidAmountException extends Exception {
	double amount;
	
	 InvalidAmountException(double amount) {
		System.out.println("Invalid amount inserted: ." + amount);
		this.amount = amount;
	}
}