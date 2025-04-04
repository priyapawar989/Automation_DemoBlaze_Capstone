package utils;

import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenshotHelper {
	
	public static String captureScreenshot(WebDriver driver, String screenshotName) {

		
		 TakesScreenshot ts = (TakesScreenshot) driver;
		    File source = ts.getScreenshotAs(OutputType.FILE);
		//    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		    String path = System.getProperty("user.dir") +"/Screenshots/" + screenshotName   + ".png";

		    try {
		        FileUtils.copyFile(source, new File(path));
		        System.out.println("📸 Screenshot captured: " + path);
		    } catch (IOException e) {
		        System.out.println("❌ Failed to save screenshot: " + e.getMessage());
		    }
		    return path;
		}
			
	
//	public static String capture(WebDriver driver) {
//        // Generate timestamp
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        // Create screenshots folder if not exist
//        File screenshotDir = new File("screenshots");
//        if (!screenshotDir.exists()) {
//            screenshotDir.mkdir();
//        }
//
//        // Define file path
//        String filePath = System.getProperty("user.dir") +"/Screenshots/" + timestamp   + ".png";
//
//        // Capture and save screenshot
//        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        File dest = new File(filePath);
//
//        try {
//            Files.copy(src.toPath(), dest.toPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return filePath;
//    }

}
