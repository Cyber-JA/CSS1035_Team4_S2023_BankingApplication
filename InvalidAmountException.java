
/**
 * This exception was defined to manage issues with negative inputs for the
 * "withdraw" and "deposit" methods. A negative amount is not accepted, hence is
 * managed by throwing such exception.
 * 
 * SECURE PRACTICE: This exception is used to prevent the user from inserting
 * values that would trigger number overflows.
 */
public class InvalidAmountException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Amount that generated the exception.
	 */
	double amount;

	InvalidAmountException(double amount) {
		System.out.println("Invalid amount inserted: ." + amount);
		this.amount = amount;
	}
}