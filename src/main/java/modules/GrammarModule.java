package modules;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static modules.BaseModule.assertSubMsg;
import static modules.CorpusModule.searchExistCorpus;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import utils.MyWebDriver;

public class GrammarModule{

	@Step("add grammar 【{name}】")
	public static void addGrammar(MyWebDriver driver, String name, String content, String corpus, String answer){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", name).sendKeys("content", content).sendKeys("corpus", corpus);
		if (answer.equals("null")) {
			driver.click("yuyi");
		} else {
			driver.sendKeys("answer1", answer);
		}
		driver.click("submit");
		
	}
	
	public static void addGrammar(MyWebDriver driver, String name, String content, String corpus, String answer, String msg){
		addGrammar(driver, name, content, corpus, answer);
		assertSubMsg(driver, msg);
	}
	
	public static void addGrammar(MyWebDriver driver, String name, String content, String corpus){
		addGrammar(driver, name, content, corpus, "null", "提交成功!" );
	}
	
	public static void addGrammarAndAssert(MyWebDriver driver, String name, String content, String corpus, String slot){
		addGrammarAndAssert(driver, name, content, corpus, "语义", slot);
	}
	
	public static void addGrammarAndAssert(MyWebDriver driver, String name, String content, String corpus, String anwser, String slot){
		addGrammar(driver, name, content, corpus);
		searchGrammar(driver, name, content, anwser);
		searchExistCorpus(driver, corpus, name, anwser, slot);
	}
	
	@Step("change grammar 【{name}】")
	public static void changeGrammar(MyWebDriver driver, String name, String content, String corpus, String answer, String msg){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("sortButton").click("sortByChange");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[4]/div[1]/img")).click();
		driver.clear("content").sendKeys("content", content).sendKeys("corpus", corpus);
		if (answer.equals("null")) {
			driver.click("yuyi");
		} else {
			driver.sendKeys("answer1", answer);
		}
		driver.click("submit");
		assertSubMsg(driver, msg);
	}
	
	@Step("change grammar 【{name}】")
	public static void changeGrammar(MyWebDriver driver, String name, String content, String corpus){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("sortButton").click("sortByChange");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[4]/div[1]/img")).click();
		driver.clear("content").sendKeys("content", content).sendKeys("corpus", corpus).click("yuyi").click("submit");
	}
	
	public static void changeGrammarAndAssert(MyWebDriver driver, String name,String content, String corpus, String slot){
		changeGrammar(driver, name, content, corpus, "null", "提交成功!");
		searchGrammar(driver, name, content, "语义");
		searchExistCorpus(driver, corpus, name, "语义", slot);
	}
	
	@Step("delete grammar 【{name}】")
	public static void deleteGrammar(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("sortButton").click("sortByChange");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[4]/div[2]/img")).click();
		driver.sleep(1000);
		driver.click("deleteSubmit");
		assertDeleteMsg(driver, msg);
	}
	
	@Step("test grammar 【{name}】")
	public static void testGrammar(MyWebDriver driver, String name, String content, String corpus, String answer){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("add").sendKeys("name", name).sendKeys("content", content).sendKeys("corpus", corpus);
		if (answer.equals("null")) {
			driver.click("yuyi");
		} else {
			driver.sendKeys("answer1", answer);
		}
		driver.click("test");
	}
	
	@Step("search grammar 【{name}】and assert")
	public static void searchGrammar(MyWebDriver driver, String name, String content, String answer){
		driver.page("grammarPage").sendKeys("searchBox", name).click("searchSubmit");
		assertSearchResult(driver, name, content, answer);
	}
	
	@Step("assert searchResult 【{name}】")
	public static void assertSearchResult(MyWebDriver driver, String name, String content, String answer) {
		driver.page("grammarPage").getElement("searchResultName").shouldExactText(name);
		driver.page("grammarPage").getElement("searchResultContent").shouldExactText(content);
		driver.page("grammarPage").getElement("searchResultAnswer").shouldExactText(answer);
	}
	

	@Step("assert deleteMsg 【{msg}】")
	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("grammarPage").getElement("deleteMsg").shouldText(msg);
		driver.page("grammarPage").click("deleteMsgClose");
	}
	
	@Step("quick add rule 【{name}】")
	public static void quickAddRule(MyWebDriver driver, String name, String content){
		driver.page("grammarPage").click("quickAddRule");
		driver.page("rulePage").sendKeys("name", name).sendKeys("content", content).click("submit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Step("quick add slot 【{name}】")
	public static void quickAddSlot(MyWebDriver driver, String name,String type){
		driver.page("grammarPage").click("quickAddSlot");
		driver.page("slotPage").sendKeys("name", name).click(type).click("submit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Step("quick add template 【{name}】")
	public static void quickAddTemplate(MyWebDriver driver, String name, String content) {
		driver.page("grammarPage").click("quickAddTemplate");
		driver.page("templatePage").sendKeys("name", name).sendKeys("content", content).click("submit");
		assertSubMsg(driver, "提交成功!");
	}
	
	@Step("quick search rule 【{name}】")
	public static void quickSearchRule(MyWebDriver driver,String name, String content){
		driver.page("grammarPage").click("quickSearch").click("quickSearchRule").sendKeys("quickSearchBox", name);
		driver.getElement("quickSearchResultRuleName").shouldText(name);
		driver.getElement("quickSearchResultRuleContent").shouldText(content);
	}
	
	@Step("quick search template 【{name}】")
	public static void quickSearchTemplate(MyWebDriver driver,String name, String content){
		driver.page("grammarPage").click("quickSearch").click("quickSearchTemplate").sendKeys("quickSearchBox", name);
		driver.getElement("quickSearchResultTemplateName").shouldText(name);
		driver.getElement("quickSearchResultTemplateContent").shouldText(content);
	}
	
	@Step("quick search slot 【{name}】")
	public static void quickSearchSlot(MyWebDriver driver,String name, String content){
		driver.page("grammarPage").click("quickSearch").click("quickSearchSlot").sendKeys("quickSearchBox", name);
		driver.getElement("quickSearchResultSlotName").shouldText(name);
		driver.getElement("quickSearchResultSlotContent").shouldText(content);
	}
	
	
	
	public static SelenideElement getCorpusTestResult(String key){
		String value = null;
		switch (key) {
		case "name":
			value="grammar名称：";
			break;
		case "content":
			value="grammar内容：";
			break;
		case "answer":
			value="grammar答案：";
			break;
		case "global_modifier":
			value="global modifier：";
			break;
		case "slotName":
			value="slot名称：";
			break;
		case "slotType":
			value="slot类型：";
			break;
		case "slotValue":
			value="slot值：";
			break;
		case "slot_modifiers":
			value="slot modifiers：";
			break;
		default:
			throw new RuntimeException("no such key {"+key+"}");
		}
		return $(byText(value)).parent().$$(By.tagName("td")).get(1);
	}
	
}
