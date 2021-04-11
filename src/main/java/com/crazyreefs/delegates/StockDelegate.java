package com.crazyreefs.delegates;

import com.crazyreefs.beans.Condition;
import com.crazyreefs.beans.User;
import com.crazyreefs.beans.Stock;
import com.crazyreefs.data.StockPostgres;
import com.crazyreefs.data.UserPostgres;
import com.crazyreefs.services.StockService;
import com.crazyreefs.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class StockDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if ("POST".equals(request.getMethod())) {
			if (isAdmin((User) request.getSession().getAttribute("user"))) {
				create(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}

	}

	private void create(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Stock s = null;
		Condition c = null;
		StockService sServ = new StockService(new StockPostgres());
		ObjectMapper om = new ObjectMapper();
		
		Map<String, Object> jsonMap = om.readValue(request.getInputStream(), Map.class);

		if (jsonMap.containsKey("name")) {
			s.setName((String) jsonMap.get("name"));
		} else {
			response.sendError(400, "Name required.");
		}

		if (!isUnique(s.getName()))
			response.sendError(400,"That name already exists.");

		if (jsonMap.containsKey("price"))
			s.setPrice((double) jsonMap.get("price"));
		if (jsonMap.containsKey("cost"))
			s.setCost((double) jsonMap.get("cost"));
		if (jsonMap.containsKey("source"))
			s.setSource((String) jsonMap.get("source"));
		if (jsonMap.containsKey("description"))
			s.setDescription((String) jsonMap.get("description"));
		if (jsonMap.containsKey("size"))
			s.setSize((String) jsonMap.get("size"));
		if (jsonMap.containsKey("category"))
			s.setCategory((String) jsonMap.get("category"));
		if (jsonMap.containsKey("subcategory"))
			s.setSubcategory((String) jsonMap.get("subcategory"));
		if (jsonMap.containsKey("available"))
			s.setAvailable((int) jsonMap.get("available"));
		if (jsonMap.containsKey("sample"))
			s.setSample((boolean) jsonMap.get("sample"));
		if (jsonMap.containsKey("light"))
			c.setLight((String) jsonMap.get("light"));
		if (jsonMap.containsKey("flow"))
			c.setFlow((String) jsonMap.get("flow"));
		if (jsonMap.containsKey("care"))
			c.setCare((String) jsonMap.get("care"));
		if (jsonMap.containsKey("safe"))
			c.setSafe((String) jsonMap.get("safe"));
		if (c != null)
			s.setConditions(c);

		int sId = sServ.addNewStockItem(s);
			s.setStockId(sId);
			response.getWriter().write(om.writeValueAsString(s));
	}
	
	private boolean isAdmin(User u) {
		if (u != null ) {
			UserService uServ = new UserService(new UserPostgres());
			if (uServ.getUserByUsername(u.getUsername()) != null) return true;
		}
		return false;
	}
	
	private boolean isUnique(String name) {
		StockService sServ = new StockService(new StockPostgres());
		
		if (sServ.getStockByName(name) != null)
			return false;
		else
			return true;
	}
}
