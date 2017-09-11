package modules;

import utils.MyWebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static modules.BaseModule.assertSubMsg;
import org.openqa.selenium.By;

import io.qameta.allure.Step;

public class RuleModule{

	@Step("add rule 【{name}】")
	public static void addRule(MyWebDriver driver, String name, String content, String msg){
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addRule(MyWebDriver driver, String name, String content){
		addRule(driver, name, content, "提交成功!");
	}
	
	public static void changeRule(MyWebDriver driver, String name, String content, String msg){
		changeRule(driver, name, content);
		assertSubMsg(driver, msg);
	}
	
	@Step("change rule 【{name}】")
	public static void changeRule(MyWebDriver driver, String name, String content){
		driver.page("mainPage").click("rule");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("rulePage").clear("content").sendKeys("content", content).click("submit");
	}
	
	@Step("delete rule 【{name}】")
	public static void deleteRule(MyWebDriver driver, String name){
		driver.page("mainPage").click("rule");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[2]/img")).click();
		driver.sleep(1000);
		driver.page("rulePage").click("submit");
	}
	
	@Step("delete rule 【{name}】")
	public static void deleteRule(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("rule");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[2]/img")).click();
		driver.sleep(1000);
		driver.page("rulePage").click("submit");
		assertDeleteMsg(driver, msg);
	}
	
	@Step("search rule 【{name}】 and assert")
	public static void searchRule(MyWebDriver driver, String name, String content){
		driver.page("rulePage").sendKeys("searchBox", name).click("searchSubmit");
		assertSearchResult(driver, name, content);
	}
	
	@Step("assert searchResult 【{name}】")
	public static void assertSearchResult(MyWebDriver driver, String name, String content){
		driver.page("rulePage").getElement("searchResultName").should(exactText(name));
		driver.page("rulePage").getElement("searchResultContent").should(exactText(content));
	}
	
	@Step("assert deleteMsg 【{msg}】")
	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("rulePage").getElement("deleteMsg").should(text(msg));
		driver.page("rulePage").click("deleteMsgClose");
	}
}
