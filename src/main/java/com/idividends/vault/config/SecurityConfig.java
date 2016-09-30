package com.idividends.vault.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final static String AUTH_USER = "AUTH_USER";
	private final static String AUTH_PASSWORD = "AUTH_PASSWORD";
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().
			withUser(System.getenv(AUTH_USER)).password(System.getenv(AUTH_PASSWORD))
				.roles("USER")
			;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated();
		http.httpBasic();
		http.csrf().disable();
	}
}
