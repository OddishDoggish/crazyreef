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

	private UserService uServ = new UserService(new UserPostgres());
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		if (request.getSession(false) == null) {
			response.sendError(400, "Invalid credentials.");

			String path = (String) request.getAttribute("path");
			User uSession = (User) request.getSession().getAttribute("user");

			if (!isAdmin(uSession))
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

			if ("GET".equals(request.getMethod())) {
				if (path != null || !path.equals("")) {

					int num = getDigitsFromString(path);
					User u = uServ.getUserByID(num);

					if (u != null) {
						response.getWriter().write(om.writeValueAsString(u));
					}
				} else {
					response.getWriter().write(om.writeValueAsString(uServ.getUsers()));
				}

			} else if ("POST".equals(request.getMethod())) {
				if (path.equals("/register")) {
					register(request, response);
				} else if (path != null) {

					int num = getDigitsFromString(path);
					User u = uServ.getUserByID(num);

					if (u != null) {
						Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);

						if (jsonMap.containsKey("password")) {
							String password = (String) jsonMap.get("password");
							uServ.updatePassword(u, password);
						}

						if (jsonMap.containsKey("email")) {
							String email = (String) jsonMap.get("email");
							if (!u.getEmail().equals(email) && !email.equals(""))
								u.setEmail(email);
						}

						if (jsonMap.containsKey("firstname")) {
							String firstname = (String) jsonMap.get("firstname");
							if (!u.getFirstname().equals(firstname) && !firstname.equals(""))
								u.setFirstname(firstname);
						}

						if (jsonMap.containsKey("lastname")) {
							String lastname = (String) jsonMap.get("lastname");
							if (!u.getLastname().equals(lastname) && !lastname.equals(""))
								u.setLastname(lastname);
						}

						uServ.updateUser(u);
						response.getWriter().write(om.writeValueAsString(u));
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			} else if ("DELETE".equals(request.getMethod())) {
				if (path != null || !path.equals("")) {

					int num = getDigitsFromString(path);
					User u = uServ.getUserByID(num);

					if (u != null) {
						uServ.removeUser(u);
						response.setStatus(HttpServletResponse.SC_OK);
					}
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			} else {
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		}

	}

	private void register(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		User u = new User();
		UserService uServ = new UserService(new UserPostgres());
		ObjectMapper om = new ObjectMapper();
		
		Map jsonMap = om.readValue(request.getInputStream(), Map.class);

		if (jsonMap.containsKey("username") && jsonMap.containsKey("password")
				&& jsonMap.containsKey("email") && jsonMap.containsKey("firstname")
				&& jsonMap.containsKey("lastname")) {
			u.setUsername((String) jsonMap.get("username"));
			String password = (String) jsonMap.get("password");
			u.setEmail((String) jsonMap.get("email"));
			u.setFirstname((String) jsonMap.get("firstname"));
			u.setLastname((String) jsonMap.get("lastname"));
			uServ.updatePassword(u, password); // The object mapper sets the pw to plaintext; this hashes it.
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
			return uServ.getUserByUsername(u.getUsername()) != null;
		}
		return false;
	}
	
	private boolean isUnique(String username) {
		UserService uServ = new UserService(new UserPostgres());
		return uServ.getUserByUsername(username) == null;
	}

	private int getDigitsFromString(String str) {
		str = str.trim();
		StringBuilder digits = new StringBuilder("0");
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i)))
				digits.append(str.charAt(i));
		}

		return Integer.parseInt(digits.toString());
	}
}
