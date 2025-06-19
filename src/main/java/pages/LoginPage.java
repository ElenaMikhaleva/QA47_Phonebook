package pages;

import dto.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

    @FindBy(xpath = "//div[@class='login_login__3EHKB']/div")
    WebElement errorMessage;

    public void typeLoginForm(User user) {
    inputEmail.sendKeys(user.getUsername());
    inputPassword.sendKeys(user.getPassword());
    btnLogIn.click();
    }

    public void closeAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        // ждет, пока алерт не появится (alertIsPresent) 5 секунд (WebDriverWait - explicitly wait)
        System.out.println(alert.getText());
        alert.accept();
        // кликает на кнопку ok в алерте, чтобы закрыть его
    }

    public boolean isErrorMessagePresent(String text) {
        return errorMessage.getText().contains(text);

    }
}
