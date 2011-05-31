package com.cgi.open.easyshare.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RenderResponse {
	
	public void render(HttpServletResponse response,ServiceResponse sr) throws IOException{
		XStream x = new XStream(new DomDriver());
		PrintWriter pw = response.getWriter();
		pw.println(x.toXML(sr));
		pw.close();
	}

}
