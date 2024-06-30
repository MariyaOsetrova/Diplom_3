package ru.osetrova.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private final WebDriver driver;
    private final By profileLink = By.xpath("//a[contains(@class,'Account_link__2ETsJ ')][@href='/account/profile']");
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logoButton = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание прогрузки страницы")
    public void waitPersonalAccountPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(profileLink));
    }

    @Step("Переход по клику на «Конструктор»")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Переход по клику на логотип Stellar Burgers")
    public void clicklogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Переход по кнопке «Выйти»")
    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }


}
