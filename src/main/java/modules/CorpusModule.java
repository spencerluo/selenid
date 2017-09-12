package modules;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import io.qameta.allure.Step;
import utils.MyWebDriver;
public class CorpusModule {
	
	@Step("search corpus 【{corpus}】")
	public static void searchCorpus(MyWebDriver driver, String corpus){
		driver.page("mainPage").click("corpus");
		driver.page("corpusPage").sendKeys("searchBox", corpus).click("searchSubmit");
	}

	@Step("assert searchResult 【{corpus}】")
	public static void assertSearchResult(MyWebDriver driver, String corpus, String grammar, String answer, String slot) {
		driver.page("corpusPage").getElement("searchResultCorpus").shouldExactText(corpus);
		driver.page("corpusPage").getElement("searchResultGrammar").shouldExactText(grammar);
		driver.page("corpusPage").getElement("searchResultAnswer").shouldExactText(answer);
		driver.page("corpusPage").getElement("searchResultSlot").shouldExactText(slot);
	}
	
	public static void searchExistCorpus(MyWebDriver driver, String corpus, String grammar, String answer, String slot){
		searchCorpus(driver, corpus);
		assertSearchResult(driver, corpus, grammar, answer, slot);
	}
	
	@Step("delete corpus 【{corpus}】")
	public static void deleteCorpus(MyWebDriver driver, String corpus, String msg){
		driver.page("mainPage").click("corpus");
		$(By.xpath("//*[@title='"+corpus+"']/following-sibling::*[5]/img")).click();
		driver.sleep(1000);
		driver.page("corpusPage").click("deleteSubmit");
		driver.getElement("deleteMsg").shouldText(msg);
		driver.click("deleteMsgClose");
		
	}
	
}
