package pages;

import org.openqa.selenium.By;
import utils.DriverBindings;

public class MainPage {

    final By signinLink = By.name("signin");
    final By signupLink = By.className("auth-f-signup-link");
    final By signupForm = By.id("signup_form");

    private final DriverBindings drB;

    public MainPage(DriverBindings driverBindings) {
        drB = driverBindings;
    }

    public void openRegistrationForm() {
        drB.click(signinLink)
                .waitForElementPresent(signupLink)
                .click(signupLink)
                .waitForElementPresent(signupForm);
    }

}
