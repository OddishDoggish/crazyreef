package com.crazyreefs.controller;

import com.crazyreefs.delegates.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

	private Map<String, FrontControllerDelegate> delegateMap;
	
	{
		System.out.println("Do things?");
		delegateMap = new HashMap<String, FrontControllerDelegate>();

		delegateMap.put("login", new LoginDelegate());
		delegateMap.put("logout", new LogoutDelegate());
		delegateMap.put("users", new UserDelegate());
		delegateMap.put("stock", new StockDelegate());
//		delegateMap.put("inventory", new InventoryDelegate());
	}

	public FrontControllerDelegate handle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("The FCD is doing shit.");

		if ("OPTIONS".equals(request.getMethod()))
			return (r1, r2) -> {};
		
		StringBuilder uriString = new StringBuilder(request.getRequestURI());
		
		uriString.replace(0, request.getContextPath().length()+1, "");
		if (uriString.indexOf("/") != -1) {
			request.setAttribute("path", uriString.substring(uriString.indexOf("/")+1));
			uriString.replace(uriString.indexOf("/"), uriString.length(), "");
		}
		System.out.println("The URI string is " + uriString);
		System.out.println("The path is " + request.getAttribute("path"));
		
		return delegateMap.get(uriString.toString());
	}
	
}
