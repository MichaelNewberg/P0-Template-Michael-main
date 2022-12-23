package com.revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.EntityNotFound;
import com.revature.models.User;
import com.revature.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public List<User> getAllUsers(){
		List<User> users = this.userDao.findAll();
		if (users.size() != 0) {
			return users;
		} else {
			throw new EntityNotFound("No users found in the database.");
		}
	}
	
	public User getUserById(int id){
		Optional<User> possibleUser = this.userDao.findById(id);
		if (possibleUser.isPresent()) {
			return possibleUser.get();
		} else {
			throw new EntityNotFound("User not found.");
		}
	}

	public User getUserByUsername(String username) {
		Optional<User> possibleUser = this.userDao.findByUserName(username);
		if (possibleUser.isPresent()) {
			return possibleUser.get();
		} else {
			throw new EntityNotFound("User not found.");
		}
	}


	public String createUser(User user){
		this.userDao.createUser(user.getUsername(), user.getPassword());
		return "User created.";
	}

	public String updateUser(User user){
		int rowCount = this.userDao.updateUser(user.getUsername(), user.getPassword());
		if (rowCount == 1) {
			return "User information updated successfully";
		} else {
			throw new EntityNotFound("Could not update user information.");
		}
	}

	public String deleteUserById(int id){
		this.userDao.deleteById(id);
		return "User with given id has been deleted.";
	}
	
}
