package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LogInTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm("elenam@gmail.com", "Password$1");
    }
}

