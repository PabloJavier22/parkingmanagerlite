package com.hormigo.david.parkingmanager.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hormigo.david.parkingmanager.bdd.CucumberConfiguration;
import com.hormigo.david.parkingmanager.user.service.UserService;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class CucumberSteps extends CucumberConfiguration {

    @MockBean
    private UserService userService;
    @Value("${local.server.port}")
    private int port;
    private static ChromeDriver driver;

    @BeforeAll
    public static void prepareWebDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

    }

    @Given("un usuario está en la página inicial")
    public void openHome() {
        driver.get("http://localhost:" + port + "/");
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/"));
    }

    @Then("se muestra la página de inicio")
    public void imAtHome() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/"));
        WebElement Button = driver.findElement(By.id("to-users-link"));
        WebElement Button2 = driver.findElement(By.id("to-draws-link"));
        WebElement Button3 = driver.findElement(By.id("to-home-link"));
        assertAll("Home",
                () -> {
                    assert (Button.isDisplayed());
                },
                () -> {
                    assert (Button2.isDisplayed());
                },
                () -> {
                    assert (Button3.isDisplayed());
                });
    }


  /** Users */

    @When("el usuario hace clic sobre el botón de Usuarios")
    public void clickUserButton() {
        driver.findElement(By.id("to-users-link")).click();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/users"));
    }

    @Then("se muestran todos los usuarios del sistema")
    public void navigateToUsersList() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/users"));
        WebElement Button = driver.findElement(By.id("to-users-link"));
        WebElement Button2 = driver.findElement(By.id("to-draws-link"));
        WebElement Button3 = driver.findElement(By.id("to-home-link"));
        WebElement createButton = driver.findElement(By.id("users-button-create"));
        assertAll("Lista Usuarios",
                () -> {
                    assert (createButton.isDisplayed());
                },
                () -> {
                    assert (Button.isDisplayed());
                },
                () -> {
                    assert (Button2.isDisplayed());
                },
                () -> {
                    assert (Button3.isDisplayed());
                });
    }

    @When("el usuario hace clic sobre el botón de crear Usuario")
    public void clickUserButton2() {
        driver.findElement(By.id("users-button-create")).click();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newUser"));
    }

    @Then("se muestra el formulario de creación de usuarios")
    public void navigateToCreateUsers() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newUser"));
        WebElement correoField = driver.findElement(By.id("user-create-field-email"));
        WebElement nameField = driver.findElement(By.id("user-create-field-name"));
        WebElement apellidoField = driver.findElement(By.id("user-create-field-lastname1"));
        WebElement apellido2Field = driver.findElement(By.id("user-create-field-lastname2"));
        assertAll("Lista Usuarios",
                () -> {
                    assert (correoField.isDisplayed());
                },
                () -> {
                    assert (nameField.isDisplayed());
                },
                () -> {
                    assert (apellidoField.isDisplayed());
                },
                () -> {
                    assert (apellido2Field.isDisplayed());
                });
    }

    /**Sorteos */

    @When("el usuario hace clic sobre el botón de Sorteo")
    public void clickSorteoButton() {
        driver.findElement(By.id("to-draws-link")).click();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/draws"));
    }

    @Then("se muestra la lista de sorteos del sistema")
    public void navigateToSorteoList() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/draws"));
        WebElement Button = driver.findElement(By.id("to-users-link"));
        WebElement Button2 = driver.findElement(By.id("to-draws-link"));
        WebElement Button3 = driver.findElement(By.id("to-home-link"));
        assertAll("Lista Soteos",
                () -> {
                    assert (Button.isDisplayed());
                },
                () -> {
                    assert (Button2.isDisplayed());
                },
                () -> {
                    assert (Button3.isDisplayed());
                });
    }

    @When("el usuario hace clic sobre el botón de crear Sorteo")
    public void clickNewSorteoButton() {
        WebElement createButton = driver.findElement(By.id("button"));
        createButton.click();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newDraw"));
    }
    
    @Then("se muestra el formulario de creación de sorteos")
    public void verifyCreateSorteoForm() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/newDraw"));
        WebElement descripcionField = driver.findElement(By.id("draw-field-description"));
        WebElement createButton = driver.findElement(By.id("draw-button-submit"));
        assertAll("Lista Sorteos",
                () -> {
                    assert (descripcionField.isDisplayed());
                },
                () -> {
                    assert (createButton.isDisplayed());
                });
    }
}