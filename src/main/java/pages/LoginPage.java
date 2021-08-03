package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public static final String LOGIN_PAGE_URL="https://bootsnipp.com/login";

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@name='remember']")
    private WebElement rememberMeCheckBox;

    @FindBy(xpath = "//input[@class='btn btn-lg btn-success btn-block']")
    private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public ProfilePage login(String email, String password, boolean rememberMe) {
        enterEmail(email);
        enterPassword(password);

        if (rememberMe) {
            if (!rememberMeCheckBox.isSelected()) {
                rememberMeCheckBox.click();
            }
        } else {
            if (rememberMeCheckBox.isSelected()) {
                rememberMeCheckBox.click();
            }
        }

        loginBtn.click();
        return new ProfilePage(driver);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
