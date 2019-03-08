package com.contact.contactwebapp;

import com.contact.contactwebapp.crud.dto.ContactReqDTO;
import com.contact.contactwebapp.crud.service.ContactService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class ContactWebappApplicationTests {

	@Autowired
	private ContactService service;

	// @Test
	public void contextLoads() {
	}

	@Test
	public void testDeletion() {
		Boolean expectedResult = true;
		Boolean isDeleted = service.deleteContact(3);
		Assert.assertEquals(" test case failed  for delete", expectedResult, isDeleted);
	}

	@Test
	public void testCreation() {
		ContactReqDTO expectedDTO = new ContactReqDTO();
		ContactReqDTO testDTO = new ContactReqDTO();
		expectedDTO.setContNumber("12346");
		expectedDTO.setEmail("yash@gmail.com");
		expectedDTO.setName("yash");

		testDTO.setContNumber("12346");
		testDTO.setEmail("yash@gmail.com");
		testDTO.setName("yash");
		testDTO = service.createContact(testDTO);
		Assert.assertEquals(" test case failed  for create", expectedDTO, testDTO);
	}
	
	@Test
	public void testUpdation() {
		ContactReqDTO expectedDTO = new ContactReqDTO();
		ContactReqDTO testDTO = new ContactReqDTO();
		expectedDTO.setContNumber("12346");
		expectedDTO.setEmail("yash@gmail.com");
		expectedDTO.setName("yash");

		testDTO.setContNumber("12346");
		testDTO.setEmail("yash@gmail.com");
		testDTO.setName("yash");
		testDTO = service.updateContact(testDTO);
		Assert.assertEquals(" test case failed  for create", expectedDTO, testDTO);
	}

}
