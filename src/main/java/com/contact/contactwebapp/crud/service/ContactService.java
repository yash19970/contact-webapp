/**
 * 
 */
package com.contact.contactwebapp.crud.service;

import com.contact.contactwebapp.crud.dto.ContactReqDTO;
import com.contact.contactwebapp.crud.dto.PaginatedResponseDTO;

import java.util.List;

/**
 * @author yashjain
 *
 */

public interface ContactService {

	/**
	 * @param maxResulIntegert
	 * @param pageIndex
	 * @return
	 */
	PaginatedResponseDTO fetchPaginatedContacts(Integer pageIndex, Integer maxResult);

	/**
	 * @param contactReqDTO
	 * @return
	 */
	ContactReqDTO createContact(ContactReqDTO contactReqDTO);

	/**
	 * @param contactReqDTO
	 * @return
	 */
	ContactReqDTO updateContact(ContactReqDTO contactReqDTO);

	/**
	 * @param id
	 * @return
	 */
	Boolean deleteContact(Integer id);

	List<ContactReqDTO> fetchContactById(Integer id);

	List<ContactReqDTO> searchContact(String name, String email);

}
