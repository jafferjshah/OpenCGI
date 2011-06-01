package com.cgi.open.easyshare.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateAppointmentException;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class AddAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "AddAppointment");
		Integer sessionId = Integer.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.SESSION_ID));
		String date = myRequest.getParameter(REQUEST_PARAMETERS.DATE);
		String fromTime = myRequest.getParameter(REQUEST_PARAMETERS.FROM_TIME);
		String toTime = myRequest.getParameter(REQUEST_PARAMETERS.TO_TIME);
		String location = myRequest.getParameter(REQUEST_PARAMETERS.LOCATION);
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Integer appointmentId=null;
		try {
			appointmentId= easyshare.addAppointment(sessionId, date, fromTime, toTime, location);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(appointmentId);
		} catch (SessionNotFoundException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (DuplicateAppointmentException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		}
		RenderResponse r=new RenderResponse();
		r.render(response, sr);
	}

}
