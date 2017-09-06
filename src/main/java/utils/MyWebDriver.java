package utils;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.source;
import static utils.XmlUtils.getChildNodes;
import static utils.XmlUtils.getNodes;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Node;

import com.codeborne.selenide.SelenideElement;

import exception.XmlNoSuchElementException;
import exception.XmlNoSuchPageException;

public class MyWebDriver{
	private Node page = null;
	private String pageName = null;
	private static MyWebDriver driver = null;
	
	public static MyWebDriver getMyDriver(){
		if(driver == null){
			driver = new MyWebDriver();
		}
		return driver;
	}
	public void get(String absoluteUrl){
		open(absoluteUrl);;
	}
	public MyWebDriver page(String pageName){
		this.pageName = pageName;
		List<Node> pages = getNodes("page", "name", pageName);
		if (!pages.isEmpty()) {
			page = pages.get(0);
		}else{
			throw new XmlNoSuchPageException("xml文件中不存在元素{"+pageName+"}");
		}
		return getMyDriver();
	}
	
	public By by(String elementName){
		List<Node> elements = getChildNodes(page, "element", "name", elementName);
		if (elements.isEmpty()) {
			throw new XmlNoSuchElementException("xml文件中不存在元素{"+pageName+" __ "+elementName+"}");
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
		default:
			throw new XmlNoSuchPageException("不存在by类型{"+type+"}");
		}
		return by;
	}
	
	public SelenideElement getElement(String elementName){
		return $(by(elementName));
	}
	
	public MyWebDriver click(String elementName){
		try {
			getElement(elementName).click();
		} catch (AssertionError e) {
			throw new NoSuchElementException("{"+pageName+" __ "+elementName+"} is not visible\n"+e.getMessage());
		}
		return getMyDriver();
	}
	
	public MyWebDriver sendKeys(String elementName, String value){
		try {
			getElement(elementName).sendKeys(value);
		} catch (AssertionError  e) {
			throw new NoSuchElementException("{"+pageName+" __ "+elementName+"} is not visible\n"+e.getMessage());
		}
		return getMyDriver();
	}
	
	public MyWebDriver clear(String elementName){
		try {
			getElement(elementName).clear();
		} catch (AssertionError e) {
			throw new NoSuchElementException("{"+pageName+" __ "+elementName+"} is not visible\n"+e.getMessage());
		}
		return getMyDriver();
	}
	
	public String getSource(){
		return source();
	}
	
	public WebDriver getDriver(){
		return getWebDriver();
	}
	
	public void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh(){
		com.codeborne.selenide.Selenide.refresh();
	}
}
