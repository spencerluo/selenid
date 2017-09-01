package com.luojiahui.selenird;

import org.testng.annotations.Test;

import utils.MyWebDriver;
import static com.luojiahui.selenird.AppModule.*;
import static com.luojiahui.selenird.LoginModule.*;
import static com.luojiahui.selenird.RuleModule.*;
import static com.luojiahui.selenird.SlotModule.*;
import static com.luojiahui.selenird.TemplateModule.*;
import static com.luojiahui.selenird.GrammarModule.*;
import org.testng.annotations.DataProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import utils.MyWebDriver;

public class TestGrammar extends BaseTest{
	MyWebDriver driver;
  @Test(dataProvider = "dp1", description="名称为中文开头，英文开头，下划线开头，英文加数字")
  public void testGrammar1(String name, String content, String corpus) {
	  addGrammar(driver, name, content, corpus);
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
		createApp(driver, "testgrammar");
		driver.page("mainPage").getElement("subMsg").should(text("模块创建成功!"));
		driver.page("mainPage").click("subMsgClose");
		enterApp(driver, "testgrammar");
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		deleteApp(driver, "testgrammar");
		driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
		driver.page("modelPage").click("closeDeleteMsg");
	}
	
	@DataProvider
	public Object[][] dp1() {
		return new Object[][] { 
			new Object[] { "语法", "语法", "语法" }, 
			new Object[] { "grammar", "语法", "语法" }, 
			new Object[] { "_grammar", "语法", "语法" }, 
			new Object[] { "grammar1", "语法", "语法" }, 
		};
	}
	
	@DataProvider
	public Object[][] dp2() {
		return new Object[][] { 
			new Object[] { "", "[=短句=]模板$(短句)", "名称不能为空" }, 
			new Object[] { "**", "[=短句=]模板$(短句)" ,"名称是英文字母、汉字或下划线，不能以数字开头"}, 
			new Object[] { "123", "[=短句=]模板$(短句)" ,"名称是英文字母、汉字或下划线，不能以数字开头"}, 
		};
	}

}
