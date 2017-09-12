package modules;

import static com.codeborne.selenide.Selenide.$;
import static modules.BaseModule.assertSubMsg;

import org.openqa.selenium.By;

import io.qameta.allure.Step;
import utils.MyWebDriver;

public class SlotModule{


	@Step("add internal slot 【{name}】")
	public static void addSlotInternal(MyWebDriver driver, String name, String mainType, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("internal").click("mainTypeButton").sendKeys("mainType", mainType).click("submit");
		assertSubMsg(driver, msg);
	}
	
	public static void addSlotInternal(MyWebDriver driver, String name){
		addSlotInternal(driver, name, "", "提交成功!");
	}
	

	public static void addSlotExt(MyWebDriver driver, String name, String mainType, String min, String max, String msg) {
		addSlotExt(driver, name, mainType, null, min, max, msg);
	}
	
	@Step("add ext slot 【{name}】")
	public static void addSlotExt(MyWebDriver driver, String name, String mainType, String assertType, String min, String max, String msg){
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("ext");
		if(mainType!=null){
			driver.click("mainTypeButton").sendKeys("mainType", mainType);
		}
		if(assertType!=null){
			$(By.xpath("//*[@value='"+assertType+"']"));
		}
		driver.clear("min").sendKeys("min", min).clear("max").sendKeys("max", max).click("submit");
		if(msg!=null){
			assertSubMsg(driver, msg);
		}
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
	
	@Step("add timepoint slot 【{name}】")
	public static void addSlotTimepoint(MyWebDriver driver, String name){
		addSlotDatetime(driver, name, "timepoint", "提交成功!");
	}
	
	@Step("add duration slot 【{name}】")
	public static void addSlotDuration(MyWebDriver driver, String name){
		addSlotDatetime(driver, name, "duration", "提交成功!");
	}
	
	
	public static void addSlotMaths(MyWebDriver driver, String name, String type , String msg){
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click(type).click("submit");
		assertSubMsg(driver, msg);
	}
	
	@Step("add number slot 【{name}】")
	public static void addSlotNumber(MyWebDriver driver, String name){
		addSlotMaths(driver, name, "number", "提交成功!");
	}
	
	@Step("add float slot 【{name}】")
	public static void addSlotFloat(MyWebDriver driver, String name){
		addSlotMaths(driver, name, "float", "提交成功!");
	}
	
	@Step("change slot 【{name}】")
	public static void changeSlotTye(MyWebDriver driver, String name, String type){
		driver.page("mainPage").click("slot");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[7]/div[1]/img")).click();
		driver.page("slotPage").click(type).click("submit");
	}
	
	public static void changeSlotTye(MyWebDriver driver, String name, String type, String msg){
		changeSlotTye(driver, name, type);
		assertSubMsg(driver, msg);
	}
	
	@Step("delete slot 【{name}】")
	public static void deleteSlot(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("slot");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[7]/div[2]/img")).click();
		driver.sleep(1000);
		driver.page("slotPage").click("submit");
		assertDeleteMsg(driver, msg);
	}
	
	@Step("assert deleteMsg 【{msg}】")
	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("slotPage").getElement("deleteMsg").shouldText(msg);
		driver.page("slotPage").click("deleteMsgClose");
	}
	
	@Step("search slot 【{name}】 and assert")
	public static void searchSlotAndAssert(MyWebDriver driver,String name, String type, String min ,String max){
		searchSLot(driver, name);
		assertSearchResult(driver, name, type, min, max);
	}
	
	@Step("search slot 【{name}】")
	public static void searchSLot(MyWebDriver driver,String name){
		driver.page("slotPage").sendKeys("searchBox", name).click("searchSubmit");
	}
	
	@Step("assert searchResult 【{name}】")
	public static void assertSearchResult(MyWebDriver driver,String name, String type, String min ,String max){
		driver.page("slotPage").getElement("searchResultName").shouldExactText(name);
		driver.page("slotPage").getElement("searchResultType").shouldExactText(type);
		driver.page("slotPage").getElement("searchResultMin").shouldExactText(min);
		driver.page("slotPage").getElement("searchResultMax").shouldExactText(max);
	}

}
