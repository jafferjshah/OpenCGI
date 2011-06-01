package com.cgi.open.easyshare.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.AttendeeNotFoundException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.FacilitatorNotFoundException;
import com.cgi.open.easyshare.SessionNotFoundException;

/**
 * Servlet implementation class RemoveFacilitator
 */
public class RemoveFacilitator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFacilitator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "RemoveFacilitator");
		Integer sessionId = Integer.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.SESSION_ID));
		String email = myRequest.getParameter(REQUEST_PARAMETERS.EMAIL);
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Boolean isRemoved=null;
		try {
			isRemoved= easyshare.removeFacilitator(sessionId, email);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(isRemoved);
		} catch (SessionNotFoundException e) {
			sr.setCode("FAILURE");
			sr.setMessage(e.getMessage());
		} catch (FacilitatorNotFoundException e) {
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
