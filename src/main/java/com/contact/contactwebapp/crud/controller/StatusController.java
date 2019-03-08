package com.contact.contactwebapp.crud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class StatusController {

	@Value("${app.version}")
	  private String appVersion;

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<String> status() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/appVersion", method = RequestMethod.GET)
	  public ResponseEntity<String> appVersion() {
	    return new ResponseEntity<>(appVersion, HttpStatus.OK);
	  }
}
