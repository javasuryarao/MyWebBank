package com.java.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.java.common.Constants;

public class SessionHelper {
	private HttpSession session;
	private HttpServletRequest request;
	
	public  SessionHelper(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		this.request = request;
		this.session = session;
	}
	
	public String getString(String key)
	{
		if(StringHelper.isEmpty(key))
		{
			return Constants.EMPTY_STRING;
		}
		Object value =  this.session.getAttribute(key);
		if(value == null)
		{
			return Constants.EMPTY_STRING;
		}
		return value.toString();
	}
	
	public int getInt(String key)
	{
		if(StringHelper.isEmpty(key))
		{
			return 0;
		}
		Object value =  this.session.getAttribute(key);
		if(value == null)
		{
			return 0;
		}
		return Integer.parseInt(value.toString());
	}

	public void setAttribute(String string, int i) {
		this.session.setAttribute(string, i);
		
	}

	public void setAttribute(String key, String StringValue) {
		this.session.setAttribute(key, StringValue);
		
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	
}
