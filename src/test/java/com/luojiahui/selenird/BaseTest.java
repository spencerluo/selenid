package com.luojiahui.selenird;


import com.codeborne.selenide.Configuration;

import utils.XmlUtils;


public class BaseTest {

	
	public BaseTest(){
		Configuration.timeout=5000;
		Configuration.browser="chrome";
		XmlUtils.init("config\\element.xml");
	}
}
