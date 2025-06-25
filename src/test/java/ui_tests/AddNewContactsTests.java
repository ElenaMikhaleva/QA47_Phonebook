package ui_tests;

import dto.Contact;
import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.HeaderMenuItems;

import static pages.BasePage.clickHeaderItem;
import static utils.RandomUtils.*;

public class AddNewContactsTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int sizeBeforeAdd;

    @BeforeMethod
    public void login() {
        User user = new User("pippin@mail.com", "WhatAbout#2Breakfast");
        homePage = new HomePage(getDriver());
        loginPage = clickHeaderItem(HeaderMenuItems.LOGIN);
        loginPage.typeLoginForm(user);
        contactsPage = new ContactsPage(getDriver());
        sizeBeforeAdd = contactsPage.getContactsListSize();
        addPage = clickHeaderItem(HeaderMenuItems.ADD);
    }

    @Test
    public void addContactPositiveTest() {
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
        int sizeAfterAdd = contactsPage.getContactsListSize();
        System.out.println("addContactPositiveTest: list size: " + sizeBeforeAdd + " --> " + sizeAfterAdd);
//        boolean validationSize = sizeAfterAdd == sizeBeforeAdd+1;
//        softAssert.assertTrue(validationSize);
        boolean validation = contactsPage.validateContactNamePhone(contact.getName(), contact.getPhone());
        Assert.assertTrue(validation);
    }
}
