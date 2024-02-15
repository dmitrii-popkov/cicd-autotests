package cicd.autotests.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static java.lang.String.format;

@Slf4j
public class TestListener extends TestListenerAdapter {

	private static final String TEST_STARTED = "testStarted";
	private static final String TEST_FINISHED = "testFinished";
	private static final String TEST_FAILED = "testFailed";
	private static final String TEST_SKIPPED = "testIgnored";
	private static final String BLOCK_OPENED = "blockOpened";
	private static final String BLOCK_CLOSED = "blockClosed";

	@Override
	public void onTestFailure(ITestResult result) {
		error(getTestName(result), result.getThrowable());
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		info(BLOCK_OPENED, iTestContext.getName());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		info(BLOCK_CLOSED, iTestContext.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		info(TEST_STARTED, getTestName(result), true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		info(TEST_FINISHED, getTestName(result), result.getEndMillis() - result.getStartMillis());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		info(TEST_SKIPPED, getTestName(result));
	}

	private void info(String messageType, String testName) {
		System.out.printf("##teamcity[%s name='%s']%n", prepareMessage(messageType), testName);
	}

	private void info(String messageType, String testName, boolean captureStandardOutput) {
		System.out.printf("##teamcity[%s name='%s' captureStandardOutput='%s']%n", prepareMessage(messageType), testName, captureStandardOutput);
	}

	private void info(String messageType, String testName, long duration) {
		System.out.printf("##teamcity[%s name='%s' duration='%s']%n", prepareMessage(messageType), testName, duration);
	}

	private void error(String testName, Throwable throwable) {
		String message = prepareMessage(ExceptionUtils.getMessage(throwable));
		String stackTrace = prepareMessage(ExceptionUtils.getStackTrace(throwable));

		System.out.printf("##teamcity[%s name='%s' message='%s' details='%s']%n", TEST_FAILED, testName, message, stackTrace);
	}

	private String prepareMessage(String message) {
		message = message.replaceAll("\\[", "|[");
		message = message.replaceAll("]", "|]");
		message = message.replaceAll("\\n", "|n");
		message = message.replaceAll("\\r", "|r");

		return message.replaceAll("'", "|'");
	}

	private String getTestName(ITestResult iTestResult) {
		return format("Test: %s.%s", iTestResult.getTestClass().getRealClass().getName(), iTestResult.getMethod().getMethodName());
	}
}
