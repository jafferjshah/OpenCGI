package com.cgi.open.easyshare.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.PresentAsOtherUserTypeException;
import com.cgi.open.easyshare.PresentAsSameUserTypeException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;

/**
 * Servlet implementation class AddMessage
 */
public class AddMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "AddMessage");
		Integer sessionId = Integer.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.SESSION_ID));
		String subject = myRequest.getParameter(REQUEST_PARAMETERS.SUBJECT);
		String text = myRequest.getParameter(REQUEST_PARAMETERS.TEXT);
		String date = myRequest.getParameter(REQUEST_PARAMETERS.DATE);
		String postTime = myRequest.getParameter(REQUEST_PARAMETERS.POST_TIME);
		String postBy = myRequest.getParameter(REQUEST_PARAMETERS.POST_BY);
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Integer messageId=null;
		try {
			messageId= easyshare.addMessage(sessionId, subject, text, date, postTime, postBy);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(messageId);
		} catch (SessionNotFoundException e) {
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
