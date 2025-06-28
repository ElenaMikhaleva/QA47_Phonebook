package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import static utils.RandomUtils.generateEmail;

@Listeners(TestNGListener.class)

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
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("Password$1")
                .build();
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isNoContactsPresent("No Contacts here!"));
    }

    @Test
    public void regNegativeTest_invalidUsername() {
        UserLombok user = UserLombok.builder()
                .username("elena12gmail.com")
                .password("Password$1")
                .build();
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

    @Test
    public void regNegativeTest_duplicateUsername() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("Password$1")
                .build();
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
        UserLombok user = UserLombok.builder()
                .username("elena123@gmail.com")
                .password("Password")
                .build();
        goToRegistrationPage();
        loginPage.typeRegForm(user);
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }
}
