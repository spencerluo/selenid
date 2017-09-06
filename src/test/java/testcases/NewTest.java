package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.TemplateModule.addTemplate;
import static modules.TemplateModule.deleteTemplate;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modules.AppModule;
import modules.LoginModule;
import modules.TemplateModule;
import utils.MyWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.*;
public class NewTest extends BaseTest {
	MyWebDriver driver;
	@Test
	public void f() throws InterruptedException {
//		driver = MyWebDriver.getMyDriver();
//		LoginModule.login(driver, "spencer", "asdD1234");
//		
//		driver.getDriver().close();
//		driver.get("http://www.baidu.com");
		open("http://www.baidu.com");
//		getWebDriver().quit();;
		
			
		
	}
	
	@AfterMethod
	public void after(){
//			driver.getDriver().quit();
	}
}
