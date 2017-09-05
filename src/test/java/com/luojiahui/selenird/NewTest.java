package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.luojiahui.selenird.AppModule.deleteApp;
import static com.luojiahui.selenird.AppModule.enterApp;
import static com.luojiahui.selenird.AppModule.importApp;
import static com.luojiahui.selenird.BaseModule.assertSubMsg;
import static com.luojiahui.selenird.BaseModule.getNewOldResult;
import static com.luojiahui.selenird.GrammarModule.addGrammar;
import static com.luojiahui.selenird.GrammarModule.changeGrammar;
import static com.luojiahui.selenird.GrammarModule.testGrammar;

import org.testng.annotations.Test;

import utils.MyWebDriver;

public class NewTest extends BaseTest {
	
	@Test
	public void f() throws InterruptedException {
		MyWebDriver driver = MyWebDriver.getMyDriver();
		LoginModule.login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		AppModule.createApp(driver, "app5");
		driver.page("mainPage").getElement("subMsg").should(text("模块创建成功!"));
		driver.page("mainPage").click("subMsgClose");
		AppModule.enterApp(driver, "app5");
		
//		TemplateModule.addTemplate(driver, "da", "[=s=]sd");
//		TemplateModule.changeTemplate(driver, "da", "[=s=]dfs", "提交成功!");
//		TemplateModule.deleteTemplate(driver, "da", "删除成功!");
		
		try {
			importApp(driver, "joke");
			driver.page("mainPage").getElement("subMsg").waitUntil(text("模块导入成功!"), 20000);
			driver.click("subMsgClose");
			enterApp(driver, "joke");
			driver.page("mainPage").click("grammar");
			org.testng.Assert.assertTrue(driver.getSource().contains("joke"));
		} finally {
			refresh();
			deleteApp(driver, "joke");
		}
		
		
			Thread.sleep(2000);
			AppModule.deleteApp(driver, "app5");
			driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
			driver.page("modelPage").click("closeDeleteMsg");
			Thread.sleep(2000);
			driver.getDriver().quit();
			Thread.sleep(2000);
		
	}
}
