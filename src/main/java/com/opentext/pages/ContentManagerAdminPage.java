package com.opentext.pages;

import org.openqa.selenium.By;

import com.opentext.base.Page;

public class ContentManagerAdminPage extends Page{
	

	public void clickOk() {
		driver.findElement(By.xpath("//*[@id=\"message-box-btn-ok\"]")).click();
	}

	public void gotoUsers() {
		driver.findElement(By.xpath("//*[@id=\"btn-users\"]/div/div/div")).click();
	}

	public void gotoSettings() {
		driver.findElement(By.xpath("//*[@id=\"btn-general-settings\"]/div/div")).click();
	}

}
