package com.bankfilegenerator.aba;

import java.math.BigDecimal;
import java.util.InputMismatchException;

import com.bankfilegenerator.annotation.Length;
import com.bankfilegenerator.annotation.MaxLength;
import com.bankfilegenerator.annotation.MultiValue;
import com.bankfilegenerator.annotation.NotEmpty;
import com.bankfilegenerator.annotation.NonNegative;
import com.bankfilegenerator.exception.InputLengthException;

public class AbaDetailParams {
	private String recordType;
	private String investorBsbCode;
	private String investorAccountNumber;
	private String indicator;
	private String transactionCode;
	private BigDecimal amount;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private String beneficiaryName;
	private String lodgementReference;
	private String bsbNumber;
	private String accountNumber;
	private String remitterName;
	private String taxAmount;
	
	public AbaDetailParams(String recordType, String investorBsbCode, String investorAccountNumber, String indicator, String transactionCode,
			BigDecimal amount, String beneficiaryName, String lodgementReference, String partnershipBsbNumber, String partnershipAccountNumber, String partnershipBeneficiaryName, String taxAmount,
			BigDecimal debitAmount, BigDecimal creditAmount) throws InputLengthException {
		this.recordType = recordType;
		this.investorBsbCode = investorBsbCode;
		this.investorAccountNumber = removeSpaces(removeDashes(investorAccountNumber));
		this.indicator = indicator;
		this.transactionCode = transactionCode;
		this.amount = amount;
		this.beneficiaryName = beneficiaryName;
		this.lodgementReference = lodgementReference;
		this.bsbNumber = partnershipBsbNumber;
		this.accountNumber = removeSpaces(removeDashes(partnershipAccountNumber));
		this.remitterName = partnershipBeneficiaryName;
		this.taxAmount = taxAmount;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
	}
	
	@MultiValue(values = {"Detail", "Descriptive", "Total"} , name = "Record Type")
	public String getRecordType() {
		return recordType;
	}
	
	@Length(length = 6, name = "Investor BSB Code")
	public String getInvestorBsbCode() {
		return investorBsbCode;
	}
	
	@MaxLength(length = 9, name = "Investor Account Number")
	public String getInvestorAccountNumber() {
		return investorAccountNumber;
	}
	
	@MultiValue(values = {"N", "W", " ", "X", "Y"} , name = "Indicator")
	public String getIndicator() {
		return indicator;
	}
	
	@MultiValue(values = {"13", "50", "51", "52", "53", "54", "55", "56", "57"} , name = "Transaction Code")
	public String getTransactionCode() {
		return transactionCode;
	}
	
	@NonNegative(name = "Notice Amount")
	public BigDecimal getAmount() {
		return amount;
	}
	
	@MaxLength(length = 32, name = "Beneficiary Name")
	@NotEmpty(name = "Beneficiary Name")
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	
	public String getLodgementReference() {
		return lodgementReference;
	}
	
	public String getBsbNumber() {
		return bsbNumber;
	}
	
	@Length(length = 9, name = "Partnership Account Number")
	public String getAccountNumber() {
		return accountNumber;
	}
	
	@NotEmpty(name = "Remitter Name or Partnership Name")
	public String getRemitterName() {
		return remitterName;
	}
	
	@MaxLength(length = 8, name = "Tax Amount")
	public String getTaxAmount() {
		return taxAmount;
	}
	
	private String removeDashes(String input) {
		return input.replace("-", "");
	}
	private String removeSpaces(String input) {
		return input.replace(" ", "");
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}
	
}

