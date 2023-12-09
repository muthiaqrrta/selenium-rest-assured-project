package org.example;

import io.restassured.response.Response;
import org.example.data.PokemonDBData;
import org.example.page.PokemonDBDetailPage;
import org.example.page.PokemonDBHomePage;
import org.example.page.PokemonDBSearchPage;
import org.example.response.PokeAPIResponse;
import org.example.response.PokeAPITypeResponse;
import org.example.response.PokeAPITypesResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AppTest {

    WebDriver webDriver;

    String pokemonDBUrl = "https://pokemondb.net/";
    String pokeApiUrl = "https://pokeapi.co/api/v2/pokemon/";
    String pokemonName;

    @Before
    public void intiate() {
        System.setProperty("webdriver.chrome.driver", "D:\\Aplikasi\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    public AppTest(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        List<String> listPokemon = Arrays.asList("pikachu", "charizard", "eevee");
        Collection<Object[]> params = new ArrayList<>(listPokemon.size());
        for (String s : listPokemon) {
            params.add(new Object[]{s});
        }
        return params;
    }

    @Test
    public void comparePokemon() {
        System.out.println("pokemon name: " + pokemonName);

        PokemonDBHomePage pokemonDBHomePage = new PokemonDBHomePage(webDriver);
        pokemonDBHomePage.openPageAt(pokemonDBUrl);

        if (pokemonDBHomePage.isPrivacyModalVisible()) {
            pokemonDBHomePage.clickCloseModalButton();
        }

        pokemonDBHomePage.searchPokemon(pokemonName);

        if (pokemonDBHomePage.isPrivacyModalVisible()) {
            pokemonDBHomePage.clickCloseModalButton();
        }

        PokemonDBSearchPage pokemonDBSearchPage = new PokemonDBSearchPage(webDriver);

        if (!pokemonDBSearchPage.doesNoResultBoxExists()) {
            pokemonDBSearchPage.clickFirstResultLink();
        }

        PokemonDBDetailPage pokemonDBDetailPage = new PokemonDBDetailPage(webDriver);

        PokemonDBData pokemonDBData = PokemonDBData.builder()
                .name(pokemonDBDetailPage.getPokemonName())
                .number(pokemonDBDetailPage.getPokemonNumber())
                .types(pokemonDBDetailPage.getPokemonTypes())
                .build();

        System.out.println(pokemonDBData);

        Response getResponse = given()
                .baseUri(pokeApiUrl)
                .accept("application/json")
                .contentType("application/json")
                .pathParam("pokemonName", pokemonName)
                .when().log().all().get(pokeApiUrl + "{pokemonName}");
        getResponse.prettyPrint();

        PokeAPIResponse pokeAPIResponse = getResponse.getBody().as(PokeAPIResponse.class);

        assertEquals("pokemon name is different with expected",
                pokemonDBData.getName().toLowerCase(), pokeAPIResponse.getName().toLowerCase());

        List<String> pokeAPITypes = pokeAPIResponse.getTypes().stream()
                .map(PokeAPITypesResponse::getType)
                .map(PokeAPITypeResponse::getName)
                .collect(Collectors.toList());

        assertEquals("pokemon types is different with expected",
                pokemonDBData.getTypes(), pokeAPITypes);

        assertEquals("pokemon number is different with expected",
                pokemonDBData.getNumber(), pokeAPIResponse.getId());
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
