package bankingAccount;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

/**
* The bankDriver program implements an application that
* allows for a bank-user interaction.
* 
* SECURE PRACTICE: In this function, user's input is received and placed into a buffer.
* Thanks to the usage of try-with-resource statement, the buffer is freed at the end of the usage.
* The program is not suffering overflows, since double values are used, which can overflows only to infinity
* and doesn't wrap around. To deal with infinity, the Math.isInfinity method is used and an exception thrown. 
* Last but not least, since some untrusted Strings are prompted by the user, it is needed to normalize
* then validate those strings, as well as encode the ones used when printing on stdout. Hence input normalization
* input validation and output encoding are performed in this file.
*
*/

public class bankDriver {

  private static final Pattern pattern = Pattern.compile("^[\\s\\w\\W]{0,20}$");
  static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
  static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
  static String Username = null;
  static String Password = null;

  /* MAIN METHOD. */
  
  /**
   * this is the main method. here user input is obtained from cli and then
   * processed. 
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
	  database a = new database();
	  //System.out.println(a.createAccount("Username", "password5", 0, 0));
	  setUTF8systemout();
    try (Scanner in = new Scanner(System.in)) { // try-with-resource statement to free the buffer    
    	System.out.println(database.getRowCount());
    	System.out.println(
          "Welcome to SJU bank, please enter your username "
          + "followed by the enter key then your password "
          + "followed by the enter key");
      Username = in.nextLine();
      Password = in.nextLine();
      if(createAccountMenu(in) == 0) {
    	  database.createAccount(Username, Password, 0, 0);
      }
      passwordRequirements(Password, in);
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
  private static void setObjectVariablesSavings() throws SQLException {
    try {
      accountS.setBalance(database.checkBalance(Username, "Savings"));
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
  private static void setObjectVariablesCheckings() throws SQLException {
    try {
      accountC.setBalance(database.checkBalance(Username, "Checking"));
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
  private static void displayMenu() {
    System.out.println("0. Exit");
    System.out.println("1. See Balance");
    System.out.println("2. Withdraw");
    System.out.println("3. Deposit");
  }

  /**
   * method specific for checking account, since it is possible to perform
   * payments.
   */
  private static void displayCheckingMenu() {
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
      
    	int val = chooseAccount(selection);
      
      if (val == 2) {
        System.out.println("Welcome to your savings account " + HTMLEntityEncode(Username));
        System.out.println("Please select a choice ranging from 0-3");
        displayMenu();
        try {
          setObjectVariablesSavings();
        } catch (SQLException e1) {
          System.out.println(e1);
        }
        while (true) {
        	
        int choice = validateIntInput(selection);
				
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
      } else if (val == 1) {
        System.out.println("Welcome to your checking account " + HTMLEntityEncode(Username));
        displayCheckingMenu();
        setObjectVariablesCheckings();

        while (true) {
        	int choice = validateIntInput(selection);
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
      } else if (val == 0) {
    	  System.out.println("Exceeded number of trials.");
    	  choice0();
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  /** 
   * Method to break from the menu. 
   */
  private static void choice0() {
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
      accountS.withdraw(validateFloatInput(selection));
      database.updateSQLBalance(Username, accountS.getBalance(), "Savings");
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
    	accountS.withdraw(validateFloatInput(selection));
      database.updateSQLBalance(Username, accountS.getBalance(), "Savings");
    } catch (InvalidAmountException e) {
      System.out.println(e);
    } catch (SQLException e) {
      System.out.println(e);
    } catch (ArithmeticException e) {
      System.out.println(e);
    } catch (WithdrawalsAvailableException e) {
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
      accountC.withdraw(validateFloatInput(selection));
      database.updateSQLBalance(Username, accountC.getBalance(),"Checking");
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
      accountC.deposit(validateFloatInput(selection));
      database.updateSQLBalance(Username, accountC.getBalance(),"Checking");
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
      accountC.makePayment(validateFloatInput(selection));
      database.updateSQLBalance(Username, accountC.getBalance(),"Checking");
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
  private static void invalidChoice() {
    System.out.println("Insert a valid command...");
  }
  
  /**
   * Method used to normalize user input according to the specified Normalizer.
   * {@link java.text.Normalizer.Form#NFKC}
   * 
   *  @param input
   *  Untrusted input to normalize.
   *  
   *  @return canonical
   *  returned normalized input.
   */
  public static String normalize(String input) {
	    String canonical =
	      java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFKC);
	    return canonical;
	  }
	 
  /**
   * Performing encoding of invalid characters before outputting it into stdout.
   * 
   *  @param input
   *  Receiving normalized, then validated input.
   *  
   *  @return encoded input.
   */
  public static String HTMLEntityEncode(String input) {
	    StringBuffer sb = new StringBuffer();
	 
	    for (int i = 0; i < input.length(); i++) {
	      char ch = input.charAt(i);
	      if (Character.isLetterOrDigit(ch) || Character.isWhitespace(ch)) {
	        sb.append(ch);
	      }
	      else if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isWhitespace(ch)) {
	    	  sb.append(ch);
	      }
	      else {
	        sb.append("&#" + (int)ch + ";");
	      }
	    }
	    return sb.toString();
	  }
  
  private static void passwordRequirements(String Password, Scanner selection) {
		String str = Password;
		int specials = 0, digits = 0, letters = 0, satisfied = 0;
		while(satisfied == 0) {
			for (int i = 0; i < str.length(); ++i) {
				   char ch = str.charAt(i);
				   if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isWhitespace(ch)) {
				      ++specials;
				   } else if (Character.isDigit(ch)) {
				      ++digits;
				   } else {
				      ++letters;
				   }
			}
			if(letters >= 8 && digits >= 1 && specials >= 1)
				satisfied = 1;
			else {
				System.out.println("Password needs to respect the following criteria:");
				System.out.println("1. At least 8 letters");
				System.out.println("2. At least 1 digit");
				System.out.println("3. At least 1 special char");
				System.out.println("Insert password:");
				str = selection.next();
			}
		}
	}
	
  /**
  * Wrapper method to set the UTF-8 Encoding.
  * 
  *  @throws UnsupportedEncodingException
  *  Exception thrown in case of unsupported encoding.
  */
  public static void setUTF8systemout() throws UnsupportedEncodingException {
		PrintStream out = new PrintStream(System.out, true, "UTF-8"); // PrintStream object with UTF-8 encoding
		System.setOut(out); // set console printing to the new PrintStream object we declared.	
	}

  
  /**
   * In this method are performed both validation and normalization 
   * of user's input. Normalization is performed with the normalize method.
   * In particular, related to the validation, the check is performed against a specified patter.
   * At the end, output encoding for nonvalid characters is performed and the value returned.
   * @see normalize
   * 
   * 
   *  @param name
   *  Input acquired by the thrown exception
   *  
   *  @param input
   *  Input normalized and then validated.
   *  
   *  @throws DataValidationException
   *  Exception thrown if, after normalizing, when validating data, is not properly formatted.
   *  
   *  @return canonical
   *  returned normalized, validated, HTML entity encoded input.
   */
  public static String validate(String name, String input) throws DataValidationException {
	  //normalizing  
	  String canonical = normalize(input);
	  	//validating
	    if (!pattern.matcher(canonical).matches()) {
	      throw new DataValidationException("Improper format in " + name + " field");
	    }
	     
	    // Performs output encoding for nonvalid characters
	    canonical = HTMLEntityEncode(canonical);
	    return canonical;
	  }
  
  protected static int chooseAccount(Scanner selection) {
	  System.out.println("Which account do you wanna access?");
	  System.out.println("Available accounts:");
	  System.out.println("1. Checking");
	  System.out.println("2. Savings");
	  int choice = validateIntInput(selection);
	  int errCounter = 3;
	  while(choice != 1 && choice != 2) {
		  
		  invalidChoice();
		  choice = validateIntInput(selection);
		  errCounter--;
		  if(errCounter == 0)
			  return 0;
	  }
	  return choice;
  }
  
  protected static int createAccountMenu(Scanner selection) {
	  
	  System.out.println("Do you want to create a new account ?");
	  System.out.println("0. Yes");
	  System.out.println("1. No");
	  int choice = -1;
	  int errorCounter = 3;
	  while(choice != 0 && choice != 1 && errorCounter != 0) {
		  choice = validateIntInput(selection);
		  errorCounter--;
	  }
	  return choice;
  }
  
  /**
   * Method used to validate correctly integer user's input.
   * 
   * @param selection.
   * Scanner used to perform reading from stdin.
   * 
   * @return intInputValue
   * Returned integer value parsed or -1 on error.
   */
  public static int validateIntInput(Scanner selection) {
      String input = selection.next();
      int intInputValue = 0;
      try {
          intInputValue = Integer.parseInt(input);
          return intInputValue;
      } catch (NumberFormatException ne) {
          return -1;
      }
  }
  
  /**
   * Method used to validate correctly float user's input.
   * 
   * @param selection.
   * Scanner used to perform reading from stdin.
   * 
   * @return floatInputValue
   * Returned float value parsed or -1 on error.
   */
  protected static float validateFloatInput(Scanner selection) {
      String input = selection.next();
      float floatInputValue = 0;
      try {
          floatInputValue = Float.parseFloat(input);
          return floatInputValue;
      } catch (NumberFormatException ne) {
          return -1;
      }
  }
  
}
