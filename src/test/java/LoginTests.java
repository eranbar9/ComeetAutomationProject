import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.ProfilePage;
import pages.LoginPage;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static pages.LoginPage.LOGIN_PAGE_URL;
import static pages.ProfilePage.PROFILE_PAGE_URL;

public class LoginTests extends BaseTest {
    public static LoginPage loginPage;

    @BeforeMethod
    public static void initTest() {
        loginPage = new LoginPage(driver);

        driver.get(LOGIN_PAGE_URL);

        assertTrue(loginPage.getPageTitle().contains("Login to free code playground for Bootstrap"));
    }

    @DataProvider
    public Object[][] getLoginData() {
        return new Object[][] {
                {"ricktwd020@gmail.com", "Qazwsxedc123"},
                {"user1@gmail.com", "abc123"},
                {"user2@gmail.com", "xyz123"},
        };
    }

    @Test(dataProvider="getLoginData")
    public void validLogin_WithRememberMe(String email, String password) {
        ProfilePage profilePage = loginPage.login(email, password, true);

        assertEquals(profilePage.getPageUrl(), PROFILE_PAGE_URL, "Login was unsuccessful");
        assertEquals(profilePage.getPageTitle().toLowerCase(), "profile");

        Set<Cookie> allCookies = driver.manage().getCookies();
        driver.close();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(LOGIN_PAGE_URL);
        for (Cookie cookie : allCookies) {
            driver.manage().addCookie(cookie);
        }
        driver.navigate().to(PROFILE_PAGE_URL);

        assertEquals(driver.getCurrentUrl(), PROFILE_PAGE_URL, "Remember Me\" functionality didn't work- login session has expired");
        assertEquals(driver.getTitle().toLowerCase(), "profile");
    }

    @Test(dataProvider="getLoginData")
    public void validLogin_WithoutRememberMe(String email, String password) {
        ProfilePage profilePage = loginPage.login(email, password, false);

        assertEquals(profilePage.getPageUrl(), PROFILE_PAGE_URL, "Login was unsuccessful");
        assertEquals(profilePage.getPageTitle().toLowerCase(), "profile");

        Set<Cookie> allCookies = driver.manage().getCookies();
        driver.close();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(LOGIN_PAGE_URL);
        for (Cookie cookie : allCookies) {
            driver.manage().addCookie(cookie);
        }
        driver.navigate().to(PROFILE_PAGE_URL);

        assertEquals(driver.getCurrentUrl(), LOGIN_PAGE_URL, "Remember Me\" functionality didn't work- we are still logged in to the system");
        assertTrue(driver.getTitle().contains("Login to free code playground for Bootstrap"));
    }
}
