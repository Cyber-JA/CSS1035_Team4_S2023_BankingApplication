
public abstract class Account_S2023_SJUBank {
 
    protected java.util.Date dateCreated;
  	protected double Balance;
 
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

	public void setBalance(double balance) {
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
	
	  /** Abstract method withdraw */
	  public abstract void withdraw(double amount);
	  
	  /** Abstract method deposit */
	  public abstract void deposit(double amount);

}