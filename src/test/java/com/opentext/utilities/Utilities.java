package com.opentext.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.opentext.base.Page;

public class Utilities extends Page {
	public static String screenshotName;

	public static void captureScreenShot() throws IOException {
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}

	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rowcount = excel.getRowCount(sheetName);
		int colcount = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rowcount - 1][1];
		Hashtable<String, String> table = null;
		for (int rowNum = 2; rowNum <= rowcount; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < colcount; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}

		return data;
	}

}
