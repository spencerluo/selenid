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

public class NewTest extends BaseTest {
	MyWebDriver driver;
	@Test
	public void f() throws InterruptedException {
		driver = MyWebDriver.getMyDriver();
		LoginModule.login(driver, "spencer", "asdD1234");
		
		TemplateModule.addTemplate(driver, "da", "[=s=]sd");
//		TemplateModule.changeTemplate(driver, "da", "[=s=]dfs", "提交成功!");
//		TemplateModule.deleteTemplate(driver, "da", "删除成功!");
		
			
		
	}
	
	@AfterMethod
	public void after(){
			driver.sleep(2000);
//			driver.getDriver().quit();
	}
}
