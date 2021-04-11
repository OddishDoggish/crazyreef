package com.crazyreefs.delegates;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface FrontControllerDelegate {
	
	void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException;
	
}
