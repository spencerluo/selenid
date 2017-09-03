package com.luojiahui.selenird;
import static com.codeborne.selenide.Selenide.open;

import utils.MyWebDriver;

public class LoginModule {

	private static int network = 1;
	public static void login (MyWebDriver driver){
		if(network==1){
			open("http://portal.olavoice.com/open/website/login/login_show");
			driver.page("loginPage").sendKeys("username", "spencer").sendKeys("password", "asdD1234").sendKeys("yanzhengma", "abcd").click("submit");
		}else{
			open("https://cn.olami.ai/open/website/login/login_show");
			driver.page("loginPage").sendKeys("username", "spencer").sendKeys("password", "asdD1234");
			driver.sleep(5000);
			driver.click("submit");
		}
	}
	
	
}
