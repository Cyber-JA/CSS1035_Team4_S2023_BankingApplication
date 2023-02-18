package bankingAccount;

public class Checking_S2023_SJUBank extends Account_S2023_SJUBank {
  /* Starting Balance of the Checking account */
  // thanks to the superclass, balance is available //private double Balance;
  /*
   * Overdraft is possible but only once. To perform a new one it is needed to
   * have a positive balance
   */
  private int overdraftcounter;
  /* A fee is applied when overdraft */
  private double overdraftFee;
  /* ATM withdrawal fee */
  private double WithdrawalsFee;

  /* Constructors */
  Checking_S2023_SJUBank() {
    try {
      this.setBalance(0);
    } catch (InvalidAmountException e) {
      this.Balance = 0;
    }
    this.setOverdraftcounter(0);
    this.setOverdraftFee(2);
    this.setWithdrawalsFee(1);
    System.out.printf("Checking account created.\n"); 
  }

  Checking_S2023_SJUBank(double balance) {
    try {
      this.setBalance(balance);
    } catch (InvalidAmountException e) {
      System.out.println("Exception: " + e);
    }
    this.setOverdraftcounter(0);
    this.setOverdraftFee(2);
    this.setWithdrawalsFee(1);
    System.out.printf("Checking account created.\n");
  }

  /* overdraftFee Getter and Setter */
  public double getOverdraftFee() {
    return overdraftFee;
  }

  public void setOverdraftFee(double overdraftFee) {
    this.overdraftFee = overdraftFee;
  }

  /* Overdraftcounter getter and setter */
  public int getOverdraftcounter() {
    return overdraftcounter;
  }

  public void setOverdraftcounter(int overdraftcounter) {
    this.overdraftcounter = overdraftcounter;
  }

  /* WithdrawalsFee getter and setter */
  public double getWithdrawalsFee() {
    return WithdrawalsFee;
  }

  public void setWithdrawalsFee(double withdrawalsFee) {
    WithdrawalsFee = withdrawalsFee;
  }

  /** Method to apply withdrawals fee. */
  public void applyWithdrawalsFee() {
    System.out.println("Applying withdrawal fee (" + this.getWithdrawalsFee() + "$)");
    Balance = Balance - this.getWithdrawalsFee();
    System.out.printf("Current balance after withdrawal: %.2f\n", this.getBalance());
  }

  /** Method to apply overdraft fee. */
  public void applyOverdraftFee() {
    System.out.println("Applying overdraft fee (" + this.getOverdraftFee() + "$)");
    Balance = Balance - this.getOverdraftFee();
    System.out.printf("Current balance after overdraft: %.2f\n", this.getBalance());
  }

  /**
   * Withdraw method.
   *
   * @throwsInvalidAmountException
   * 
   * @throwsOverdraftAccountException
   */
  @Override
  public void withdraw(double amount) throws InvalidAmountException, OverdraftAccountException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.printf("Amount selected: %.2f\n", amount);
    // checking if account will be overdrawn by withdrawal
    if (Balance - amount < 0 && overdraftcounter == 0) { 

      // final double fee = 2; //fee imposed to the first overdraft transaction
      System.out.println("Insufficient funds, a two dollar fee will be levied.");
      // first overdraft transaction is allowed but a two dollar fee is imposed.
      Balance = Balance - amount; 
      this.applyWithdrawalsFee();
      this.applyOverdraftFee();
      overdraftcounter++; // mark the overdraft
      // if not overdraft, proceed with the withdrawal
    } else if (Balance - amount >= 0 && overdraftcounter == 0) { 
      System.out.printf("Withdrawn amount: %.2f\n", amount);
      Balance = Balance - amount;
      this.applyWithdrawalsFee();
      if (this.getBalance() < 0) {
        this.applyOverdraftFee();
        this.overdraftcounter++;

      }
      // last possible case, if overdraft, cannot proceed with withdrawal
    } else if (overdraftcounter != 0) { 

      throw new OverdraftAccountException(this.getOverdraftcounter());

    }
  }

  /**
   * Deposit method.
   *
   * @throwsInvalidAmountException
   */
  @Override
  public void deposit(double amount) throws InvalidAmountException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    System.out.println("Depositing...");
    Balance = Balance + amount;
    System.out.printf("Deposited amount: %.2f\n", amount);
    if (Balance >= 0) { //
      if (this.getOverdraftcounter() > 0)
        System.out.println("Overdraft restored.");
      this.setOverdraftcounter(0);
    }

  }

  /**
   * Method to perform payment with checking account.
   *
   * @throwsInvalidAmountException
   *
   * @throwsOverdraftAccountException
   */
  public void makePayment(double amount) throws InvalidAmountException, OverdraftAccountException {
    if (amount <= 0) {
      throw new InvalidAmountException(amount);
    }
    if (this.getOverdraftcounter() != 0) {
      throw new OverdraftAccountException(this.getOverdraftcounter());
    }

    else if (this.Balance - amount < 0 && this.getOverdraftcounter() == 0) {
      this.Balance -= amount;
      System.out.println("Insufficient funds, a two dollar fee will be levied.");
      this.applyOverdraftFee();
      overdraftcounter++; // mark the overdraft
      System.out.printf("Payment completed. New balance: %.2f\n", this.getBalance());
      return;
    } else {
      this.Balance -= amount;
      System.out.printf("Payment completed. New balance: %.2f\n", this.getBalance());
    }
  }

}

/**
 * This exception is used in case in which the account is overdrafted. In this
 * case, the withdrawal cannot be done, hence this exception will be thrown.
 */
class OverdraftAccountException extends Exception {

  private static final long serialVersionUID = 1L;
  int overdraftCounter;

  OverdraftAccountException(int overdraftCounter) {
    System.out.println("This account is overdrafted, restore it to positive balance to proceed");
    this.overdraftCounter = overdraftCounter;
  }
}