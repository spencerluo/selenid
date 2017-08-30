package utils;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.source;
import static utils.XmlUtils.getChildNodes;
import static utils.XmlUtils.getNodes;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Node;

import com.codeborne.selenide.SelenideElement;

public class MyWebDriver{
	private Node page = null;
	private static MyWebDriver driver = null;
	
	public static MyWebDriver getMyDriver(){
		if(driver == null){
			driver = new MyWebDriver();
		}
		return driver;
	}
	
	public MyWebDriver page(String pageName){
		List<Node> pages = getNodes("page", "name", pageName);
		if (!pages.isEmpty()) {
			page = pages.get(0);
		}
		return getMyDriver();
	}
	
	public By by(String elementName){
		List<Node> elements = getChildNodes(page, "element", "name", elementName);
		if (elements.isEmpty()) {
			try {
				throw new Exception("xml文件中不存在元素{"+elementName+"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Node element = elements.get(0);
		String type = element.getAttributes().getNamedItem("type").getNodeValue();
		String value = element.getAttributes().getNamedItem("value").getNodeValue();
		By by = null;
		switch (type) {
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "className":
			by = By.className(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		}
		return by;
	}
	
	public SelenideElement getElement(String elementName){
		return $(by(elementName));
	}
	
	public MyWebDriver click(String elementName){
		$(by(elementName)).click();
		return getMyDriver();
	}
	
	public MyWebDriver sendKeys(String elementName, String value){
		$(by(elementName)).sendKeys(value);
		return getMyDriver();
	}
	
	public MyWebDriver clear(String elementName){
		$(by(elementName)).clear();
		return getMyDriver();
	}
	
	public String getSource(){
		return source();
	}
	
	public WebDriver getDriver(){
		return getWebDriver();
	}
}
