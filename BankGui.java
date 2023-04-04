import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.apache.logging.log4j.*;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class BankGui {
	static Checking_S2023_SJUBank accountC = new Checking_S2023_SJUBank();
	static Savings_S2023_SJUBank accountS = new Savings_S2023_SJUBank();
	static final Logger logger = LogManager.getLogger(BankLogin.class);
	protected Shell BankingAccount;
	private Text amountField;
	private Text SbalanceField;
	private Text CbalanceField;
	private Table transactionHistory;
	// generic ArrayList with inference to String type
	ArrayList<String[]> transactionHistoryarray;
	static String Username = null;
	static int UID = 0;
	database a = new database();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @throws SQLException
	 */
	BankGui(String Username) throws SQLException {
		BankGui.Username = Username;
		BankGui.UID = a.checkUID(Username);
		transactionHistoryarray = a.generateTransactionHistory(Integer.toString(BankGui.UID));
	}

	public static void main(String[] args) {
		try {
			BankGui window = new BankGui(Username);
			window.open();

		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	/**
	 * Open the window.
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		Display display = Display.getDefault();
		createContents();
		BankingAccount.open();
		BankingAccount.layout();
		while (!BankingAccount.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @throws SQLException
	 */
	protected void createContents() throws SQLException {
		setObjectVariablesSavings();
		setObjectVariablesCheckings();
		BankingAccount = new Shell();
		BankingAccount.setSize(976, 599);
		BankingAccount.setText("Banking Account");
		BankingAccount.setLayout(null);
		Button logo = new Button(BankingAccount, SWT.NONE);
		logo.setBounds(0, 0, 242, 101);
		logo.setImage(SWTResourceManager.getImage("C:\\Users\\dckfa\\Downloads\\clearlogo_244x100.png"));
		logo.setText("New Button");
		logo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Credits window = new Credits();
				window.open();
			}
		});
		Label lblbalance = new Label(BankingAccount, SWT.NONE);
		lblbalance.setBounds(541, 15, 90, 15);
		lblbalance.setText("Savings Balance:");

		Label lblCheckingBalance = new Label(BankingAccount, SWT.NONE);
		lblCheckingBalance.setBounds(530, 67, 98, 15);
		lblCheckingBalance.setText("Checking Balance:");

		SbalanceField = new Text(BankingAccount, SWT.BORDER);
		SbalanceField.setBounds(634, 12, 76, 21);
		SbalanceField.setEditable(false);
		String SavingsBalance = String.valueOf(accountS.getBalance());
		SbalanceField.setText(SavingsBalance);

		CbalanceField = new Text(BankingAccount, SWT.BORDER);
		CbalanceField.setBounds(634, 64, 76, 21);
		CbalanceField.setEditable(false);
		String CheckingsBalance = String.valueOf(accountC.getBalance());
		CbalanceField.setText(CheckingsBalance);

		DateTime dateTime = new DateTime(BankingAccount, SWT.BORDER);
		dateTime.setBounds(790, 0, 80, 24);
		dateTime.setTouchEnabled(true);
		dateTime.setEnabled(false);

		Button btnLogout = new Button(BankingAccount, SWT.NONE);
		btnLogout.setBounds(869, 0, 90, 25);
		btnLogout.setText("Logout");
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BankLogin window = new BankLogin();
				BankingAccount.close();
				window.open();

			}

		});

		Label lblTransactionHistory = new Label(BankingAccount, SWT.NONE);
		lblTransactionHistory.setBounds(0, 120, 164, 15);
		lblTransactionHistory.setText("Transaction History:");

		transactionHistory = new Table(BankingAccount, SWT.BORDER | SWT.FULL_SELECTION);
		transactionHistory.setBounds(0, 141, 959, 427);
		transactionHistory.setHeaderVisible(true);
		transactionHistory.setData(dateTime);
		transactionHistory.setData("1", lblTransactionHistory);

		TableColumn TransactionDate = new TableColumn(transactionHistory, SWT.LEFT);
		TransactionDate.setWidth(235);
		TableColumn TransactionType = new TableColumn(transactionHistory, SWT.LEFT);
		TransactionType.setWidth(355);
		TableColumn Amount = new TableColumn(transactionHistory, SWT.LEFT);
		Amount.setWidth(166);
		TableColumn AccountTypeColumn = new TableColumn(transactionHistory, SWT.LEFT);
		AccountTypeColumn.setWidth(174);
		Amount.setWidth(145);
		TransactionDate.setText("Transaction Date:");
		TransactionType.setText("Transaction Type");
		AccountTypeColumn.setText("Account Type:");
		Amount.setText("Amount");

		for (int i = 0; i < transactionHistoryarray.size(); i++) {
			TableItem item1 = new TableItem(transactionHistory, SWT.NONE);
			item1.setText(transactionHistoryarray.get(i));

		}

		Label lblAmount = new Label(BankingAccount, SWT.NONE);
		lblAmount.setBounds(248, 67, 55, 15);
		lblAmount.setText("Amount:");

		amountField = new Text(BankingAccount, SWT.BORDER);
		amountField.setBounds(309, 64, 75, 26);
		Button Sbutton = new Button(BankingAccount, SWT.RADIO);
		Sbutton.setBounds(390, 66, 90, 16);
		Sbutton.setText("Savings");
		Sbutton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

		Button Cbutton = new Button(BankingAccount, SWT.RADIO);
		Cbutton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		Cbutton.setBounds(390, 85, 90, 16);
		Cbutton.setText("Checking");
		Button Withdraw = new Button(BankingAccount, SWT.NONE); //
		Withdraw.setBounds(248, 10, 98, 25);
		Withdraw.setText("Withdraw");
		Withdraw.setImage(null);
		Withdraw.addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings("static-access")
			public void widgetSelected(SelectionEvent e) {
				float amount = validateFloatInput(amountField.getText().replace(",", "."));
				BigDecimal val = new BigDecimal(amount);
				val = val.setScale(2, RoundingMode.HALF_UP);
				float amount1 = val.floatValue();
				if (Sbutton.getSelection() == true) {

					try {
						if (database.checkBalance(Username, "Savings") == accountS.getBalance()) {
							accountS.withdraw(amount1);
							database.updateTransactionHistory("Withdrawl", amount1, BankGui.UID, "Savings");
							database.updateSQLBalance(Username, accountS.getBalance(), "Savings");
							String SavingsBalance = String.valueOf(accountS.getBalance());
							SbalanceField.setText(SavingsBalance);
						} else {
							MessageBox box = new MessageBox(BankingAccount, SWT.OK);
							box.setText("Memory edit Error");
							box.setMessage("Memory mismatch.");
							box.open();
							String SavingsBalance = String.valueOf(database.checkBalance(Username, "Savings"));
							SbalanceField.setText(SavingsBalance);
							accountS.setBalance(Double.parseDouble(SavingsBalance));
							database.updateVlog("Memory Edit", Integer.toString(UID));
						}
					} catch (ArithmeticException e1) {
						logger.error("Exception", e1);
					} catch (InvalidAmountException e1) {
						logger.error("Exception", e1);
					} catch (WithdrawalsAvailableException e1) {
						logger.error("Exception", e1);
					} catch (SQLException e1) {
						logger.error("Exception", e1);
					}

				}
				if (Cbutton.getSelection() == true) {

					float amount2 = amount1;
					if (Cbutton.getSelection() == true) {

						try {
							if (database.checkBalance(Username, "Checking") == accountC.getBalance()) {
								accountC.withdraw(amount2);
								database.updateTransactionHistory("Withdrawl", amount1, BankGui.UID, "Checking");
								database.updateSQLBalance(Username, accountC.getBalance(), "Checking");
								String CheckingBalance = String.valueOf(accountC.getBalance());
								CbalanceField.setText(CheckingBalance);

							} else {
								MessageBox box = new MessageBox(BankingAccount, SWT.OK);
								box.setText("Memory edit Error");
								box.setMessage("Memory mismatch.");
								box.open();
								String CheckingBalance = String.valueOf(database.checkBalance(Username, "Checking"));
								CbalanceField.setText(CheckingBalance);
								accountC.setBalance(Double.parseDouble(CheckingBalance));
								database.updateVlog("Memory Edit", Integer.toString(UID));
							}
						} catch (ArithmeticException e1) {
							logger.error("Exception", e1);
						} catch (InvalidAmountException e1) {
							logger.error("Exception", e1);
						} catch (SQLException e1) {
							logger.error("Exception", e1);
						} catch (OverdraftAccountException e1) {
							logger.error("Exception", e1);
						}

					}

				}
			}
		});

		Button makePayment = new Button(BankingAccount, SWT.NONE);
		makePayment.setBounds(352, 10, 98, 25);
		makePayment.setText("Make Payment");
		makePayment.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				float amount = validateFloatInput(amountField.getText().replace(",", "."));
				BigDecimal val = new BigDecimal(amount);
				val = val.setScale(2, RoundingMode.HALF_UP);
				float amount1 = val.floatValue();
				if (Sbutton.getSelection() == true) {
					MessageBox box = new MessageBox(BankingAccount, SWT.OK);
					box.setText("Error");
					box.setMessage(
							"Make Payment is only available for checking accounts, please select the checking account button.");
					box.open();

				}
				if (Cbutton.getSelection() == true) {

					float amount2 = amount1;
					if (Cbutton.getSelection() == true) {

						try {
							if (database.checkBalance(Username, "Checking") == accountC.getBalance()) {
								accountC.makePayment(amount2);
								database.updateTransactionHistory("Payment", amount1, BankGui.UID, "Checking");
								database.updateSQLBalance(Username, accountC.getBalance(), "Checking");
								String CheckingBalance = String.valueOf(accountC.getBalance());
								CbalanceField.setText(CheckingBalance);

							} else {
								MessageBox box = new MessageBox(BankingAccount, SWT.OK);
								box.setText("Memory edit Error");
								box.setMessage("Memory mismatch.");
								box.open();
								String CheckingBalance = String.valueOf(database.checkBalance(Username, "Checking"));
								CbalanceField.setText(CheckingBalance);
								accountC.setBalance(Double.parseDouble(CheckingBalance));
								database.updateVlog("Memory Edit", Integer.toString(UID));
							}
						} catch (ArithmeticException e1) {
							logger.error("Exception", e1);
						} catch (InvalidAmountException e1) {
							logger.error("Exception", e1);
						} catch (SQLException e1) {
							logger.error("Exception", e1);
						} catch (OverdraftAccountException e1) {
							logger.error("Exception", e1);
						}

					}

				}
			}
		});

		Button Deposit = new Button(BankingAccount, SWT.NONE);
		Deposit.setBounds(456, 10, 75, 25);
		Deposit.setText("Deposit");

		Label usernamedisplay = new Label(BankingAccount, SWT.NONE);
		usernamedisplay.setBounds(248, 108, 136, 15);
		usernamedisplay.setText("Welcome: " + Username);
		Deposit.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				float amount = validateFloatInput(amountField.getText().replace(",", "."));
				BigDecimal val = new BigDecimal(amount);
				val = val.setScale(2, RoundingMode.HALF_UP);
				float amount1 = val.floatValue();
				if (Sbutton.getSelection() == true) {

					try {
						if (database.checkBalance(Username, "Savings") == accountS.getBalance()) {
							accountS.deposit(amount1);
							database.updateTransactionHistory("Deposit", amount1, BankGui.UID, "Savings");
							database.updateSQLBalance(Username, accountS.getBalance(), "Savings");
							String SavingsBalance = String.valueOf(accountS.getBalance());
							SbalanceField.setText(SavingsBalance);
						} else {
							MessageBox box = new MessageBox(BankingAccount, SWT.OK);
							box.setText("Memory edit Error");
							box.setMessage("Memory mismatch.");
							box.open();
							String SavingsBalance = String.valueOf(database.checkBalance(Username, "Savings"));
							SbalanceField.setText(SavingsBalance);
							accountS.setBalance(Double.parseDouble(SavingsBalance));
							database.updateVlog("Memory Edit", Integer.toString(UID));
						}
					} catch (ArithmeticException e1) {
						logger.error("Exception", e1);
					} catch (InvalidAmountException e1) {
						logger.error("Exception", e1);
					} catch (SQLException e1) {
						logger.error("Exception", e1);
					}

				}
				if (Cbutton.getSelection() == true) {

					float amount2 = amount1;
					try {
						if (database.checkBalance(Username, "Checking") == accountC.getBalance()) {
							accountC.deposit(amount2);
							database.updateTransactionHistory("Payment", amount1, BankGui.UID, "Checking");
							database.updateSQLBalance(Username, accountC.getBalance(), "Checking");
							String CheckingBalance = String.valueOf(accountC.getBalance());
							CbalanceField.setText(CheckingBalance);

						} else {
							MessageBox box = new MessageBox(BankingAccount, SWT.OK);
							box.setText("Memory edit Error");
							box.setMessage("Memory mismatch.");
							box.open();
							String CheckingBalance = String.valueOf(database.checkBalance(Username, "Checking"));
							CbalanceField.setText(CheckingBalance);
							accountC.setBalance(Double.parseDouble(CheckingBalance));
						}
					} catch (ArithmeticException e1) {
						logger.error("Exception", e1);
					} catch (InvalidAmountException e1) {
						logger.error("Exception", e1);
					} catch (SQLException e1) {
						logger.error("Exception", e1);
					}

				}

			}
		});
	}

	static void setObjectVariablesSavings() throws SQLException {
		try {
			accountS.setBalance(database.checkBalance(Username, "Savings"));
		} catch (InvalidAmountException e) {
			logger.error("Exception ", e);
		} catch (SQLException e) {
			logger.error("Exception ", e);
		}
		accountS.setUID(database.checkUID(Username));
		// needed to set withdrawals available
	}

	/**
	 * This method is used to retrieve data from db and setup the account.
	 * 
	 * @throws SQLException Exception thrown in case of wrong SQL request.
	 * 
	 * @see SQLException
	 */
	static void setObjectVariablesCheckings() throws SQLException {
		try {
			accountC.setBalance(database.checkBalance(Username, "Checking"));
		} catch (InvalidAmountException e) {
			logger.error("Exception ", e);
		} catch (SQLException e) {
			logger.error("Exception ", e);
		}
		accountC.setUID(database.checkUID(Username));
		// needed to set the correct overdraft
	}

	/**
	 * Method used to validate correctly integer user's input.
	 * 
	 * @param selection. Scanner used to perform reading from stdin.
	 * 
	 * @return intInputValue Returned integer value parsed or -1 on error.
	 */
	public static int validateIntInput(String value) {
		String input = value;
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
	 * @param selection. Scanner used to perform reading from stdin.
	 * 
	 * @return floatInputValue Returned float value parsed or -1 on error.
	 */
	protected static float validateFloatInput(String value) {
		String input = value;
		float floatInputValue = 0;
		try {
			floatInputValue = Float.parseFloat(input);
			return floatInputValue;
		} catch (NumberFormatException ne) {
			return -1;
		}
	}

}
