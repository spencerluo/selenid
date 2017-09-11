package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.createApp;
import static modules.AppModule.enterApp;
import static modules.GrammarModule.testGrammar;
import static modules.LoginModule.login;
import static modules.SlotModule.addSlotExt;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import modules.AppModule;
import modules.GrammarModule;
import utils.MyWebDriver;

public class NewTest1 extends BaseTest{
	MyWebDriver driver;
  @Test
  public void f() {
	  addSlotExt(driver, "names");
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
