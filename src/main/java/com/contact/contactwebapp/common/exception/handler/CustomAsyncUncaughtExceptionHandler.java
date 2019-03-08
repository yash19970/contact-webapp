package com.contact.contactwebapp.common.exception.handler;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomAsyncUncaughtExceptionHandler.class);

	@Override
	public void handleUncaughtException(final Throwable throwable, final Method method, final Object... params) {
		logger.error("Exception message - " + throwable.getMessage());
		logger.error("Exception Method name - " + method.getName());
		for (final Object param : params) {
			logger.error("Parameter value - " + param);
		}

	}

}
