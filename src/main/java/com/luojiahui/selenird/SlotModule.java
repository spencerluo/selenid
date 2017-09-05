package com.luojiahui.selenird;

import utils.MyWebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SlotModule extends BaseModule {

	public static void addSlotString(MyWebDriver driver, String name, String type, String mainType, String subType,
			String min, String max, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click(type).sendKeys("mainType", mainType)
				.sendKeys("subType", subType);
		driver.clear("min").sendKeys("min", min).clear("max").sendKeys("max", max).click("submit");
		assertSubMsg(driver, msg);
	}

	public static void addSlotInternal(MyWebDriver driver, String name, String mainType, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("internal").sendKeys("mainType", mainType).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotInternal(MyWebDriver driver, String name){
		addSlotInternal(driver, name, "", "提交成功!");
	}
	

	public static void addSlotExt(MyWebDriver driver, String name, String mainType, String min, String max, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("ext").sendKeys("mainType", mainType).click("submit");
		driver.clear("min").sendKeys("min", min).clear("max").sendKeys("max", max).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotExt(MyWebDriver driver, String name) {
		addSlotExt(driver, name, "","1", "20", "提交成功!");
	}
	

	public static void addSlotDatetime(MyWebDriver driver, String name, String subType,
			String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("datetime").click(subType);
		driver.click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotTimepoint(MyWebDriver driver, String name){
		addSlotDatetime(driver, name, "timepoint", "提交成功!");
	}
	
	public static void addSlotDuration(MyWebDriver driver, String name){
		addSlotDatetime(driver, name, "duration", "提交成功!");
	}
	
	public static void addSlotMaths(MyWebDriver driver, String name, String type , String msg){
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click(type).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotNumber(MyWebDriver driver, String name){
		addSlotMaths(driver, name, "number", "提交成功!");
	}
	
	public static void addSlotFloat(MyWebDriver driver, String name){
		addSlotMaths(driver, name, "float", "提交成功!");
	}

}
