package testcases;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.switchTo;
import static modules.AppModule.createApp;
import static modules.AppModule.deleteApp;
import static modules.AppModule.enterApp;
import static modules.BaseModule.configureModules;
import static modules.BaseModule.release;
import static modules.GrammarModule.addGrammar;
import static modules.LoginModule.login;
import static modules.SlotModule.addSlotDuration;
import static modules.SlotModule.addSlotExt;
import static modules.SlotModule.addSlotFloat;
import static modules.SlotModule.addSlotNumber;
import static modules.SlotModule.addSlotTimepoint;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyWebDriver;
import utils.ReadExcel;

public class TestCite extends BaseTest{
	MyWebDriver driver;
  @Test(dataProvider="dp1",priority=1)
  public void readySlo1t(String name) {
	  try {
		addSlotExt(driver, name, name, "1", "10", "提交成功!");
	} catch(Exception e) {
		driver.refresh();
	}
  }
  
  @Test(priority=2)
  public void readySlot2() {
	  addSlotTimepoint(driver, "时间点");
	  addSlotDuration(driver, "时间段");
	  addSlotNumber(driver, "年龄");
	  addSlotFloat(driver, "浮点");
  }
  
  @Test(dataProvider="dp2",priority=3)
  public void readyGrammar(String type, String name, String content, String corpus){
	  try {
		addGrammar(driver, name, content, corpus);
	} catch (Exception e) {
		driver.refresh();
	}
  }
  
  @Test(priority=4)
  public void goToTest() throws Exception{
	  release(driver);
	  switchTo().window(0);
	  configureModules(driver, "testcite", "99");
	  driver.page("loginPage").click("test");
  }
  
  @Test(dataProvider = "dp3",priority=5)
	public void testCite(String type, String Q1, String Q2, String result) throws Exception {
		String[] questions = { Q1, Q2 };
		for (String question : questions) {
			driver.page("loginPage").clear("question").sendKeys("question", question).click("testSubmit");
		}
		driver.getElement("answer").should(text(result));
	}
  
	@BeforeClass
	public void beforeClass() {
		driver = MyWebDriver.getMyDriver();
		login(driver, "spencer", "asdD1234");
		driver.page("loginPage").click("user").click("nli");
		switchTo().window(1);
		driver.sleep(2000);
		createApp(driver, "testcite");
		enterApp(driver, "testcite");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		switchTo().window(1);
		deleteApp(driver, "testcite");
		driver.getDriver().quit();
	}
	
	@DataProvider
	public Object[][] dp1() {
			return new Object[][] { 
				new Object[] { "人物" }, 
				new Object[] { "地点" }, 
				new Object[] { "州"}, 
				new Object[] { "国家"}, 
				new Object[] { "省"}, 
				new Object[] { "城市"}, 
				new Object[] { "区县"}, 
			};
		}
	
	
	@DataProvider
	public Object[][] dp2() {
		return new ReadExcel("classes\\grammar.xlsx","cite_grammar").getData();
	}
	
	@DataProvider
	public Object[][] dp3() {
		return new ReadExcel("classes\\grammar.xlsx","cite_corpus").getData();
	}
}
