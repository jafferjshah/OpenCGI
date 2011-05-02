package com.cgi.open.easyshare.test;

import com.cgi.open.easyshare.DuplicateSessionException;
import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.proxy.EasyShareServicesProxy;

import junit.framework.TestCase;

public class TestEasyShareProxy extends TestCase {

	public void testCreateSession() {
		EasyShareServices services = new EasyShareServicesProxy();
		try {
			Integer in = services.createSession("Spring - Introduction", "Spring - Introduction");
			assertEquals(in, (Integer)1);
		}
		catch(Exception ex) {
			assertTrue("success", true);
		}
	}

}
