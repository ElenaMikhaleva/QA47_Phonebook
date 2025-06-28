package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import java.lang.reflect.Method;
import java.util.Random;

import static utils.RandomUtils.generateEmail;

@Listeners(TestNGListener.class)

public class LogInTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void goToLogInPage() {
        homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        loginPage = new LoginPage(getDriver());
        // @BeforeMethod(ApplicationManager -> @BeforeMethod(RegistrationTests) -> @Test -> @AfterMethod(RegistrationTests) -> @ApplicationManager
    }

    @Test
    public void loginPositiveTest(Method method) {
        logger.info("start method " + method.getName());
        User user = new User("elenam@gmail.com", "Password$1");
        logger.info("test data ------> " + user.toString());
        goToLogInPage();
        loginPage.typeLoginForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isContactsPresent());
    }

    @Test
    public void loginNegativeTest_invalidUsername() {
        User user = new User("elenamgmail.com", "Password$1");
        goToLogInPage();
        loginPage.typeLoginForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

    @Test
    public void loginNegativeTest_invalidPassword() {
        User user = new User("elenam@gmail.com", "Password");
        goToLogInPage();
        loginPage.typeLoginForm(user);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessagePresent("Login Failed with code 401"));
    }

}

