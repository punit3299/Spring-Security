package com.springsecurityjdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
//	Accessible to all, even not loggedIn
	@GetMapping("/")
	public String home() {
		return "<h1>Welcome</h1>";
	}
	
//	Acessible to user and admin both
	@GetMapping("/user")
	public String userHome() {
		return "<h1>Welcome User</h1>";
	}
	
//	Acessible to admin only
	@GetMapping("/admin")
	public String adminHome() {
		return "<h1>Welcome Admin</h1>";
	}

}