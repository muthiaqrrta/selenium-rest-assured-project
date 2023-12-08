package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class PokemonDBDetailPage extends PageObject {

    By pokemonName = By.xpath("//main[@id='main']//h1");

    By pokemonTypesLinks = By.xpath("(//h2[text()='Pokédex data']//following::table)[1]//a[contains(@class, 'type-icon')]");

    By pokemonNumberStrong = By.xpath("(//h2[text()='Pokédex data']//following::table)[1]//strong");

    public PokemonDBDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getPokemonName() {
        return getTextFromElement(pokemonName);
    }

    public List<String> getPokemonTypes() {
        List<String> pokemonTypes = getTextFromElements(pokemonTypesLinks);

        return pokemonTypes.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public int getPokemonNumber() {
        return Integer.parseInt(getTextFromElement(pokemonNumberStrong));
    }

}
