package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.LoginModule.login;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestLogin extends BaseTest{
	MyWebDriver driver;
  @Test
  public void testLogin() {
	  login(driver, "spencer", "asdD1234");
  }
  
  @Test(dependsOnMethods = "testLogin")
	public void testDocument() throws Exception {
		driver.get("http://portal.olavoice.com/open/nli/web/search_grammar");
		driver.page("mainPage").click("setting").click("document");
		switchTo().window(1);
		driver.getElement("文档中心").should(text("OLAMI 文档中心"));
	}
  
  @Test(dependsOnMethods = "testDocument")
	public void testLogout() throws Exception {
	  switchTo().window(0);
	  driver.page("mainPage").click("setting").click("logout");
		Thread.sleep(2000);
		driver.page("loginPage").getElement("submit").should(text("登录"));
	}
  
  @Test(dependsOnMethods="testLogout")
  public void testChangeLogin() throws Exception{
	  login(driver, "emmahao", "asdf123");
		driver.page("loginPage").getElement("user").should(text("emmahao"));
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(2);
		driver.page("mainPage").getElement("grammar").should(text("grammar"));
	}
  @BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.sleep(2000);
		driver.getDriver().quit();
	}
}
