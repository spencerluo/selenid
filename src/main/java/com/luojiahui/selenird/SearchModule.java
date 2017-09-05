package com.luojiahui.selenird;

import utils.MyWebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchModule {

	public static void allSearch(MyWebDriver driver, String content, String type) {
		driver.page("mainPage").clear("searchBox").sendKeys("searchBox", content).click("searchSubmit");
		driver.sleep(2000);
		driver.page("searchPage").click(type);
	}

	public static void assertGrammarResult(MyWebDriver driver, String grammarName, String grammarContent,
			String grammarAnswer) {
		driver.page("searchPage").getElement("grammarName").should(text(grammarName));
		driver.page("searchPage").getElement("grammarContent").should(text(grammarContent));
		driver.page("searchPage").getElement("grammarAnswer").should(text(grammarAnswer));
	}

	public static void assertGrammarMatchResult(MyWebDriver driver, String grammarName, String grammarContent,
			String grammarAnswer) {
		driver.page("searchPage").getElement("grammarMatchName").should(text(grammarName));
		driver.page("searchPage").getElement("grammarMatchContent").should(text(grammarContent));
		driver.page("searchPage").getElement("grammarMatchAnswer").should(text(grammarAnswer));
	}

	public static void assertRuleResult(MyWebDriver driver, String ruleName, String ruleContent) {
		driver.page("searchPage").getElement("ruleName").should(text(ruleName));
		driver.page("searchPage").getElement("ruleContent").should(text(ruleContent));
	}

	public static void assertTemplateResult(MyWebDriver driver, String templateName, String templateContent) {
		driver.page("searchPage").getElement("templateName").should(text(templateName));
		driver.page("searchPage").getElement("templateContent").should(text(templateContent));
	}

	public static void assertSlotResult(MyWebDriver driver, String slotName, String slotType, String slotMin,
			String slotMax) {
		driver.page("searchPage").getElement("slotName").should(text(slotName));
		driver.page("searchPage").getElement("slotType").should(text(slotType));
		driver.page("searchPage").getElement("slotMin").should(text(slotMin));
		driver.page("searchPage").getElement("slotMax").should(text(slotMax));
	}

	public static void assertCorpusResult(MyWebDriver driver, String corpusName, String corpusGrammar,
			String corpusAnswer, String corpusSlot) {
		driver.page("searchPage").getElement("corpusName").should(text(corpusName));
		driver.page("searchPage").getElement("corpusGrammar").should(text(corpusGrammar));
		driver.page("searchPage").getElement("corpusAnswer").should(text(corpusAnswer));
		driver.page("searchPage").getElement("corpusSlot").should(text(corpusSlot));
	}
}
