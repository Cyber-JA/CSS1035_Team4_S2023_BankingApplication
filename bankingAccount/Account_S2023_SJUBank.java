package bankingAccount;

public abstract class Account_S2023_SJUBank {

  protected java.util.Date dateCreated;
  /**
   * This is the Balance of the account created.
   * Both the subclasses deploys this variable to store the amount of the account.*/
  protected double Balance;
  
  /**
   * This is a variable related to the User ID. 
   * An identification of the owner of the account.*/
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

  public void setUID(int uid) {
    UID = uid;
  }
  /**
   * Method used to set the balance in the account.
   */
  public void setBalance(double balance) throws InvalidAmountException {
    if (balance < 0) {
      throw new InvalidAmountException(balance);
    } else
      Balance = balance;
  }

  /* Get dateCreated. */
  public java.util.Date getDateCreated() {
    return dateCreated;
  }

  @Override
  public String toString() {
    return "created on " + dateCreated;
  }

  /**
   * Abstract method withdraw.
   * 
   * @param amount 
   * This is the amount that the user wants to withdraw.
   * This is gonna be subtracted to the balance of the account.
   *
   * @throws WithdrawalsAvailableException 
   * Exception thrown if the number of withdrawals available
   * is exhausted.
   * 
   * @throws OverdraftAccountException
   * Exception thrown if the account is overdrafted and a withdrawal is requested.
   * 
   * @throws InvalidAmountException
   * Exception thrown when the amount is not correct in the context.
   * 
   * @see InvalidAmountException
   * 
   * @see WithdrawalsAvailableException
   * 
   * @see OverdraftAccountException
   */
  public abstract void withdraw(double amount)
      throws InvalidAmountException, WithdrawalsAvailableException, OverdraftAccountException;

  /** Abstract method deposit. 
   * 
   * @throws InvalidAmountException
   * Exception thrown when the amount is not correct in the context.
   * 
   * @param amount 
   * is the amount that the user wants to deposit into the account.
   * This is gonna be added to the amount
   */
  public abstract void deposit(double amount) throws InvalidAmountException;

}