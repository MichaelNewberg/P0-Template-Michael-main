package com.revature.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;

@RestController
public class AuthenticateController {
	@Autowired
	private UserService userService;

	//have to compare inputs to a list
	@PostMapping("/login")
	public ResponseEntity<User> login(HttpSession session, @RequestBody UsernamePasswordAuthentication credentials){
		User user = userService.getUserByUsername(credentials.getUsername());
		if (user != null){//will return null pointer exception
			if (user.getPassword().equals(credentials.getPassword())) {
				session.setAttribute("user", user);
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/logout")
	public String logout(HttpSession session){
        session.invalidate();
        return "Logged out successfully.";
    }

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user){
		return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.OK);
	}

	@GetMapping("/user/{name}")
	public ResponseEntity<User> getUserbyName(@PathVariable String name){
		return new ResponseEntity<>(this.userService.getUserByUsername(name), HttpStatus.OK);
	}
	//add update user after everything is done
}
