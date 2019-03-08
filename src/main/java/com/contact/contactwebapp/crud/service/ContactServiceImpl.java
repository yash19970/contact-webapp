/**
 * 
 */
package com.contact.contactwebapp.crud.service;

import com.contact.contactwebapp.common.dto.CustomQueryHolder;
import com.contact.contactwebapp.common.repo.JpaRepo;
import com.contact.contactwebapp.crud.builder.ContactBuilder;
import com.contact.contactwebapp.crud.builder.queryBuilder.ContactQueryBuilder;
import com.contact.contactwebapp.crud.dto.ContactReqDTO;
import com.contact.contactwebapp.crud.dto.PaginatedResponseDTO;
import com.contact.contactwebapp.crud.entity.ContactEntity;
import com.contact.contactwebapp.crud.enums.DBStatusEnum;
import com.contact.contactwebapp.util.objectMapper.DataMapper;
import com.contact.contactwebapp.validation.ContactAppValidation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashjain
 *
 */
@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private JpaRepo<ContactEntity> contRepo;

	@Autowired
	private ContactAppValidation validator;

	@Override
	public PaginatedResponseDTO fetchPaginatedContacts(Integer pageIndex, Integer maxResult) {
		PaginatedResponseDTO paginatedDTO = new PaginatedResponseDTO();
		List<ContactReqDTO> responseDTOs = new ArrayList<>();
		List<ContactEntity> entities = contRepo.findPaginationByQuery(
				ContactQueryBuilder.generateQueryToFetchPaginatedContact(), pageIndex, maxResult);
		List<Object[]> count = contRepo.runNativeQuery(ContactQueryBuilder.generateQueryToFetchTotalActiveCount());
		if (entities != null && !entities.isEmpty()) {
			responseDTOs = ContactBuilder.buildDTOsFromEntity(entities);
		}
		paginatedDTO.setContacts(responseDTOs);
		paginatedDTO.setTotalResult(count == null ? 0 : DataMapper.convertToInteger(count.get(0)));

		return paginatedDTO;
	}

	@Override
	public List<ContactReqDTO> fetchContactById(Integer id) {
		List<ContactEntity> entities = contRepo.findByQuery(ContactQueryBuilder.generateQueryToFetchContact(id));
		List<ContactReqDTO> responseDTOs = ContactBuilder.buildDTOsFromEntity(entities);
		return responseDTOs;
	}

	@Override
	public ContactReqDTO createContact(ContactReqDTO contactReqDTO) {
		List<ContactEntity> existingContacts = fetchContactByEmailOrNumber(contactReqDTO);
		validator.validateInputData(contactReqDTO);
		validator.validateForDuplicate(existingContacts);
		ContactEntity entity = contRepo.update(ContactBuilder.buildEntityFromDTO(contactReqDTO));
		ContactReqDTO responseDTO = ContactBuilder.buildDTOFromEntity(entity);
		return responseDTO;
	}

	private List<ContactEntity> fetchContactByEmailOrNumber(ContactReqDTO contactReqDTO) {
		List<ContactEntity> existingContacts = contRepo
				.findByQuery(ContactQueryBuilder.generateQueryToFetchByEmailOrNumber(contactReqDTO.getEmail(),
						contactReqDTO.getContNumber(), contactReqDTO.getId()));
		return existingContacts;
	}

	@Override
	public ContactReqDTO updateContact(ContactReqDTO contactReqDTO) {
		List<ContactEntity> existingContacts = fetchContactByEmailOrNumber(contactReqDTO);
		validator.validateInputData(contactReqDTO);
		validator.validateForDuplicate(existingContacts);
		ContactEntity entity = contRepo.update(ContactBuilder.buildEntityFromDTO(contactReqDTO));
		ContactReqDTO responseDTO = ContactBuilder.buildDTOFromEntity(entity);
		return responseDTO;
	}

	@Override
	public Boolean deleteContact(Integer id) {
		contRepo.deleteById(id, ContactEntity.class);
		return Boolean.TRUE;
	}

	@Override
	public List<ContactReqDTO> searchContact(String name, String email) {
		validator.validateBothParams(name, email);
		CustomQueryHolder query = ContactQueryBuilder.generateQueryTosearchContact(name, email);
		List<ContactEntity> entities = contRepo.findByQuery(query);
		List<ContactReqDTO> responseDTOs = ContactBuilder.buildDTOsFromEntity(entities);
		return responseDTOs;
	}

}
