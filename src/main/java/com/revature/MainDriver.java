package com.revature;



import java.util.ArrayList;
import java.util.List;

import com.revature.controller.RequestMapping;
import com.revature.repository.MoonDao;

import io.javalin.Javalin;

public class MainDriver {
	static List<MoonDao> moons = new ArrayList<>();
	public static void main(String[] args) {

		Javalin app = Javalin.create(confg ->{
			confg.plugins.enableDevLogging();
		});
		app.post("/moon", ctx -> {
			//MoonDao newMoon = ctx.bodyAsClass(MoonDao.class);
			//moons.add(Moon.createMoon());

		});

		app.get("/moon/{id}", ctx -> {
			//int id = Integer.parseInt(ctx.pathParam("id"));
			//MoonDao moon = moons.get(id); 
		});
		RequestMapping.setupEndpoints(app);
		app.start(7000);
	}
}
