package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutAndOrderConfirmationPage {
	
	WebDriver driver;
    WebDriverWait wait;

    private By cartLink = By.id("cartur");
    private By placeOrderBtn = By.xpath("//button[text()='Place Order']");
    private By nameInput = By.id("name");
    private By countryInput = By.id("country");
    private By cityInput = By.id("city");
    private By cardInput = By.id("card");
    private By monthInput = By.id("month");
    private By yearInput = By.id("year");
    private By purchaseBtn = By.xpath("//button[text()='Purchase']");
    private By confirmationModal = By.className("sweet-alert"); // or .showSweetAlert
    private By okBtn = By.xpath("//button[text()='OK']");

    public CheckoutAndOrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
    }

    public void placeOrder(String name, String country, String city, String card, String month, String year) {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        driver.findElement(countryInput).sendKeys(country);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(cardInput).sendKeys(card);
        driver.findElement(monthInput).sendKeys(month);
        driver.findElement(yearInput).sendKeys(year);

        driver.findElement(purchaseBtn).click();
    }

    public boolean isConfirmationDisplayed() {
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
            return modal.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(okBtn)).click();
    }

}
