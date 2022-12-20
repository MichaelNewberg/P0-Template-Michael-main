package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {

	private UserDao userDao;//use so I dont need to remake this for each constructor.
	public PlanetDao(){
		this.userDao = new UserDao();
	}
	
    //added throws clause to method signature, alternative is to return an empty ArrayList.
	//Method could still succeed with no plants returned, not ideal.
	//Let service layer and/or API handle the exception later.
    public List<Planet> getAllPlanets() throws SQLException{
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Planet> planets = new ArrayList<>();
			while(rs.next()){//the resultset next method returns a boolean.
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(1);
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		} catch (SQLException e) {
			System.out.println("Planet not found.");
			return new Planet();
		}
	}

	public Planet getPlanetById(String username, int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "select * from planets where id= ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
			return planet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public Planet createPlanet(String username, Planet p) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "insert into planets values (default,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());
			int ownerId = this.userDao.getUserByUsername(username).getId();
			ps.setInt(2, ownerId);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			Planet newPlanet = new Planet();
			rs.next();
			int newPlanetId = rs.getInt("id");
			newPlanet.setId(newPlanetId);
			newPlanet.setName(p.getName());
			newPlanet.setOwnerId(ownerId);
			return newPlanet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public void deletePlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			System.out.println(e.getMessage());//good spot to add some logging?
		}
	}
}


