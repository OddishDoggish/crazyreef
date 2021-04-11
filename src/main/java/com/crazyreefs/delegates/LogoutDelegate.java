package com.crazyreefs.delegates;

import com.crazyreefs.beans.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if ("POST".equals(request.getMethod())) {
			if (request.getSession(false) != null) {
				User u = (User) request.getSession().getAttribute("user");
				response.getWriter().write("You have successfully logged out: " + u.getUsername());
				request.getSession().invalidate();
			} else {
				response.getWriter().write("There was no user logged into this session.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
