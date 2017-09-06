package modules;
import static com.codeborne.selenide.Selenide.open;

import utils.MyWebDriver;

public class LoginModule {

	private static int network = 1;
	public static void login (MyWebDriver driver, String username, String password){
		if(network==1){
			driver.get("http://portal.olavoice.com/open/website/login/login_show");
			driver.page("loginPage").sendKeys("username", username).sendKeys("password", password).sendKeys("yanzhengma", "abcd").click("submit");
		}else{
			open("https://cn.olami.ai/open/website/login/login_show");
			driver.page("loginPage").sendKeys("username", "spencer").sendKeys("password", "asdD1234");
			driver.sleep(5000);
			driver.click("submit");
		}
	}
	
}
