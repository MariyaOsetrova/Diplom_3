import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.osetrova.Model.User;
import ru.osetrova.Steps.UserSteps;
import ru.osetrova.WebDriverFactory;
import ru.osetrova.pageobject.AuthPage;
import ru.osetrova.pageobject.HomePage;
import ru.osetrova.pageobject.PersonalAccountPage;

import static org.junit.Assert.assertEquals;
import static ru.osetrova.utils.Config.*;

public class PersonalAccountTest {
    private WebDriver driver;
    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;

    @Before
    public void startUp() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@ya.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(10));
        user.setName(RandomStringUtils.randomAlphabetic(10));
        userSteps.createUser(user);
        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();

    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет» авторизованным пользователем")
    @Description("Переход в личный кабинет осуществлен. Открывается профиль пользователя")
    public void transitionPersonalAccountAuthUserFromHomePage() {
        driver.get(URL_LOGIN);
        HomePage homePage = new HomePage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        AuthPage authPage = new AuthPage(driver);
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.clickButtonPersonalAccount();
        personalAccountPage.waitPersonalAccountPage();
        assertEquals(URL_PERSONAL_ACCOUNT, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход из личного кабинета по кнопке - Конструктор")
    @Description("Открывается страница с конструктором")
    public void transitionConstructortFromPersonalAccount() {
        driver.get(URL_LOGIN);
        HomePage homePage = new HomePage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        AuthPage authPage = new AuthPage(driver);
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.clickButtonPersonalAccount();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clickConstructorButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals("Оформить заказ", homePage.getNameButtonHome());
    }

    @Test
    @DisplayName("Переход из личного кабинета по логотипу - Stellar Burgers")
    @Description("Открывается главная страница")
    public void transitionLogoFromPersonalAccount() {
        driver.get(URL_LOGIN);
        HomePage homePage = new HomePage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        AuthPage authPage = new AuthPage(driver);
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.clickButtonPersonalAccount();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clicklogoButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals(URL_HOME, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Выход из личного кабинета по кнопке - Выйти")
    @Description("Пользователь вышел из системы. Открыватеся главная страница для неавторизованного пользователя")
    public void transitionExitButtonFromPersonalAccount() {
        driver.get(URL_LOGIN);
        HomePage homePage = new HomePage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        AuthPage authPage = new AuthPage(driver);
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.clickButtonPersonalAccount();
        personalAccountPage.waitPersonalAccountPage();
        personalAccountPage.clickExitButton();
        authPage.waitLoginButton();
        assertEquals(URL_LOGIN, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет» не авторизованным пользователем")
    @Description("Переход в личный кабинет не осуществлен. Открывается форма авторизации")
    public void transitionPersonalAccountNotAuthUserFromHomePage() {
        driver.get(URL_HOME);
        HomePage homePage = new HomePage(driver);
        AuthPage authPage = new AuthPage(driver);
        homePage.clickButtonPersonalAccount();
        authPage.waitLoginButton();
        homePage.clickButtonPersonalAccount();
        assertEquals(URL_LOGIN, driver.getCurrentUrl());
    }

    @After
    public void deleteUser() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        driver.quit();
        userSteps.userLogin(user);
        accessToken = userSteps.getToken(user);
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}