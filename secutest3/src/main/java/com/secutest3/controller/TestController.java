package com.secutest3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

	@GetMapping("user")
	public String testUser() {
		return "In user page";
	}
	@GetMapping("admin")
	public String testAdmin() {
		return "In admin page";
	}
}
