package com.invoiceApproval.entity;

public class ResponseVO {

	public String code;
	public String message;
	public String errorMessage;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ResponseVO(String code, String message, String errorMessage) {
		super();
		this.code = code;
		this.message = message;
		this.errorMessage = errorMessage;
	}
	public ResponseVO() {
		super();
	}
	
}
