package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.luojiahui.selenird.AppModule.*;
import static com.luojiahui.selenird.LoginModule.login;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestApp extends BaseTest {
	MyWebDriver driver;

	@Test(dataProvider = "dp1", description = "名称为中文、英文、下划线、英文＋数字")
	public void testApp1(String appName) {
		createApp(driver, appName);
		deleteApp(driver, appName);
	}

	@Test(description = "名称重复")
	public void testApp2() {
		try {
			createApp(driver, "test1");
			createApp(driver, "test1", "模块创建失败:test1 already exist");
		} finally {
			refresh();
			deleteApp(driver, "test1");
		}
	}

	@Test(description = "名称大小写")
	public void testApp3() {
		try {
			createApp(driver, "test3");
			createApp(driver, "TEST3", "模块创建失败:test3 already exist");
		} finally {
			refresh();
			deleteApp(driver, "test3");
		}
	}
	
	@Test(description = "导入")
	public void testApp4() {
		try {
			importApp(driver, "joke");
		} finally {
			refresh();
			deleteApp(driver, "joke");
		}
	}

	@Test(description = "名称是数字、特殊字符、为空", dataProvider = "dp2")
	public void testApp4(String name, String msg) {
		driver.page("mainPage").click("changeApp");
		driver.page("modelPage").click("add").sendKeys("addAppName", name).click("submit");
		driver.getElement("nameErrorMsg").should(text(msg));
	}

	@AfterMethod
	public void afterMethod() {
		refresh();
	}

	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver);
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.getDriver().quit();
	}

	@DataProvider
	public Object[][] dp1() {
		return new Object[][] { 
			new Object[] { "应用" }, 
			new Object[] { "myapp" }, 
			new Object[] { "_myapp" },
			new Object[] { "myapp1" }, };
	}

	@DataProvider
	public Object[][] dp2() {
		return new Object[][] { 
			new Object[] { "123", "名称是英文字母、汉字或下划线" }, 
			new Object[] { "**" ,"名称是英文字母、汉字或下划线"}, 
			new Object[] { "" , "名称不能为空"},};
	}
}
