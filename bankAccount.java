public abstract class bankAccount {
 
  private java.util.Date dateCreated;

 
  protected bankAccount() {
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

  /** Abstract method getArea */
  public abstract void withdraw(double amount);
  public abstract void deposit(double amount);




}