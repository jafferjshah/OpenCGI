package com.cgi.open;

import com.cgi.open.easyshare.EasyShareServices;
import com.cgi.open.easyshare.proxy.EasyShareServicesProxy;
import com.cgi.open.persist.PersistenceServices;
import com.cgi.open.persist.proxy.PersistenceServicesJavaProxy;

public class ServicesMapper {
	public static EasyShareServices getEasyShareServicesProxyInstance() {
		return new EasyShareServicesProxy();
	}
	public static PersistenceServices getPersistenceServicesProxyInstance() {
		return new PersistenceServicesJavaProxy();
	}
}
