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

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.entity.ResponseVO;

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
	public ResponseVO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		logger.info("Enter InvoiceExceptionHandler handleMethodArgumentNotValid()");
		ResponseVO errorDetails = new ResponseVO();
		errorDetails.setCode(Constants.FAILED);
		errorDetails.setMessage(null);
		errorDetails.setErrorMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
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
	public ResponseVO invoiceApprovalException(InvoiceApprovalException ex)
	{
		logger.info("Enter InvoiceExceptionHandler handleMethodArgumentNotValid()");
		ResponseVO errorDetails = new ResponseVO();
		errorDetails.setCode(Constants.FAILED);
		errorDetails.setMessage("Exception in application");
		errorDetails.setErrorMessage(ex.getErrorMessage());
		return errorDetails;
	}
}
