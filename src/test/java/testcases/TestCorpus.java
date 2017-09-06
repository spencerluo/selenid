package testcases;


import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.createApp;
import static modules.AppModule.deleteApp;
import static modules.AppModule.enterApp;
import static modules.CorpusModule.deleteCorpus;
import static modules.CorpusModule.searchExistCorpus;
import static modules.GrammarModule.addGrammar;
import static modules.LoginModule.login;

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
		driver.sleep(2000);
		createApp(driver, "testcorpus");
		enterApp(driver, "testcorpus");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
			deleteApp(driver, "testcorpus");
		} finally {
			driver.sleep(2000);
			driver.getDriver().quit();
		}
	}
}
