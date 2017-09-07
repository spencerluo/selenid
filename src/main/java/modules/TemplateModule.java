package modules;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import static modules.BaseModule.assertSubMsg;
import utils.MyWebDriver;

public class TemplateModule{

	public static void addTemplate(MyWebDriver driver, String name, String content, String msg){
		driver.page("mainPage").click("template");
		driver.page("templatePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addTemplate(MyWebDriver driver, String name, String content){
		addTemplate(driver, name, content, "提交成功!");
	}
	
	public static void changeTemplate(MyWebDriver driver, String name, String content, String msg){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("templatePage").clear("content").sendKeys("content", content).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void changeTemplate(MyWebDriver driver, String name, String content){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("templatePage").clear("content").sendKeys("content", content).click("submit");
	}
	
	public static void deleteTemplate(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[2]/img")).click();
		driver.sleep(1000);
		driver.page("templatePage").click("submit");
		assertDeleteMsg(driver, msg);
	}
	
	public static void searchTemplate(MyWebDriver driver, String name, String content){
		driver.page("templatePage").sendKeys("searchBox", name).click("searchSubmit");
		assertSearchResult(driver, name, content);
	}
	
	public static void assertSearchResult(MyWebDriver driver, String name, String content){
		driver.page("templatePage").getElement("searchResultName").should(text(name));
		driver.page("templatePage").getElement("searchResultContent").should(text(content));
	}
	
	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("templatePage").getElement("deleteMsg").should(text(msg));
		driver.page("templatePage").click("deleteMsgClose");
	}
	
	
}
