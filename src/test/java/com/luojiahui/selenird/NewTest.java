package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;

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
		Thread.sleep(2000);
		AppModule.deleteApp(driver, "app5");
		driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
		driver.page("modelPage").click("closeDeleteNotice");
		Thread.sleep(2000);
		
	}
}
