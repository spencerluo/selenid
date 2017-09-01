package com.luojiahui.selenird;
import utils.MyWebDriver;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;;
public class AppModule {

	public static void createApp(MyWebDriver driver, String appName){
		driver.page("mainPage").click("changeApp");
		driver.page("modelPage").click("add").sendKeys("addAppName", appName).click("submit");
		
	}
	
	public static void deleteApp(MyWebDriver driver, String appName){
		driver.page("mainPage").click("changeApp");
		$(By.xpath("//*[@value='" + appName + "']/following-sibling::*/a[4]")).click();
		driver.sleep(1000);
		driver.page("modelPage").click("delete").click("deleteTwice");
	}
	
	public static void importApp(MyWebDriver driver, String appName){
		driver.page("modelPage").click("import");
		$(By.xpath("//*[@value='" + appName + "']")).click();
		driver.page("modelPage").click("导入");
	}
	
	public static void enterApp(MyWebDriver driver, String appName){
		$(By.xpath("//*[@value='" + appName + "']/following-sibling::*/a[1]")).click();
	}
}
