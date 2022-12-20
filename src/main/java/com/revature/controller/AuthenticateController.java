package com.revature.controller;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;

import io.javalin.http.Context;

public class AuthenticateController {
	
	private UserService userService = new UserService();

	public void authenticate(Context ctx) {
		
		UsernamePasswordAuthentication loginRequest = ctx.bodyAsClass(UsernamePasswordAuthentication.class);
		
		User u = userService.getUserByUsername(loginRequest.getUsername());
	
		if (u != null && u.getPassword().equals(loginRequest.getPassword())) {
			ctx.sessionAttribute("user", u);
			ctx.status(200);
		} else {
			ctx.status(400);
		}
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
