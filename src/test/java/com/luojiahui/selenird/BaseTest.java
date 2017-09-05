package com.luojiahui.selenird;


import com.codeborne.selenide.Configuration;

import utils.ReadExcel;
import utils.XmlUtils;


public class BaseTest {

	
	public BaseTest(){
		Configuration.timeout=8000;
		Configuration.browser="chrome";
		XmlUtils.init("config\\element.xml");
	}
}
