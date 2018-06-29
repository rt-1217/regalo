package com.internousdev.regalo.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.regalo.dao.PasswordResetCompleteDAO;
import com.opensymphony.xwork2.ActionSupport;

public class PasswordResetCompleteAction extends ActionSupport implements SessionAware {

	private String userId;        //ユーザーID
	private String password;      //現在のパスワード
	private String password1;     //新しいパスワード
	private String password2;     //確認パスワード
	private String errorMessage;  //エラーメッセージ

	private Map<String,Object> session;

	private static final String ERRORPAGE = "errorPage";

	public String execute() throws SQLException {

		String result = ERROR;

		if(!(session.containsKey("loginFlg"))) {
			return ERRORPAGE;
		}

		PasswordResetCompleteDAO dao = new PasswordResetCompleteDAO();

		int check = 0;

		System.out.println("passwordResetCompleteAction:password"+password);

		check = dao.PasswordReset(password1,userId,password);

		if(check > 0) {
			result = SUCCESS;
		}

		return result;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
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

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

