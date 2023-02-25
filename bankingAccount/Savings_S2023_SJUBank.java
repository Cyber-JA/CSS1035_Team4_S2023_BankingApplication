package bankingAccount;

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

  /* Interest rate getter and setter */
  public double getInterestRate() {
    return InterestRate;
  }

  public void setInterestRate(double interestRate) {
    InterestRate = interestRate;
  }

  /* WithdrawalsAvailable getter and setter */
  public int getWithdrawalsAvailable() {
    return WithdrawalsAvailable;
  }

  public void setWithdrawalsAvailable(int withdrawalsAvailable) {
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
  public void withdraw(double amount) throws InvalidAmountException, WithdrawalsAvailableException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.printf("Withdrawning amount: %.2f ...\n", amount);
    if (Balance - amount < 0) {
      System.out.println("Insufficient funds");
    } else if (this.getWithdrawalsAvailable() == 0) {
      throw new WithdrawalsAvailableException(this.getWithdrawalsAvailable());
    } else {
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
   * @see InvalidAmountException
   * 
   * @param amount is the parameter received by the method to perform the deposit. 
   * This will be added to the balance.
   */
  @Override
  public void deposit(double amount) throws InvalidAmountException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.println("Depositing...");
    Balance = Balance + amount;
    System.out.printf("Deposited amount: %.2f\n", amount);
    System.out.printf("Current Balance: %.2f\n", getBalance());
  }

  /* Accrued interest method */
  public void computeAccruedInterest() {
    System.out.println("Accrued interest forecast: " + this.getBalance() * this.getInterestRate());
  }

}