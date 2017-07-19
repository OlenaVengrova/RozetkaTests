package pages;

import utils.DriverBindings;
import org.openqa.selenium.By;

public class UserProfilePage {

    final By userEmailLocator = By.xpath("//div[@class=\"profile-col-main\"]//div[@class=\"profile-f-i-title\"][text()=\"Электронная почтa\"]/following-sibling::div");

    private final DriverBindings drB;

    public UserProfilePage(DriverBindings driverBindings) {
        drB = driverBindings;
    }

    public String getUserEmail(){
        return drB.getWebElementText(userEmailLocator);
    }

}
