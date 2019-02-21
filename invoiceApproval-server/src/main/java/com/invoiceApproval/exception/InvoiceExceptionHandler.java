package com.invoiceApproval.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InvoiceExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
		ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<Object>(ex.getBindingResult().getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
	}
}
