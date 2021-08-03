import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initDriver();
    }

    private void initDriver() {
        System.setProperty("webdriver.chrome.driver", "E:\\Users\\PC\\Downloads\\TEMP\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}
