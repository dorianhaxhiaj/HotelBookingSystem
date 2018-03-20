package com.databridgeproject.configuration;

import java.net.URLEncoder;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
@Service
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig 
		extends WebSecurityConfigurerAdapter {

	private final static String LOGIN_REDIRECT_MESSAGE = "You need to login first!";
	private final static String LOGIN_ERROR_MESSAGE = "Username and password incorrect!";
	private final static String LOGOUT_SUCCESSFULL_MESSAGE = "You have logged out successfully!";
	
	@Autowired
	DataSource dataSource;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		       .anyRequest().permitAll()
			.and().formLogin()
				.loginPage("/login?redirect=" + URLEncoder.encode(LOGIN_REDIRECT_MESSAGE, "UTF-8")).permitAll()
				.loginProcessingUrl("/login")
	            .failureUrl("/login?error=" + URLEncoder.encode(LOGIN_ERROR_MESSAGE, "UTF-8"))
	            .usernameParameter("username").passwordParameter("password")
	            .defaultSuccessUrl("/")
            .and().logout()
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/login?logout=" + URLEncoder.encode(LOGOUT_SUCCESSFULL_MESSAGE, "UTF-8"))
            .and()
            .httpBasic()
            .and()
            .csrf();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
					throws Exception {
		
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}

