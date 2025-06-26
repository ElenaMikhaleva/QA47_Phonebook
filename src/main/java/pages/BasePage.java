package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HeaderMenuItems;

import java.time.Duration;

public abstract class BasePage {
    // родительский класс для всех классов страниц
    static WebDriver driver;

    public static void setDriver(WebDriver wd) {
        driver = wd;
        // потому что должен быть один драйвер в проекте, из ApplicationManager передает сюда
    }

    public static void pause(int time) {
        try {
            Thread.sleep(time*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isElementPresent (WebElement element) {
        return element.isDisplayed();
    }

    public boolean isTextInElementPresent(WebElement element, String text) {
        return element.getText().contains(text);
    }

    public static <T extends BasePage> T clickHeaderItem(HeaderMenuItems headerMenuItem) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator()))).click();
        switch(headerMenuItem) {
            case HOME -> { return (T) new HomePage(driver); }
            case ABOUT -> { return (T) new AboutPage(driver); }
            case CONTACTS -> { return (T) new ContactsPage(driver); }
            case ADD -> { return (T) new AddPage(driver); }
            case LOGIN -> { return (T) new LoginPage(driver); }
            case SIGN_OUT -> { return (T) new LoginPage(driver); }
            default -> throw new IllegalArgumentException("Invalid parameter headerMenuItem");
        }
    }

    public boolean validateURLDoesNotContain(String str) {
        return(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.not(ExpectedConditions.urlContains(str))));
    }
}
