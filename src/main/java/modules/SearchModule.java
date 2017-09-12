package modules;

import io.qameta.allure.Step;
import utils.MyWebDriver;

public class SearchModule {

	@Step("all content search 【{content}】")
	public static void allSearch(MyWebDriver driver, String content, String type) {
		driver.page("mainPage").clear("searchBox").sendKeys("searchBox", content).click("searchSubmit");
		driver.sleep(2000);
		driver.page("searchPage").click(type);
	}

	@Step("assert grammarResult")
	public static void assertGrammarResult(MyWebDriver driver, String grammarName, String grammarContent,
			String grammarAnswer) {
		driver.page("searchPage").getElement("grammarName").shouldExactText(grammarName);
		driver.page("searchPage").getElement("grammarContent").shouldExactText(grammarContent);
		driver.page("searchPage").getElement("grammarAnswer").shouldExactText(grammarAnswer);
	}

	@Step("assert grammarMatchResult")
	public static void assertGrammarMatchResult(MyWebDriver driver, String grammarName, String grammarContent,
			String grammarAnswer) {
		driver.page("searchPage").getElement("grammarMatchName").shouldExactText(grammarName);
		driver.page("searchPage").getElement("grammarMatchContent").shouldExactText(grammarContent);
		driver.page("searchPage").getElement("grammarMatchAnswer").shouldExactText(grammarAnswer);
	}

	@Step("assert ruleResult")
	public static void assertRuleResult(MyWebDriver driver, String ruleName, String ruleContent) {
		driver.page("searchPage").getElement("ruleName").shouldExactText(ruleName);
		driver.page("searchPage").getElement("ruleContent").shouldExactText(ruleContent);
	}

	@Step("assert templateResult")
	public static void assertTemplateResult(MyWebDriver driver, String templateName, String templateContent) {
		driver.page("searchPage").getElement("templateName").shouldExactText(templateName);
		driver.page("searchPage").getElement("templateContent").shouldExactText(templateContent);
	}

	@Step("assert slotResult")
	public static void assertSlotResult(MyWebDriver driver, String slotName, String slotType, String slotMin,
			String slotMax) {
		driver.page("searchPage").getElement("slotName").shouldExactText(slotName);
		driver.page("searchPage").getElement("slotType").shouldExactText(slotType);
		driver.page("searchPage").getElement("slotMin").shouldExactText(slotMin);
		driver.page("searchPage").getElement("slotMax").shouldExactText(slotMax);
	}

	@Step("assert corpusResult")
	public static void assertCorpusResult(MyWebDriver driver, String corpusName, String corpusGrammar,
			String corpusAnswer, String corpusSlot) {
		driver.page("searchPage").getElement("corpusName").shouldExactText(corpusName);
		driver.page("searchPage").getElement("corpusGrammar").shouldExactText(corpusGrammar);
		driver.page("searchPage").getElement("corpusAnswer").shouldExactText(corpusAnswer);
		driver.page("searchPage").getElement("corpusSlot").shouldExactText(corpusSlot);
	}
}
