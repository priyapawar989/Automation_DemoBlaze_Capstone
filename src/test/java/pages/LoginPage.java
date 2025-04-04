package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginPage {
	
	WebDriver driver;
    WebDriverWait wait;

    private By loginNavBtn = By.id("login2");
    private By loginModal = By.id("logInModal");
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By loginBtn = By.xpath("//button[text()='Log in']");
    private By welcomeMsg = By.id("nameofuser");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginModal() {
        wait.until(ExpectedConditions.elementToBeClickable(loginNavBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
    }

    public void login(String username, String password) {
        openLoginModal();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
    
    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMsg)).getText();
    }


    public String getAlertTextIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (TimeoutException e) {
            return null;
        }


    }
}
