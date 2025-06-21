package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

import static utils.RandomUtils.generateEmail;

public class RegistrationTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        homePage = new HomePage(getDriver());
        homePage.clickBtnHeaderLogin();
        loginPage = new LoginPage(getDriver());
        // @BeforeMethod(ApplicationManager -> @BeforeMethod(RegistrationTests) -> @Test -> @AfterMethod(RegistrationTests) -> @ApplicationManager
    }

    @Test
    public void regPositiveTest() {
        User user = new User(generateEmail(5), "Password$1");
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isNoContactsPresent("No Contacts here!"));
    }

    @Test
    public void regNegativeTest_invalidUsername() {
        User user = new User("elena12gmail.com", "Password$1");
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void regNegativeTest_duplicateUsername() {
        User user = new User(generateEmail(5), "Password$1");
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        if (contactsPage.isNoContactsPresent("No Contacts here!")) {
            contactsPage.clickSignOut();
            loginPage.typeRegForm(user);
            Assert.assertTrue(loginPage.closeAlertReturnText().contains("User already exist"));
        }
        else {
            Assert.fail("unsuccessful registration with user " + user.toString());
        }
    }

    @Test
    public void regNegativeTest_invalidPassword() {
        User user = new User("elena123@gmail.com", "Password");
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }
}
