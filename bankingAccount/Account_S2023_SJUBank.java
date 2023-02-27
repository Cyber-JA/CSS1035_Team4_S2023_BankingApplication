package bankingAccount;

/**
 * This is the superclass from which the savings and checking account are derived. 
 * SECURE PRACTICE: prior to all the computations, the Math.isInfinity method is used to check
 * that the double value is not overflowing to infinity/-infinity.
 */

public abstract class Account_S2023_SJUBank {

  static private java.util.Date dateCreated = new java.util.Date();;
  /**
   * This is the Balance of the account created.
   * Both the subclasses deploys this variable to store the amount of the account.
   * 
   */
  protected double Balance;
  
  /**
   * This is a variable related to the User ID. 
   * An identification of the owner of the account.*/
  private int UID;

  protected Account_S2023_SJUBank() {
    this.Balance = 0;
  }

  protected Account_S2023_SJUBank(double balance) {
    Balance = balance;
  }
  
  /**
   * Getter used to retrieve the balance of the instance.
   * 
   * @return Balance
   * Actual amount into the account
   */
  protected double getBalance() {
    return Balance;
  }
  
  /**
   * Getter used to retrieve the User ID of the account's owner.
   * 
   * @return UID
   * Actual identifier of the owner.
   */
  protected int getUID() {
    return UID;
  }

  /**
   * Setter used to assign the user ID of the account's owner.
   * 
   * Actual amount into the account
   * 
   * @param uid
   * User ID to set.
   */
  protected void setUID(int uid) {
    UID = uid;
  }
  /**
   * Method used to set the balance in the account.
   * 
   * @param balance
   * Used to set the balance into the account to the received value.
   * 
   * @throws InvalidAmountException
   * Exception thrown when the amount is not valid in this context
   * 
   * @see InvalidAmountException
   */
  protected void setBalance(double balance) throws InvalidAmountException {
    if (balance < 0) {
      throw new InvalidAmountException(balance);
    } else
      Balance = balance;
  }

  /**
   *  Get dateCreated.
   *  
   *  @return dateCreated
   *  Returns the date of the creation of the account.
   */
  protected Object getDateCreated() {
    return dateCreated.clone();
  }

  /**
   * Method used to display the creation date of the account.
   * 
   * @return String
   * String returned with the information required.
   */
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
   * @throws ArithmeticException
   * Exception thrown when the result of the operations performed overflow to infinity/-infinity.
   * 
   * @see InvalidAmountException
   * 
   * @see WithdrawalsAvailableException
   * 
   * @see OverdraftAccountException
   */
  public abstract void withdraw(double amount)
      throws InvalidAmountException, WithdrawalsAvailableException, OverdraftAccountException, ArithmeticException;

  /** Abstract method deposit. 
   * 
   * @throws InvalidAmountException
   * Exception thrown when the amount is not correct in the context.
   * 
   * @throws ArithmeticException
   * Exception thrown when the result of the operations performed overflow to infinity/-infinity.
   * 
   * @param amount 
   * is the amount that the user wants to deposit into the account.
   * This is gonna be added to the amount
   */
  public abstract void deposit(double amount) throws InvalidAmountException, ArithmeticException;

}