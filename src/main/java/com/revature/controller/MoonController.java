package com.revature.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.AuthenticationFailed;
import com.revature.exceptions.EntityNotFound;
import com.revature.models.Moon;
import com.revature.service.MoonService;
@RestController
public class MoonController {
	private static Logger moonLogger = LoggerFactory.getLogger(MoonController.class);
	@Autowired
	private MoonService mService;

	@ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenicationFailed(AuthenticationFailed e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    //allows us to specify
    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    //if name is too long for the table
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>("Could not delete moon.", HttpStatus.BAD_REQUEST);
    }
	@GetMapping("/api/moons")
	public ResponseEntity<List<Moon>> getAllMoons(){
		return new ResponseEntity<>(this.mService.getAllMoons(), HttpStatus.OK);
	}
	@GetMapping("/api/moon/{name}")
	public ResponseEntity<Moon> getMoonByName(@PathVariable String name){
		return new ResponseEntity<>(this.mService.getMoonByName(name), HttpStatus.OK);
	}
	@GetMapping("/api/moon/id/{id}")
	public ResponseEntity<Moon> getMoonById(@PathVariable int id) {
		return new ResponseEntity<>(this.mService.getMoonById(id), HttpStatus.OK);
	}
	@GetMapping("/api/planet/{planetId}/moons")
	public ResponseEntity<List<Moon>> getMoonsByPlanetId(@PathVariable int planetId){
		return new ResponseEntity<>(this.mService.getMoonsFromPlanet(planetId), HttpStatus.OK);
	}
	@PostMapping("/api/moon")
	public ResponseEntity<String> createMoon(@RequestBody Moon moon){
		String message = this.mService.createMoon(moon);
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	@PatchMapping("/api/moon")
	public ResponseEntity<String> updateMoon(@RequestBody Moon moon){
		return new ResponseEntity<>(this.mService.updateMoon(moon), HttpStatus.OK);
	}
	@DeleteMapping("/api/moon/{id}")
	public ResponseEntity<String> deleteMoonById(@PathVariable int id){
		return new ResponseEntity<>(this.mService.deleteMoonById(id), HttpStatus.OK);
	}
}
