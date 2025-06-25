package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

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
}
