package utils;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.aventstack.extentreports.*;

public class ExtentTestManager {
	
	private  static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	private static ExtentReports extent;
	
	  public static ExtentTest startTest(String testName) {
	        ExtentReports extent = ExtentTestManager.getInstance();
	        ExtentTest test = extent.createTest(testName);
	        extentTest.set(test);
	        return test;
	    }
	  public static ExtentTest getTest() {
	        return extentTest.get();
	    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir")  + "\\ExtentReport\\\\ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            reporter.config().setDocumentTitle("Demoblaze Automation Report");
            reporter.config().setReportName("Test Execution Summary");
        }
        return extent;
    }

}
