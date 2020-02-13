package com.bankfilegenerator.aba;

import com.bankfilegenerator.annotation.Equals;
import com.bankfilegenerator.annotation.Length;
import com.bankfilegenerator.annotation.MaxLength;
import com.bankfilegenerator.annotation.MultiValue;
import com.bankfilegenerator.annotation.NotEmpty;
import com.bankfilegenerator.annotation.NotNull;

public class AbaDescriptiveParams {
	private String recordType;
	private String reelSequenceNumber;
	private String financialInstitution;
	private String businessName; //Name of Use supplying file --> User Preferred Specification
	private String userIdentificationNumber;
	private String description;
	private String date;
	
	public AbaDescriptiveParams(String recordType, String reelSequenceNumber, String financialInstitution, String businessName, 
			String userIdentificationNumber, String description, String date) {
		this.recordType = recordType;
		this.reelSequenceNumber = reelSequenceNumber;
		this.financialInstitution = financialInstitution;
		this.businessName = businessName;
		this.userIdentificationNumber = userIdentificationNumber;
		this.description = description;
		this.date = date;
	}
	
	@MultiValue(values = {"Detail", "Descriptive", "Total"} , name = "Record Type")
	public String getRecordType() {
		return recordType;
	}
	
	@Equals(value = "01", name = "Reel Sequence Number")
	@Length(length = 2, name = "Reel Sequence Number")
	public String getReelSequenceNumber() {
		return reelSequenceNumber;
	}
	
	@NotEmpty(name = "Business Name or Partnership Name")
	public String getBusinessName() {
		return businessName;
	}
	
	@NotNull(name = "Business Name or Partnership Name")
	@MaxLength(length = 6, name = "User Identification Number")
	public String getUserIdentificationNumber() {
		return userIdentificationNumber;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDate() {
		return date;
	}
	
	@Length(length = 3, name = "Financial Institution or Partnership's bank name")
	public String getFinancialInstitution() {
		return financialInstitution;
	}
	
	
}
