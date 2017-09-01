package utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;


import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import io.qameta.allure.Attachment;

public class AllureReporterListener implements IHookable {

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {

        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            takeScreenShot(testResult.getMethod().getMethodName());
        }
    }


    @Attachment(value = "Failure in method {0}", type = "image/png")
	public static byte[] takeScreenShot(String methodName) {
		TakesScreenshot tss = (TakesScreenshot) getWebDriver();
		return tss.getScreenshotAs(OutputType.BYTES);
	}
}
