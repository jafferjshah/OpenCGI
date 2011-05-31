package com.cgi.open.easyshare.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AdminAssignedException;
import com.cgi.open.easyshare.DuplicateAppointmentException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;

/**
 * Servlet implementation class AssignAdmin
 */
public class AssignAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "AssignAdmin");
		Integer sessionId = Integer.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.SESSION_ID));
		String email = myRequest.getParameter(REQUEST_PARAMETERS.EMAIL);
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Boolean isAssigned=null;
		try {
			isAssigned= easyshare.assignAdmin(sessionId,email);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(isAssigned);
		} catch (SessionNotFoundException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (PresentAsOtherUserTypeException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (UserNotFoundException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (AdminAssignedException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (PresentAsSameUserTypeException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		}
		RenderResponse r=new RenderResponse();
		r.render(response, sr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
