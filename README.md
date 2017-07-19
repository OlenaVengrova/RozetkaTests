Registration test for https://www.rozetka.com.ua

In this test project I used Selenium WebDriver, Page Object pattern, Temp Mail service, Retrofit HTTP client for Java, TestNg, Maven, Log4j.

MainPage, RegistrationPage and UserProfilePage are classes which contain methods responsible for managing according sections on www.rozetka.com.ua

DriverBindings is an utility class for more convenient work with Selenium WebDriver.

TempMail class is an utility class for using all goodness of Temp Mail service 

RegistrationTest is a test class which uses all previously mentioned classes for testing registration on   www.rozetka.com.ua. It uses TestNg annotation and assertion. 

To run test please execute the following command “mvn test” in terminal