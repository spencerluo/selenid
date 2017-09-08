package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.createApp;
import static modules.AppModule.enterApp;
import static modules.GrammarModule.testGrammar;
import static modules.LoginModule.login;
import static modules.SlotModule.addSlotExt;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import modules.AppModule;
import modules.GrammarModule;
import utils.MyWebDriver;

public class NewTest1 extends BaseTest{
	MyWebDriver driver;
  @Test
  public void f() {
	  addSlotExt(driver, "data");
		testGrammar(driver, "grammar57", "测试<data><{data@=data}><{@=goods}>", "测试数据", "好的");
		GrammarModule.getCorpusTestResult("name").should(text("grammar57"));
		GrammarModule.getCorpusTestResult("content").should(text("测试<data><{data@=data}><{@=goods}>"));
		GrammarModule.getCorpusTestResult("answer").should(text("好的"));
		GrammarModule.getCorpusTestResult("global_modifier").should(text("goods"));
		GrammarModule.getCorpusTestResult("slotName").should(text("data"));
		GrammarModule.getCorpusTestResult("slotType").should(text("ext"));
		GrammarModule.getCorpusTestResult("slotValue").should(text("数据"));
		GrammarModule.getCorpusTestResult("slot_modifiers").should(text("data"));
  }
  
	@AfterMethod()
	public void afterMethod() {
	}
	
	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		driver.sleep(2000);
		createApp(driver, "testgrammar");
		enterApp(driver, "testgrammar");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
//			release(driver);
			driver.refresh();
			AppModule.deleteApp(driver, "testgrammar");
		} finally {
			driver.sleep(2000);
			driver.getDriver().quit();
		}
	}
}
