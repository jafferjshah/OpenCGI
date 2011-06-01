package com.cgi.open.easyshare.test.composite;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.Message;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;

public class AddGetMessageTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd1,sd2,sd3;
	public AddGetMessageTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd1= new ServiceDef();
		sd1.setServiceName("Add Message");
		Set<String> elig = new HashSet<String>();
		elig.add("FACILITATOR");
		elig.add("ATTENDEE");
		sd1.setEligUserTypes(elig);
		
		sd2= new ServiceDef();
		sd2.setServiceName("Get Messages");
		sd2.setEligUserTypes(elig);
		
		sd1.setSessionRelationExists(Boolean.TRUE);
		sd2.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddGetMessages(){
		Integer sessionId=7;
		String subject="forum";
		String text="how was the session";
		String date="03/26/2011";
		String post_time="1530";
		String post_by="safia@cgi.com";
		Boolean isAdded,actual,isRetrieved;
		try {
			if (sp.isServReqValid(sd1, "safia@cgi.com", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			isAdded=msgId>0;
			actual=isAdded;
			}
			else{
				throw new InvalidServiceInvocationException();
			}
			
			if (sp.isServReqValid(sd1, "jaffer@cgi.com", sessionId)) {
			Integer msgId=easy.addMessage(sessionId, subject, text, date, post_time, post_by);
			List<Message>messages=easy.getMessages(sessionId);
			if(messages.toString().contains(msgId.toString())){
				isRetrieved=Boolean.TRUE;
			}
			else{
				isRetrieved=Boolean.FALSE;
			}
			actual=actual&&isRetrieved;
			}
			else{
				throw new InvalidServiceInvocationException();
			}
			assertEquals(Boolean.TRUE,actual);
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}

	}

}
