package modules;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static modules.CorpusModule.searchExistCorpus;

import org.openqa.selenium.By;

import utils.MyWebDriver;

public class GrammarModule extends BaseModule{

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
	
	public static void deleteGrammar(MyWebDriver driver, String name, String msg){
		driver.page("mainPage").click("grammar");
		driver.page("grammarPage").click("sortButton").click("sortByChange");
		$(By.xpath("//*[@title='" + name + "']/following-sibling::*[4]/div[2]/img")).click();
		driver.sleep(1000);
		driver.click("deleteSubmit");
		assertDeleteMsg(driver, msg);
	}
	
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
	
	public static void searchGrammar(MyWebDriver driver, String name, String content, String answer){
		driver.page("grammarPage").sendKeys("searchBox", name).click("searchSubmit");
		assertSearchResult(driver, name, content, answer);
	}
	
	public static void assertSearchResult(MyWebDriver driver, String name, String content, String answer) {
		driver.page("grammarPage").getElement("searchResultName").should(text(name));
		driver.page("grammarPage").getElement("searchResultContent").should(text(content));
		driver.page("grammarPage").getElement("searchResultAnswer").should(text(answer));
	}
	

	public static void assertDeleteMsg(MyWebDriver driver, String msg){
		driver.page("grammarPage").getElement("deleteMsg").should(text(msg));
		driver.page("grammarPage").click("deleteMsgClose");
	}
}
