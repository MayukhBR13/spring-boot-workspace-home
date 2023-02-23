package com.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.springboot.model.Item;
import com.springboot.model.ROLES;
import com.springboot.model.User;

public class UserService {
	static List<User> items=new ArrayList();
	static {
		items.add(new User("admin","admin",ROLES.ADMIN));
		items.add(new User("user","user",ROLES.USER));
	}
	
	public boolean validate(String username,String password) {
		return this.items.stream().anyMatch(e->
			e.getUsername()==username&&e.getPassword()==password);
	}

	public Optional<User> findByUsername(String username) {
		List<User> users=items.stream().filter(u->u.getUsername().equals(username))
				.collect(Collectors.toList());
		if(users.size()>=1)
			return Optional.of(users.get(0));
		else
			return Optional.empty();
	}
	

}
