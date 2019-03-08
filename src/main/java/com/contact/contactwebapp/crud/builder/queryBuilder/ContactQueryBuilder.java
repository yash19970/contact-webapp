/**
 * 
 */
package com.contact.contactwebapp.crud.builder.queryBuilder;

import com.contact.contactwebapp.common.dto.CustomQueryHolder;
import com.contact.contactwebapp.crud.entity.ContactEntity;
import com.contact.contactwebapp.crud.enums.DBStatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yashjain
 *
 */
public class ContactQueryBuilder {

	public static CustomQueryHolder generateQueryToFetchContact(Integer id) {
		CustomQueryHolder queryHolder = new CustomQueryHolder();
		queryHolder.setInParamMap(populateParamToFetchContact(id));
		queryHolder.setQueryString(generateQueryStringToFetchContact());
		return queryHolder;
	}

	private static Map<String, Object> populateParamToFetchContact(Integer id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("status", DBStatusEnum.ACTIVE.getValue());
		return map;
	}

	private static String generateQueryStringToFetchContact() {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(ContactEntity.class.getName())
				.append(" contact WHERE contact.id =:id and contact.status =:status ");
		return sb.toString();
	}

	public static CustomQueryHolder generateQueryToFetchPaginatedContact() {
		CustomQueryHolder queryHolder = new CustomQueryHolder();
		queryHolder.setInParamMap(populateParamToFetchPaginatedContact());
		queryHolder.setQueryString(generateQueryStringToFetchPaginatedContact());
		return queryHolder;
	}

	private static String generateQueryStringToFetchPaginatedContact() {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(ContactEntity.class.getName()).append(" contact WHERE contact.status =:status ");
		return sb.toString();
	}

	private static Map<String, Object> populateParamToFetchPaginatedContact() {
		Map<String, Object> map = new HashMap<>();
		map.put("status", DBStatusEnum.ACTIVE.getValue());
		return map;
	}

	public static CustomQueryHolder generateQueryToFetchTotalActiveCount() {
		CustomQueryHolder queryHolder = new CustomQueryHolder();
		queryHolder.setInParamMap(populateParamToFetchPaginatedContact());
		queryHolder.setQueryString(generateQueryStringToFetchTotalActiveCount());
		return queryHolder;
	}

	private static String generateQueryStringToFetchTotalActiveCount() {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT count(contact.id) FROM contact_user contact WHERE contact.status =:status ");
		return sb.toString();
	}

	public static CustomQueryHolder generateQueryToFetchByEmailOrNumber(String email, String contNumber, Integer id) {
		CustomQueryHolder queryHolder = new CustomQueryHolder();
		queryHolder.setInParamMap(populateParamToFetchByEmailOrNumber(email, contNumber, id));
		queryHolder.setQueryString(generateQueryStringToFetchByEmailOrNumber(id));
		return queryHolder;
	}

	private static String generateQueryStringToFetchByEmailOrNumber(Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(ContactEntity.class.getName()).append(
				" contact where ( contact.emailId =:email or contact.contNumber =:no ) and contact.status = :status ");
		if (id != null) {
			sb.append("and contact.id !=:id ");
		}
		return sb.toString();
	}

	private static Map<String, Object> populateParamToFetchByEmailOrNumber(String email, String contNumber,
			Integer id) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", DBStatusEnum.ACTIVE.getValue());
		map.put("no", contNumber);
		map.put("email", email);
		if (id != null) {
			map.put("id", id);
		}
		return map;
	}

	public static CustomQueryHolder generateQueryTosearchContact(String name, String email) {
		CustomQueryHolder queryHolder = new CustomQueryHolder();
		queryHolder.setInParamMap(populateParamTosearchContact(email, name));
		queryHolder.setQueryString(generateQueryStringTosearchContact(email, name));
		return queryHolder;
	}

	private static Map<String, Object> populateParamTosearchContact(String email, String name) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", DBStatusEnum.ACTIVE.getValue());
		if (email != null && !email.isEmpty()) {
			map.put("email", email+"%");
		}
		if (name != null && !name.isEmpty()) {
			map.put("name", name+"%");
		}
		return map;
	}

	private static String generateQueryStringTosearchContact(String email, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM ").append(ContactEntity.class.getName()).append(" contact WHERE contact.status =:status ");
		if (name != null && !name.isEmpty()) {
			sb.append(" and contact.contName like :name ");
		}
		if (email != null && !email.isEmpty()) {
			sb.append("  contact.emailId like :email ");
		}
		return sb.toString();
	}

}
