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
import utils.MyWebDriver;

public class NewTest extends BaseTest {
	MyWebDriver driver;
	@Test
	public void f() throws InterruptedException {
		driver = MyWebDriver.getMyDriver();
		LoginModule.login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		AppModule.createApp(driver, "app5");
		AppModule.enterApp(driver, "app5");
		
//		TemplateModule.addTemplate(driver, "da", "[=s=]sd");
//		TemplateModule.changeTemplate(driver, "da", "[=s=]dfs", "提交成功!");
//		TemplateModule.deleteTemplate(driver, "da", "删除成功!");
		
		addTemplate(driver, "temp16", "[=短句=]制作$(短句)");
		deleteTemplate(driver, "temp16", "删除成功!");
		driver.page("templatePage").sendKeys("searchBox", "temp16").click("searchSubmit");
		Assert.assertTrue(!driver.getSource().contains("[=短句=]制作$(短句)"));
		
			
		
	}
	
	@AfterMethod
	public void after(){
		AppModule.deleteApp(driver, "app5");
		driver.getDriver().quit();
	}
}
