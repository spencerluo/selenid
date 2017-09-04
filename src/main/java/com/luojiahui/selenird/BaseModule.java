package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import utils.MyWebDriver;

public class BaseModule {

	public static void assertSubMsg(MyWebDriver driver, String msg){
		driver.page("mainPage").getElement("subMsg").should(text(msg));
		driver.page("mainPage").click("subMsgClose");
	}
	
	public static Map<String, SelenideElement> getNewOldResult(MyWebDriver driver, String type){
		if(type.equals("new")){
			type = "newId";
			driver.click("新结果");
		}else{
			type = "oldId";
			driver.click("旧结果");
		}
		SelenideElement grammarName = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[1]"));
		SelenideElement global_modifier = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[2]"));
		SelenideElement slotName = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[3]"));
		SelenideElement slotType = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[4]"));
		SelenideElement slotValue = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[5]"));
		SelenideElement slot_modifier = $(By.xpath("//*[@id='"+type+"']/tbody/tr/td[6]"));
		Map<String, SelenideElement> map = new HashMap<String, SelenideElement>();
		map.put("grammarName", grammarName);
		map.put("global_modifier", global_modifier);
		map.put("slotName", slotName);
		map.put("slotType", slotType);
		map.put("slotValue", slotValue);
		map.put("slot_modifier", slot_modifier);
		return map;
	}
}
