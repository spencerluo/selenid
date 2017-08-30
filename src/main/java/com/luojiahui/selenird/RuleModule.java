package com.luojiahui.selenird;

import utils.MyWebDriver;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

public class RuleModule {

	public static void addRule(MyWebDriver driver, String name, String content){
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
	}
	
	public static void changeRule(MyWebDriver driver, String name, String content){
		driver.page("mainPage").click("rule");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[1]/img")).click();
		driver.page("rulePage").clear("content").sendKeys("content", content).click("submit");
	}
	
	public static void changeChange(MyWebDriver driver, String name, String content){
		changeRule(driver, name, content);
		driver.page("rulePage").click("changeSubmit");
	}
	
	public static void deleteRule(MyWebDriver driver, String name){
		driver.page("mainPage").click("rule");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[3]/div[2]/img")).click();
		driver.page("rulePage").click("submit");
	}
	
	public static void searchRule(MyWebDriver driver, String name){
		driver.page("rulePage").sendKeys("searchBox", name).click("searchSubmit");
	}
}
