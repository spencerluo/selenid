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

	@Test(description="名称为中文开头，英文开头，下划线开头，英文加数字",dataProvider="dp1")
	public void testRule1(String name, String content) {
		addRule(driver, name, content);
		searchRule(driver, name, content);
	}

	@Test(description="名称为空，特殊字符，数字开头", dataProvider="dp2")
	public void testRule2(String name, String content, String msg) {
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		driver.page("rulePage").getElement("titleErrorMsg").should(text(msg));
	}
	
	@Test(description="内容为空")
	public void testRule3(){
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", "内容为空").sendKeys("content", "").click("submit");
		driver.page("rulePage").getElement("contentErrorMsg").should(text("内容不能为空"));
	}
	
	@Test(description="引用内容为空")
	public void testRule4(){
		addRule(driver, "引用内容为空", "<>", "no viable alternative at input '<>'");
	}
	
	@Test(description="引用已有的rule")
	public void testRule5(){
		addRule(driver, "已有的rule", "aa");
		addRule(driver, "引用已有的rule", "<已有的rule>");
		searchRule(driver, "引用已有的rule", "<已有的rule>");
	}
	
	@Test(description="引用slot_modifier")
	public void testRule6(){
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
	  public Object[][] dp1() {
	    return new Object[][] {
	      new Object[] { "规则", "我的规则" },
	      new Object[] { "rule", "我的规则" },
	      new Object[] { "_rule", "我的规则" },
	      new Object[] { "rule1", "我的规则" },
	    };
	  }
	  
	  @DataProvider
	  public Object[][] dp2() {
	    return new Object[][] {
	      new Object[] { "", "我的规则" ,"名称不能为空"},
	      new Object[] { "**", "我的规则", "名称是英文字母、汉字或下划线，不能以数字开头"},
	      new Object[] { "123", "我的规则", "名称是英文字母、汉字或下划线，不能以数字开头"},
	    };
	  }
}
