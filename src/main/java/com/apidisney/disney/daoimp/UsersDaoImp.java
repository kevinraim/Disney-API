package com.apidisney.disney.daoimp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apidisney.disney.dao.UsersDao;
import com.apidisney.disney.models.Users;


@Repository
@Transactional
@Service
public class UsersDaoImp implements UsersDao{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Users create(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return entityManager.merge(user);
	}

	@Override
	public Users findByUsername(String username) {
		return entityManager.createQuery("FROM Users WHERE username LIKE '"+username+"'", Users.class).getSingleResult();
	}

}
