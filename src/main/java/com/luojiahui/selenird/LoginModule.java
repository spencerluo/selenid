package com.luojiahui.selenird;
import static com.codeborne.selenide.Selenide.open;

import utils.MyWebDriver;

public class LoginModule {

	public static void login (MyWebDriver driver){
		open("http://portal.olavoice.com/open/website/login/login_show");
		driver.page("loginPage").sendKeys("username", "spencer").sendKeys("password", "asdD1234").sendKeys("yanzhengma", "abcd").click("submit");
	}
	
	
}
