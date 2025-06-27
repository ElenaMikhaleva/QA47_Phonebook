package pages;

import dto.User;
import dto.UserLombok;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(css = "input[name='email']")
    WebElement inputEmail;

    @FindBy(css = "input[name='password']")
    WebElement inputPassword;

    @FindBy(css = "button[name='login']")
    WebElement btnLogIn;

    @FindBy(css = "button[name='registration']")
    WebElement btnReg;

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessage;

    public void typeLoginForm(User user) {
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
        logger.info("click btnLogin <" + btnLogIn.getTagName() + ">");
        btnLogIn.click();
    }

    public void typeRegForm(UserLombok user) {
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
        btnReg.click();
    }

    public void closeAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        // ждет, пока алерт не появится (alertIsPresent) 5 секунд (WebDriverWait - explicitly wait)
        System.out.println(alert.getText());
        alert.accept();
        // кликает на кнопку ok в алерте, чтобы закрыть его
    }

    public String closeAlertReturnText() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public boolean isErrorMessagePresent(String text) {
        return isTextInElementPresent(errorMessage, text);
    }
}
