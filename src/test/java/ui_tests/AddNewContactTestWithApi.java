package ui_tests;

import dto.Contact;
import dto.ContactsDto;
import dto.User;
import io.restassured.response.Response;
import manager.ApplicationManager;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.HeaderMenuItems;

import static pages.BasePage.clickHeaderItem;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class AddNewContactTestWithApi extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;

    @BeforeMethod
    public void login() {
//        User user = new User("pippin@mail.com", "WhatAbout#2Breakfast");
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        homePage = new HomePage(getDriver());
        loginPage = clickHeaderItem(HeaderMenuItems.LOGIN);
        loginPage.typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
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
        BasePage.pause(15);
        ContactController contactController = new ContactController();
        contactController.login();
        Response response = contactController.getAllContacts();
        System.out.println(response.getStatusLine());
        ContactsDto contactsDto = new ContactsDto();
        if (response.getStatusCode() == 200) {
            contactsDto = response.body().as(ContactsDto.class);
        }
        for (Contact c : contactsDto.getContacts()) {
            if (c.equals(contact)) {
                System.out.println("c: " + c);
                System.out.println("contact: " + contact);
//                Assert.assertEquals(c, contact);
            }
            else Assert.fail();
        }
    }
}
