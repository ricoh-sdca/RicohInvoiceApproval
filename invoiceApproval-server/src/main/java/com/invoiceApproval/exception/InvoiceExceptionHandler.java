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

@ControllerAdvice
public class InvoiceExceptionHandler {

	private static final Logger logger = LogManager.getLogger(InvoiceExceptionHandler.class);

	/**
	 * This method handle method parameter validation exceptions.
	 * 
	 * @param ex
	 * @return
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
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ErrorDetails userNotFoundException(UserNotFoundException ex)
	{
		logger.info("Enter InvoiceExceptionHandler handleMethodArgumentNotValid()");
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date());
		errorDetails.setMessage("Validation failed");
		errorDetails.setDetails(ex.getMessage());
		return errorDetails;
	}
}
