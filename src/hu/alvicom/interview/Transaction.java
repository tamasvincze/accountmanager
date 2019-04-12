package hu.alvicom.interview;

import java.math.BigDecimal;

public class Transaction {

	private String targetAccountNumber;
	private CurrencyEnum currency;
	private BigDecimal amount;
	private BigDecimal exchangeRate;

	public Transaction(String targetAccountNumber, CurrencyEnum currency, BigDecimal amount, BigDecimal exchangeRate) {
		super();
		this.targetAccountNumber = targetAccountNumber;
		this.currency = currency;
		this.amount = amount;
		this.exchangeRate = exchangeRate;
	}

	public String getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(String targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public String toString() {
		return "Transaction [currency=" + currency + ", amount=" + amount + ", exchangeRate=" + exchangeRate + "]";
	}

}