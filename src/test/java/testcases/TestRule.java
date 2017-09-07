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
import static modules.RuleModule.addRule;
import static modules.RuleModule.changeRule;
import static modules.RuleModule.deleteRule;
import static modules.RuleModule.searchRule;
import static modules.SlotModule.addSlotExt;
import static modules.SlotModule.addSlotInternal;
import static modules.TemplateModule.addTemplate;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;

public class TestRule extends BaseTest{
	MyWebDriver driver;

	@Test(description="名称为中文开头，英文开头，下划线开头，英文加数字",dataProvider="dp1")
	public void testRule1(String name, String content) {
		addRule(driver, name, content);
		searchRule(driver, name, content);
	}

	@Test(description="名称为空，特殊字符，数字开头", dataProvider="dp2")
	public void testRule2(String name, String content, String msg) {
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		driver.page("rulePage").getElement("titleErrorMsg").should(text(msg));
	}
	
	@Test(description="内容为空")
	public void testRule3(){
		driver.page("mainPage").click("rule");
		driver.page("rulePage").click("add").sendKeys("name", "rule2").sendKeys("content", "").click("submit");
		driver.page("rulePage").getElement("contentErrorMsg").should(text("内容不能为空"));
	}
	
	@Test(description="引用内容为空")
	public void testRule4(){
		addRule(driver, "rule3", "<>", "no viable alternative at input '<>'");
	}
	
	@Test(description="引用已有的rule, 删除被引用的rule")
	public void testRule5(){
		addRule(driver, "rule4", "aa");
		addRule(driver, "rule5", "<rule4>");
		searchRule(driver, "rule5", "<rule4>");
		deleteRule(driver, "rule4", "删除失败!已被使用!");
	}
	
	@Test(description="引用slot_modifier，修改slot_modifier,引起corpus变化")
	public void testRule6(){
		addSlotInternal(driver, "action");
		addRule(driver, "rule6", "(开启|打开|开下)<{action@=open}>");
		addGrammar(driver, "grammar1", "<rule6>电视", "打开电视");
		changeRule(driver, "rule6", "(开启|打开|开下)<{action@=opens}>");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("打开电视"));
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("slot_modifier").should(text("opens"));
		getNewOldResult(driver, "old").get("slot_modifier").should(text("open"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "提交成功!");
		searchRule(driver, "rule6", "(开启|打开|开下)<{action@=opens}>");
	}
	
	

	@Test(description="引用不存在的slot_modifier")
	public void testRule7(){
		addRule(driver, "rule7", "(做|做点|做下)<{do@=do}>","is neither a slot name nor the global intention");
	}
	
	@Test(description="引用globle_modifier，修改globle_modifier,引起corpus变化")
	public void testRule8(){
		addRule(driver, "rule8", "(听|听见)<{@=ting}>");
		addGrammar(driver, "grammar2", "<rule8>音乐", "听音乐");
		changeRule(driver, "rule8", "(听|听见)<{@=tings}>");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("听音乐"));
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("global_modifier").should(text("tings"));
		getNewOldResult(driver, "old").get("global_modifier").should(text("ting"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "提交成功!");
		searchRule(driver, "rule8", "(听|听见)<{@=tings}>");
	}
	
	@Test(description="内容包含slot赋值，修改slot赋值，引起corpus变化")
	public void testRule9(){
		addSlotInternal(driver, "this");
		addRule(driver, "rule9", "动作<{this=动作}>");
		addGrammar(driver, "grammar3", "做<rule9>", "做动作");
		changeRule(driver, "rule9", "动作<{this=假动作}>");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("做动作"));
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("slotValue").should(text("假动作"));
		getNewOldResult(driver, "old").get("slotValue").should(text("动作"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "提交成功!");
		searchRule(driver, "rule9", "动作<{this=假动作}>");
	}
	
	@Test(description="引用template")
	public void testRule10(){
		addTemplate(driver, "temp1", "[=s=]吃$(s)");
		addRule(driver, "rule10", "<temp1(东西)>");
		searchRule(driver, "rule10", "<temp1(东西)>");
	}
	
	@Test(description="引用不存在的rule")
	public void testRule11(){
		addRule(driver, "rule11", "<r1>", "<r1> is not defined");
	}
	
	@Test(description="引用slot")
	public void testRule12(){
		addSlotExt(driver, "device");
		addRule(driver, "rule12", "修<device>");
		searchRule(driver, "rule12", "修<device>");
	}
	
	@Test(description="内容为特殊字符")
	public void testRule13(){
		addRule(driver, "rule13", "**", "提交失败!编译错误!");
	}
	
	
	@Test(description="名称大小写重复")
	public void testRule14(){
		addRule(driver, "rule14", "aa");
		addRule(driver, "RuLe14", "aa", "提交失败!命名产生冲突!");
	}
	
	@Test(description="正常修改rule")
	public void testRule15(){
		addRule(driver, "rule15", "aa");
		changeRule(driver, "rule15", "aabb", "提交成功!");
		searchRule(driver, "rule15", "aabb");
	}
	
	@Test(description="修改rule，grammar无匹配")
	public void testRule16(){
		addRule(driver, "rule16", "aa");
		addGrammar(driver, "grammar4", "<rule16>", "aa");
		changeRule(driver, "rule16", "aabb");
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text("无"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "after corpus delete or modify , grammar:<grammar4> can not match any corpus, it must have at least one corpus");
	}
	
	@Test(description="正常删除rule")
	public void testRule17(){
		addRule(driver, "rule17", "删除rule");
		deleteRule(driver, "rule17", "删除成功!");
		driver.page("rulePage").sendKeys("searchBox", "rule17").click("searchSubmit");
		Assert.assertTrue(!driver.getSource().contains("删除rule"));
	}
	
	@Test(description="删除已被grammar使用的rule")
	public void testRule18(){
		addRule(driver, "rule18", "删除已被grammar使用的rule");
		addGrammar(driver, "grammar5", "<rule18>", "删除已被grammar使用的rule");
		deleteRule(driver, "rule18", "删除失败!已被使用!");
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
		createApp(driver, "testrule");
		enterApp(driver, "testrule");
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		try {
			deleteApp(driver, "testrule");
		} finally {
			driver.sleep(2000);
			driver.getDriver().quit();
		}
	}
	
	  @DataProvider
	  public Object[][] dp1() {
	    return new Object[][] {
	      new Object[] { "规则", "我的规则" },
	      new Object[] { "rule", "我的规则" },
	      new Object[] { "_rule", "我的规则" },
	      new Object[] { "rule1", "我的规则" },
	    };
	  }
	  
	  @DataProvider
	  public Object[][] dp2() {
	    return new Object[][] {
	      new Object[] { "", "我的规则" ,"名称不能为空"},
	      new Object[] { "**", "我的规则", "名称是英文字母、汉字或下划线，不能以数字开头"},
	      new Object[] { "123", "我的规则", "名称是英文字母、汉字或下划线，不能以数字开头"},
	    };
	  }
}
