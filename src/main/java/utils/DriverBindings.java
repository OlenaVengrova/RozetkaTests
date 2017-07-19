package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverBindings {

    private WebDriver driver;
    private Logger log = Logger.getLogger(DriverBindings.class);

    public DriverBindings(WebDriver dr){
        this.driver = dr;
    }

    public WebElement getWebElement(By locator) {
        log.info("[getWebElement]: Getting an element by locator:" + locator);

        return driver.findElement(locator);
    }

    public DriverBindings goTo(String link){
        driver.get(link);
        log.info("[goTo]: Open page: "+ link);

        return this;
    }

    public DriverBindings moveMouseToElement(By locator){
        log.info("[moveMouseToElement]: Moving mose to element with locator: "+locator);
        new Actions(driver).moveToElement(driver.findElement(locator)).build().perform();

        return this;
    }

    public DriverBindings typeText(By fieldLocator, String text){
        getWebElement(fieldLocator).sendKeys(text);
        log.info("[typeText]: Typed text/path: "+text+" into field with locator: "+fieldLocator);

        return this;
    }

    public DriverBindings waitForElementPresent(By locator){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        log.info("[waitForElementPresent]: Waiting for element with locator: "+locator+" present");

        return this;
    }

    public DriverBindings click(By locator) {
        try {
            driver.findElement(locator).click();
            log.info("[click]: Clicking on element with locator: "+locator);
        } catch (NoSuchElementException e) {
            log.warn("Following exception was raised", e);
        }

        return this;
    }

    public String getWebElementText(By locator){
        log.info("[getWebElementText]: Getting text of element with "+locator+" locator");

        return getWebElement(locator).getText();
    }


}
