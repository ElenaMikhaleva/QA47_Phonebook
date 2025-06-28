package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {

    Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        // будет вызван, когда будет запущен любой метод с аннотацей @Test
        ITestListener.super.onTestStart(result);
        logger.info("-----------------------------------------------------------");
        logger.info("START TEST -> " + result.getMethod());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        logger.info("SUCCESSFUL TEST -> " + result.getMethod());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        logger.info("FAILED TEST -> " + result.getMethod());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        logger.info("SKIPPED TEST -> " + result.getMethod());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // дин тест запускается несколько раз (например, flaky test), если больше какого-то процента тестов упало (30%), то тест failed
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // когда запускается тестирование
        ITestListener.super.onStart(context);
        logger.info("TESTING STARTED -----> " + context.getStartDate());

    }

    @Override
    public void onFinish(ITestContext context) {
        // когда тестирование заканчивается
        ITestListener.super.onFinish(context);
        logger.info("TESTING FINISHED -----> " + context.getStartDate());
    }
}
