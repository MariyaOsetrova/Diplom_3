import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.osetrova.WebDriverFactory;
import ru.osetrova.pageobject.HomePage;

import static org.junit.Assert.assertEquals;
import static ru.osetrova.utils.Config.URL_HOME;


public class ConstructorTest {
    private WebDriver driver;

    @Before
    public void startUp() {

        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get(URL_HOME);
    }

    @Test
    @DisplayName("Переход в раздел - Булки")
    @Description("Переход в раздед - Булки - осуществлен")
    public void openTabOfBun() {
        HomePage homePage = new HomePage(driver);
        homePage.clickTabFilling();
        homePage.clickTabBun();
        String actualTab = homePage.getTextTabOpen();
        assertEquals("Булки", actualTab);
    }

    @Test
    @DisplayName("Переход в раздел - Соусы")
    @Description("Переход в раздед - Соусы - осуществлен")
    public void openTabOfSauce() {
        HomePage homePage = new HomePage(driver);
        homePage.clickTabSauce();
        String actualTab = homePage.getTextTabOpen();
        assertEquals("Соусы", actualTab);
    }

    @Test
    @DisplayName("Переход в раздел - Начинки")
    @Description("Переход в раздед - Начинки - осуществлен")
    public void openTabOfFilling() {
        HomePage homePage = new HomePage(driver);
        homePage.clickTabFilling();
        String actualTab = homePage.getTextTabOpen();
        assertEquals("Начинки", actualTab);
    }


    @After
    public void closeWindow() {
        driver.quit();
    }
}
