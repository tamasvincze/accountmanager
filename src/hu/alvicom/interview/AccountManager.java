package hu.alvicom.interview;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AccountManager implements Runnable {

	public static final String CSV_SEPARATOR = ";";

	private List<Account> accounts;
	private List<Transaction> transactions;

	public static void main(String[] args) {

		AccountManager manager = new AccountManager();
		Account account1 = new Account("11111111-22222222", CurrencyEnum.HUF, BigDecimal.valueOf(150000));
		Account account2 = new Account("22222222-33333333", CurrencyEnum.USD, BigDecimal.valueOf(1230));

		manager.addAccounts(account1, account2);

		Thread bgThread = new Thread(manager);
		bgThread.start();
	}

	private void addAccounts(Account... accounts) {
		for (Account account : accounts) {
			this.accounts.add(account);
		}
	}

	private void start() throws IOException {
		int transactionCounter = 1;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			File folder = new File(Paths.get("").toFile().getAbsolutePath());

			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".csv")) {

					Transaction transaction = parseCSV(file.getAbsolutePath());

					if (transaction != null) {

						Optional<Account> accountOptional = accounts.stream()
								.filter(acc -> acc.getAccountNumber().equals(transaction.getTargetAccountNumber()))
								.findAny();

						if (accountOptional.isPresent()) {
							processTransaction(accountOptional.get(), transaction);

							if (transactionCounter++ % 10 == 0) {
								accounts.stream().forEach(acc -> {
									System.out.println(acc);
									acc.getTransactions().stream().forEach(t -> System.out.println("\t" + t));
								});
							}
						} else {
							System.err.println(
									"Back account not found by id of: " + transaction.getTargetAccountNumber());
						}
					}

					file.delete();
				}
			}
		}
	}

	private void processTransaction(Account account, Transaction transaction) {
		BigDecimal amount = account.getCurrency().equals(transaction.getCurrency()) ? transaction.getAmount()
				: transaction.getAmount().multiply(transaction.getExchangeRate());
		account.setBalance(account.getBalance().add(amount));
		account.getTransactions().add(transaction);
		transactions.add(transaction);
	}

	private Transaction parseCSV(String file) throws IOException {

		Path path = Paths.get(file);

		Transaction transaction = null;
		try (Stream<String> s = Files.lines(path)) {
			transaction = s.filter(line -> !line.contains("balanceNumber")).map(t -> {
				return getTransactionFromLine(t);
			}).findFirst().get();
		}

		return transaction;
	}

	private Transaction getTransactionFromLine(String line) {

		String[] lineElements = line.split(CSV_SEPARATOR);

		String accountNumber = lineElements[0];
		CurrencyEnum currency = CurrencyEnum.valueOf(lineElements[1]);
		BigDecimal amount = new BigDecimal(lineElements[2]);
		BigDecimal exchangeRate = !lineElements[3].isEmpty() && !lineElements[3].equals("null")
				? new BigDecimal(lineElements[3])
				: null;

		return new Transaction(accountNumber, currency, amount, exchangeRate);
	}

	public AccountManager() {
		this.accounts = new ArrayList<Account>();
		this.transactions = new ArrayList<Transaction>();

	}

	@Override
	public void run() {
		System.out.println("AccountManager is starting...");
		try {
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
