/**
 * 
 */
package com.contact.contactwebapp.crud.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author yashjain
 *
 */
@Data
public class ContactReqDTO {
	private Integer id;
	private String name;
	private String email;
	private String contNumber;
	private Date createdAt;

}
