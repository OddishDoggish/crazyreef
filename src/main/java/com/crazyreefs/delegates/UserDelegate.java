package com.crazyreefs.delegates;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.crazyreefs.beans.User;
import com.crazyreefs.data.UserPostgres;
import com.crazyreefs.services.UserService;

public class UserDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if ("POST".equals(request.getMethod())) {
			if (isAdmin((User) request.getSession().getAttribute("user"))) {
				register(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}

	}

	private void register(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		User u = null;
		UserService uServ = new UserService(new UserPostgres());
		ObjectMapper om = new ObjectMapper();
		
		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);

		if (jsonMap.containsKey("username") && jsonMap.containsKey("password")
				&& jsonMap.containsKey("email") && jsonMap.containsKey("firstname")
				&& jsonMap.containsKey("lastname")) {
			u.setUsername((String) jsonMap.get("username"));
			String password = (String) jsonMap.get("password");
			u.setEmail((String) jsonMap.get("email"));
			u.setFirstname((String) jsonMap.get("firstname"));
			u.setLastname((String) jsonMap.get("lastname"));
			uServ.updatePassword(u, password); // The object mapper sets the pw to plantext; this hashes it.
		} else {
			response.sendError(400, "All fields required.");
		}

		if (isUnique(u.getUsername())) {
			int uId = uServ.registerUser(u);
			u.setUserId(uId);
			response.getWriter().write(om.writeValueAsString(u));
		} else {
			response.sendError(400, "Invalid fields");
		}
	}
	
	private boolean isAdmin(User u) {
		if (u != null ) {
			UserService uServ = new UserService(new UserPostgres());
			if (uServ.getUserByUsername(u.getUsername()) != null) return true;
		}
		return false;
	}
	
	private boolean isUnique(String username) {
		UserService uServ = new UserService(new UserPostgres());
		
		if (uServ.getUserByUsername(username) != null)
			return false;
		else
			return true;
	}
}
