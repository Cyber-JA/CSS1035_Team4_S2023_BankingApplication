package bankingAccount;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class bankDriver {

  static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
  static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
  static String Username = null;
  static String Password = null;

  /************** MAIN METHOD. *************/
  public static void main(String[] args)
      throws NoSuchAlgorithmException, SQLException, IOException, InterruptedException {

    /*
     * this is the main method. here user input is obtained from cli and then
     * processed
     */

    try (Scanner in = new Scanner(System.in)) { // try-with-resource statement to free the buffer
      System.out.println(
          "Welcome to SJU bank, please enter your username "
          + "followed by the enter key then your password "
          + "followed by the enter key");
      Username = in.nextLine();
      Password = in.nextLine();
      // exiting from the try-catch statement will free the buffer
      Scanner selection = new Scanner(System.in); 
      // login
      if (database.login(Username, Password) == 1) {
        System.out.println("Login successful");
        // ATM-like menu
        interactiveMenu(selection);
      } else {
        System.out.println(database.login(Username, Password));
        System.out.println("Login failed");
      }
    }
  }

  /**
   * This method is used to retrieve data from db and setup the account.
   */
  public static void setObjectVariablesSavings() throws SQLException {
    try {
      accountS.setBalance(database.checkBalance(Username));
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    }
    accountS.setUID(database.checkUID(Username));
    // needed to set withdrawals available
  }

  /**
   * This method is used to retrieve data from db and setup the account.
   */
  public static void setObjectVariablesCheckings() throws SQLException {
    try {
      accountC.setBalance(database.checkBalance(Username));
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    }
    accountC.setUID(database.checkUID(Username));
    // needed to set the correct overdraft
  }

  /**
   * This method is used to display a general menu, common to savings and checking
   * account.
   */
  public static void displayMenu() {
    System.out.println("0. Exit");
    System.out.println("1. See Balance");
    System.out.println("2. Withdraw");
    System.out.println("3. Deposit");
  }

  /**
   * method specific for checking account, since it is possible to perform
   * payments.
   */
  public static void displayCheckingMenu() {
    System.out.println("Please select a choice ranging from 0-4");
    displayMenu();
    System.out.println("4. Make payment");
  }

  /**
   * This is the method in which is implemented the ATM-like menu and the process
   * associated The user is required to prompt a number in the range printed into
   * the menu to perform the corresponding action.
   */
  public static void interactiveMenu(Scanner selection) {
    try {
      if (database.checkAccountType(Username).contains("Savings")) {
        System.out.println("Welcome to your account " + Username);
        System.out.println("Please select a choice ranging from 0-3");
        displayMenu();
        try {
          setObjectVariablesSavings();
        } catch (SQLException e1) {
          System.out.println(e1);
        }
        int choice = -1;
        while (true) {
          choice = selection.nextInt();
          if (choice == 0) {
            choice0();
            break;
          }
          if (choice == 1) {
            choice1Savings();
            continue;
          }
          if (choice == 2) {
            choice2Savings(selection);
            continue;
          }
          if (choice == 3) {
            choice3Savings(selection);
            continue;
          } else
            invalidChoice();

        }
      } else if (database.checkAccountType(Username).contains("Checking")) {
        System.out.println("Welcome to your account " + Username);
        displayCheckingMenu();
        setObjectVariablesCheckings();

        while (true) {
          int choice = selection.nextInt();
          if (choice == 0) {
            choice0();
            break;
          }
          if (choice == 1) {
            choice1Checking();
            continue;
          }
          if (choice == 2) {
            choice2Checking(selection);
            continue;

          }
          if (choice == 3) {
            choice3Checking(selection);
            continue;
          }
          if (choice == 4) {
            choice4Checking(selection);
            continue;
          } else
            invalidChoice();

        }
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  /** method to break from the menu. */
  public static void choice0() {
    System.out.println("Thanks for using SJU Bank Services!");
  }

  /** wrapper to perform the withdraw from savings account. */
  public static void choice2Savings(Scanner selection) {
    System.out.println("How much would you like to withdraw?");
    try {
      accountS.withdraw(selection.nextFloat());
      database.updateSQLBalance(Username, accountS.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println("Exception:" + e);
    } catch (WithdrawalsAvailableException e) {
      System.out.println("Exception:" + e);
    } catch (SQLException e) {
      System.out.println("Exception:" + e);
    }
    System.out.println("Please select a choice ranging from 0-3");
    displayMenu();
  }

  /** wrapper to perform the deposit into savings account. */
  public static void choice3Savings(Scanner selection) {
    System.out.println("How much would you like to deposit?");
    try {
      accountS.deposit(selection.nextFloat());
      database.updateSQLBalance(Username, accountS.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    }
    System.out.println("Please select a choice ranging from 0-3");
    displayMenu();
  }

  /** wrapper to perform the withdraw from checking account. */
  public static void choice2Checking(Scanner selection) {
    System.out.println("How much would you like to withdraw?");
    try {
      accountC.withdraw(selection.nextFloat());
      database.updateSQLBalance(Username, accountC.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println("Exception:" + e);
    } catch (OverdraftAccountException e) {
      System.out.println("Exception:" + e);
    } catch (SQLException e) {
      System.out.println("Exception:" + e);
    }
    displayCheckingMenu();
  }

  /** wrapper to perform the deposit into checking account. */
  public static void choice3Checking(Scanner selection) {
    System.out.println("How much would you like to deposit?");
    try {
      accountC.deposit(selection.nextFloat());
      database.updateSQLBalance(Username, accountC.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println("Exception:" + e);
    } catch (SQLException e) {
      System.out.println("Exception:" + e);
    }
    displayCheckingMenu();
  }

  /** wrapper to perform the payment linked to the checking account. */
  public static void choice4Checking(Scanner selection) {
    System.out.println("How much would you like to pay");
    try {
      accountC.makePayment(selection.nextFloat());
      database.updateSQLBalance(Username, accountC.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (OverdraftAccountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    }
    displayCheckingMenu();
  }

  /**
   * Wrapper for the method to check the balance into checking account.
   */
  public static void choice1Checking() {
    System.out.println("Balance: " + accountC.getBalance());
    displayCheckingMenu();
  }

  /**
   * Wrapper for the method to check the balance into savings account.
   */
  public static void choice1Savings() {
    System.out.println("Balance: " + accountS.getBalance());
    System.out.println("Please select a choice ranging from 0-3");
    displayMenu();
  }

  public static void invalidChoice() {
    System.out.println("Insert a valid command...");
  }
}