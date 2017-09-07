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

import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;


import utils.MyWebDriver;
import utils.ReadExcel;
public class NewTest extends BaseTest {
	MyWebDriver driver;
	
	  @Test(dataProvider="dp")
	  public void testCorpus(String question) {
		  driver.page("loginPage").clear("question").sendKeys("question", question).click("testSubmit");
		  driver.getElement("answer").should(text(question));
	  }
	  
		@BeforeClass
		public void beforeClass() throws Exception {
			Configuration.timeout=3000;
			driver = MyWebDriver.getMyDriver();
			login(driver, "spencer", "asdD1234");
//			configureModules(driver, "hotel", "99");
			driver.page("loginPage").click("test");
		}

		@AfterClass(alwaysRun = true)
		public void afterClass() {
				driver.getDriver().quit();
		}
		
		@DataProvider
		public Object[][] dp() {
			return new ReadExcel("classes\\grammar.xlsx","test").getData();
		}
	}
