package com.bankfilegenerator.aba;

import java.math.BigDecimal;

public class AbaHelper {
	
	//HELPER FUNCTIONS START
	
	//prepends 0 after checking if there is any difference in the length of input string and required length
	private String prependZeroTillRequiredLength(String input, int requiredLength) {
		int inputLength = input.length();
		String resultString = "";
		if(inputLength < requiredLength) {
			int diffLength = (requiredLength-inputLength);
			for(int i=0;i<diffLength;i++) {
				resultString+="0";
			}
			resultString+=input;
		}else {
			resultString = input;
		}
		return resultString;
	}
	
	//prepends " "[space] after checking if there is any difference in the length of input string and required length
	private String prependSpaceTillRequiredLength(String input, int requiredLength) {
		int inputLength = input.length();
		String resultString = "";
		if(inputLength < requiredLength) {
			int diffLength = (requiredLength-inputLength);
			for(int i=0;i<diffLength;i++) {
				resultString+=" ";
			}
			resultString+=input;
		}else {
			resultString = input;
		}
		return resultString;
	}
	
	//appends " "[space] after checking if there is any difference in the length of input string and required length
	private String appendSpaceTillRequiredLength(String input, int requiredLength) {
		int inputLength = input.length();
		String resultString = input;
		if(inputLength < requiredLength) {
			int diffLength = (requiredLength-inputLength);
			for(int i=0;i<diffLength;i++) {
				resultString+=" ";
			}
		}else {
			resultString = input;
		}
		return resultString;
	}
	
	//adds hyphen at the presentIndex after trailing zeros in the beginning 
	private String addHyphenAfterTrailingZerosTillPosition(String input, int presentIndex) {
		return input.substring(0,presentIndex)+"-"+input.substring(presentIndex,input.length());
	}

	//removes all dashes(if any)
	private String removeDashes(String input) {
		return input.replace("-", "");
	}
	
	//removes all dashes(if any)
	private String removeSpaces(String input) {
		return input.replace(" ", "");
	}
	
	//gets first index after trailing zeros --> eg 001234 (returns 2) 12345 (returns -1)
	private int getFirstIndexAfterTrailingZeros(String accountNumber) {
		int firstIndex = -1;
		for(int i=0;i<accountNumber.length();i++) {
			if(accountNumber.charAt(i) != 0) {
				firstIndex = i-1;
				break;
			}
		}
		return firstIndex;
	}
	
	private String getNSpaces(int n) {
		String resultString = "";
		for(int i=0;i<n;i++) {
			resultString+=" ";
		}
		return resultString;
	}
	
	private String getNZeros(int n) {
		String resultString = "";
		for(int i=0;i<n;i++) {
			resultString+="0";
		}
		return resultString;
	}
	
//	// removes all characters except small/capital alphabets, digits, .[Dot]
//	private String removeAllPunctuationFromString(String input) {
//		return input.replaceAll("[^a-zA-Z0-9.]", "");
//	}
	
	//converting amount to Cents in BigDecimal
	private BigDecimal convertToCents(BigDecimal amountNumber) {
		return amountNumber.multiply(new BigDecimal("100"));
	}
	
//	private boolean checkAllBlanks(String name) {
//		return name.trim().isEmpty();
//	}
	
	//HELPER FUNCTIONS END
	
	
	//PARSE FUNCTIONS START
	public String getTransactionCode(String transactionCode) {
		// TODO Auto-generated method stub
		if(transactionCode.length() == 0) return getNSpaces(2);
		return transactionCode;
	}
	
	public String parseBranchNumber(String branchNumber) {
		//REQUIRED FORMAT --> XXX-YYY
		if(branchNumber.length() == 0) return getNSpaces(7);
		branchNumber = removeDashes(branchNumber);
		branchNumber = removeSpaces(branchNumber);
		if(branchNumber.length() > 6) {
			branchNumber = branchNumber.substring(0, 6);
		}else if(branchNumber.length() < 6) {
			branchNumber = prependZeroTillRequiredLength(branchNumber, 7);
		}
		//place hyphen at position 3 [Starting from 0]
		branchNumber = branchNumber.substring(0, 3)+"-"+branchNumber.substring(3, 6);
		return branchNumber;
	}

