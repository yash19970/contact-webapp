/**
 * 
 */
package com.contact.contactwebapp.crud.builder;

import com.contact.contactwebapp.crud.dto.ContactReqDTO;
import com.contact.contactwebapp.crud.entity.ContactEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yashjain
 *
 */
public class ContactBuilder {

	/**
	 * @param contactReqDTO
	 * @return
	 */
	public static ContactEntity buildEntityFromDTO(ContactReqDTO contactReqDTO) {
		ContactEntity entity = new ContactEntity();
		entity.setContName(contactReqDTO.getName());
		entity.setContNumber(contactReqDTO.getContNumber());
		if (contactReqDTO.getId() == null) {
			entity.setCreatedAt(new Date());
		} else {
			entity.setCreatedAt(contactReqDTO.getCreatedAt());
		}
		entity.setEmailId(contactReqDTO.getEmail());
		entity.setId(contactReqDTO.getId());
		return entity;
	}

	/**
	 * @param entities
	 * @return
	 */
	public static List<ContactReqDTO> buildDTOsFromEntity(List<ContactEntity> entities) {
		List<ContactReqDTO> dtos = new ArrayList<>();
		for (ContactEntity entity : entities) {
			ContactReqDTO dto = buildDTOFromEntity(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public static ContactReqDTO buildDTOFromEntity(ContactEntity entity) {
		ContactReqDTO dto = new ContactReqDTO();
		dto.setContNumber(entity.getContNumber());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setEmail(entity.getEmailId());
		dto.setId(entity.getId());
		dto.setName(entity.getContName());
		return dto;
	}

}
