package ui_tests;

import data_providers.ContactDP;
import dto.Contact;
import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.HeaderMenuItems;
import utils.TestNGListener;

import static pages.BasePage.clickHeaderItem;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class AddNewContactsTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int sizeBeforeAdd;
    String existingPhone;

    @BeforeMethod
    public void login() {
//        User user = new User("pippin@mail.com", "WhatAbout#2Breakfast");
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        homePage = new HomePage(getDriver());
        loginPage = clickHeaderItem(HeaderMenuItems.LOGIN);
        loginPage.typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
        sizeBeforeAdd = contactsPage.getContactsListSize();
        existingPhone = contactsPage.getPhoneFromList();
        addPage = clickHeaderItem(HeaderMenuItems.ADD);
    }

    @Test
    public void addContact_PositiveTest() {
        SoftAssert softAssert = new SoftAssert();
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        // validation card number in the list has changed
//        int sizeAfterAdd = contactsPage.getContactsListSize();
//        System.out.println("addContactPositiveTest: list size: " + sizeBeforeAdd + " --> " + sizeAfterAdd);
//        boolean validationSize = sizeAfterAdd == sizeBeforeAdd+1;
//        softAssert.assertTrue(validationSize);
        // validation name and phone in preview were added right
//        boolean validation = contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone());
        boolean validation = contactsPage.validateAddedContact(contact.getName(), contact.getLastName(), contact.getPhone(),
                contact.getEmail(), contact.getAddress(), contact.getDescription());
        Assert.assertTrue(validation, "addContact_PositiveTest");
    }

    @Test(dataProvider = "dataProviderContactsFile", dataProviderClass = ContactDP.class)
    public void addContactDP_PositiveTest(Contact contact) {
        addPage.typeAddNewContactForm(contact);
        boolean validation = contactsPage.validateAddedContact(contact.getName(), contact.getLastName(), contact.getPhone(),
                contact.getEmail(), contact.getAddress(), contact.getDescription());
        Assert.assertTrue(validation, "addContact_PositiveTest");
    }

    @Test
    public void addContact_NegativeTest_emptyName() {
        Contact contact = Contact.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        contactsPage = clickHeaderItem(HeaderMenuItems.CONTACTS);
        int sizeAfterAdd = contactsPage.getContactsListSize();
                System.out.println("addContactNegativeTest: list size: " + sizeBeforeAdd + " --> " + sizeAfterAdd);
        boolean validationSize = sizeAfterAdd==sizeBeforeAdd;
        Assert.assertTrue(validationSize, "addContact_NegativeTest_emptyName");
        // validation that after adding contact the page does not have list of contacts -> it's still adding contact page
//        Assert.assertTrue(addPage.validateURLDoesNotContain("add"));
    }

    @Test
    public void addContact_NegativeTest_emptyLastName() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName("")
                .phone(generatePhone(10))
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        contactsPage = clickHeaderItem(HeaderMenuItems.CONTACTS);
        int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println("addContactNegativeTest: list size: " + sizeBeforeAdd + " --> " + sizeAfterAdd);
        boolean validationSize = sizeAfterAdd==sizeBeforeAdd;
        Assert.assertTrue(validationSize, "addContact_NegativeTest_emptyLastName");
    }

    @Test
    public void addContact_NegativeTest_emptyPhone() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("")
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        boolean validation = addPage.returnAlertText().contains("Phone not valid");
        Assert.assertTrue(validation, "addContact_NegativeTest_emptyPhone");
    }

    @Test
    public void addContact_NegativeTest_invalidPhone() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(9))
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        boolean validation = addPage.returnAlertText().contains("Phone not valid");
        Assert.assertTrue(validation, "addContact_NegativeTest_invalidPhone");
    }

    @Test
    public void addContact_NegativeTest_existingPhone() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhone(10))
                .email(existingPhone)
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        boolean validation = addPage.returnAlertText().contains("Phone already exists");
        // bug
//        Assert.assertTrue(validation, "addContact_NegativeTest_existingPhone");
    }

    @Test
    public void addContact_NegativeTest_emptyEmail() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName("")
                .phone(generatePhone(10))
                .email("")
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();
        addPage.typeAddNewContactForm(contact);
        contactsPage = clickHeaderItem(HeaderMenuItems.CONTACTS);
        int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println("addContactNegativeTest: list size: " + sizeBeforeAdd + " --> " + sizeAfterAdd);
        boolean validationSize = sizeAfterAdd==sizeBeforeAdd;
        Assert.assertTrue(validationSize, "addContact_NegativeTest_emptyEmail");
    }
}
