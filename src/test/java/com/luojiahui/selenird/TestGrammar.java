package com.luojiahui.selenird;

import org.testng.annotations.Test;

import utils.MyWebDriver;
import static com.luojiahui.selenird.AppModule.*;
import static com.luojiahui.selenird.BaseModule.assertSubMsg;
import static com.luojiahui.selenird.BaseModule.getNewOldResult;
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
		addGrammar(driver, name, content, corpus, "null");
		driver.page("grammarPage").getElement("titleErrorMsg").should(text(msg));
	}

	@Test(description = "名称为大写+小写")
	public void testGrammar3() {
		addGrammarAndAssert(driver, "GoodIdea", "GoodIdea", "GoodIdea","无");
	}

	@Test(description = "名称重复")
	public void testGrammar4() {
		addGrammar(driver, "名称重复", "名称重复", "名称重复");
		addGrammar(driver, "名称重复", "名称重复", "名称重复", "null", "提交失败!命名产生冲突!");
	}

	@Test(description = "名称忽略大小写")
	public void testGrammar5() {
		addGrammar(driver, "lucky", "lucky", "lucky");
		addGrammar(driver, "LUCKY", "LUCKY", "LUCKY", "null", "提交失败!命名产生冲突!");
	}

	@Test(description = "内容为空")
	public void testGrammar6() {
		addGrammar(driver, "grammar6", "", "grammar6", "null");
		driver.page("grammarPage").getElement("contentErrorMsg").should(text("内容不能为空"));
	}
	
	@Test(description = "例句为空")
	public void testGrammar7() {
		addGrammar(driver, "grammar7", "grammar7", "", "null");
		driver.page("grammarPage").getElement("corpusErrorMsg").should(text("例句不能为空"));
	}
	
	@Test(description = "答案为空")
	public void testGrammar8() {
		addGrammar(driver, "grammar8", "grammar8", "grammar8", "");
		driver.page("grammarPage").getElement("answerErrorMsg").should(text("答案至少填写一个"));
	}
	
	@Test(description = "内容包含特殊字符")
	public void testGrammar9() {
		addGrammar(driver, "grammar9", "**", "grammar9","null", "提交失败!编译错误!");
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
	
	@Test(description = "内容引用slot_modifier")
	public void testGrammar21() {
		addSlotInternal(driver, "mai");
		addGrammarAndAssert(driver, "grammar21", "买电视<{mai@=buy}>", "买电视", "mai：");
	}
	
	@Test(description = "内容引用带匹配的slot_modifier")
	public void testGrammar22() {
		addSlotInternal(driver, "actions");
		addRule(driver, "shuo", "说|想说");
		addGrammarAndAssert(driver, "grammar22", "<shuo{actions@=shuo}>故事", "说故事", "actions：");
	}
	
	@Test(description = "内容引用global_modifier")
	public void testGrammar23() {
		addGrammarAndAssert(driver, "grammar23", "装电视<{@=装&build}>", "装电视", "无");
	}
	
	@Test(description = "内容引用带匹配的globle_modifier")
	public void testGrammar24() {
		addRule(driver, "lian", "练|锻炼");
		addGrammarAndAssert(driver, "grammar24", "<lian{@=练}>钢琴", "练钢琴", "无");
	}
	
	@Test(description = "内容引用间接赋值的Slot")
	public void testGrammar25() {
		addRule(driver, "chang", "唱|要唱");
		addSlotInternal(driver, "slot1");
		addGrammarAndAssert(driver, "grammar25", "<chang{slot1=$}>戏曲", "唱戏曲", "slot1：唱");
		changeGrammarAndAssert(driver, "grammar25", "<chang{slot1=$}>戏曲", "要唱戏曲", "slot1：要唱");
	}
	
	@Test(description = "内容引用间接赋值的Slot")
	public void testGrammar26() {
		addSlotInternal(driver, "slot2");
		addGrammarAndAssert(driver, "grammar26_1", "听广播<{slot2+=听}><{slot2+=广}><{slot2+=播}>", "听广播", "slot2：听广播");
		addGrammarAndAssert(driver, "grammar26_2", "听粤剧<{slot2+=听}><{slot2+=广}><{slot2=播}>", "听粤剧", "slot2：播");
	}
	
	@Test(description = "内容引用间接赋值的Slot配合+=")
	public void testGrammar27() {
		addSlotInternal(driver, "slot3");
		addRule(driver, "xie", "写|书写");
		addGrammarAndAssert(driver, "grammar27", "<xie{slot3=$}>小说<{slot3+=小说}>", "写小说", "slot3：写小说");
	}
	
	@Test(description = "内容包含slot作为条件赋值的对象")
	public void testGrammar28() {
		addSlotInternal(driver, "记");
		addRule(driver, "记", "记|记住");
		addGrammar(driver, "grammar28", "<记{记=ji}>笔记", "记笔记", "null", "slot cannot have assignment expression");
	}
	
	@Test(description = "内容包含slo赋值，并修改slot赋值")
	public void testGrammar29() {
		addSlotInternal(driver, "slot4");
		addGrammar(driver, "grammar29", "(修仙|修人)<{slot4=修}>", "修仙");
		changeGrammar(driver, "grammar29", "(修仙|修人)<{slot4=xiu}>", "修人");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("修仙"));
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("slotValue").should(text("xiu"));
		getNewOldResult(driver, "old").get("slotValue").should(text("修"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "提交成功!");
		assertSearchResult(driver, "grammar29", "(修仙|修人)<{slot4=xiu}>","语义");
		searchExistCorpus(driver, "修人", "grammar29", "语义", "slot4：xiu");
	}
	
	@Test(description = "例句不匹配任何grammar")
	public void testGrammar30() {
		testGrammar(driver, "grammar30", "拆电视", "拆", "null");
		driver.getElement("testMsg").should(text("无匹配!"));
		driver.click("testOK").click("submit");
		assertSubMsg(driver, "new grammar:<grammar30> can not match any corpus");
	}
	
	@Test(description = "例句只匹配当前grammar")
	public void testGrammar31_1() {
		testGrammar(driver, "grammar31_1", "来跳舞", "来跳舞", "null");
		driver.getElement("testMsg").should(text("匹配成功!"));
		driver.click("testClose").click("submit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Test(description = "例句同时匹配当前grammar和其他grammar")
	public void testGrammar31() {
		addGrammar(driver, "chipingguo", "吃苹果", "吃苹果");
		testGrammar(driver, "grammar31", "吃苹果", "吃苹果", "null");
		driver.getElement("testMsg").should(text("匹配成功但产生了冲突!"));
		driver.click("testOK").click("submit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Test(description = "例句不匹配当前grammar但匹配其他的grammar")
	public void testGrammar32() {
		addGrammar(driver, "dazhong", "大众", "大众");
		testGrammar(driver, "grammar32", "打字", "大众", "null");
		driver.getElement("testMsg").should(text("匹配失败!与其它grammar相匹配!"));
		driver.click("testOK").click("submit");
		assertSubMsg(driver, "new grammar:<grammar32> can not match any corpus");
	}
	
	@Test(description = "引用slot、rule、template名称的大小写")
	public void testGrammar33() {
		addSlotInternal(driver, "slot5");
		addRule(driver, "rule5", "看|看见");
		addTemplate(driver, "temp5", "[=s=]$(s)远方");
		addGrammarAndAssert(driver, "grammar33", "<TEMP5(<RULE5>)><{SLOT5=as}>", "看见远方", "slot5：as");
	}
	
	@Test(description = "增加weight比重低的语法")
	public void testGrammar34() {
		addSlotExt(driver, "slot6");
		addGrammar(driver, "我要看星星", "我要看星星", "我要看星星");
		addGrammar(driver, "grammar34", "我要看<slot6>", "我要看星星", "null", "new grammar:<grammar34> can not match any corpus");
	}
	
	@Test(description = "增加weight比重高的语法")
	public void testGrammar35() {
		addSlotExt(driver, "slot7");
		addGrammar(driver, "观测天空", "观测<slot7>", "观测天空");
		addGrammar(driver, "grammar35", "观测天空", "观测天空","null");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("观测天空"));
		driver.page("mainPage").click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text("grammar35"));
		getNewOldResult(driver, "old").get("grammarName").should(text("观测天空"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "after corpus delete or modify , grammar:<观测天空> can not match any corpus, it must have at least one corpus.");
	}
	
	@Test(description = "答案中有slot赋值")
	public void testGrammar36() {
		addSlotExt(driver, "slot8");
		addGrammar(driver, "grammar36", "仰望<slot8>", "仰望星空", "<SLOT8>很美", "提交成功!");
		searchGrammar(driver, "grammar36", "仰望<slot8>", "<SLOT8>很美");
		searchExistCorpus(driver, "仰望星空", "grammar36", "<SLOT8>很美", "slot8：星空");
	}
	
	@Test(description = "内容为英文时例句匹配不区分大小写")
	public void testGrammar37() {
		addGrammarAndAssert(driver, "grammar37", "shuohua", "SHUOHUA" ,"无");
	}
	
	@Test(description = "内容为英文时例句匹配不区分全角半角")
	public void testGrammar38() {
		addGrammarAndAssert(driver, "grammar38", "jianghua", "ｊｉａｎｇｈｕａ" ,"无");
	}
	
	@Test(description = "内容为标点符号时例句匹配不区分全角半角")
	public void testGrammar39() {
		addGrammarAndAssert(driver, "grammar39", "hello','zhangsan", "hello，zhangsan" ,"无");
	}
	
	@Test(description = "any 规则")
	public void testGrammar40() {
		addGrammarAndAssert(driver, "grammar40", "我想听<any:1|10>歌曲", "我想听热门歌曲" ,"无");
	}
	
	@Test(description = "skipper规则")
	public void testGrammar41() {
		addGrammarAndAssert(driver, "grammar41", "你还好[吗]", "！！！你还好，，，吗。。。" ,"无");
	}
	
	@Test(description = " repeater 规则")
	public void testGrammar42() {
		addGrammarAndAssert(driver, "grammar42", "好累(哈|啊|吧|呵)*", "好累啊啊啊" ,"无");
	}
	
	@Test(description = "内容引用不存在的slot、template或rule")
	public void testGrammar43() {
		addGrammar(driver, "grammar43", "<xiexie>你", "谢谢你" ,"null","<xiexie> is not defined");
	}
	
	@Test(description = "删除grammar")
	public void testGrammar44() {
		addGrammar(driver, "grammar44", "你好", "你好" );
		deleteGrammar(driver, "grammar44", "删除成功!");
	}
	
	@Test(description = "内容不修改，新增例句只匹配当前grammar")
	public void testGrammar45() {
		addGrammar(driver, "grammar45", "(聆听|倾听)者", "聆听者" );
		changeGrammarAndAssert(driver, "grammar45", "(聆听|倾听)者", "倾听者", "无");
	}
	
	@Test(description = "内容不修改，新增例句不匹配任何grammar")
	public void testGrammar46() {
		addGrammar(driver, "grammar46", "听故事", "听故事" );
		changeGrammar(driver, "grammar46", "听故事", "听啥", "null", "听啥: new corpus cannot match any grammar");
	}
	
	@Test(description = "内容不修改，新增例句不匹配当前grammar但匹配其他的grammar")
	public void testGrammar47() {
		addGrammar(driver, "讲啥", "讲啥", "讲啥" );
		addGrammar(driver, "grammar47", "讲故事", "讲故事" );
		changeGrammar(driver, "grammar47", "讲故事", "讲啥", "null", "讲啥: corpus cannot match grammar: <grammar47>");
	}
	
	@Test(description = "内容修改，新grammar匹配当前例句但不匹配之前的例句，之前例句不匹配其他grammar")
	public void testGrammar48() {
		addGrammar(driver, "grammar48", "说故事", "说故事" );
		changeGrammar(driver, "grammar48", "说些故事", "说些故事");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("说故事"));
		driver.click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text(""));
		getNewOldResult(driver, "old").get("grammarName").should(text("grammar48"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "提交成功!");
		assertSearchResult(driver, "grammar48", "说些故事","语义");
		searchExistCorpus(driver, "说些故事", "grammar48", "语义", "无");
	}
	
	@Test(description = "内容修改，新grammar匹配当前例句同时匹配之前的例句，新例句也不匹配其他grammar")
	public void testGrammar49() {
		addGrammar(driver, "grammar49", "编故事", "编故事" );
		changeGrammarAndAssert(driver, "grammar49", "编[一段]故事", "编一段故事", "无");
	}
	
	@Test(description = "内容修改，新grammar既不匹配当前例句也不匹配之前的例句，新例句也不匹配其他grammar")
	public void testGrammar50() {
		addGrammar(driver, "grammar50", "设计故事", "设计故事" );
		changeGrammar(driver, "grammar50", "设计一段故事", "设计一个故事");
		driver.page("mainPage").getElement("subMsg").should(text("以下例句的新匹配结果与旧结果不一致,是否确认修改?没有grammar匹配的语料将被删除"));
		driver.getElement("语料").should(text("设计故事"));
		driver.click("详情");
		getNewOldResult(driver, "new").get("grammarName").should(text(""));
		getNewOldResult(driver, "old").get("grammarName").should(text("grammar50"));
		driver.page("grammarPage").click("submitChange");
		assertSubMsg(driver, "设计一个故事: new corpus cannot match any grammar");
	}
	
	@Test(description = "内容修改，新grammar不匹配当前例句但匹配之前的例句，新例句也不匹配其他grammar")
	public void testGrammar51() {
		addGrammar(driver, "grammar51", "描述故事", "描述故事" );
		changeGrammar(driver, "grammar51", "描述(故事|小说)", "描述战争", "null","描述战争: new corpus cannot match any grammar");
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
		enterApp(driver, "testgrammar");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		deleteApp(driver, "testgrammar");
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
