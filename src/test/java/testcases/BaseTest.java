package testcases;


import com.codeborne.selenide.Configuration;

import utils.XmlUtils;


public class BaseTest {

	
	public BaseTest(){
		Configuration.timeout=8000;
		Configuration.browser="chrome";
		XmlUtils.init("classes\\element.xml");
	}
}
