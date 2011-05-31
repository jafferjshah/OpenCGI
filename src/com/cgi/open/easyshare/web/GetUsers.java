package com.cgi.open.easyshare.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserNotFoundException;
import com.cgi.open.easyshare.model.User;
import com.cgi.open.easyshare.model.UserType;

/**
 * Servlet implementation class GetUsers
 */
public class GetUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyHttpServletRequest myRequest=new MyHttpServletRequest(request);
		ServiceResponse sr = new ServiceResponse();
		sr.initServiceResponse(myRequest, "GetUsers");
		UserType userType = UserType.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.USERTYPE));
		Integer sessionId=Integer.valueOf(myRequest.getParameter(REQUEST_PARAMETERS.SESSION_ID));
		EasyShareServices easyshare = ServicesMapper.getEasyShareServicesProxyInstance();
		Set<User> users=null;
		try {
			users= easyshare.getUsers(sessionId, userType);
			sr.setCode("SUCCESS");
			sr.setMessage("SUCCESS");
			sr.setData(users);
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
