package com.luojiahui.selenird;

import org.testng.annotations.Test;

import utils.MyWebDriver;
import static com.luojiahui.selenird.AppModule.*;
import static com.luojiahui.selenird.LoginModule.*;
import static com.luojiahui.selenird.RuleModule.*;
import static com.luojiahui.selenird.SlotModule.*;
import static com.luojiahui.selenird.TemplateModule.*;
import static com.luojiahui.selenird.CorpusModule.*;

import static com.luojiahui.selenird.GrammarModule.*;
import static com.luojiahui.selenird.CorpusModule.*;
import org.testng.annotations.DataProvider;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import utils.MyWebDriver;

public class TestGrammar extends BaseTest {
	MyWebDriver driver;

	@Test(dataProvider = "dp1", description = "名称为中文开头，英文开头，下划线开头，英文加数字")
	public void testGrammar1(String name, String content, String corpus) {
		addGrammarAndAssert(driver, name, content, corpus,"无");
	}

	@Test(dataProvider = "dp2", description = "名称是特殊字符，为空，数字开头")
	public void testGrammar2(String name, String content, String corpus, String msg) {
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", name).sendKeys("content", content).sendKeys("corpus",corpus).click("yuyi");
		driver.click("submit");
		driver.page("grammarPage").getElement("titleErrorMsg").should(text(msg));
	}

	@Test(description = "名称为大写+小写")
	public void testGrammar3() {
		addGrammarAndAssert(driver, "GoodIdea", "GoodIdea", "GoodIdea","无");
	}

	@Test(description = "名称重复")
	public void testGrammar4() {
		addGrammar(driver, "名称重复", "名称重复", "名称重复");
		addGrammar(driver, "名称重复", "名称重复", "名称重复", "提交失败!命名产生冲突!");
	}

	@Test(description = "名称忽略大小写")
	public void testGrammar5() {
		addGrammar(driver, "lucky", "lucky", "lucky");
		addGrammar(driver, "LUCKY", "LUCKY", "LUCKY", "提交失败!命名产生冲突!");
	}

	@Test(description = "内容为空")
	public void testGrammar6() {
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", "内容为空").sendKeys("content", "").sendKeys("corpus","内容为空").click("yuyi");
		driver.click("submit");
		driver.page("grammarPage").getElement("contentErrorMsg").should(text("内容不能为空"));
	}
	
	@Test(description = "例句为空")
	public void testGrammar7() {
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", "例句为空").sendKeys("content", "例句为空").sendKeys("corpus","").click("yuyi");
		driver.click("submit");
		driver.page("grammarPage").getElement("corpusErrorMsg").should(text("例句不能为空"));
	}
	
	@Test(description = "答案为空")
	public void testGrammar8() {
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", "答案为空").sendKeys("content", "答案为空").sendKeys("corpus","答案为空");
		driver.click("submit");
		driver.page("grammarPage").getElement("answerErrorMsg").should(text("答案至少填写一个"));
	}
	
	@Test(description = "内容包含特殊字符")
	public void testGrammar9() {
		addGrammar(driver, "grammar9", "**", "grammar9","提交失败!编译错误!");
	}
	
	@Test(description = "内容引用rule")
	public void testGrammar10() {
		addRule(driver, "ting", "听|听见|想听|要听");
		addGrammarAndAssert(driver, "grammar10", "<ting>歌", "听歌","无");
	}
	
	@Test(description = "内容引用template")
	public void testGrammar11() {
		addTemplate(driver, "chi", "[=短句=]吃$(短句)");
		addGrammarAndAssert(driver, "grammar11", "<chi(水果)>", "吃水果","无");
	}
	
	@Test(description = "内容引用template，template参数为rule")
	public void testGrammar12() {
		addTemplate(driver, "he", "[=短句=]喝$(短句)");
		addRule(driver, "shui", "西瓜汁|果汁");
		addGrammarAndAssert(driver, "grammar12", "<he(<shui>)>", "喝果汁","无");
	}
	
	@Test(description = "内容引用template，template参数为slot")
	public void testGrammar13() {
		addTemplate(driver, "kao", "[=短句=]烤$(短句)");
		addSlotExt(driver, "食物");
		addGrammarAndAssert(driver, "grammar13", "<kao(<食物>)>", "烤牛排","食物：牛排");
	}
	
	
	@Test(description = "内容引用template，template有两个参数")
	public void testGrammar14() {
		addTemplate(driver, "chihe", "[=a,b=]吃$(a)喝$(b)");
		addGrammarAndAssert(driver, "grammar14", "<chihe(香,辣)>", "吃香喝辣","无");
	}
	
	@Test(description = "内容引用grammar")
	public void testGrammar15() {
		addGrammarAndAssert(driver, "grammartemp", "grammartemp", "grammartemp","无");
	}
	
	@Test(description = "内容引用slot_ext")
	public void testGrammar16() {
		addSlotExt(driver, "device");
		addGrammarAndAssert(driver, "grammar16", "看<device>", "看电视","device：电视");
	}
	
	@Test(description = "内容引用slot_datetime_timepoint")
	public void testGrammar17() {
		addSlotTimepoint(driver, "timepoint");
		addGrammarAndAssert(driver, "grammar17", "<timepoint>的天气", "下午六点的天气", "timepoint：下午六点");
	}
	
	@Test(description = "内容引用slot_datetime_duration")
	public void testGrammar18() {
		addSlotDuration(driver, "duration");
		addGrammarAndAssert(driver, "grammar18", "<duration>到了吗", "三星期到了吗", "duration：三星期");
	}
	
	@Test(description = "内容引用slot_number")
	public void testGrammar19() {
		addSlotNumber(driver, "number");
		addGrammarAndAssert(driver, "grammar19", "<number>的平方", "2的平方", "number：2");
	}
	
	@Test(description = "内容引用slot_float")
	public void testGrammar20() {
		addSlotFloat(driver, "float");
		addGrammarAndAssert(driver, "grammar20", "<float>的次方", "2.5的次方", "float：2.5");
	}
	
	@AfterMethod
	public void afterMethod() {
		refresh();
	}

	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver);
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		createApp(driver, "testgrammar");
		driver.page("mainPage").getElement("subMsg").should(text("模块创建成功!"));
		driver.page("mainPage").click("subMsgClose");
		enterApp(driver, "testgrammar");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		deleteApp(driver, "testgrammar");
		driver.page("modelPage").getElement("deleteMsg").should(text("删除成功!"));
		driver.page("modelPage").click("closeDeleteMsg");
		driver.getDriver().quit();
	}

	@DataProvider
	public Object[][] dp1() {
		return new Object[][] { new Object[] { "语法", "语法", "语法" }, new Object[] { "grammar", "grammar", "grammar" },
				new Object[] { "_grammar", "_grammar", "_grammar" },
				new Object[] { "grammars", "grammars", "grammars" }, };
	}

	@DataProvider
	public Object[][] dp2() {
		return new Object[][] { new Object[] { "", "名称为空", "名称为空", "名称不能为空" },
				new Object[] { "**", "名称是特殊字符", "名称是特殊字符", "名称是英文字母、汉字或下划线，不能以数字开头" },
				new Object[] { "123", "名称是数字", "名称是数字", "名称是英文字母、汉字或下划线，不能以数字开头" }, };
	}

}
