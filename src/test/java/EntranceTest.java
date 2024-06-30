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
import ru.osetrova.pageobject.PasswordRecoveryPage;
import ru.osetrova.pageobject.RegistrationPage;

import static org.junit.Assert.assertEquals;
import static ru.osetrova.utils.Config.*;

public class EntranceTest {
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
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Пользователь авторизован через форму авторизации с главной страницы")
    public void logAccountToButtonHomePage() {
        driver.get(URL_HOME);
        HomePage homePage = new HomePage(driver);
        AuthPage authPage = new AuthPage(driver);
        homePage.clickButtonLogAccount();
        authPage.waitLoginButton();
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals("Оформить заказ", homePage.getNameButtonHome());
    }

    @Test
    @DisplayName("Вход через кнопку из «Личного кабинета»")
    @Description("Пользователь авторизован через форму личного кабинета")
    public void logPersonalAccountToButtonHomePage() {
        driver.get(URL_LOGIN);
        HomePage homePage = new HomePage(driver);
        AuthPage authPage = new AuthPage(driver);
        authPage.waitLoginButton();
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals("Оформить заказ", homePage.getNameButtonHome());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Пользователь авторизован через форму из кнопки регистрации")
    public void logAccountToButtonRegistrationPage() {
        driver.get(URL_REGISTER);
        HomePage homePage = new HomePage(driver);
        AuthPage authPage = new AuthPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitRegisterForm();
        registrationPage.clickLoginButton();
        authPage.waitLoginButton();
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals("Оформить заказ", homePage.getNameButtonHome());
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля.")
    @Description("Пользователь авторизован через ссылку из формы восстановления пароля")
    public void logAccountToButtonPasswordRecoveryPage() {
        driver.get(URL_FORGOT_PASSWORD);
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        HomePage homePage = new HomePage(driver);
        AuthPage authPage = new AuthPage(driver);
        passwordRecoveryPage.clickButtonLogAccount();
        authPage.waitLoginButton();
        authPage.setFieldAuth(user.getEmail(), user.getPassword());
        authPage.clickloginButton();
        homePage.waitHomePageLoading();
        homePage.getNameButtonHome();
        assertEquals("Оформить заказ", homePage.getNameButtonHome());
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
