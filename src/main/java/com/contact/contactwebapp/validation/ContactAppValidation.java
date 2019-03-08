/**
 * 
 */
package com.contact.contactwebapp.validation;

import com.contact.contactwebapp.WebAppConstants;
import com.contact.contactwebapp.common.exception.ValidationException;
import com.contact.contactwebapp.crud.entity.ContactEntity;
import com.contact.contactwebapp.util.general.ErrorDetailUtil;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author yashjain
 *
 */
@Component
public class ContactAppValidation {

	@Resource(name = "propertiesReader")
	private Properties propertiesReader;

	public void validateForDuplicate(List<ContactEntity> existingContacts) {
		if (existingContacts != null && !existingContacts.isEmpty()) {
			throw new ValidationException(
					ErrorDetailUtil.generateErrorDetail(propertiesReader.getProperty("contact.duplicate.found.code"),
							propertiesReader.getProperty("contact.duplicate.found.message")));
		}

	}

	public void validateBothParams(String name, String email) {
		if ((name == null || (name != null && name.isEmpty()))
				&& (email == null || (email != null && email.isEmpty()))) {
			throw new ValidationException(ErrorDetailUtil.generateErrorDetail(
					propertiesReader.getProperty("contact.both.param.not.found.code"),
					propertiesReader.getProperty("contact.both.param.not.found.message")));
		}

	}

	public void authenticateApiKey(String apiKey) {
		if ((apiKey == null || apiKey.isEmpty()) || !apiKey.equals(WebAppConstants.APP_KEY)) {
			throw new ValidationException(
					ErrorDetailUtil.generateErrorDetail(propertiesReader.getProperty("contact.auth.failed.code"),
							propertiesReader.getProperty("contact.auth.failed.message")));
		}

	}

}
