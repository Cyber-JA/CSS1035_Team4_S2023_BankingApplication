package bankingAccount;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

/**
* The bankDriver program implements an application that
* allows for a bank-user interaction.
*
* @author  Giuseppe, Hassan, Tatiana, Rajiv, Jake
* @version 1.0
* @since   2023-02-24 
*/

public class bankDriver {
private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]{0,20}$");
  static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
  static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
  static String Username = null;
  static String Password = null;

  /* MAIN METHOD. */
  
  /**
   * this is the main method. here user input is obtained from cli and then
   * processed. SECURE PRACTICE: In this function, user's input is received and placed into a buffer.
   * Thanks to the usage of try-with-resource statement, the buffer is freed at the end of the usage.
   * The program is not suffering overflows, since double values are used, which can overflows only to infinity
   * and doesn't wrap around. To deal with infinity, the Math.isInfinity method is used and an exception thrown. 
   * 
   * @param args
   * Can be used to pass arguments to the main method.
   * 
   * @throws NoSuchAlgorithmException
   * Exception thrown in case of error related to hash algorithms used into the program, performing the login.
   * 
   * @throws SQLException
   * Exception thrown in case of wrong SQL request.
   * 
   * @throws IOException
   * Exception thrown in case of wrong IO operations.
   * 
   * @throws ArithmeticException
   * Exception thrown in case of overflows.
   * 
   * @see SQLException
   * 
   * @see IOException
   * 
   * @see NoSuchAlgorithmException
   * 
   * @see ArithmeticException
   */

  public static void main(String[] args)
      throws NoSuchAlgorithmException, SQLException, IOException, ArithmeticException {
	  
    try (Scanner in = new Scanner(System.in)) { // try-with-resource statement to free the buffer
      System.out.println(
          "Welcome to SJU bank, please enter your username "
          + "followed by the enter key then your password "
          + "followed by the enter key");
      Username = in.nextLine();
      Password = in.nextLine();
      validate(Username,Username);
      validate(Password,Password);
      // exiting from the try-catch statement will free the buffer
      Scanner selection = new Scanner(System.in); 
      // login
      if (database.login(Username, Password) == 1) {
        System.out.println("Login successful");
        // ATM-like menu
        interactiveMenu(selection);
      } else {
        System.out.println("Login failed");
      }
    }
  }

  /**
   * This method is used to retrieve data from db and setup the account.
   * 
   * @throws SQLException
   * Exception thrown in case of wrong SQL request.
   * 
   * @see SQLException
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
   * 
   * @throws SQLException
   * Exception thrown in case of wrong SQL request.
   * 
   * @see SQLException
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
   * 
   * @param selection 
   * Used to manage the user input.
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

  /** 
   * Method to break from the menu. 
   */
  public static void choice0() {
    System.out.println("Thanks for using SJU Bank Services!");
  }

  /** 
   * Wrapper to perform the withdraw from savings account. 
   *  
   * @param selection 
   * Used to manage the user input.
   */
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

  /** 
   * Wrapper to perform the deposit into savings account.
   * 
   * @param selection 
   * Used to manage the user input.
   */
  public static void choice3Savings(Scanner selection) {
    System.out.println("How much would you like to deposit?");
    try {
      accountS.deposit(selection.nextFloat());
      database.updateSQLBalance(Username, accountS.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    } catch (ArithmeticException e) {
        System.out.println(e);
    }
    System.out.println("Please select a choice ranging from 0-3");
    displayMenu();
  }

  /**
   *  Wrapper to perform the withdraw from checking account. 
   *  
   * @param selection 
   * Used to manage the user input.
   */
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
    } catch (ArithmeticException e) {
        System.out.println(e);
    }
    
    displayCheckingMenu();
  }

  /** 
   * Wrapper to perform the deposit into checking account.
   * 
   * @param selection 
   * Used to manage the user input.
   */
  public static void choice3Checking(Scanner selection) {
    System.out.println("How much would you like to deposit?");
    try {
      accountC.deposit(selection.nextFloat());
      database.updateSQLBalance(Username, accountC.getBalance());
    } catch (InvalidAmountException e) {
      System.out.println("Exception:" + e);
    } catch (SQLException e) {
      System.out.println("Exception:" + e);
    } catch (ArithmeticException e) {
        System.out.println(e);
    }
    displayCheckingMenu();
  }

  /** 
   * Wrapper to perform the payment linked to the checking account. 
   * 
   * @param selection 
   * Used to manage the user input.
   */
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
    } catch (ArithmeticException e) {
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
  /**
   * Method to manage an invalid choice from the menu.
   */
  public static void invalidChoice() {
    System.out.println("Insert a valid command...");
  }
  public static String validate(String name, String input) throws DataValidationException {
	    String canonical = normalize(input);
	 
	    if (!pattern.matcher(canonical).matches()) {
	      throw new DataValidationException("Improper format in " + name + " field");
	    }
	     
	    // Performs output encoding for nonvalid characters
	    canonical = HTMLEntityEncode(canonical);
	    return canonical;
	  }
	 
	  // Normalizes to known instances 
	  private static String normalize(String input) {
	    String canonical =
	      java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFKC);
	    return canonical;
	  }
	 
	  // Encodes nonvalid data
	  private static String HTMLEntityEncode(String input) {
	    StringBuffer sb = new StringBuffer();
	 
	    for (int i = 0; i < input.length(); i++) {
	      char ch = input.charAt(i);
	      if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
	        sb.append(ch);
	      } else {
	        sb.append("&#" + (int)ch + ";");
	      }
	    }
	    return sb.toString();
	  }
	}
