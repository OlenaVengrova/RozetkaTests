import models.Email;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import pages.UserProfilePage;
import utils.DriverBindings;
import utils.TempMail;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationTest {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private UserProfilePage userProfilePage;

    private final static String ROZETKA_URL = "https://my.rozetka.com.ua";
    private final static String ROZETKA_EMAIL_SENDER = "ROZETKA <sales@rozetka.com.ua>";

    private final static Pattern ACTIVATION_LINK_PATTERN =
            Pattern.compile("<a href=\"(https://my.rozetka.com.ua/authorize/[^\"]*)\"");

    @BeforeClass
    public void setUp() throws InterruptedException, IOException {
        driver = new ChromeDriver();
        DriverBindings drB = new DriverBindings(driver);
        registrationPage = new RegistrationPage(drB);
        userProfilePage = new UserProfilePage(drB);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void registration()
            throws IOException, NoSuchAlgorithmException, TempMail.NoEmailsException, InterruptedException {
        String emailAddress = TempMail.generateEmailAddress();

        driver.get(ROZETKA_URL);

        registrationPage.openRegistrationForm();
        registrationPage.performRegistration("test", emailAddress, "Qwerty12345");

        driver.get(getEmailActivationUrl(emailAddress));

        Assert.assertEquals(userProfilePage.getUserEmail(), emailAddress);
    }

    private static String getEmailActivationUrl(String emailAddress)
            throws IOException, NoSuchAlgorithmException, TempMail.NoEmailsException, InterruptedException {

        Email activationEmail = getActivationEmail(emailAddress);

        String emailText = activationEmail.getMailText();

        Matcher authorizeLinkMatcher = ACTIVATION_LINK_PATTERN.matcher(emailText);

        if (authorizeLinkMatcher.find()) {
            return authorizeLinkMatcher.group(1);
        }

        throw new RuntimeException("Activation link not found");
    }

    private static Email getActivationEmail(String emailAddress)
            throws IOException, NoSuchAlgorithmException, TempMail.NoEmailsException, InterruptedException {
        List<Email> emails = TempMail.waitForEmails(emailAddress, 6000);

        for (Email email : emails) {
            if (email.getMailFrom().equals(ROZETKA_EMAIL_SENDER)) {
                return email;
            }
        }

        throw new RuntimeException("Activation email not found");
    }

}
