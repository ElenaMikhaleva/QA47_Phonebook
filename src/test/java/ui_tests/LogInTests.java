package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class LogInTests extends ApplicationManager {

    @Test
    public void regPositiveTest() {
        User user = new User("elena12@gmail.com", "Password$1");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeRegForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isContactsPresent());
    }

    @Test
    public void regNegativeTest_invalidUsername() {
        User user = new User("elena12gmail.com", "Password$1");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeRegForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Registration failed with code 400"));
    }

    @Test
    public void regNegativeTest_invalidPassword() {
        User user = new User("elena123@gmail.com", "Password");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeRegForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Registration failed with code 400"));
    }

    @Test
    public void loginPositiveTest() {
        User user = new User("elenam@gmail.com", "Password$1");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isContactsPresent());
    }

    @Test
    public void loginNegativeTest_invalidUsername() {
        User user = new User("elenamgmail.com", "Password$1");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_invalidPassword() {
        User user = new User("elenam@gmail.com", "Password");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

}

