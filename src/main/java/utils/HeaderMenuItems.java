package utils;

public enum HeaderMenuItems {
    HOME("//a[text()='HOME']"),
    ABOUT("//a[text()='ABOUT']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ADD("//a[text()='ADD']"),
    LOGIN("//a[text()='LOGIN']"),
    SIGN_OUT("//button[text()='Sign Out']");

    private final String locator;

    HeaderMenuItems(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
