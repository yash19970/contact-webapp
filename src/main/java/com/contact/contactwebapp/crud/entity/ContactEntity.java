/**
 * 
 */
package com.contact.contactwebapp.crud.entity;

import com.contact.contactwebapp.crud.enums.DBStatusEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yashjain
 *
 */
@Getter
@Setter
@Entity
@Table(name = "contact_user")
public class ContactEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String contName;
	@Column(name = "contact_no")
	private String contNumber;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "status")
	private Byte status = DBStatusEnum.ACTIVE.getValue();
	@Column(name = "created_at")
	private Date createdAt;

}
