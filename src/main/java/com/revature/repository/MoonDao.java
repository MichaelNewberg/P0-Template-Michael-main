package com.revature.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Moon;

public interface MoonDao extends JpaRepository<Moon, Integer>{
    Optional<Moon> getMoonByName(String moonName);
	List<Moon> findByMyPlanetId(int myPlanetId);

	@Transactional
	@Modifying
	@Query(value= "insert into moons values (default, :name , :myPlanetId)", nativeQuery = true)
	void createMoon(@Param("name") String name, @Param("myPlanetId") int myPlanetId);

	@Transactional
	@Modifying
	@Query(value= "update moons set name = :name , my_planet_Id = :myPlanetId where moon_id = :id", nativeQuery = true)
	int updateMoon(@Param("name") String name, @Param("myPlanetId") int myPlanetId, @Param("id") int id);
    
}
