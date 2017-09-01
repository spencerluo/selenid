package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import utils.MyWebDriver;

public class BaseModule {

	public static void assertSubMsg(MyWebDriver driver, String msg){
		driver.page("mainPage").getElement("subMsg").should(text(msg));
		driver.page("mainPage").click("subMsgClose");
	}
	
	public static Map<String, String> getNewOldResult(MyWebDriver driver, String type){
		if(type.equals("new")){
			type = "newId";
			driver.click("新结果");
		}else{
			type = "oldId";
			driver.click("旧结果");
		}
		String grammarName = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[1]")).text();
		String global_modifier = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[2]")).text();
		String slotName = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[3]")).text();
		String slotType = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[4]")).text();
		String slotValue = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[5]")).text();
		String slot_modifier = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[6]")).text();
		Map<String, String> map = new HashMap<String, String>();
		map.put("grammarName", grammarName);
		map.put("global_modifier", global_modifier);
		map.put("slotName", slotName);
		map.put("slotType", slotType);
		map.put("slotValue", slotValue);
		map.put("slot_modifier", slot_modifier);
		return map;
	}
}
