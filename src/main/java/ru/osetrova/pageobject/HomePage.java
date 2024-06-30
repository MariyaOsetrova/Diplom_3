package ru.osetrova.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final By animation = By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']");
    private final By bun = By.xpath(".//span[text()='Булки']");
    private final By sauce = By.xpath(".//span[text()='Соусы']");
    private final By filling = By.xpath(".//span[text()='Начинки']");
    private final By tabCurrent = By.xpath("//div[contains(@class,'tab_tab__1SPyG tab_tab_type_current__2BEPc')]");
    private final By logAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By buttonOrderOrLogAccountName = By.xpath("//button[contains(@class,'button_button__33qZ0')]");
    private final By personalAccountButton = By.xpath("//a[contains(@class,'AppHeader_header__link__3D_hX')][@href='/account']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы")
    public void waitHomePageLoading() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (animation));
    }

    @Step("Клик по табу - Булки")
    public void clickTabBun() {
        driver.findElement(bun).click();
    }

    @Step("Клик по табу - Соусы")
    public void clickTabSauce() {
        driver.findElement(sauce).click();
    }

    @Step("Клик по табу - Начинки")
    public void clickTabFilling() {
        driver.findElement(filling).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(tabCurrent));
    }

    @Step("Получить текст открытого таба")
    public String getTextTabOpen() {
        return driver.findElement(tabCurrent).getText();
    }

    @Step("Получить текст кнопки в зависимость от авторизации")
    public String getNameButtonHome() {
        return driver.findElement(buttonOrderOrLogAccountName).getText();
    }

    @Step("Клик по кнопке - Войти в аккаунт")
    public void clickButtonLogAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(logAccount));
        driver.findElement(logAccount).click();
    }

    @Step("Клик по кнопке - Личный кабинет")
    public void clickButtonPersonalAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(personalAccountButton));
        driver.findElement(personalAccountButton).click();
    }
}
