package app.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.spring.security.model.MyUser;
import app.spring.security.repository.MyUserRepo;

@RestController
public class UserController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MyUserRepo myUserRepo;

	@PostMapping("/register")
	public MyUser createNewUser(@RequestBody MyUser myUser) {
		myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
		return myUserRepo.save(myUser);
	}
}
