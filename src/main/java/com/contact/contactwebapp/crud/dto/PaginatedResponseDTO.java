/**
 * 
 */
package com.contact.contactwebapp.crud.dto;

import java.util.List;

import lombok.Data;

/**
 * @author yashjain
 *
 */
@Data
public class PaginatedResponseDTO {
	private Integer totalResult;
	private List<ContactReqDTO> contacts;

}
