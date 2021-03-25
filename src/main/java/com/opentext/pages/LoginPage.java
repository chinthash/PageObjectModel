package com.opentext.pages;

import com.opentext.base.Page;

public class LoginPage extends Page {

	public void doLogin(String username, String password) {
		type("login.username_ID", username);
		type("login.password_ID", password);
		click("login.submit_ID");
	}

}
