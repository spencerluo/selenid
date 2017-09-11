package modules;

import static com.codeborne.selenide.Condition.exactText;

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
		driver.page("searchPage").getElement("grammarName").should(exactText(grammarName));
		driver.page("searchPage").getElement("grammarContent").should(exactText(grammarContent));
		driver.page("searchPage").getElement("grammarAnswer").should(exactText(grammarAnswer));
	}

	@Step("assert grammarMatchResult")
	public static void assertGrammarMatchResult(MyWebDriver driver, String grammarName, String grammarContent,
			String grammarAnswer) {
		driver.page("searchPage").getElement("grammarMatchName").should(exactText(grammarName));
		driver.page("searchPage").getElement("grammarMatchContent").should(exactText(grammarContent));
		driver.page("searchPage").getElement("grammarMatchAnswer").should(exactText(grammarAnswer));
	}

	@Step("assert ruleResult")
	public static void assertRuleResult(MyWebDriver driver, String ruleName, String ruleContent) {
		driver.page("searchPage").getElement("ruleName").should(exactText(ruleName));
		driver.page("searchPage").getElement("ruleContent").should(exactText(ruleContent));
	}

	@Step("assert templateResult")
	public static void assertTemplateResult(MyWebDriver driver, String templateName, String templateContent) {
		driver.page("searchPage").getElement("templateName").should(exactText(templateName));
		driver.page("searchPage").getElement("templateContent").should(exactText(templateContent));
	}

	@Step("assert slotResult")
	public static void assertSlotResult(MyWebDriver driver, String slotName, String slotType, String slotMin,
			String slotMax) {
		driver.page("searchPage").getElement("slotName").should(exactText(slotName));
		driver.page("searchPage").getElement("slotType").should(exactText(slotType));
		driver.page("searchPage").getElement("slotMin").should(exactText(slotMin));
		driver.page("searchPage").getElement("slotMax").should(exactText(slotMax));
	}

	@Step("assert corpusResult")
	public static void assertCorpusResult(MyWebDriver driver, String corpusName, String corpusGrammar,
			String corpusAnswer, String corpusSlot) {
		driver.page("searchPage").getElement("corpusName").should(exactText(corpusName));
		driver.page("searchPage").getElement("corpusGrammar").should(exactText(corpusGrammar));
		driver.page("searchPage").getElement("corpusAnswer").should(exactText(corpusAnswer));
		driver.page("searchPage").getElement("corpusSlot").should(exactText(corpusSlot));
	}
}
