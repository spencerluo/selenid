package modules;

import static com.codeborne.selenide.Selenide.$;
import static modules.BaseModule.assertSubMsg;

import org.openqa.selenium.By;

import io.qameta.allure.Step;
import utils.MyWebDriver;

public class TemplateModule{

	@Step("add template 【{name}】")
	public static void addTemplate(MyWebDriver driver, String name, String content, String msg){
		driver.page("mainPage").click("template");
		driver.page("templatePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addTemplate(MyWebDriver driver, String name, String content){
		addTemplate(driver, name, content, "提交成功!");
	}
	
	@Step("change template 【{name}】")
	public static void changeTemplate(MyWebDriver driver, String name, String content, String msg){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("templatePage").clear("content").sendKeys("content", content).click("submit");
		assertSubMsg(driver, msg);
	}
	
	@Step("change template 【{name}】")
	public static void changeTemplate(MyWebDriver driver, String name, String content){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("templatePage").clear("content").sendKeys("content", content).click("submit");
	}
	
	@Step("delete template 【{name}】")
	public static void deleteTemplate(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("template");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[2]/img")).click();
		driver.sleep(1000);
		driver.page("templatePage").click("submit");
		assertDeleteMsg(driver, msg);
	}
	
	@Step("search template 【{name}】")
	public static void searchTemplate(MyWebDriver driver, String name, String content){
		driver.page("templatePage").sendKeys("searchBox", name).click("searchSubmit");
		assertSearchResult(driver, name, content);
	}
	
	@Step("assert searchResult 【{name}】")
	public static void assertSearchResult(MyWebDriver driver, String name, String content){
		driver.page("templatePage").getElement("searchResultName").shouldExactText(name);
		driver.page("templatePage").getElement("searchResultContent").shouldExactText(content);
	}
	
	@Step("assert deleteMsg 【{msg}】")
	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("templatePage").getElement("deleteMsg").shouldText(msg);
		driver.page("templatePage").click("deleteMsgClose");
	}
	
	
}
