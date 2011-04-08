package com.cgi.open.easyshare.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class CreateSessionTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd;
	public CreateSessionTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd= new ServiceDef();
		sd.setServiceName("createSession");
		Set<String> elig = new HashSet<String>();
		elig.add("ADMIN");
		sd.setEligUserTypes(elig);
		sd.setSessionRelationExists(Boolean.FALSE);
	}
	
	public void testCreateSession(){
		String sessionName="Spring5.6";
		String description="features";
		Integer sessionId=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", null)) {
			sessionId = easy.createSession(sessionName,description);
			assertTrue("session created",sessionId>0);
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (DuplicateSessionException e) {
			fail("DuplicateSessionException:Should not occur");
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} 
	}
	
	public void testCreateDuplicateSession(){
		String sessionName="Spring5.6";
		String description="features";
		Integer sessionId=null;
		try {
			if (sp.isServReqValid(sd, "anupama.charles", null)){
			sessionId = easy.createSession(sessionName,description);
			fail("should raise a DuplicateSessionException ");
			}
			else{
				throw new InvalidServiceInvocationException();
			}
		} catch (DuplicateSessionException e) {
			assertTrue("success",true);
		}  catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		}catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		}
	}
}
