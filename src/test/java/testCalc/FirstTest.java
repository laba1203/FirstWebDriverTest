package testCalc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import util.PropertyLoader;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by MLaba on 22-May-17.
 */
public class FirstTest {
    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeClass
    public void setUp(){
        final File file = new File(PropertyLoader.loadProperty("path.webDriver"));
        System.setProperty(PropertyLoader.loadProperty("webDriver"), file.getAbsolutePath());
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5, 1000);


    }

    @Test
    public void test1(){
        driver.navigate().to("http://juliemr.github.io/protractor-demo/");

        String header = driver.findElement(By.xpath("//div[@ng-controller='CalcCtrl']/div[1]/h3[1]")).getText();

        Assert.assertEquals(header, "Super Calculator");

    }

    @Test
    public void test2(){
        WebElement firstField = driver.findElement(By.xpath("//input[@ng-model='first']"));
        firstField.sendKeys("1");


        WebElement secondField = driver.findElement(By.xpath("//input[@ng-model='second']"));
        secondField.sendKeys("1");

        Assert.assertEquals(firstField.getAttribute("value"), "1");
        Assert.assertEquals(secondField.getAttribute("value"), "1");

    }

    @Test
    public void test3(){
        Select operatorField = new Select(driver.findElement(By.xpath("//select[@ng-model='operator']")));
        operatorField.selectByValue("ADDITION");

        Assert.assertEquals(operatorField.getFirstSelectedOption().getText(), "+");

    }

    @Test
    public void test4() throws InterruptedException {
        WebElement goButton = driver.findElement(By.id("gobutton"));
        goButton.click();
//        String textResult = null;
        try{
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2[@class = 'ng-binding']"), "2"));  //textToBePresentInElement(By.xpath("//h2[@class = 'ng-binding']"), "2"));
//            textResult = driver.findElement(By.xpath("//h2[@class = 'ng-binding']")).getText();
        }
        catch (org.openqa.selenium.TimeoutException e){
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testDivision(){
        operation("10", "DIVISION", "2", "5");
    }

    @Test
    public void testModulo(){
        operation("17", "MODULO", "3", "2");
    }

    @Test
    public void testMultiplication(){
        operation("3", "MULTIPLICATION", "4", "12");
    }

    @Test
    public void testSubtraction(){
        operation("7", "SUBTRACTION", "6", "1");
    }





    public void operation(String value1, String operator ,String value2, String expectedValue){
        WebElement firstField = driver.findElement(By.xpath("//input[@ng-model='first']"));
        firstField.sendKeys(value1);
        WebElement secondField = driver.findElement(By.xpath("//input[@ng-model='second']"));
        secondField.sendKeys(value2);

        Select operatorField = new Select(driver.findElement(By.xpath("//select[@ng-model='operator']")));
        operatorField.selectByValue(operator);

        WebElement goButton = driver.findElement(By.id("gobutton"));
        goButton.click();

        try{
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2[@class = 'ng-binding']"), expectedValue));  //textToBePresentInElement(By.xpath("//h2[@class = 'ng-binding']"), "2"));
//            textResult = driver.findElement(By.xpath("//h2[@class = 'ng-binding']")).getText();
        }
        catch (org.openqa.selenium.TimeoutException e){
            Assert.assertTrue(false);
        }


    }



    @AfterClass
    public void tearDown(){
        driver.close();
    }

}
