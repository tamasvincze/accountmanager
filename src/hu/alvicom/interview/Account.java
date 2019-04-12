package hu.alvicom.interview;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

	private String accountNumber;

	private CurrencyEnum currency;

	private BigDecimal balance;

	private List<Transaction> transactions;

	public Account(String accountNumber, CurrencyEnum currency, BigDecimal balance) {
		super();
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		if (transactions == null) {
			transactions = new ArrayList<Transaction>();
		}
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", currency=" + currency + ", balance=" + balance + "]";
	}

}
