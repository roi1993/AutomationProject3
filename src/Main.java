import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kater\\Desktop\\Automation\\drivers\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();

//
//        1.	Navigate  to carfax.com
        driver.get("https://www.carfax.com/");

//        2.	Click on Find a Used Car
        driver.findElement(By.linkText("Find a Used Car")).click();

//       3.	Verify the page title contains the word “Used Cars”
         driver.getTitle().contains("Used Cars");

//        4.	Choose “Tesla” for  Make.
        WebElement makes=driver.findElement(By.xpath("//select[@name = 'make']"));
        new Select(makes).selectByValue("Subaru");
        Thread.sleep(1000);
        new Select(makes).selectByValue("Tesla");
        Thread.sleep(2000);

//        5.  Verify that the Select Model dropdown box contains 4 current Tesla models - (Model 3, Model S, Model X, Model Y).
       WebElement models= driver.findElement(By.xpath("//select[@name = 'model']"));

        List<String> actualOptions = new ArrayList<>();
        List<String> expectedOptions = Arrays.asList("Model 3", "Model S", "Model X", "Model Y");
        List<WebElement> elements = new Select(models).getOptions();
        for (int i = 1; i < elements.size() - 1; i++){
            actualOptions.add(elements.get(i).getText().trim());
        }
        Assert.assertEquals(actualOptions, expectedOptions);
        Thread.sleep(1000);

//        6.	Choose “Model S” for Model.
        new Select(models).selectByValue("Model S");
        Thread.sleep(2000);


//        7.	Enter the zip code 22182 and click Next
        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys("22182");
        driver.findElement(By.id("make-model-form-submit")).click();
        Thread.sleep(2000);

//        8.	Verify that the page contains the text “Step 2 – Show me cars with”
      WebElement text =driver.findElement(By.xpath("//h3[@class='searchForm-wrapper-header--lp']"));
      Assert.assertEquals(text.getText(),"Step 2 - Show me cars with");

//        9.	Check all 4 checkboxes.

        driver.findElement(By.xpath("//span[@class='checkbox-list-item--fancyCbx'][@aria-label='Toggle noAccidents']")).click();
        driver.findElement(By.xpath("//span[@class='checkbox-list-item--fancyCbx'][@aria-label='Toggle oneOwner']")).click();
        driver.findElement(By.xpath("//span[@class='checkbox-list-item--fancyCbx'][@aria-label='Toggle personalUse']")).click();
        driver.findElement(By.xpath("//span[@class='checkbox-list-item--fancyCbx'][@aria-label='Toggle serviceRecords']")).click();

        Thread.sleep(2000);

//        10.	Save the count of results from “Show me X Results” button. In this case it is 10.
         WebElement countOfResults=driver.findElement(By.xpath("//span[@class='totalRecordsText']"));
         String actualCount=countOfResults.getText();

//        11.	Click on “Show me x Results” button.
        driver.findElement(By.xpath("//button[@class='button large primary-green show-me-search-submit']")).click();
        Thread.sleep(2000);

//        12.	Verify the results count by getting the actual number of results displayed in the page by getting the count of WebElements that represent each result
//
       int x= Integer.parseInt(actualCount);
       List<WebElement> listOfWebElements = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article"));
       Assert.assertEquals(listOfWebElements.size(),x);

//        13.	Verify that each result header contains “Tesla Model S”.
        List<WebElement> listOfHeaders = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article//h4"));
        for (WebElement headers:listOfHeaders) {
            headers.getText().contains("Tesla Model S");
        }

//        14.	Get the price of each result and save them into a List in the order of their appearance. (You can exclude “Call for price” options)
        List<WebElement> listOfPrices = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article//span[@class='srp-list-item-price']"));
        List<String>listPrice=new ArrayList<>();
        for(WebElement price:listOfPrices){
            listPrice.add(price.getText());
        }
        for (String p:listPrice) {
            int i=listPrice.indexOf(p);
            listPrice.set(i,p.substring(8));
        }


//        15.	Choose “Price - High to Low” option from the Sort By menu
        WebElement sortBy=driver.findElement(By.xpath(" //select[@class='srp-header-sort-select srp-header-sort-select-desktop--srp']"));
        new Select(sortBy).selectByValue("PRICE_DESC");

//        16.	Verify that the results are displayed from high to low price.
        List<WebElement> listHighToLow = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article//span[@class='srp-list-item-price']"));
        List<String>priceHighToLow=new ArrayList<>();
        for(WebElement price:listHighToLow){
            priceHighToLow.add(price.getText());
        }
        for (String p:priceHighToLow) {
            int i=priceHighToLow.indexOf(p);
            priceHighToLow.set(i,p.substring(8));}

        Collections.sort(priceHighToLow);
        Collections.reverse(priceHighToLow);
        Collections.sort(listPrice);
        Collections.reverse(listPrice);
        Assert.assertEquals(priceHighToLow,listPrice);


//        17.	Choose “Mileage - Low to High” option from Sort By menu
          new Select(sortBy).selectByValue("MILEAGE_ASC");

//        18.	Verify that the results are displayed from low to high mileage.
        //List<WebElement> listOfMileage=driver.findElements(By.xpath(""));

//        19.	Choose “Year - New to Old” option from Sort By menu
          new Select(sortBy).selectByValue("YEAR_DESC");

//        20.	Verify that the results are displayed from new to old year.
        List<WebElement> listOfYear = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article//h4"));
        List<String>listYear=new ArrayList<>();
        for(WebElement year:listOfYear){
            listYear.add(year.getText());
        }

        for (String y:listYear) {
            int z=listYear.indexOf(y);
            listYear.set(z,y.substring(0,5));}


//        Push the code to a new GitHub repo and share the link in a text file and submit.
//








    }


}
