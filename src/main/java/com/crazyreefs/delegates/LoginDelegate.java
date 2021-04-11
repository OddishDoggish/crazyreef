package com.crazyreefs.delegates;

import com.crazyreefs.beans.User;
import com.crazyreefs.data.UserPostgres;
import com.crazyreefs.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class LoginDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		if ("POST".equals(request.getMethod())) {
			logIn(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	private void logIn(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {

		UserService uServ = new UserService(new UserPostgres());
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);
		
		String username = (String) jsonMap.get("username");
		String password = (String) jsonMap.get("password");
		
		//User u = uServ.loginUser(username, password);
		User u = uServ.getUserByID(1);
		
		if (u != null) {
			request.getSession().setAttribute("user", u);
			response.getWriter().write((om.writeValueAsString(u)));
		} else {
			response.sendError(400, "Incorrect credentials.");
		}
	}
	
}
