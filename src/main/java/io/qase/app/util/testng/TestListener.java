package io.qase.app.util.testng;

import io.qase.app.util.allure.AllureUtil;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.printf("========= STARTING TEST %s ==============%n", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.printf("========= FINISHED TEST %s Duration: %ss ===========%n", result.getName(),
                getExecutionTime(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.printf("============ SKIPPING TEST %s ===========%n", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.printf("======= FAILED TEST %s Duration: %ss ========%n", result.getName(),
                getExecutionTime(result));
        takeScreenshot(result);
    }

    private long getExecutionTime(ITestResult result) {
        return TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
    }

    private byte[] takeScreenshot(ITestResult result) {
        ITestContext context = result.getTestContext();
        try {
            WebDriver driver = (WebDriver) context.getAttribute("driver");
            if (driver != null) {
                return AllureUtil.takeScreenshot(driver);
            } else {
                return new byte[]{};
            }
        } catch (NoSuchSessionException | IllegalStateException ex) {
            return new byte[]{};
        }
    }
}
