package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.deleteApp;
import static modules.BaseModule.configureModules;
import static modules.LoginModule.login;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;
import utils.ReadExcel;

public class TestTest extends BaseTest{
	MyWebDriver driver;
	
  @Test(dataProvider="dp")
  public void testCorpus(String name, String question, String result) {
	  driver.page("loginPage").clear("question").sendKeys("question", question).click("testSubmit");
	  driver.getElement("answer").should(text(result));
  }
  
	@BeforeClass
	public void beforeClass() throws Exception {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		configureModules(driver, "testgrammar", "99");
		driver.page("loginPage").click("test");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.refresh();
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		deleteApp(driver, "testgrammar");
		driver.getDriver().quit();
	}
	
	@DataProvider
	public Object[][] dp() {
		return new ReadExcel("classes\\grammar.xlsx","corpus").getData();
	}
}
