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
import ru.osetrova.pageobject.RegistrationPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.osetrova.utils.Config.URL_LOGIN;
import static ru.osetrova.utils.Config.URL_REGISTER;

public class RegistrationTest {
    private WebDriver driver;
    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;

    @Before
    public void startUp() {

        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get(URL_REGISTER);
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Пользователь зарегистирован")
    public void registrationNewUser() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@ya.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(10));
        user.setName(RandomStringUtils.randomAlphabetic(10));
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitRegisterForm();
        registrationPage.registrationUser(user.getName(), user.getEmail(), user.getPassword());
        AuthPage authPage = new AuthPage(driver);
        authPage.waitLoginButton();
        assertEquals(URL_LOGIN, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Ошибку для некорректного пароля. Минимальный пароль — шесть символов.")
    @Description("Пользователь не зарегистирован. Появляется ошибка о неккорректном пароле")
    public void errorPasswordRegistrationMessage() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@ya.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(5));
        user.setName(RandomStringUtils.randomAlphabetic(10));
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitRegisterForm();
        registrationPage.registrationUser(user.getName(), user.getEmail(), user.getPassword());
        assertTrue(registrationPage.isErrorPasswordMessage());
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
