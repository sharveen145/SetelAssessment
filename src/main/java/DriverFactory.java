import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public abstract class DriverFactory {

    public static WebDriver getDriver(String  browser) {
        WebDriver driver = null;
        switch (browser.toUpperCase())
        {
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            case "CHROME":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        return driver;
    }
}
