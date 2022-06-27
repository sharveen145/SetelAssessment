import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AmazonWebTestCase extends DriverFactory {

    WebDriver driver=null;
    String product = "iPhone 11";
//    static Map<Float, String> map = new HashMap<>();
    static Map<String, String> map = new HashMap<>();
//    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//            .withTimeout(Duration.ofSeconds(60))
//            .pollingEvery(Duration.ofSeconds(2L))
//            .ignoring(NoSuchElementException.class);


    @BeforeTest
    public void setUp() {
        driver=DriverFactory.getDriver("chrome");
    }

//    @Test
//    public void openAmazon() {
//        String url="https://www.amazon.com/";
//        driver.get(url);
//        if (!driver.getTitle().equalsIgnoreCase("Amazon.com. Spend less. Smile more."))
//            throw new NoSuchElementException("Amazon page not launched.");
//        System.out.println("Amazon page launched successfully.");
//    }
//    @Test(dependsOnMethods = "openAmazon")
//    public void SearchAmazonItem() {
//        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
//        driver.findElement(By.id("nav-search-submit-button")).click();
//    }
//
//    @Test(dependsOnMethods = "SearchAmazonItem")
//    public void getAmazonItems() throws InterruptedException {
//        List<WebElement> itemNames = driver.findElements(By
//                .xpath("//span[contains(@class,'a-size-medium') and contains(@class,'a-text-normal')]/ancestor::a"));
//        List<WebElement> itemPrices = driver.findElements(By
//                .xpath("//span[@class='a-offscreen']/ancestor::span[@class='a-price']"));
//        int itemNameCount = itemNames.size();
//        for (int i = 0; i < itemNameCount; i++) {
//            Thread.sleep(1000);
//            String value = itemPrices.get(i).getText().replace("$", "").trim().replaceAll("\n", ".");
////            float value2 = Float.valueOf(value);
//            map.put(value,itemNames.get(i).getText());
//        }
//        System.out.println("The search result for "+ product +" from AMAZON Website: ");
////        sortByKeys();
//        }

    @Test
//            (dependsOnMethods = "getAmazonItems")
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
      driver.findElement(By.xpath("//button[contains(@class,'search-box__button') and contains(text(),'SEARCH')]")).click();
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
            String value = itemPrices.get(i).getText().replace("$", "").trim();
//            float value2 = Float.valueOf(value);
            map.put(value,itemNames.get(i).getText());
        }
        System.out.println("The search result for "+ product +" from Lazada Website: ");
        }


    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public static void sortByKeys()
    {
//        TreeMap<Float, String> sorted = new TreeMap<>(map);
//        for (Map.Entry<Float, String> entry : sorted.entrySet())
//            System.out.println("Name " + entry.getValue() + ", Price = $" + entry.getKey());

        TreeMap<String, String> sorted = new TreeMap<>(map);
        for (Map.Entry<String, String> entry : sorted.entrySet())
            System.out.println("Name: " + entry.getValue() + ", Price: $" + entry.getKey());
    }

}
