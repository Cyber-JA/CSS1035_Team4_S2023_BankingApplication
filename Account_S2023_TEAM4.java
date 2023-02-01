public abstract class Account_S2023_TEAM4 {
 
  private java.util.Date dateCreated;

 
	  protected Account_S2023_TEAM4() {
	    dateCreated = new java.util.Date();
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