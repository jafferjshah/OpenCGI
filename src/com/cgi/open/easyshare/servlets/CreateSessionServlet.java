package com.cgi.open.easyshare.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;

/**
 * Servlet implementation class CreateSessionServlet
 */
public class CreateSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EasyShareServices easy= ServicesMapper
	.getEasyShareServicesProxyInstance();

    /**
     * Default constructor. 
     */
    public CreateSessionServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String sessionName,desc;
		Integer sNo=null;
		
			sessionName=(String)request.getParameter(REQUEST_PARAMETERS.SESSION_NAME); 
			System.out.println(REQUEST_PARAMETERS.SESSION_NAME);
			desc=(String)request.getParameter(REQUEST_PARAMETERS.DESCRIPTION);
		
		try {
			sNo=easy.createSession(sessionName,desc);
		} catch (DuplicateSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(sNo);
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
