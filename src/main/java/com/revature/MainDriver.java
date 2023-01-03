package com.revature;



import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.revature.repository.MoonDao;


@SpringBootApplication
public class MainDriver {
	static List<MoonDao> moons = new ArrayList<>();
	public static void main(String[] args) {
		System.out.println("Starting application.");
		SpringApplication.run(MainDriver.class, args);
	}
}