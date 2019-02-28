package com.invoiceApproval.exception;

public class InvoiceApprovalException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public InvoiceApprovalException(String message) {
		super(message);
		this.errorMessage = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
