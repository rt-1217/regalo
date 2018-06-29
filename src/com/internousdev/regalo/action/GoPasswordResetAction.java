package com.internousdev.regalo.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class GoPasswordResetAction extends ActionSupport implements SessionAware {

	public Map<String,Object> session;
	private static final String ERRORPAGE = "errorPage";
	private String errorMessage=null;

	public String execute() {
		String result = SUCCESS;

		if(!(session.containsKey("loginFlg"))) {
			return ERRORPAGE;
		}

		return result;
	}

	public Map<String,Object> getSession() {
		return session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static String getErrorpage() {
		return ERRORPAGE;
	}


}
