

/**
 * This exception is used in case in which the account is overdrafted. In this
 * case, the withdrawal cannot be done, hence this exception will be thrown.
 */
public class OverdraftAccountException extends Exception {

  private static final long serialVersionUID = 1L;
  
  /**
   * Counter's value that generated the exception. 
   */
  int overdraftCounter;

  OverdraftAccountException(int overdraftCounter) {
    System.out.println("This account is overdrafted, restore it to positive balance to proceed");
    this.overdraftCounter = overdraftCounter;
  }
}