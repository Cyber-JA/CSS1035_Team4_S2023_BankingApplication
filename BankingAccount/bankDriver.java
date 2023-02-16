package BankingAccount;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class bankDriver {
	
	static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
	static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
	static String Username = null;
	static String Password = null;
	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, InterruptedException {
		//selectData("user");
		//login("admin","letmei");
		
		//TODO implement methods from this code into the new SQL class file GIUSEPPE
		Scanner in = new Scanner(System.in); //Remember to clean to buffer
		System.out.println("Welcome to SJU bank, please enter your username followed by the enter key then your password followed by the enter key");
		 Username = in.nextLine();  
		 Password = in.nextLine();
		 System.out.println(Username);//to be removed
		 System.out.println(Password);//to be removed
		 Scanner selection = new Scanner(System.in);
		 int choice = -1;
		//wrap into method
		 if(database.login(Username,Password)==1) {
			 System.out.println("Login successful");
			//wrap into method
			 if(database.checkAccountType(Username).contains("Savings")) {
				 System.out.println("Welcome to your account " + Username);
				 System.out.println("Please select a choice ranging from 0-2");
				 displayMenu();
				 setObjectVariablesSavings();
				 while(true) {
					 choice = selection.nextInt();
					 if(choice == 0) {
						//wrap into method
						 System.out.println("Thanks for using SJU Bank Services!");
						 break;
					 }
					 if(choice==1) {
						//wrap into method
						 System.out.println("How much would you like to withdraw?");
						 try {
							accountS.withdraw(selection.nextFloat());
						} catch (InvalidAmountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (WithdrawalsAvailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 database.updateSQLBalance(Username,accountS.getBalance());
						 //System.out.println(checkBalance(Username));
						 System.out.println("Please select a choice ranging from 0-2");
						 displayMenu();
						 continue;
					 }
					 if(choice==2) {
						//wrap into method
						 System.out.println("How much would you like to deposit?");
						 try {
							accountS.deposit(selection.nextFloat());
						} catch (InvalidAmountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 database.updateSQLBalance(Username,accountS.getBalance());
						 System.out.println("Please select a choice ranging from 0-2");
						 displayMenu();
						 continue;
						 
					 }
					 
				 }
			 }
			 else if(database.checkAccountType(Username).contains("Checking")) {
				 System.out.println("Welcome to your account " + Username);
				 displayCheckingMenu();
				 setObjectVariablesCheckings();
				 while(true) {
					 choice = selection.nextInt();
					 if(choice == 0) {
						 //wrap into method
						 System.out.println("Thanks for using SJU Bank Services!");
						 break;
					 }
					 if(choice==1) {
						 //wrap into method
						 System.out.println("How much would you like to withdraw?");
						 try {
							accountC.withdraw(selection.nextFloat());
						} catch (InvalidAmountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (OverdraftAccountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 database.updateSQLBalance(Username,accountC.getBalance());
						 //System.out.println(checkBalance(Username));
						 displayCheckingMenu();
						 continue;
					 }
					 if(choice==2) {
						 //wrap into method
						 System.out.println("How much would you like to deposit?");
						 try {
							accountC.deposit(selection.nextFloat());
						} catch (InvalidAmountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 database.updateSQLBalance(Username,accountC.getBalance());
						 displayCheckingMenu();
						 continue;
						 
					 }
					 if(choice==3) {
						//wrap into method
						 System.out.println("How much would you like to pay");
						 try {
							accountC.makePayment(selection.nextFloat());
						} catch (InvalidAmountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (OverdraftAccountException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 database.updateSQLBalance(Username,accountC.getBalance());
						 displayCheckingMenu();
						 continue;
						 
					 }
					 
				 }
			 }
		 }
		 else {
			 System.out.println("Login failed");
		 }
	}
	
	public static void setObjectVariablesSavings() throws SQLException {
		try {
			accountS.setBalance(database.checkBalance(Username));
		} catch (InvalidAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountS.setUID(database.checkUID(Username));
		//retrieve withdrawals available from db
	}
	
	public static void setObjectVariablesCheckings() throws SQLException {
		try {
			accountC.setBalance(database.checkBalance(Username));
		} catch (InvalidAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountC.setUID(database.checkUID(Username));
		//retrieve overdraft from db
	}
	
	public static void displayMenu() {
		 System.out.println("0. Exit");
		 System.out.println("1. Withdraw");
		 System.out.println("2. Deposit");
	}
	
	public static void displayCheckingMenu() {
		System.out.println("Please select a choice ranging from 0-3");
		displayMenu();
		System.out.println("3. Make payment");
	}
}