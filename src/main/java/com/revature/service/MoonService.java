package com.revature.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.exceptions.EntityNotFound;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;

@Service
public class MoonService {
	@Autowired
	private MoonDao moonDao;

	
	public List<Moon> getAllMoons(){
		List<Moon> moons = this.moonDao.findAll();
		if (moons.size() != 0) {
			return moons;
		} else {
			throw new EntityNotFound("No moons found in the database.");
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId){
		List<Moon> planetMoons = this.moonDao.findByMyPlanetId(planetId);
		if (planetMoons.size() != 0) {
			return planetMoons;
		} else {
			throw new EntityNotFound("No moons found for this planet.");
		}
	}

	public Moon getMoonByName(String moonName){
		Optional<Moon> possibleMoon = this.moonDao.getMoonByName(moonName);
		if (possibleMoon.isPresent()) {
			return possibleMoon.get();
		} else {
			throw new EntityNotFound("Moon not found.");
		}
	}

	public Moon getMoonById(int moonId) {
		Optional<Moon> possibleMoon = this.moonDao.findById(moonId);
		if (possibleMoon.isPresent()) {
			return possibleMoon.get();
		} else {
			throw new EntityNotFound("Moon not found.");
		}
	}

	public String createMoon(Moon moon){
		this.moonDao.createMoon(moon.getName(), moon.getMyPlanetId());
		return "Moon created.";
	}

	public String updateMoon(Moon moon){
		int rowCount = this.moonDao.updateMoon(moon.getName(), moon.getMyPlanetId(), moon.getId());
		if (rowCount == 1) {
			return "Moon update.";
		} else {
			throw new EntityNotFound("Could not update moon.");
		}
	}
	public String deleteMoonById(int moonId) {
		this.moonDao.deleteById(moonId);
		return "Moon with given id deleted.";
	}
}
