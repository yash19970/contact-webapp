package com.contact.contactwebapp.common.exception.handler;


import com.contact.contactwebapp.common.dto.ErrorDetail;
import com.contact.contactwebapp.common.exception.ValidationException;
import com.contact.contactwebapp.util.general.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler {
	private static Logger logger = LogManager.getLogger(ApplicationExceptionHandler.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ErrorDetail handleBaseException(Exception e) {
		logger.fatal(StringUtil.getStackTraceInStringFmt(e));
		logger.fatal("exception orrcured due to : " + e.getCause());
		return new ErrorDetail("IS_00-00", "Error while processing the request");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = ValidationException.class)
	public ErrorDetail handleValidationException(ValidationException e) {
		logger.fatal(":: got validation:: " + StringUtil.getStackTraceInStringFmt(e));
		logger.fatal(":: Error Detail :: " + e.getErrorDetail());
		return e.getErrorDetail();
	}

}
