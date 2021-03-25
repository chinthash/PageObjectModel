package com.opentext.rough;

import com.opentext.pages.ContentManagerAdminPage;
import com.opentext.pages.LoginPage;

public class TestUserLogin {

	public static void main(String[] args) {
		{
			LoginPage login = new LoginPage();
			login.doLogin("admin", "admin");
			ContentManagerAdminPage page = new ContentManagerAdminPage();
			page.clickOk();
			page.gotoUsers();

		}

	}

}
