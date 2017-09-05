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
import static com.luojiahui.selenird.SearchModule.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestSearch extends BaseTest{
	MyWebDriver driver;
  @Test
  public void searchGrammar() {
	  addRule(driver, "shuiguo", "苹果|香蕉");
	  addGrammar(driver,"grammar1", "吃<shuiguo>", "吃苹果","好的","提交成功!");
	  driver.sleep(5000);
	  allSearch(driver, "grammar1", "grammar");
	  assertGrammarResult(driver, "grammar1", "吃<shuiguo>", "好的");
  }
  @Test
  public void searchRule() {
	  addRule(driver, "rule1", "苹果|香蕉");
	  driver.sleep(5000);
	  allSearch(driver, "rule1", "rule");
	  assertRuleResult(driver, "rule1", "苹果|香蕉");
  }
  @Test
  public void searchTemplate() {
	  addTemplate(driver, "temp1", "[=s=]吃$(s)");
	  driver.sleep(5000);
	  allSearch(driver, "temp1", "template");
	  assertTemplateResult(driver, "temp1", "[=s=]吃$(s)");
  }
  @Test
  public void searchSlot() {
	  addSlotExt(driver, "slot1");
	  driver.sleep(5000);
	  allSearch(driver, "slot1", "slot");
	  assertSlotResult(driver, "slot1", "ext", "1", "20");
  }
  
  @Test
  public void searchCorpus() {
	  addSlotExt(driver, "object");
	  addGrammar(driver, "testcorpus", "啊*<{object=cc}>", "啊啊啊","好的","提交成功!");
	  driver.sleep(5000);
	  allSearch(driver, "啊啊啊", "corpus");
	  assertCorpusResult(driver, "啊啊啊", "testcorpus", "好的","object：cc");
  }
  
  @AfterMethod
  
  public void afterMethod(){
	  driver.refresh();
  }
	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		createApp(driver, "testsearch");
		enterApp(driver, "testsearch");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		switchTo().window(1);
		deleteApp(driver, "testsearch");
		driver.getDriver().quit();
	}
}
