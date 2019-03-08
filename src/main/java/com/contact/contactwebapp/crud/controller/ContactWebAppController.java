/**
 * 
 */
package com.contact.contactwebapp.crud.controller;

import com.contact.contactwebapp.crud.dto.ContactReqDTO;
import com.contact.contactwebapp.crud.dto.PaginatedResponseDTO;
import com.contact.contactwebapp.crud.service.ContactService;
import com.contact.contactwebapp.validation.ContactAppValidation;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yashjain
 *
 */
@RestController
@RequestMapping("/contactwebapp")
public class ContactWebAppController {

	@Autowired
	private ContactService contactService;
	@Autowired
	private ContactAppValidation validate;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<PaginatedResponseDTO> fetchPaginatedContacts(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "apiKey", required = true) String apiKey,
			@RequestParam(value = "maxResult", required = false, defaultValue = "10") Integer maxResult) {
		validate.authenticateApiKey(apiKey);
		PaginatedResponseDTO responseDTOs = contactService.fetchPaginatedContacts(pageIndex, maxResult);
		if (responseDTOs != null) {
			return new ResponseEntity<PaginatedResponseDTO>(responseDTOs, HttpStatus.OK);
		}
		return new ResponseEntity<PaginatedResponseDTO>(responseDTOs, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<ContactReqDTO>> searchContact(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "apiKey", required = true) String apiKey,
			@RequestParam(value = "email", required = false) String email) {
		validate.authenticateApiKey(apiKey);
		List<ContactReqDTO> responseDTOs = contactService.searchContact(name, email);
		if (responseDTOs != null && !responseDTOs.isEmpty()) {
			return new ResponseEntity<List<ContactReqDTO>>(responseDTOs, HttpStatus.OK);
		}
		return new ResponseEntity<List<ContactReqDTO>>(responseDTOs, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<ContactReqDTO> createContact(final @NotNull @RequestBody ContactReqDTO contactReqDTO,
			@RequestParam(value = "apiKey", required = true) String apiKey) {
		validate.authenticateApiKey(apiKey);
		ContactReqDTO responseDTO = contactService.createContact(contactReqDTO);
		return new ResponseEntity<ContactReqDTO>(responseDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<ContactReqDTO> updateContact(final @NotNull @RequestBody ContactReqDTO contactReqDTO,
			@RequestParam(value = "apiKey", required = true) String apiKey) {
		validate.authenticateApiKey(apiKey);
		ContactReqDTO respDTO = contactService.updateContact(contactReqDTO);
		return new ResponseEntity<ContactReqDTO>(respDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteContact(final @NotNull @RequestParam Integer id,
			@RequestParam(value = "apiKey", required = true) String apiKey) {
		validate.authenticateApiKey(apiKey);
		Boolean sucess = false;
		if (id == null) {
			return new ResponseEntity<Boolean>(sucess, HttpStatus.BAD_REQUEST);
		}
		sucess = contactService.deleteContact(id);
		return new ResponseEntity<Boolean>(sucess, HttpStatus.OK);
	}

}
