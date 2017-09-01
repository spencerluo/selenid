package com.luojiahui.selenird;

import org.testng.annotations.Test;

import utils.MyWebDriver;

public class NewTest1 {
  @Test
  public void f() {
	  MyWebDriver driver = MyWebDriver.getMyDriver();
	  LoginModule.login(driver);
  }
}
