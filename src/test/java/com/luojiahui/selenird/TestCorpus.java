package com.luojiahui.selenird;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.luojiahui.selenird.AppModule.createApp;
import static com.luojiahui.selenird.AppModule.deleteApp;
import static com.luojiahui.selenird.AppModule.enterApp;
import static com.luojiahui.selenird.BaseModule.assertSubMsg;
import static com.luojiahui.selenird.BaseModule.getNewOldResult;
import static com.luojiahui.selenird.GrammarModule.addGrammar;
import static com.luojiahui.selenird.GrammarModule.changeGrammar;
import static com.luojiahui.selenird.LoginModule.login;
import static com.luojiahui.selenird.RuleModule.addRule;
import static com.luojiahui.selenird.SlotModule.addSlotExt;
import static com.luojiahui.selenird.TemplateModule.addTemplate;
import static com.luojiahui.selenird.TemplateModule.changeTemplate;
import static com.luojiahui.selenird.TemplateModule.deleteTemplate;
import static com.luojiahui.selenird.TemplateModule.searchTemplate;
import static com.luojiahui.selenird.CorpusModule.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestCorpus extends BaseTest{
	MyWebDriver driver;
  @Test
  public void testAddCorpus() {
	  addGrammar(driver, "吃水果", "吃(苹果|香蕉)", "吃苹果");
	  searchExistCorpus(driver, "吃苹果", "吃水果", "语义", "无");
	  driver.page("corpusPage").clear("searchBox").sendKeys("searchBox", "吃香蕉").click("searchSubmit").click("corpusSubmit").click("subMsgClose");
	  searchExistCorpus(driver, "吃香蕉", "吃水果", "语义", "无");
  }
  
  @Test(dependsOnMethods="testAddCorpus")
  public void testDeleteCorpus(){
	  deleteCorpus(driver, "吃苹果", "删除成功!");
	  deleteCorpus(driver, "吃香蕉", "after corpus delete or modify , grammar:<吃水果> can not match any corpus, it must have at least one corpus.");
  }
  
  @BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		createApp(driver, "testcorpus");
		enterApp(driver, "testcorpus");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		deleteApp(driver, "testcorpus");
		driver.getDriver().quit();
	}
}
