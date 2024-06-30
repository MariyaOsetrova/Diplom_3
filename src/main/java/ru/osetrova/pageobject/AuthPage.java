package ru.osetrova.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// форма авторизации
public class AuthPage {
    private final WebDriver driver;
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By emailField = By.xpath(".//div[./label[text()='Email']]/input[@name='name']");
    private final By passwordField = By.xpath(".//div[./label[text()='Пароль']]/input[@name='Пароль']");

    public AuthPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание прогрузки кнопки - войти")
    public void waitLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(loginButton));
    }

    @Step("Заполнение полей авторизации для входа")
    public void setFieldAuth(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик по кнопе 'Войти'")
    public void clickloginButton() {
        driver.findElement(loginButton).click();
    }

}
