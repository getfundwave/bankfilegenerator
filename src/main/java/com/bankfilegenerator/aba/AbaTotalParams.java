package com.bankfilegenerator.aba;

import java.math.BigDecimal;

import com.bankfilegenerator.annotation.Equals;
import com.bankfilegenerator.annotation.Length;
import com.bankfilegenerator.annotation.MultiValue;

public class AbaTotalParams {
	private String recordType;
	private String bsbFormatFilter;
	private BigDecimal netTotalAmount;
	private BigDecimal creditTotalAmount;
	private BigDecimal debitTotalAmount;
	private int recordCount;
	
	public AbaTotalParams(String recordType, String bsbFormatFilter, BigDecimal netTotalAmount, BigDecimal creditTotalAmount, BigDecimal debitTotalAmount, int recordCount) {
		this.recordType = recordType;
		this.bsbFormatFilter = bsbFormatFilter;
		this.netTotalAmount = netTotalAmount;
		this.creditTotalAmount = creditTotalAmount;
		this.debitTotalAmount = debitTotalAmount;
		this.recordCount = recordCount;
	}
	
	@Equals(value = "999-999", name = "BSB Format Filter")
	public String getBsbFormatFilter() {
		return bsbFormatFilter;
	}
	
	@MultiValue(values = {"Detail", "Descriptive", "Total"} , name = "Record Type")
	public String getRecordType() {
		return recordType;
	}
	
	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}
	
	public BigDecimal getCreditTotalAmount() {
		return creditTotalAmount;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public BigDecimal getDebitTotalAmount() {
		return debitTotalAmount;
	}
	
}

