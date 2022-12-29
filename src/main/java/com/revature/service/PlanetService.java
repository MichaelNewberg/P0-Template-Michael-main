package com.revature.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.exceptions.EntityNotFound;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

@Service
public class PlanetService {
	@Autowired
	private PlanetDao planetDao;

	public List<Planet> getAllPlanets(){
		List<Planet> planets = this.planetDao.findAll();
		if (planets.size() != 0) {
			return planets;
		} else {
			throw new EntityNotFound("No planets found in the database.");
		}
	}

	public Planet getPlanetByName(String planetName) {
		Optional<Planet> possiblePlanet = this.planetDao.findByName(planetName);
		if (possiblePlanet.isPresent()) {
			return possiblePlanet.get();
		} else {
			throw new EntityNotFound("Planet not found.");
		}
	}

	public Planet getPlanetById(int planetId){
		Optional<Planet> possiblePlanet = this.planetDao.findById(planetId);
		if (possiblePlanet.isPresent()) {
			return possiblePlanet.get();
		} else {
			throw new EntityNotFound("Planet not found.");
		}
	}

	public String createPlanet(Planet planet){
		this.planetDao.createPlanet(planet.getName(), planet.getOwnerId());
		return "Planet created.";
	}

	public String updatePlanet(String name, int ownerId){
		int rowCount = this.planetDao.updatePlanet(name, ownerId);
		if (rowCount == 1) {
			return "Planet updated.";
		} else {
			throw new EntityNotFound("Could not update planet.");
		}

	}

	public String deletePlanetById(int planetId){
		this.planetDao.deleteById(planetId);
		return "Planet with given id deleted.";
	}
}
