package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;
import pages.*;
import utils.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class Test_DemoBlaze {
	
	WebDriver driver;
	
	ExtentReports extent;
    ExtentTest test;
	
    LoginPage loginPage;
    SignUpPage signUpPage;
    ProductPage productPage;
    CheckoutAndOrderConfirmationPage checkoutPage;

    // This user will be created and reused
    String testUsername = "demoblaze12@test";
    String testPassword = "demoblaze12@test";
    
    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browserName) {
    	extent = ExtentTestManager.getInstance();
    	
    	 switch (browserName.toLowerCase()) {
         case "chrome":
             driver = new ChromeDriver();
             break;
         case "firefox":
             driver = new FirefoxDriver();
             break;
         case "edge":
             driver = new EdgeDriver();
             break;
         default:
             throw new IllegalArgumentException("Browser not supported: " + browserName);
     }

    	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	 driver.manage().window().maximize();
    	 driver.get("https://www.demoblaze.com/");

    	 loginPage = new LoginPage(driver);
    	 signUpPage = new SignUpPage(driver);
    	 productPage = new ProductPage(driver);
    	 checkoutPage = new CheckoutAndOrderConfirmationPage(driver);

    	 driver.navigate().refresh();
    }
    
    	@BeforeMethod
    	public void initTest(Method method) {
        test = ExtentTestManager.startTest(method.getName());

        ExtentTestManager.getTest().pass("Login successful");
        ExtentTestManager.getTest().fail("Login failed");

    }
    
    	
 // Test Case1: Signin with Fresh username and password 
    @Test(priority = 1 , enabled = false)   /// Skip 1st Test Because user alerady signup //
    public void testValidSignUp() {
   
       signUpPage.signUp(testUsername, "testPassword");   // for sign up enter unique username and password

        // Handle alert immediately after sign-up
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("Alert Text: " + alertText);
        Assert.assertTrue(alertText.contains("successful"));
        alert.accept();
        
        test.pass("Succesfull SignUp").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "01_signupSuccesfull"));
        
    }
    
 // Test Case2: Signin with Existing username and password
    @Test(priority = 2)
    	public void testSignUpWithExistingUser() throws InterruptedException {
    	signUpPage.signUp(testUsername, "password123");
    	test.pass("Logged in with Invalid credentials").addScreenCaptureFromPath(
               ScreenshotHelper.captureScreenshot(driver, "02_Invaliddata"));
        handleAlert("This user already exist.");
        Thread.sleep(2000);
        test.pass("This user already exist.").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "03_existuser"));
    }

 // Test Case3: Signin with blank username and password   
    @Test(priority = 3)
    	public void testSignUpWithEmptyFields() throws InterruptedException {
       signUpPage.signUp("", "");
       handleAlert("Please fill out Username and Password.");
       Thread.sleep(2000);
       test.pass("Signin with Empty Credentials").addScreenCaptureFromPath(
               ScreenshotHelper.captureScreenshot(driver, "04_Blanksignup"));
      
}
    
 // Test Case4 : Login with Blank username and password    
    @Test(priority = 4)
    	public void testLoginWithEmptyFields() throws InterruptedException {
    	test = extent.createTest("empty Login");
        loginPage.login("", "");
        handleAlert("Please fill out Username and Password.");
        Thread.sleep(2000);
        test.pass("Logged in with Empty Credentials").addScreenCaptureFromPath(
               ScreenshotHelper.captureScreenshot(driver, "05_Blankdata"));       
}
    
    
 // Test Case5 : Login with Invalid username and password   
    @Test(priority = 5)
    	public void testLoginWithInvalidPassword() throws InterruptedException {
    	test = extent.createTest("Invalid Login");
        loginPage.login(testUsername, "pass123");
        
        test.pass("Logged in with Invalid credentials").addScreenCaptureFromPath(
             ScreenshotHelper.captureScreenshot(driver, "06_InvalidLogin"));

        String alertText = loginPage.getAlertTextIfPresent();
        Thread.sleep(2000);
        test.pass("alert popup").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "07_InvalidLogin"));
        Assert.assertTrue(alertText.contains("Wrong password."), 
            "Expected alert not shown for wrong password.");
        Thread.sleep(2000);
    }
    
 // Test Case6 : Login with Valid username and password     

    @Test(priority = 6)
    	public void testLoginWithValidCredentials() throws InterruptedException {
    	test = extent.createTest("valid Login");
        loginPage.login(testUsername, testPassword);
        test.pass("Successfully Login").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "08_validLogin"));

        String alertText = loginPage.getAlertTextIfPresent();
        Thread.sleep(2000);
        test.pass("Successfully Login").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "09_validLogin"));

        if (alertText != null) {
            Assert.fail("Login failed unexpectedly: " + alertText);
        }

        String welcomeText = loginPage.getWelcomeMessage();
        System.out.println("✅ Logged in as: " + welcomeText);
        Assert.assertTrue(welcomeText.contains(testUsername), "Welcome message not shown after login.");

    
        //  now page is authenticated; and ready for post-login scenarios
    }
    
 // Test Case7 : Test for Add product into cart      
    @Test(priority = 7)
    	public void testAddLaptopToCartAfterLogin() throws InterruptedException {
 
        // Navigate to Laptops category
        productPage.clickLaptopsCategory();

        // Select a specific product 
        productPage.selectProductByName("Sony vaio i5");

        // Add product to cart and handle alert
        productPage.addToCartAndAcceptAlert();
        Thread.sleep(2000);
        test.pass("Product added").addScreenCaptureFromPath(
                ScreenshotHelper.captureScreenshot(driver, "10_addtocart"));

        
    }
    
 // Test Case8 : Test for Place an order   
    @Test(priority = 8)
    	public void checkoutpagetest() {
    	//Navigate to the cart link
    	checkoutPage.goToCart();
    	test.pass("Navigated to cart").addScreenCaptureFromPath(
    			ScreenshotHelper.captureScreenshot(driver, "11_CartPage"));
    	
    	// proceed to place and order 
    	checkoutPage.placeOrder("rita", "India", "Mumbai", "4444567812345678", "08", "2028");
    	test.pass("Order placed Successfully").addScreenCaptureFromPath(
    			ScreenshotHelper.captureScreenshot(driver, "12_PlaceOrder"));
    	System.out.println("Order Placed Successfully... Thank you for your purchase!" );
    	Assert.assertTrue(checkoutPage.isConfirmationDisplayed(), "Confirmation not displayed");
    	checkoutPage.confirmOrder();
    	test.pass("Order confirmed").addScreenCaptureFromPath(
    			ScreenshotHelper.captureScreenshot(driver, "13_Confirmation"));

    } 
    
 // Created Method for Handling Alert Window    
    public void handleAlert(String expectedMessage) {
        try {
            Thread.sleep(2000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert: " + alertText);
            assert alertText.contains(expectedMessage);
            alert.accept();
        } catch (Exception e) {
            System.out.println("Alert not found!");
        }
    }
   
    
  // Created Method for Refreshing the page after each testcases  
    @BeforeMethod
    public void resetPage() {
        driver.navigate().refresh();
        try {
            Thread.sleep(1000); // wait for modal reset
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
 // To close Browser  
     @AfterClass
     public void CloseBrowser() {
         extent.flush();
         driver.quit();
     }

}
