package pages;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    // родительский класс для всех классов страниц
    static WebDriver driver;

    public static void setDriver(WebDriver wd) {
        driver = wd;
        // потому что должен быть один драйвер в проекте, из ApplicationManager передает сюда
    }

    public void pause(int time) {
        try {
            Thread.sleep(time*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
