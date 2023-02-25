package com.secutest3.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	List<User> users=new ArrayList<>();
	

	public MyUserDetailsService() {
		super();
		users.add((User)User//.withDefaultPasswordEncoder()
	            .builder()
				.username("user")
	            .password("password")
	            .roles("USER")
	            .build());

		users.add((User)User//.withDefaultPasswordEncoder()
	            .builder()
				.username("admin")
	            .password("password")
	            .roles("ADMIN")
	            .build());
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username+"  :::::in loadUserByUsername");
//		UserDetails us= User//.withDefaultPasswordEncoder()
//	            .builder()
//				.username("user")
//	            .password("password")
//	            .roles("USER")
//	            .build();
//		System.out.println("US: "+us);
//		return us;
		for(User u : users) {
		System.out.println(u.getUsername()+u.getPassword()+" @new");
			if(u.getUsername().equals(username)) {
				System.out.println(u);
				return u;
				
			}
		}
		throw new UsernameNotFoundException("USERNAME NOT FOUnD");
	}

}
