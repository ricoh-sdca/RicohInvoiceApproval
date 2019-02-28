package com.invoiceApproval.exception;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author atul_jadhav
 *
 */
@ControllerAdvice
public class InvoiceExceptionHandler {

	private static final Logger logger = LogManager.getLogger(InvoiceExceptionHandler.class);

	/**
	 * This method handle method parameter validation exceptions.
	 * 
	 * @param ex
	 * @return ErrorDetails
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDetails handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		logger.info("Enter InvoiceExceptionHandler handleMethodArgumentNotValid()");
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date());
		errorDetails.setMessage("Validation failed");
		errorDetails.setDetails(ex.getBindingResult().getFieldError().getDefaultMessage());
		return errorDetails;
	}
	
	/**
	 * 
	 * This method handle exception if user is not found.
	 * @param ex
	 * @return ErrorDetails
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(InvoiceApprovalException.class)
	public ErrorDetails invoiceApprovalException(InvoiceApprovalException ex)
	{
		logger.info("Enter InvoiceExceptionHandler handleMethodArgumentNotValid()");
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date());
		errorDetails.setMessage("Exception in application");
		errorDetails.setDetails(ex.getErrorMessage());
		return errorDetails;
	}
}
