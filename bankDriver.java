
public class bankDriver {

	public static void main(String[] args) {
		// arraylist of bank accounts
		// arraylist of savings accounts
		
		Checking_S2023_TEAM4 acc1 = new Checking_S2023_TEAM4(100);
		//acc1.deposit(100);
		acc1.withdraw(100);  // testing methods
		System.out.println(acc1.getBalance());
		acc1.withdraw(100.1);
		acc1.deposit(1);
		System.out.println(acc1.getBalance());
		acc1.withdraw(.1);
		System.out.println(acc1.getBalance());
		
	}

}
