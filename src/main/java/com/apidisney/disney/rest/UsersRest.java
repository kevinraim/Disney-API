package com.apidisney.disney.rest;

import java.io.IOException;

import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apidisney.disney.daoimp.UsersDaoImp;
import com.apidisney.disney.mailsender.MailService;
import com.apidisney.disney.models.Users;

@RestController
public class UsersRest {
	
	@Autowired
	private UsersDaoImp userImp;
	
	@Autowired
	private MailService mailService;

	
	@PostMapping(value="/auth/register")
	public  ResponseEntity<?> register(@RequestBody Users user) throws IOException {
		
		if(user.getEmail() == null || user.getEmail().isEmpty() ||
		user.getUsername() == null || user.getUsername().isEmpty() ||
		user.getPassword() == null || user.getPassword().isEmpty()) {
			return ResponseEntity.badRequest().body("Fields cannot be null or empty");
		}
		
		if(!EmailValidator.getInstance().isValid(user.getEmail())){
			return ResponseEntity.badRequest().body("Invalid email");
		}
		
		mailService.sendTextEmail(user.getEmail());
		userImp.create(user);
		return ResponseEntity.ok("Successful register");
	}
}
