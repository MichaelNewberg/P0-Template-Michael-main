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
import com.revature.models.Planet;
import com.revature.service.PlanetService;


@RestController
public class PlanetController {
	private static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);
	@Autowired
	private PlanetService pService;

	@ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenicationFailed(AuthenticationFailed e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	//allows all objects in this class to use a custom exception message and to use less code (see below)
    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    //if name is too long for the table
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>("Could not delete planet.", HttpStatus.BAD_REQUEST);
    }

	@GetMapping("/api/planets")
	public ResponseEntity<List<Planet>> getAllPlanets(){
		//delete later
		System.out.println("getAllPlanets API method called.");
		planetLogger.info("Get getAllPlanets() called.");
		return new ResponseEntity<>(this.pService.getAllPlanets(), HttpStatus.OK);
	}
	@GetMapping("/api/planet/{name}")
	public ResponseEntity<Planet> getPlanetByName(@PathVariable String name){
		planetLogger.info("Get getPlanetByName called.");
		return new ResponseEntity<>(this.pService.getPlanetByName(name), HttpStatus.OK);
	}
	@GetMapping("/api/planet/id/{id}")
	public ResponseEntity<Planet> getPlanetByID(@PathVariable int id){
		planetLogger.info("Get getPlanetById called.");
		return new ResponseEntity<>(this.pService.getPlanetById(id), HttpStatus.OK);
	}
	@PostMapping("/api/planet")
	public ResponseEntity<String> createPlanet(@RequestBody Planet planet){
		planetLogger.info("Post createTeam() called.");
		return new ResponseEntity<>(this.pService.createPlanet(planet), HttpStatus.CREATED);
	}
	@PatchMapping("/api/planet")
	public ResponseEntity<String> updatePlanet(@RequestBody Planet planet){
		planetLogger.info("Patch updateTeam() called.");
		return new ResponseEntity<>(this.pService.updatePlanet(planet.getName(), planet.getOwnerId()), HttpStatus.CREATED);
	}
	@DeleteMapping("/api/planet/{id}")
	public ResponseEntity<String> deletePlanetById(@PathVariable int id){
		planetLogger.info("Delete deletePlanetbyId called");
		return new ResponseEntity<>(this.pService.deletePlanetById(id), HttpStatus.OK);
	}
}
