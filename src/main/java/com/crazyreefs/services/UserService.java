package com.crazyreefs.services;

import com.crazyreefs.beans.User;
import com.crazyreefs.data.UserDAO;
import com.crazyreefs.util.BCrypt;

import java.util.Set;

public class UserService {

	private UserDAO userDAO;

	public UserService(UserDAO ud) {
		userDAO = ud; 
	}
	
	// Create
	public int registerUser(User u) {
		return userDAO.createUser(u);
	}
	
	// Read
	public User loginUser(String username, String password) {
		User u = userDAO.findUserByUsername(username);
		System.out.println("The password is: " + u.getPassword());
		System.out.println("The hashed password is: " + BCrypt.hashpw(u.getPassword(),BCrypt.gensalt()));
		if (u != null && (BCrypt.checkpw(u.getPassword(),password)))
			return u;
		else
		return null;
	}

	public User getUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
		}
	
	public User getUserByID(int id) {
		return userDAO.findUserByUserId(id);
	}
	
	public Set<User> getUsers() {
		return userDAO.findUsers();
	}

	
	// Update
	public void updatePassword(User u, String password) {
		String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
		u.setPassword(pw_hash);
		userDAO.updateUser(u);
	}

	public void updateUser(User u) {
		userDAO.updateUser(u);
	}
	
	
	// Delete
	public void removeUser(User u) {
			userDAO.deleteUser(u);
	}
}

