package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static utils.PropertiesReader.getProperty;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        setDriver(driver);
//        driver.get("https://telranedu.web.app/home");
        driver.get(getProperty("login.properties", "url"));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(css = "a[href='/login']")
    WebElement btnHeaderLogin;

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessageLogin;

    public void clickBtnHeaderLogin() {
        btnHeaderLogin.click();
    }


}


