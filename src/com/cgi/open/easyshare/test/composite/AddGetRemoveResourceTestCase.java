package com.cgi.open.easyshare.test.composite;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.cgi.open.ServicesMapper;
import com.cgi.open.easyshare.DuplicateResourceException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.InvalidServiceInvocationException;
import com.cgi.open.easyshare.ResourceNotFoundException;
import com.cgi.open.easyshare.SessionNotFoundException;
import com.cgi.open.easyshare.UserTypeNotValidException;
import com.cgi.open.easyshare.model.Resource;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.model.ServiceDef;


public class AddGetRemoveResourceTestCase extends TestCase{
	EasyShareServices easy;
	SeparationOfUserConcerns sp; 
	ServiceDef sd1,sd2,sd3;
	public AddGetRemoveResourceTestCase(String name){
		super(name);
	}

	
	public void setUp() {
		 easy= ServicesMapper
		.getEasyShareServicesProxyInstance();
		 sp= ServicesMapper
			.getSeparationOfUserConcernsProxyInstance();
		sd1= new ServiceDef();
		sd1.setServiceName("Add Resource");
		Set<String> elig1 = new HashSet<String>();
		elig1.add("FACILITATOR");
		sd1.setEligUserTypes(elig1);
		
		sd2= new ServiceDef();
		sd2.setServiceName("Get Resource");
		Set<String> elig2 = new HashSet<String>();
		elig2.add("ATTENDEE");
		elig2.add("FACILITATOR");
		sd2.setEligUserTypes(elig2);
		
		sd3= new ServiceDef();
		sd3.setServiceName("Remove Resource");
		Set<String> elig3 = new HashSet<String>();
		elig3.add("FACILITATOR");
		sd3.setEligUserTypes(elig3);
		
		sd1.setSessionRelationExists(Boolean.TRUE);
		sd2.setSessionRelationExists(Boolean.TRUE);
		sd3.setSessionRelationExists(Boolean.TRUE);
	}
	
	public void testAddGetResource(){
		Integer sessionId=7;
		String resourceName="Snapshots";
		String url="resources/Snapshots";
		Boolean actual,isAdded,isRetrieved,isRemoved=null;
		Integer resourceId;
		Set<Resource>resources;
		try {
			if (sp.isServReqValid(sd1, "jaffer@cgi.com", sessionId)) {
			resourceId=easy.addResource(sessionId, resourceName, url);
			isAdded=resourceId>0;
			actual=isAdded;
			}
			else{
				throw new InvalidServiceInvocationException();
			}
			
			if (sp.isServReqValid(sd2, "javed@cgi.com", sessionId)) {
			resources=easy.getResources(sessionId);
			if(resources.toString().contains(resourceId.toString())){
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
			
			if (sp.isServReqValid(sd3, "jaffer@cgi.com", sessionId)) {
				isRemoved=easy.removeResource(sessionId, resourceId);
				actual=actual&&isRemoved;
			}else{
					throw new InvalidServiceInvocationException();
			}
			
			if (sp.isServReqValid(sd2, "jaffer@cgi.com", sessionId)) {
			resources=easy.getResources(sessionId);
			System.out.println(resources);
			if(resources.toString().contains(resourceId.toString())){
				isRetrieved=Boolean.FALSE;
			}
			else{
				isRetrieved=Boolean.TRUE;
			}
			actual=actual&&isRetrieved;
			}
			else{
				throw new InvalidServiceInvocationException();
			}
			
			assertEquals(Boolean.TRUE,actual);	
		} catch (SessionNotFoundException e) {
			fail("SessionNotFoundException : Should not occur");
		} catch (DuplicateResourceException e) {
			fail("DuplicateResourceException : Should not occur");
		} catch (InvalidServiceInvocationException e) {
			fail("Invalid service invocation");
		} catch (UserTypeNotValidException e) {
			fail("UserTypeNotValidException : Should not occur");
		} catch (ResourceNotFoundException e) {
			fail("ResourceNotFoundException : Should not occur");
		}
	}

}
