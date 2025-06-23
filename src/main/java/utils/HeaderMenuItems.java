package utils;

public enum HeaderMenuItems {
    ADD("//a[text()='ADD']"),
    LOGIN("//a[text()='LOGIN']");

    private final String locator;

    HeaderMenuItems(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
