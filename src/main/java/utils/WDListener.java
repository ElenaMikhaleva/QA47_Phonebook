package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class WDListener implements WebDriverListener {

    Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void afterGet(WebDriver driver, String url) {
        // вызывается, когда пользуемся методом get (когда создаем HomePage)
        WebDriverListener.super.afterGet(driver, url);
        logger.info("open page -> " + url);
    }

    @Override
    public void beforeFindElement(WebElement element, By locator) {
        // когда вызываем метод findElement
        WebDriverListener.super.beforeFindElement(element, locator);
        logger.info("find element with locator -> " + locator.toString());
    }

    @Override
    public void afterClick(WebElement element) {
        // после клика на элемент
        WebDriverListener.super.afterClick(element);
        logger.info("click to element -> <" + element.getTagName() + ">");
    }



    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        // после введения данных
        WebDriverListener.super.afterSendKeys(element, keysToSend);
        logger.info("send keys <" + element.getTagName() + "> ->" + Arrays.toString(keysToSend));
    }
}
