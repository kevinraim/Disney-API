package com.apidisney.disney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsersDetailsServiceImp userDetails;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		String password = passwordEncoder().encode("admin");
		auth.inMemoryAuthentication().withUser("admin").password(password).roles("USER");
		
		auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
	}
	
    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
    	security.csrf().disable()
    	.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().hasRole("USER")
    	.and()
    	.httpBasic();
    }
    
}