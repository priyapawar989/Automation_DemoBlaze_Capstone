package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignUpPage {
	
	WebDriver driver;
	WebDriverWait wait;

    // Locators
    private By signUpButton = By.id("signin2");
    private By usernameField = By.id("sign-username");
    private By passwordField = By.id("sign-password");
    private By submitButton = By.xpath("//button[text()='Sign up']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openSignUpModal() {
        driver.findElement(signUpButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
    }

    

    public void enterUsername(String username) {
        WebElement userField = driver.findElement(usernameField);
        userField.clear();
        userField.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passField = driver.findElement(passwordField);
        passField.clear();
        passField.sendKeys(password);
    }
   
    public void clickSignUp() {
        driver.findElement(submitButton).click();
    }

    public void signUp(String username, String password) {
        openSignUpModal();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        enterUsername(username);
        enterPassword(password);
        clickSignUp();
    }

}
