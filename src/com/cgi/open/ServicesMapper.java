package com.cgi.open;

import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.proxy.EasyShareServicesProxy;
import com.cgi.open.external.UserIntegration;
import com.cgi.open.external.proxy.UserIntegrationJavaProxy;
import com.cgi.open.persist.PersistenceServices;
import com.cgi.open.persist.proxy.PersistenceServicesJavaProxy;
import com.cgi.open.userconcerns.SeparationOfUserConcerns;
import com.cgi.open.userconcerns.proxy.SeparationOfUserConcernsProxy;

public class ServicesMapper {
	public static EasyShareServices getEasyShareServicesProxyInstance() {
		return new EasyShareServicesProxy();
	}
	public static PersistenceServices getPersistenceServicesProxyInstance() {
		return new PersistenceServicesJavaProxy();
	}
	public static SeparationOfUserConcerns getSeparationOfUserConcernsProxyInstance() {
		return new SeparationOfUserConcernsProxy();
	}
	public static UserIntegration getUserIntegrationProxyInstance() {
		return new UserIntegrationJavaProxy();
	}
}
