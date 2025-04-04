package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;


public class ProductPage {

	 WebDriver driver;
	    WebDriverWait wait;

	    private By categoryLaptops = By.linkText("Laptops");
	    private By productTitles = By.cssSelector(".card-title a"); // All products under Laptops
	    private By addToCartBtn = By.linkText("Add to cart");

	    public ProductPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    public void clickLaptopsCategory() {
	        wait.until(ExpectedConditions.elementToBeClickable(categoryLaptops)).click();
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productTitles));
	    }

	    public void selectProductByName(String productName) {
	        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productTitles));
	        for (WebElement product : products) {
	            if (product.getText().equalsIgnoreCase(productName)) {
	                product.click();
	                return;
	            }
	        }
	        throw new NoSuchElementException("Product not found: " + productName);
	    }

	    public void addToCartAndAcceptAlert() {
	        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

	        // Wait and accept alert
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        String alertText = alert.getText();
	        alert.accept();

	        System.out.println("🛒 Alert received: " + alertText);
	        if (!alertText.contains("Product added")) {
	            throw new RuntimeException("Unexpected alert message: " + alertText);
	        }
	    }
	
}
