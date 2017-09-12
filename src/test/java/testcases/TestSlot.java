package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.createApp;
import static modules.AppModule.deleteApp;
import static modules.AppModule.enterApp;
import static modules.BaseModule.assertSubMsg;
import static modules.BaseModule.getNewOldResult;
import static modules.GrammarModule.addGrammar;
import static modules.LoginModule.login;
import static modules.SlotModule.addSlotDuration;
import static modules.SlotModule.addSlotExt;
import static modules.SlotModule.addSlotFloat;
import static modules.SlotModule.addSlotInternal;
import static modules.SlotModule.addSlotNumber;
import static modules.SlotModule.addSlotTimepoint;
import static modules.SlotModule.changeSlotTye;
import static modules.SlotModule.deleteSlot;
import static modules.SlotModule.searchSlotAndAssert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestSlot extends BaseTest {
	MyWebDriver driver;

	@Test(description = "名称为中文开头，英文开头，下划线开头，英文加数字", dataProvider = "dp1")
	public void testSlot1(String name) {
		addSlotExt(driver, name);
		searchSlotAndAssert(driver, name, "ext", "1", "20");
	}

	@Test(description = "名称为空，特殊字符，数字开头", dataProvider = "dp2")
	public void testSlot2(String name, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("number").click("submit");
		driver.getElement("errorMsg").shouldText(msg);
	}

	@Test(description = "类型datetime+timepoint")
	public void testSlot3() {
		addSlotTimepoint(driver, "slot3");
		searchSlotAndAssert(driver, "slot3", "datetime", "无", "无");
	}

	@Test(description = "类型datetime+duration")
	public void testSlot4() {
		addSlotDuration(driver, "slot4");
		searchSlotAndAssert(driver, "slot4", "datetime", "无", "无");
	}

	@Test(description = "类型number")
	public void testSlot5() {
		addSlotNumber(driver, "slot5");
		searchSlotAndAssert(driver, "slot5", "number", "无", "无");
	}

	@Test(description = "类型float")
	public void testSlot6() {
		addSlotFloat(driver, "slot6");
		searchSlotAndAssert(driver, "slot6", "float", "无", "无");
	}

	@Test(description = "类型Internal")
	public void testSlot7() {
		addSlotInternal(driver, "slot7");
		searchSlotAndAssert(driver, "slot7", "internal", "无", "无");
	}
	
	@Test(description = "测试ext类型的长度",dataProvider="dp3")
	public void testSlot8(String name, String min, String max, String msg) {
		driver.page("mainPage").click("slot");
		driver.page("slotPage").click("add").sendKeys("name", name).click("ext");
		driver.clear("min").sendKeys("min", min).clear("max").sendKeys("max", max).click("submit");
		driver.getElement("errorMsg").shouldText(msg);
	}
	
	@Test(description = "grammar不匹配ext类型的长度")
	public void testSlot9() {
		addSlotExt(driver, "slot9", "", "3", "4", "提交成功!");
		addGrammar(driver, "g1", "w<slot9>", "wasdef", "null", "new grammar:<g1> can not match any corpus");
		driver.refresh();
		addGrammar(driver, "g2", "w<slot9>", "w", "null", "new grammar:<g2> can not match any corpus");
	}
	
	@Test(description = "修改slot类型,不影响corpus")
	public void testSlot10() {
		addSlotExt(driver, "slot10", "", "1", "20", "提交成功!");
		changeSlotTye(driver, "slot10", "number","提交成功!");
		changeSlotTye(driver, "slot10", "float","提交成功!");
		changeSlotTye(driver, "slot10", "internal","提交成功!");
		changeSlotTye(driver, "slot10", "datetime","提交成功!");
	}
	
	@Test(description = "修改slot类型,影响corpus")
	public void testSlot11() {
		addSlotExt(driver, "slot11", "", "1", "20", "提交成功!");
		addGrammar(driver, "g11", "看<slot11>", "看电影");
		changeSlotTye(driver, "slot11", "number");
		driver.page("mainPage").getElement("subMsg").shouldText("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除");
		driver.getElement("语料").shouldText("看电影");
		driver.click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text(""));
		getNewOldResult(driver, "old").get("grammarName").should(text("g11"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "after corpus delete or modify , grammar:<g11> can not match any corpus, it must have at least one corpus.");
	}
	
	@Test(description="正常删除")
	public void testSlot12(){
		addSlotNumber(driver, "slot12");
		deleteSlot(driver, "slot12", "删除成功!");
	}
	
	@Test(description="删除被grammar引用的slot")
	public void testSlot13(){
		addSlotNumber(driver, "slot13");
		addGrammar(driver, "g3", "<slot13>的平方", "2的平方");
		deleteSlot(driver, "slot13", "删除失败!已被使用!");
	}
	
	

	@AfterMethod
	public void afterMethod() {
		refresh();
	}

	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		driver.sleep(2000);
		createApp(driver, "testslot");
		enterApp(driver, "testslot");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
			deleteApp(driver, "testslot");
		} finally {
			driver.sleep(2000);
			driver.getDriver().quit();
		}
	}

	@DataProvider
	public Object[][] dp1() {
		return new Object[][] { 
			new Object[] { "跟踪" }, 
			new Object[] { "slot" }, 
			new Object[] { "_slot" },
			new Object[] { "slot1" }, };
	}

	@DataProvider
	public Object[][] dp2() {
		return new Object[][] { 
			new Object[] { "**", "名称是英文字母、汉字或下划线" }, 
			new Object[] { "123", "名称是英文字母、汉字或下划线" },
			new Object[] { "", "名称不能为空" }, };
	}
	
	@DataProvider
	public Object[][] dp3() {
		return new Object[][] { 
			new Object[] { "s1", "-1","10","'最短值'必须是数字且在[0~1024]之间" }, 
			new Object[] { "s2", "1025","1026","'最短值'必须是数字且在[0~1024]之间" },
			new Object[] { "s2", "","1026","'最短值'必须是数字且在[0~1024]之间" },
			new Object[] { "s3", "1","-1","'最长值'必须是数字且在[0~1024]之间" }, 
			new Object[] { "s4", "1","1025","'最长值'必须是数字且在[0~1024]之间" }, 
			new Object[] { "s3", "1","","'最长值'必须是数字且在[0~1024]之间" }, 
			new Object[] { "s3", "8","5","最长值不能小于最短值" }, 
			};
	}
}
