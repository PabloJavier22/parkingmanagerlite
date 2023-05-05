package com.hormigo.david.parkingmanager.userIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.ChoiceCallback;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hormigo.david.parkingmanager.user.service.UserService;
import com.mysql.jdbc.Driver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @MockBean
    private UserService userService;
    @Value("${local.server.port}")
    private  int port;
    private static ChromeDriver chromeDriver;
    @BeforeAll
    public static void prepareWebDriver() {

        System.setProperty("webdriver.chrome.driver","D:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
         chromeDriver = new ChromeDriver(options);
         
        
    }

    @Test 
    @Order(1)
    public void testIndex(){
        chromeDriver.get("http://localhost:"+port+"/");
        String pageTitle = chromeDriver.getTitle();
        assertEquals("CPIFP Los Camaleones", pageTitle);
        WebElement button1 = chromeDriver.findElement(By.id("to-home-link"));
        WebElement button2 = chromeDriver.findElement(By.id("to-users-link"));

        assertAll("La pagina de Index se esta mostrando", 
        () -> {assertNotNull(button1);},
        () -> {assertNotNull(button2);});
        button2.click();
    }

    @Test
    @Order(2)
    public void UserTest() throws InterruptedException{  
        String url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/users", url_actual);
        WebElement userCreateButton = chromeDriver.findElement(By.id("users-button-create"));
        assertAll("La pagina de User se esta mostrando", 
        () -> {assertNotNull(userCreateButton);});      
        userCreateButton.click();
    }

    @Test
    @Order(3)
    public void CreateUserTest() throws InterruptedException {
  ; 
        String url_actual= chromeDriver.getCurrentUrl(); 
        assertEquals("http://localhost:"+port+"/newUser", url_actual);
        WebElement button1 = chromeDriver.findElement(By.id("user-create-button-submit"));
        button1.click();
        WebElement correo = chromeDriver.findElement(By.id("user-create-field-email"));
        correo.sendKeys("test@gmail.com");
        WebElement textField = chromeDriver.findElement(By.id("user-create-field-name"));
        textField.sendKeys("nombreTest");
        WebElement textField2 = chromeDriver.findElement(By.id("user-create-field-lastname1"));
        textField2.sendKeys("apellidoTest");
        WebElement textField3 = chromeDriver.findElement(By.id("user-create-field-lastname2"));
        textField3.sendKeys("segundApellidoTest");
        WebElement roleField = chromeDriver.findElement(By.id("user-create-field-role"));
        roleField.sendKeys("PROFESSOR");
        button1 = chromeDriver.findElement(By.id("user-create-button-submit"));
        button1.click();
    }
    @Test
    @Order(4)
    public void createDrawsTest(){
        chromeDriver.get("http://localhost:"+port+"/newDraw");
        WebElement descripcion = chromeDriver.findElement(By.id("draw-field-description"));
        descripcion.sendKeys("sorteoTest");
    }

    @Test
    @Order(5)
    public void CheckingLinks(){
        chromeDriver.get("http://localhost:"+port+"/");


        /**-----------------------Cheking Links of Users-------------------------------- */


        String url_actual= chromeDriver.getCurrentUrl(); 
        assertEquals("http://localhost:"+port+"/", url_actual);
        WebElement home = chromeDriver.findElement(By.id("to-home-link"));
        WebElement users = chromeDriver.findElement(By.id("to-users-link"));
        
        home.click(); //home -> home
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/", url_actual);
        users = chromeDriver.findElement(By.id("to-users-link"));
        users.click(); //home -> users
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/users", url_actual);
        WebElement createUsers = chromeDriver.findElement(By.id("users-button-create"));
        createUsers.click(); //users -> createUsers
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/newUser", url_actual);
        home = chromeDriver.findElement(By.id("to-home-link"));
        home.click(); //createUsers -> home
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/", url_actual);
        chromeDriver.get("http://localhost:"+port+"/newUser"); //createUsers
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/newUser", url_actual);
        users = chromeDriver.findElement(By.id("to-users-link"));
        users.click(); //createUsers -> users
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/users", url_actual);
        home = chromeDriver.findElement(By.id("to-home-link"));
        home.click(); //users -> home
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/", url_actual);
        


        /**-----------------------Cheking Links of Draw-------------------------------- */
        
        WebElement draws = chromeDriver.findElement(By.id("to-draws-link"));
        draws.click(); //home -> draws
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/draws", url_actual);
        WebElement createDraws =  chromeDriver.findElement(By.id("to-new-draws-links"));
        createDraws.click(); //draws -> createDraws
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/newDraw", url_actual);

        home = chromeDriver.findElement(By.id("to-home-link"));
        home.click(); //createDraws -> home
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/", url_actual);
        chromeDriver.get("http://localhost:"+port+"/newDraw"); //createDraws
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/newDraw", url_actual);
        draws = chromeDriver.findElement(By.id("to-draws-link"));
        draws.click(); //createDraws -> Draws
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/draws", url_actual);
        home = chromeDriver.findElement(By.id("to-home-link"));
        home.click(); //users -> home
        url_actual = chromeDriver.getCurrentUrl();
        assertEquals("http://localhost:"+port+"/", url_actual);
        chromeDriver.quit();
    }
}