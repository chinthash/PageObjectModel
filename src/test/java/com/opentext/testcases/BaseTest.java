package com.opentext.testcases;

import org.testng.annotations.AfterSuite;

import com.opentext.base.Page;

public class BaseTest {
	@AfterSuite
	public void tearDown() {
		Page.quit();
	}
}
