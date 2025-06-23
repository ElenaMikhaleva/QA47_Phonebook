package ui_tests;

import dto.Contact;
import dto.User;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.HeaderMenuItems;

import static pages.BasePage.clickHeaderButtons;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class AddNewContactsTests extends ApplicationManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;

    @BeforeMethod
    public void login() {
        User user = new User("pippin@mail.com", "WhatAbout#2Breakfast");
        homePage = new HomePage(getDriver());
        loginPage = clickHeaderButtons(HeaderMenuItems.LOGIN);
        loginPage.typeLoginForm(user);
        addPage = clickHeaderButtons(HeaderMenuItems.ADD);
    }

    @Test
    public void addContactPositiveTest() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123467853")
                .email(generateEmail(5))
                .address("Earth " + generateString(5))
                .description(generateString(15))
                .build();

    }
}
