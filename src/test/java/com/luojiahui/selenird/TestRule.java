package com.luojiahui.selenird;

import org.testng.annotations.Test;

import utils.MyWebDriver;
import static com.luojiahui.selenird.AppModule.*;
import static com.luojiahui.selenird.LoginModule.*;
import static com.luojiahui.selenird.RuleModule.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class TestRule extends BaseTest{
	MyWebDriver driver;

	@Test(description="名称为中文开头，英文开头，下划线开头，英文加数字",dataProvider="dp")
	public void testRule1(String name, String content) {
		addRule(driver, name, content);
		driver.page("mainPage").getElement("subMsg").should(text("提交成功!"));
		driver.page("mainPage").click("subMsgClose");
		searchRule(driver, name);
		driver.page("rulePage").getElement("searchResultName").should(text(name));
		driver.page("rulePage").getElement("searchResultContent").should(text(content));
	}

	@BeforeMethod
	public void beforeMethod() {
		
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
		createApp(driver, "testrule");
		driver.page("mainPage").getElement("subMsg").should(text("模块创建成功!"));
		driver.page("mainPage").click("subMsgClose");
		enterApp(driver, "testrule");
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		deleteApp(driver, "testrule");
		driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
		driver.page("modelPage").click("closeDeleteMsg");
	}
	
	  @DataProvider
	  public Object[][] dp() {
	    return new Object[][] {
	      new Object[] { "规则", "我的规则" },
	      new Object[] { "rule", "我的规则" },
	      new Object[] { "_rule", "我的规则" },
	      new Object[] { "rule1", "我的规则" },
	    };
	  }
}