	public String parseInvestorAccountNumber(String accountNumber) {
		//REQUIRED FORMAT --> 9 Digits, If Trailing zeros at start --> 00-XXXX
		if(accountNumber.length() == 0) return getNSpaces(9);
		accountNumber = removeDashes(accountNumber);
		accountNumber = removeSpaces(accountNumber);
		//check for trailing zeros at the start
		int firstIndex = getFirstIndexAfterTrailingZeros(accountNumber);
		if(firstIndex != -1) {
			//trailing zeros at start are present
			accountNumber = addHyphenAfterTrailingZerosTillPosition(accountNumber, firstIndex+1);
		}
		return prependSpaceTillRequiredLength(accountNumber, 9);
	}
	
	public String getRecordType(String recordType) {
		switch(recordType) {
			case "Detail":
				return "1";
			case "Descriptive":
				return "0";
			case "Total":
				return "7";
			default:
				return " ";
		}
	}
	
	public String getIndicator(String indicator) {
		// N, W, X, Y " "[default] --> WIP
		if(indicator.length() == 0) return getNSpaces(1);
		return indicator;
	}
	
	public String parseAmount(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) == 0) return getNZeros(10);
		String resultString = "";
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			//amount should be > 0
			amount = convertToCents(amount);
			resultString = prependZeroTillRequiredLength(Integer.toString((int) amount.longValue()), 10);
		}
		return resultString;
	}
	
	public String parseBeneficiaryName(String name) {
		String resultName = "";
		if(name.length() == 0) return  getNSpaces(32);
		if(name.length() > 32) name = name.substring(0,32);
		resultName = appendSpaceTillRequiredLength(name,32);
		return resultName;
	}
	
	public String parseLodgementReference(String reference) {
		/* All coded character set valid. Field must be left justified, and contain only the 16 character Employee Benefits Card number; for example 5550033890123456.
		No leading spaces, zeroes, hyphens or other characters can be included.*/
		if(reference.length() == 0) return getNSpaces(18);
		String resultReference = "";
		if(reference.length() > 18) {
			resultReference = reference.substring(0,18);
		}else{
			resultReference = appendSpaceTillRequiredLength(reference,18);
		}
		return resultReference;
	}
	
	
	public String parseBsbNumber(String bsbNumber) {
		//REQUIRED FORMAT --> XXX-YYY
		if(bsbNumber.length() == 0) return getNSpaces(7);
		bsbNumber = removeDashes(bsbNumber);
		bsbNumber = removeSpaces(bsbNumber);
		//Feed input now should always be 6 DIGITS
		if(bsbNumber.length() > 6) {
			bsbNumber = bsbNumber.substring(0, 6);
		}else if(bsbNumber.length() < 6) {
			bsbNumber = prependZeroTillRequiredLength(bsbNumber, 7);
		}
		//place hyphen at position 3 [Starting from 0]
		bsbNumber = bsbNumber.substring(0, 3)+"-"+bsbNumber.substring(3, 6);
		return bsbNumber;
	}
	
	public String parseAccountNumber(String accountNumber) {
		// REQUIRED FORMAT -> Right justified, blank filled
		if(accountNumber.length() == 0) return getNSpaces(9);
		accountNumber = removeDashes(accountNumber);
		accountNumber = removeSpaces(accountNumber);
		accountNumber = prependSpaceTillRequiredLength(accountNumber, 9);
		return accountNumber;
	}
	
	public String parseNameOfRemitter(String name) {		
		String resultName = "";
		if(name.length() > 16) return name.substring(0,16);
		resultName = appendSpaceTillRequiredLength(name, 16);
		return resultName;
	}
	
	public String parseAmountOfWithholdingTax(String taxAmount) {
		//REQUIRED FORMAT --> Numeric only valid. Show in cents without punctuation. Right justified, zero filled. Unsigned.
		if(taxAmount.length() == 0) return getNZeros(8);
		return taxAmount;
	}
	
	//DESCRIPTIVE ROW
	public String parseBusinessName(String businessName) {
		//REQUIRED FORMAT -> Left justified, blank filled. All coded character set valid. Must not be all blanks.
		String resultBusinessName = "";
		if(businessName.length() > 26) return businessName.substring(0,26);
		resultBusinessName = appendSpaceTillRequiredLength(businessName, 26);
		return resultBusinessName;
	}
	
	public String parseUserIdentificationNumber(String userIdentificationNumber) {
		//REQUIRED FORMAT -> Must be numeric, right justified, zero filled.
		if(userIdentificationNumber.length() == 0) return getNZeros(6);
		if(userIdentificationNumber.length() < 6) return prependZeroTillRequiredLength(userIdentificationNumber, 6);
		return userIdentificationNumber;
	}
	
	public String parseDescription(String description) {
		String resultDescription = "";
		if(description.length() == 0) {
			resultDescription = appendSpaceTillRequiredLength("DESC", 12);
		}
		if(description.length() > 12) return description.substring(0,12);
		resultDescription = appendSpaceTillRequiredLength(description, 12);
		return resultDescription;
	}
	
	public String parseDate(String date) {
		if(date.length() != 6) return getNZeros(6);
		return date;
	}
	
	public String parseTotalRecordCount(int count) {
		String resultTotalCount = prependZeroTillRequiredLength(Integer.toString(count), 6);
		return resultTotalCount;
	}
	
	//PARSE FUNCTIONS END
	
	//FINAL PROCESSING FUNCTIONS
	public String getDetailRecordString(AbaDetailParams abaDetailParams) {
		return getRecordType(abaDetailParams.getRecordType())
				+parseBranchNumber(abaDetailParams.getInvestorBsbCode())
				+parseInvestorAccountNumber(abaDetailParams.getInvestorAccountNumber())
				+getIndicator(abaDetailParams.getIndicator())
				+getTransactionCode(abaDetailParams.getTransactionCode())
				+parseAmount(abaDetailParams.getAmount())
				+parseBeneficiaryName(abaDetailParams.getBeneficiaryName())
				+parseLodgementReference(abaDetailParams.getLodgementReference())
				+parseBsbNumber(abaDetailParams.getBsbNumber())
				+parseAccountNumber(abaDetailParams.getAccountNumber())
				+parseNameOfRemitter(abaDetailParams.getRemitterName())
				+parseAmountOfWithholdingTax(abaDetailParams.getTaxAmount());
	}
	
	public String getDescriptiveRecordString(AbaDescriptiveParams abaDescriptiveParams) {
		return getRecordType(abaDescriptiveParams.getRecordType())
				+getNSpaces(17)
				+abaDescriptiveParams.getReelSequenceNumber()
				+abaDescriptiveParams.getFinancialInstitution()
				+getNSpaces(7)
				+parseBusinessName(abaDescriptiveParams.getBusinessName())
				+parseUserIdentificationNumber(abaDescriptiveParams.getUserIdentificationNumber())
				+parseDescription(abaDescriptiveParams.getDescription())
				+parseDate(abaDescriptiveParams.getDate())
				+getNSpaces(40);
	}
	
	public String getTotalRecordString(AbaTotalParams abaTotalParams) {
		return 	getRecordType(abaTotalParams.getRecordType())
				+abaTotalParams.getBsbFormatFilter()
				+getNSpaces(12)
				+parseAmount(abaTotalParams.getNetTotalAmount())
				+parseAmount(abaTotalParams.getCreditTotalAmount())
				+parseAmount(abaTotalParams.getDebitTotalAmount())
				+getNSpaces(24)
				+parseTotalRecordCount(abaTotalParams.getRecordCount())
				+getNSpaces(40);
	}
	
	public static String getAbaString(String detailString, String descriptiveString, String totalString) {
		return descriptiveString + "\n" + detailString + totalString;
	}
	
}


