package com.crazyreefs.data;

import com.crazyreefs.beans.User;
import com.crazyreefs.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class UserPostgres implements UserDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int createUser(User u) {
		Integer id = 0;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO admin_user VALUES "
					+ "(default, ?, ?, ?, ?, ?)";
			String[] keys = {"user_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstname());
			pstmt.setString(4, u.getLastname());
			pstmt.setString(5, u.getEmail());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		u.setUserId(id);
		return id;
	}

	@Override
	public Set<User> findUsers() {
		
		HashSet<User> users = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM admin_user";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			users = new HashSet<>();
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstname(rs.getString("first_name"));
				u.setLastname(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				
				users.add(u);
				
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return users;
	}

	@Override
	public User findUserByUsername(String username) {
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM admin_user " +
					"WHERE username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstname(rs.getString("first_name"));
				u.setLastname(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return u;
	}
	
	@Override
	public User findUserByUserId(int userId) {
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM admin_user " +
					"WHERE user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstname(rs.getString("first_name"));
				u.setLastname(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return u;
	}

	@Override
	public void updateUser(User u) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "UPDATE admin_user SET " +
					"first_name = ?, last_name = ?, email = ? " +
					"WHERE user_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getFirstname());
			pstmt.setString(2, u.getLastname());
			pstmt.setString(3, u.getEmail());
			pstmt.setInt(4, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(User u) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM admin_user " +
					"WHERE user_id = ?";
				
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}

}
