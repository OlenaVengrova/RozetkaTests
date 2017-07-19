package pages;

import utils.DriverBindings;
import org.openqa.selenium.By;

public class RegistrationPage {
    final By signinLink = By.name("signin");
    final By signupLink = By.className("auth-f-signup-link");
    final By signupBtn = By.cssSelector(".signup .btn-link-sign button");
    final By userNameInput = By.name("title");
    final By emailInput = By.cssSelector("#signup_form input[name='email']");
    final By passwordInput = By.name("password");

    private final DriverBindings drB;

    public RegistrationPage(DriverBindings driverBindings) {
        drB = driverBindings;
    }

    public void performRegistration(String userName, String emailAddress, String password) {
        drB.typeText(userNameInput, userName)
                .typeText(emailInput, emailAddress)
                .typeText(passwordInput, password)
                .click(signupBtn);
    }

    public void openRegistrationForm() {
        drB.click(signinLink)
                .waitForElementPresent(signupLink)
                .click(signupLink)
                .waitForElementPresent(signupBtn);
    }
}
