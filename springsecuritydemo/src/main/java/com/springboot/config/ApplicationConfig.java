package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springboot.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	private final UserService userService;
	@Bean
	public UserDetailsService userDetailsService() {
		return username-> userService.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("Username not found"));
		
	}
}
