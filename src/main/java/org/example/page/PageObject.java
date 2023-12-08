package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class PageObject {

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 5);
    }

    public void openPageAt(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void clickElement(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element))
                .click();
    }
    public void clickElement(By locator) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(getElement(locator)))
                .click();
    }

    public String getTextFromElement(By locator) {
        return getElement(locator).getText();
    }

    public List<String> getTextFromElements(By locator) {
        return getElements(locator).stream().map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void typeAndEnter(By locator, String input) {
        getElement(locator).sendKeys(input, Keys.ENTER);
    }

    public boolean doesElementExist(By locator) {
        try {
            getElement(locator).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void waitForElementToStale(WebElement element) {
        try {
            webDriverWait.until(ExpectedConditions.stalenessOf(element));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void scrollToElement(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                    getElement(locator));
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
