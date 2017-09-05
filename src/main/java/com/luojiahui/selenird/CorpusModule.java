package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import utils.MyWebDriver;
public class CorpusModule {
	
	public static void searchCorpus(MyWebDriver driver, String corpus){
		driver.page("mainPage").click("corpus");
		driver.page("corpusPage").sendKeys("searchBox", corpus).click("searchSubmit");
	}

	public static void assertSearchResult(MyWebDriver driver, String corpus, String grammar, String answer, String slot) {
		driver.page("corpusPage").getElement("searchResultCorpus").should(text(corpus));
		driver.page("corpusPage").getElement("searchResultGrammar").should(text(grammar));
		driver.page("corpusPage").getElement("searchResultAnswer").should(text(answer));
		driver.page("corpusPage").getElement("searchResultSlot").should(text(slot));
	}
	
	public static void searchExistCorpus(MyWebDriver driver, String corpus, String grammar, String answer, String slot){
		searchCorpus(driver, corpus);
		assertSearchResult(driver, corpus, grammar, answer, slot);
	}
	
	public static void deleteCorpus(MyWebDriver driver, String corpus, String msg){
		driver.page("mainPage").click("corpus");
		$(By.xpath("//*[@title='"+corpus+"']/following-sibling::*[5]/img")).click();
		driver.sleep(1000);
		driver.page("corpusPage").click("deleteSubmit");
		driver.getElement("deleteMsg").should(text(msg));
		driver.click("deleteMsgClose");
		
	}
	
}
