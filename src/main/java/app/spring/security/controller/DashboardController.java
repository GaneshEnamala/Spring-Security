package app.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = { "/", "/home", "/index", "/homepage" })
	public String homepage() {
		return "index";
	}

	@GetMapping("/user/home")
	public String userPage() {
		return "user";
	}

	@GetMapping("/admin/home")
	public String adminPage() {
		return "admin";
	}
	
	@GetMapping("/login")
	public String handleLogin() {
		return "custom_login";
	}
}
