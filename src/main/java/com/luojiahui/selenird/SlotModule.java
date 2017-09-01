package com.luojiahui.selenird;

import utils.MyWebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SlotModule extends BaseModule{
	
	public static void addSlot(MyWebDriver driver, String name,  String type, String mainType, String subType, String min, String max, String msg){
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click(type).sendKeys("mainType", mainType).sendKeys("subType", subType);
		driver.clear("min").sendKeys("min", min).clear("max").sendKeys("max", max).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotInternal(MyWebDriver driver, String name){
		addSlot(driver, name, "internal", "", "", "1", "20", "提交成功!");
	}
	
	public static void addSlotExt(MyWebDriver driver, String name){
		addSlot(driver, name, "ext", "", "", "1", "20", "提交成功!");
	} 
	

}
