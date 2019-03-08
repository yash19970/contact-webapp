package com.contact.contactwebapp.util.general;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author prashantkumar
 *
 */
public class EnumUtils {

	public static <T extends Enum<T>> void checkEnumFieldsAreUnique(final Class<T> enumType, final String fieldName) {
		final Set<Object> tempSet = new HashSet<>();
		try {
			final Field valField = enumType.getDeclaredField(fieldName);
			valField.setAccessible(true);
			for (final Enum e : enumType.getEnumConstants()) {
				if (!tempSet.add(valField.get(e))) {
					System.out.println("The id the givent enum :: " + e.toString() + " :: have to be unique");
					System.exit(0);
				}
			}

		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
