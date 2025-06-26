package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ContactsPage extends BasePage{
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(css = "[href='/contacts']")
    WebElement btnHeaderContacts;
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnHeaderSignOut;
    @FindBy(className = "contact-page_message__2qafk")
    WebElement messageNoContacts;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM']")
    List<WebElement> contactsList;
    @FindBy(xpath = "(//div[@class='contact-item_card__2SOIM'])[last()]")
    WebElement addedContact;
    @FindBy(css = ".contact-item-detailed_card__50dTS")
    WebElement contactCard;

    public boolean validateAddedContact(String name, String lastName, String phone, String email, String address, String descr) {
        addedContact.click();
        String[] card = contactCard.getText().split("\n");
        boolean resultName = card[0].equals(name + " " + lastName);
        boolean resultPhone = card[1].equals(phone);
        boolean resultEmail = card[2].equals(email);
        boolean resultAddress = card[3].equals(address);
        boolean resultDescr = card[4].equals("Description: " + descr);
        if (resultName && resultAddress && resultEmail && resultPhone && resultDescr)
            return true;
        else {
            if (resultName == false) System.out.println("Name EXPECTED " + name + " " + lastName + " RESULT " + card[0]);
            if (resultPhone == false) System.out.println("Phone EXPECTED " + phone + " RESULT " + card[1]);
            if (resultEmail == false) System.out.println("Email EXPECTED " + email + " RESULT " + card[2]);
            if (resultAddress == false) System.out.println("Address EXPECTED " + address + " RESULT " + card[3]);
            if (resultDescr == false) System.out.println("Descr EXPECTED " + descr + " RESULT " + card[4]);
            return false;
        }
    }

    public boolean isContactsPresent() {
        return isElementPresent(btnHeaderContacts);
    }

    public boolean isNoContactsPresent(String text) {
        return isTextInElementPresent(messageNoContacts, text);
    }

    public void clickSignOut() {
        btnHeaderSignOut.click();
    }

    public Integer getContactsListSize() {
        return contactsList.size();
    }

    public boolean validateContactNamePhone(String name, String phone) {
        for (WebElement e : contactsList) {
            if (e.getText().contains(name) && e.getText().contains(phone)) return true;
        }
        return false;
    }

    public String getPhoneFromList() {
        if (contactsList.isEmpty()) {
            return contactsList.get(0).getText().split("\n")[1];
        }
        else {
            System.out.println("getPhoneFromList: Contact list is empty");
            return null;
        }
    }
}
