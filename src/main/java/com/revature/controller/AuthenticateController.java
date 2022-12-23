package com.revature.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthenticateController {
	@PostMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("user", "password");
		return "Logged in successfully.";
	}

	public void register(Context ctx) {
		//no code to handle something going wrong, like account with username already taken. Not handling it at the moment.
		UsernamePasswordAuthentication registerRequest = ctx.bodyAsClass(UsernamePasswordAuthentication.class);

		User newUser = userService.register(registerRequest);

		ctx.json(newUser).status(201);
	}

	public void invalidateSession(Context ctx) {
		ctx.req().getSession().invalidate();
	}
	
	public boolean verifySession(Context ctx) {	
		return ctx.sessionAttribute("user") != null;
	}
}
