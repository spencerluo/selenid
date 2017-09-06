package testcases;

import static com.codeborne.selenide.Selenide.open;

import org.testng.annotations.Test;

public class NewTest1 {
  @Test
  public void f() {
	  open("http://www.sina.com");
  }
}
