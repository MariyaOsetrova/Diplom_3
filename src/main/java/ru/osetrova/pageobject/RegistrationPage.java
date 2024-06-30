package ru.osetrova.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    public final By registerText = By.xpath("//h2[text()='Регистрация']");
    private final By nameField = By.xpath(".//div[./label[text()='Имя']]/input[@name='name']");
    private final By emailField = By.xpath(".//div[./label[text()='Email']]/input[@name='name']");
    private final By passwordField = By.xpath(".//div[./label[text()='Пароль']]/input[@name='Пароль']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    public final By errorPasswordMessage = By.xpath(".//p[text()='Некорректный пароль']");
    public final By loginButton = By.className("Auth_link__1fOlj");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание прогрузки формы")
    public void waitRegisterForm() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(registerText));
    }

    @Step("Заполнение полей формы")
    public void fillingFormField(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажатие на кнопку - Зарегистрироваться")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Нажатие на кнопку - Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Регистрация пользователя")
    public void registrationUser(String name, String email, String password) {
        fillingFormField(name, email, password);
        clickRegisterButton();
    }

    @Step("Ошибка при вводе пароля меньше 6 символов")
    public boolean isErrorPasswordMessage() {
        return driver.findElement(errorPasswordMessage).isDisplayed();
    }
}
