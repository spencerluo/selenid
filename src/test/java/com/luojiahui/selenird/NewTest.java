package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
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
		
		addGrammar(driver, "grammar48", "说故事", "说故事" );
		changeGrammar(driver, "grammar48", "说些故事", "说些故事");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("说故事"));
		driver.click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text(""));
		getNewOldResult(driver, "old").get("grammarName").should(text("grammar48"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "提交成功!");
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
