package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.luojiahui.selenird.BaseModule.assertSubMsg;
import static com.luojiahui.selenird.GrammarModule.testGrammar;

import org.testng.annotations.Test;

import utils.MyWebDriver;

public class NewTest extends BaseTest {
	
	@Test
	public void f() throws InterruptedException {
		MyWebDriver driver = MyWebDriver.getMyDriver();
		LoginModule.login(driver);
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		AppModule.createApp(driver, "app5");
		driver.page("mainPage").getElement("subMsg").should(text("模块创建成功!"));
		driver.page("mainPage").click("subMsgClose");
		AppModule.enterApp(driver, "app5");
		
//		TemplateModule.addTemplate(driver, "da", "[=s=]sd");
//		TemplateModule.changeTemplate(driver, "da", "[=s=]dfs", "提交成功!");
//		TemplateModule.deleteTemplate(driver, "da", "删除成功!");
		
		SlotModule.addSlotExt(driver, "slot1");
		refresh();
		SlotModule.addSlotInternal(driver, "slot2");
		refresh();
		SlotModule.addSlotDuration(driver, "slot3");
		refresh();
		SlotModule.addSlotTimepoint(driver, "slot4");
		refresh();
		SlotModule.addSlotNumber(driver, "slot5");
		refresh();
		SlotModule.addSlotFloat(driver, "slot6");
		refresh();
		
		
			Thread.sleep(2000);
			AppModule.deleteApp(driver, "app5");
			driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
			driver.page("modelPage").click("closeDeleteMsg");
			Thread.sleep(2000);
			driver.getDriver().quit();
			Thread.sleep(2000);
		
	}
}
