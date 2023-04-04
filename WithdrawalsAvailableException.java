
/**
 * This exception is defined to manage issues in case the number of withdrawals
 * available is = 0, hence this is thrown if the user try to perform such action
 * under the condition just specified.
 */
public class WithdrawalsAvailableException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Number of withdrawals available that generated the exception.
	 */
	int withdrawalsAvailable;

	WithdrawalsAvailableException(int withdrawalsAvailable) {
		System.out.println("Withdrawals available: " + withdrawalsAvailable);
		this.withdrawalsAvailable = withdrawalsAvailable;
	}
}