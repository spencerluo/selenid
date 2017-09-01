package com.luojiahui.selenird;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;

import utils.MyWebDriver;
public class CorpusModule {
	
	public static void searchResult(MyWebDriver driver, String corpus){
		driver.page("mainPage").click("corpus");
		driver.page("corpusPage").sendKeys("searchBox", corpus).click("searchSubmit");
	}

	public static void assertSearchResult(MyWebDriver driver, String corpus, String grammar, String answer, String slot) {
		driver.page("corpusPage").getElement("searchResultCorpus").should(text(corpus));
		driver.page("corpusPage").getElement("searchResultGrammar").should(text(grammar));
		driver.page("corpusPage").getElement("searchResultAnswer").should(text(answer));
		driver.page("corpusPage").getElement("searchResultSlot").should(text(slot));
	}
	
}
