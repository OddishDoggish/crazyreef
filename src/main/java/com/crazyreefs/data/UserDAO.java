package com.crazyreefs.data;

import java.util.Set;

import com.crazyreefs.beans.User;

public interface UserDAO {
	
	public int createUser(User u);
	public Set<User> findUsers();
	public User findUserByUsername(String username);
	public User findUserByUserId(int userId);
	public void updateUser(User u);
	public void deleteUser(User u);

}
