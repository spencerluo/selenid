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
import static modules.GrammarModule.changeGrammar;
import static modules.LoginModule.login;
import static modules.RuleModule.addRule;
import static modules.SlotModule.addSlotExt;
import static modules.TemplateModule.addTemplate;
import static modules.TemplateModule.changeTemplate;
import static modules.TemplateModule.deleteTemplate;
import static modules.TemplateModule.searchTemplate;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;


public class TestTemplate extends BaseTest{
	MyWebDriver driver;

	@Test(dataProvider = "dp1", description="名称为中文开头，英文开头，下划线开头，英文加数字")
	public void testTempalte1(String name, String content) {
		addTemplate(driver, name, content);
		searchTemplate(driver, name, content);
	}
	
	@Test(description="名称为空，特殊字符，数字开头", dataProvider="dp2")
	public void testTempalte2(String name, String content, String msg) {
		driver.page("mainPage").click("template");
		driver.page("templatePage").click("add").sendKeys("name", name).sendKeys("content", content).click("submit");
		driver.page("templatePage").getElement("titleErrorMsg").should(text(msg));
	}
	
	@Test(description="内容为空")
	public void testTempalte3(){
		driver.page("mainPage").click("template");
		driver.page("templatePage").click("add").sendKeys("name", "temp2").sendKeys("content", "").click("submit");
		driver.page("templatePage").getElement("contentErrorMsg").should(text("内容不能为空"));
	}
	
	@Test(description="内容为特殊字符")
	public void testTempalte4(){
		addTemplate(driver, "temp4", "**", "提交失败!编译错误!");
	}
	
	@Test(description="定义两个参数")
	public void testTempalte5(){
		addTemplate(driver, "temp5", "[=a,b=]吃$(a)喝$(b)");
		searchTemplate(driver, "temp5", "[=a,b=]吃$(a)喝$(b)");
	}
	
	@Test(description="没有定义参数")
	public void testTempalte6(){
		addTemplate(driver, "temp6", "吃", "missing template argument list");
	}
	
	@Test(description="引用已有的template,删除被引用的template")
	public void testTempalte7(){
		addTemplate(driver, "temps", "[=s=]吃$(s)");
		addTemplate(driver, "temp7", "[=s=]<temps(啥)>$(s)");
		searchTemplate(driver, "temp7", "[=s=]<temps(啥)>$(s)");
		deleteTemplate(driver, "temps", "删除失败!已被使用!");
	}
	
	@Test(description="引用rule")
	public void testTempalte8(){
		addRule(driver, "rule1", "aa");
		addTemplate(driver, "temp8", "[=s=]<rule1>$(s)");
		searchTemplate(driver, "temp8", "[=s=]<rule1>$(s)");
	}
	
	@Test(description="引用slot")
	public void testTempalte9(){
		addSlotExt(driver, "slot1");
		addTemplate(driver, "temp9", "[=s=]写<slot1>$(s)");
		searchTemplate(driver, "temp9", "[=s=]写<slot1>$(s)");
	}
	
	@Test(description="定义template申明参数，没有引用参数")
	public void testTempalte10(){
		addTemplate(driver, "temp10", "[=短句=]吃");
		searchTemplate(driver, "temp10", "[=短句=]吃");
	}
	
	@Test(description="定义template申明参数，引用一个没有申明的参数")
	public void testTempalte11(){
		addTemplate(driver, "temp11", "[=短句=]吃$(s)", "named argument 's' not declared");
	}
	
	@Test(description="修改template")
	public void testTempalte12(){
		addTemplate(driver, "temp12", "[=短句=]吃$(短句)");
		changeTemplate(driver, "temp12", "[=短句=]吃喝$(短句)","提交成功!");
		searchTemplate(driver, "temp12", "[=短句=]吃喝$(短句)");
	}
	
	@Test(description="修改template，grammar无匹配")
	public void testTempalte13(){
		addTemplate(driver, "temp13", "[=短句=]吃$(短句)");
		addGrammar(driver, "grammar1", "<temp13(东西)>", "吃东西");
		changeTemplate(driver, "temp13", "[=短句=]喝$(短句)");
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text("无"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "after corpus delete or modify , grammar:<grammar1> can not match any corpus, it must have at least one corpus");
	}
	
	@Test(description="修改template，影响corpus")
	public void testTempalte14(){
		addTemplate(driver, "temp14", "[=短句=](玩|闹)$(短句)");
		addGrammar(driver, "grammar2", "<temp14(东西)>", "玩东西");
		changeGrammar(driver, "grammar2", "<temp14(东西)>", "闹东西","null", "提交成功!");
		changeTemplate(driver, "temp14", "[=短句=]玩$(短句)");
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text("无"));
		driver.page("rulePage").click("changeSubmit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Test(description="删除已被grammar使用的template")
	public void testTempalte15(){
		addTemplate(driver, "temp15", "[=短句=]制作$(短句)");
		addGrammar(driver, "grammar3", "<temp15(东西)>","制作东西" );
		deleteTemplate(driver, "temp15", "删除失败!已被使用!");
		searchTemplate(driver, "temp15", "[=短句=]制作$(短句)");
	}
	
	@Test(description="正常删除template")
	public void testTempalte16(){
		addTemplate(driver, "temp16", "[=短句=]制作$(短句)");
		deleteTemplate(driver, "temp16", "删除成功!");
		driver.page("templatePage").sendKeys("searchBox", "temp16").click("searchSubmit");
		Assert.assertTrue(!driver.getSource().contains("[=短句=]制作$(短句)"));
	}
	
	@Test(description="template递归")
	public void testTempalte17(){
		addTemplate(driver, "temp17", "[=s=]<temp17($(s))>","<temp17> error, line 1 Col 1:template <temp17> recursively depends on itself (temp17->temp17)");
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
		createApp(driver, "testtemplate");
		enterApp(driver, "testtemplate");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		deleteApp(driver, "testtemplate");
		driver.getDriver().quit();
	}

	@DataProvider
	public Object[][] dp1() {
		return new Object[][] { 
			new Object[] { "模板", "[=短句=]模板$(短句)" }, 
			new Object[] { "temp", "[=短句=]模板$(短句)" }, 
			new Object[] { "_temp", "[=短句=]模板$(短句)" }, 
			new Object[] { "temp1", "[=短句=]模板$(短句)" }, 
		};
	}
	
	@DataProvider
	public Object[][] dp2() {
		return new Object[][] { 
			new Object[] { "", "[=短句=]模板$(短句)", "名称不能为空" }, 
			new Object[] { "**", "[=短句=]模板$(短句)" ,"名称是英文字母、汉字或下划线，不能以数字开头"}, 
			new Object[] { "123", "[=短句=]模板$(短句)" ,"名称是英文字母、汉字或下划线，不能以数字开头"}, 
		};
	}

}
