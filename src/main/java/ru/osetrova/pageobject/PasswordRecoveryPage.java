package ru.osetrova.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private final WebDriver driver;
    private final By loginLink = By.xpath("//a[contains(@class,'Auth_link__1fOlj')][@href='/login']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по ссылке - Войти")
    public void clickButtonLogAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(loginLink));
        driver.findElement(loginLink).click();
    }
}
