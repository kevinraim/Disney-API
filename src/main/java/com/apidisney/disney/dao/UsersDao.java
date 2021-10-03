package com.apidisney.disney.dao;


import com.apidisney.disney.models.Users;

public interface UsersDao {
	
	Users findByUsername(String username);
	
	Users create(Users user);
	
}
