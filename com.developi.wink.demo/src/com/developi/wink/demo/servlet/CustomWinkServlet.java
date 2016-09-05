package com.developi.wink.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.domino.services.AbstractRestServlet;

public class CustomWinkServlet extends AbstractRestServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doDestroy() {
		System.out.println("Custom Wink Servlet destroyed...");
		super.doDestroy();
	}

	@Override
	protected void doInit() throws ServletException {
		super.doInit();
		System.out.println("Custom Wink Servlet initialized.");
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doService(request, response);
		} catch(Throwable t) {
			t.printStackTrace();
			response.sendError(500, "Application failed!");
		}
	}

}
