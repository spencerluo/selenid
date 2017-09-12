package modules;

import utils.MyWebDriver;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import io.qameta.allure.Step;;

public class AppModule {

	@Step("create app 【{appName}】")
	public static void createApp(MyWebDriver driver, String appName, String msg) {
		driver.page("mainPage").click("changeApp");
		driver.sleep(2000);
		driver.page("modelPage").click("add");
		driver.sendKeys("addAppName", appName);
		driver.click("submit");
		driver.page("mainPage").getElement("subMsg").shouldText(msg);
		driver.page("mainPage").click("subMsgClose");
	}

	public static void createApp(MyWebDriver driver, String appName) {
		createApp(driver, appName, "模块创建成功!");
	}

	@Step("delete app 【{appName}】")
	public static void deleteApp(MyWebDriver driver, String appName, String msg) {
		driver.page("mainPage").click("changeApp");
		driver.sleep(2000);
		$(By.xpath("//*[@value='" + appName + "']/following-sibling::*/a[4]")).click();
		driver.sleep(1000);
		driver.page("modelPage").click("delete");
		driver.sleep(1000);
		driver.click("deleteTwice");
		driver.page("modelPage").getElement("deleteMsg").shouldText(msg);
		driver.page("modelPage").click("closeDeleteMsg");
	}

	public static void deleteApp(MyWebDriver driver, String appName) {
		deleteApp(driver, appName, "删除成功!");
	}

	@Step("import app 【{appName}】")
	public static void importApp(MyWebDriver driver, String appName) {
		driver.page("mainPage").click("changeApp");
		driver.sleep(1000);
		driver.page("modelPage").click("import");
		$(By.xpath("//*[@value='" + appName + "']")).scrollTo().click();
		driver.page("modelPage").click("导入");
	}

	@Step("enter app 【{appName}】")
	public static void enterApp(MyWebDriver driver, String appName) {
		$(By.xpath("//*[@value='" + appName + "']/following-sibling::*/a[1]")).click();
	}
}
