
public class bankDriver {

	public static void main(String[] args) {
		// arraylist of bank accounts
		// arraylist of savings accounts
		
		checkingAccount acc1 = new checkingAccount(100);
		//acc1.deposit(100);
		acc1.withdraw(100.1);  // testing methods
		System.out.println(acc1.getBalance());
		acc1.withdraw(100.1);
		acc1.deposit(1);
		System.out.println(acc1.getBalance());
		acc1.withdraw(.1);
		System.out.println(acc1.getBalance());
		
	}

}
