package com.developi.wink.template.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.domino.services.AbstractRestServlet;

public class CustomWinkServlet extends AbstractRestServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doDestroy() {
		
		// This part will run on HTTP quit.
		// You can inject your code if you want to execute anything special before the project unloaded.
		System.out.println("Wink Servlet destroyed...");
		
		super.doDestroy();
	}

	@Override
	protected void doInit() throws ServletException {
		super.doInit();

		// This part will run once on the first request taken by the servlet.
		// You can inject your code if you want to execute anything special on start.
		System.out.println("Wink Servlet initialized.");
		
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// This part will run every time a request taken. You can inject your code here if you need to do anything
			// before the request fulfilled.
			//
			// For instance, you might want to check a specific authentication key here, force SSL connection
			// or log the request in case of debugging, etc.
			
			super.doService(request, response);
			
			// This part will run when response is ready. You can inject your code here if you need to do anything
			// after the request fulfilled.
			//
			// For instance, you might want to add a specific response tag here or log the response for debugging.
			
		} catch(Throwable t) {
			t.printStackTrace();
			response.sendError(500, "Application failed!");
		}
	}

}
