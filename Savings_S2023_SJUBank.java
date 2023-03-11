

/**
 * Class used to implement the savings account functionalities.
 * This extends the Account superclass. 
 */
public class Savings_S2023_SJUBank extends Account_S2023_SJUBank {

  /** 
  * Only a limited number of withdrawal per month is possible 
  */
  private int WithdrawalsAvailable;
  /** 
   * Annual interest rate 
   */
  private double InterestRate;

  /**
   * Constructor methods.
   * 
   * @param no param is provided, hence the Balance is set to 0.
   */
  Savings_S2023_SJUBank() {
    try {
      this.setBalance(0);
    } catch (InvalidAmountException e) {
      this.Balance = 0;
    }
    this.setWithdrawalsAvailable(2); // should be retrieved by the db
    this.setInterestRate(0.01);
    System.out.printf("Savings account created.\n");
  }
  
  /**
   * Constructor methods.
   *
   * @throws InvalidAmountException
   * Exception thrown when the amount is not correct in the context.
   * 
   * @see InvalidAmountException
   * 
   * @param balance is the initial amount deposited into the account.
   */
  Savings_S2023_SJUBank(double balance) {
    try {
      this.setBalance(balance);
    } catch (InvalidAmountException e) {
      System.out.println("Exception: " + e);
    }
    this.setWithdrawalsAvailable(2); // should be retrieved by the db
    this.setInterestRate(0.01);
    System.out.printf("Savings account created.\n"); 
  }

  /** 
   * interest rate Getter 
   * 
   * @return InterestRate
   * Returns the set interest rate applied from the bank.
   */
  protected double getInterestRate() {
    return InterestRate;
  }

  /**
   * interest rate Setter
   * 
   * @param interestRate
   * Value used to set interest rate from the bank.
   */
  protected void setInterestRate(double interestRate) {
    InterestRate = interestRate;
  }

  /** 
   * withdrawalsAvailable Getter 
   * 
   * @return WithdrawalsAvailable
   * Returns the Withdrawals Available for this account.
   */
  protected int getWithdrawalsAvailable() {
    return WithdrawalsAvailable;
  }
  
  /**
   * withdrawalsAvailable Setter
   * 
   * @param withdrawalsAvailable
   * Value used to set withdrawals available for this account.
   */
  protected void setWithdrawalsAvailable(int withdrawalsAvailable) {
    this.WithdrawalsAvailable = withdrawalsAvailable;
  }

  /**
   * Withdraw method.
   *
   * @throws WithdrawalsAvailableException 
   * Exception thrown if the number of withdrawals available
   * is exhausted.
   * 
   * @throws InvalidAmountException 
   * Exception thrown when the amount is not correct in the context.
   * 
   * @see InvalidAmountException
   * 
   * @see WithdrawalsAvailableException
   * 
   * @param amount is the parameter received by the method to perform the withdraw. 
   * This will be subtracted from the balance.
   */
  @Override
  public void withdraw(double amount) throws InvalidAmountException, WithdrawalsAvailableException, ArithmeticException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.printf("Withdrawning amount: %.2f ...\n", amount);
    if (Balance - amount < 0) {
      System.out.println("Insufficient funds");
    } else if (this.getWithdrawalsAvailable() == 0) {
      throw new WithdrawalsAvailableException(this.getWithdrawalsAvailable());
    } else {
		if(Double.isInfinite(Balance - amount))
	  	  throw new ArithmeticException();
	    else
      Balance = Balance - amount;
      System.out.printf("Current Balance: (%.2f)\n", getBalance());
      this.WithdrawalsAvailable--;
      System.out.printf("Withdrawn amount: %.2f\n", amount);
      System.out.println("Withdrawals Available after operation: " + getWithdrawalsAvailable());
    }
  }

  /**
   * Deposit method.
   * 
   * @throws InvalidAmountException
   * Exception thrown when the amount is not correct in the context.
   * 
   * @throws ArithmeticException
   * Exception thrown when the value overflows.
   * 
   * @see InvalidAmountException
   * 
   * @see ArithmeticException
   * 
   * @param amount is the parameter received by the method to perform the deposit. 
   * This will be added to the balance.
   */
  @Override
  public void deposit(double amount) throws InvalidAmountException, ArithmeticException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.println("Depositing...");
    if(Double.isInfinite(Balance + amount))
  	  throw new ArithmeticException();
    else
    Balance = Balance + amount;
    System.out.printf("Deposited amount: %.2f\n", amount);
    System.out.printf("Current Balance: %.2f\n", getBalance());
  }

  /** 
   * Accrued interest method 
   *  Method displaying the forecast accrued interest.
   */
  public void computeAccruedInterest() {
    System.out.println("Accrued interest forecast: " + this.getBalance() * this.getInterestRate());
  }

}