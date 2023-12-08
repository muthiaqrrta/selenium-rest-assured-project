package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PokemonDBSearchPage extends PageObject {

    By noResultsBox = By.xpath("//div[@class='gs-snippet' and text()='No Results']");

    By searchResultLinks = By.xpath("//div[@class='gsc-thumbnail-inside']//a");

    By searchResultLinksBoldText = By.xpath("//div[@class='gsc-thumbnail-inside']//a/b");

    By modalCloseSpan = By.xpath("//div[contains(@class, 'modal-wrapper visible')]//span");

    public PokemonDBSearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean doesNoResultBoxExists() {
        return doesElementExist(noResultsBox);
    }

    public String getFirstResultBoldText() {
        return getTextFromElements(searchResultLinks).get(0);
    }

    public void clickCloseModalButton() {
        clickElement(modalCloseSpan);
    }

    public boolean isPrivacyModalVisible() {
        return doesElementExist(modalCloseSpan);
    }

    public void clickFirstResultLink() {
        clickElement(getElements(searchResultLinks).get(0));
    }
}
