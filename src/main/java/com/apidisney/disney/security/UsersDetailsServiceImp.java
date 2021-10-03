package com.apidisney.disney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apidisney.disney.daoimp.UsersDaoImp;
import com.apidisney.disney.models.Users;

@Service
public class UsersDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	@Lazy
	private UsersDaoImp usersImp;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = usersImp.findByUsername(username);
		UserBuilder builder = null;
		
		if(user != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(user.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else {
			throw new UsernameNotFoundException("Not Found User");
		}
		return builder.build();
	}

}
