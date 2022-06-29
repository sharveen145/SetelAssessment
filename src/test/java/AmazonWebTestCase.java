import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class AmazonWebTestCase extends DriverFactory {

    WebDriver driver=null;
    String product = "iPhone 11";
    static Map<Float, String> map = new HashMap<>();


    @BeforeTest
    public void setUp() {
        driver=DriverFactory.getDriver("chrome");
    }

    @Test
    public void openAmazon() {
        String url="https://www.amazon.com/";
        driver.get(url);
        if (!driver.getTitle().equalsIgnoreCase("Amazon.com. Spend less. Smile more."))
            throw new NoSuchElementException("Amazon page not launched.");
        System.out.println("Amazon page launched successfully.");
    }
    @Test(dependsOnMethods = "openAmazon")
    public void SearchAmazonItem() {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @Test(dependsOnMethods = "SearchAmazonItem")
    public void getAmazonItems() throws InterruptedException {
        List<WebElement> itemNames2 = driver.findElements(By
                .xpath("//span[contains(@class,'a-size-medium') and contains(@class,'a-text-normal')]/ancestor::a"));
        List<WebElement> itemPrices2 = driver.findElements(By
                .xpath("//span[@class='a-offscreen']/ancestor::span[@class='a-price']"));
        int itemNameCount2 = itemNames2.size();
        for (int i = 0; i < itemNameCount2; i++) {
            Thread.sleep(1000);
            String valueAmazon = itemPrices2.get(i).getText().replace("$", "").trim().replaceAll("\n", ".");
            if (valueAmazon.contains(","))
                valueAmazon = valueAmazon.replaceAll(",", "");
            if (!valueAmazon.isEmpty()) {
                float finalValue = Float.parseFloat(valueAmazon);
                map.put(finalValue, itemNames2.get(i).getText());
            }
        }
    }

    @Test(dependsOnMethods = "getAmazonItems")
    public void navigateToLazada() {
        String url2="https://www.lazada.sg/";
        driver.get(url2);
        if (!driver.getTitle().equalsIgnoreCase("Lazada.sg: Online Shopping Singapore - Electronics, Home Appliances, Mobiles, Tablets & more"))
            throw new NoSuchElementException("Lazada page not launched.");
        System.out.println("Lazada page launched successfully.");
    }

    @Test(dependsOnMethods = "navigateToLazada")
    public void SearchLazadaItem() throws InterruptedException {
        WebElement searchBar = driver.findElement(By.id("q"));
        searchBar.sendKeys(product);
        driver.findElement(By.xpath("//button[contains(text(),'SEARCH')]")).click();
        WebElement sortBy = driver.findElement(By.className("pI6oU"));
        fluentWaitTillVisible(sortBy);
    }

    @Test(dependsOnMethods = "SearchLazadaItem")
    public void getLazadaItems() throws InterruptedException {
        List<WebElement> itemNames = driver.findElements(By
                .xpath("//div[@class='RfADt']//a[@title]"));
        List<WebElement> itemPrices = driver.findElements(By
                .xpath("//span[@class='ooOxS']"));
        int itemNameCount = itemNames.size();
        for (int i = 0; i < itemNameCount; i++) {
            Thread.sleep(1000);
            String valueLazada = itemPrices.get(i).getText().replace("$", "").trim();
            if (valueLazada.contains(","))
                valueLazada = valueLazada.replaceAll(",", "");
            if (!valueLazada.isEmpty()) {
                float finalValue = Float.parseFloat(valueLazada);
                map.put(finalValue, itemNames.get(i).getText());
            }
        }
    }

    @Test(dependsOnMethods = "getLazadaItems")
    public void sortAndPrint() {
        sortByKeys();
    }


    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public static void sortByKeys()
    {
        TreeMap<Float, String> sorted = new TreeMap<>(map);
        for (Map.Entry<Float, String> entry : sorted.entrySet())
            System.out.println("Name " + entry.getValue() + ", Price = $" + entry.getKey());
    }

    public void fluentWaitTillVisible(WebElement by){
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.presenceOfElementLocated((By) by));
    }

}
