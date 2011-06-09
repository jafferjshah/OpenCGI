package com.cgi.open.easyshare.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Servlet implementation class CreateSessionServlet
 */
public class CreateSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/*protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(request, "CreateSession");
		String sessionName = request.getParameter("sessionName");
		String description = request.getParameter("description");
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Integer sessionId = 0;
		try {
			sessionId = easyshare.createSession(sessionName, description);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(sessionId);
		} catch (DuplicateSessionException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		}
		XStream x = new XStream(new DomDriver());
		PrintWriter pw = response.getWriter();
		pw.println(x.toXML(sr));
		pw.close();
	}*/
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "CreateSession");
		String sessionName = myRequest.getParameter(REQUEST_PARAMETERS.SESSION_NAME);
		String description = myRequest.getParameter(REQUEST_PARAMETERS.DESCRIPTION);
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Integer sessionId = 0;
		try {
			sessionId = easyshare.createSession(sessionName, description);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(sessionId);
		} catch (DuplicateSessionException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		}
		MyHttpServletResponse myResponse=new MyHttpServletResponse(response);
		myResponse.setServiceResponse(sr);
		RenderResponse r=new RenderResponse();
		r.render(myResponse);
	}

}
