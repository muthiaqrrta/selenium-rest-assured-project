package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PokemonDBHomePage extends PageObject {

    By searchInput = By.xpath("//input[@id='sitesearch']");

    By modalClosePan = By.xpath("//div[contains(@class, 'modal-wrapper visible')]//span");

    public PokemonDBHomePage(WebDriver driver) {
        super(driver);
    }

    public void searchPokemon(String input) {
        typeAndEnter(searchInput, input);
    }

    public boolean isPrivacyModalVisible() {
        return doesElementExist(modalClosePan);
    }

    public void clickCloseModalButton() {
        clickElement(modalClosePan);
    }
}
