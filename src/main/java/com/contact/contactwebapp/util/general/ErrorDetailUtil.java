package com.contact.contactwebapp.util.general;
import com.contact.contactwebapp.common.dto.ErrorDetail;
import com.contact.contactwebapp.common.exception.ValidationException;

public class ErrorDetailUtil {
	public static ErrorDetail generateErrorDetail(String errorCode, String errorDesc) {
		return new ErrorDetail(errorCode == null ? "" : errorCode, errorDesc == null ? "" : errorDesc);
	}

	public static void throwValidationException(String errorDesc,String errorCode) {
		ErrorDetail error = generateErrorDetail(errorCode, errorDesc);
		throw new ValidationException(error);

	}
}
