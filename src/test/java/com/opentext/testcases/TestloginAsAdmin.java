package com.opentext.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.opentext.pages.LoginPage;
import com.opentext.utilities.Utilities;

public class TestloginAsAdmin extends BaseTest {
	//Test for Login As Admin
	@Test(dataProviderClass = Utilities.class, dataProvider = "dp")
	public void testloginAsAdmin(Hashtable<String, String> data) {
		LoginPage page = new LoginPage();
		page.doLogin(data.get("username"), data.get("password"));
		Assert.fail("Manually failing testcase");
	}
}
