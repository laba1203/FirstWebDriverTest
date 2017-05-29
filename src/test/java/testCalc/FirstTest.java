package testCalc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.PropertyLoader;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.concurrent.TimeUnit;

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

//**To delete:
//        System.out.println("first testCalc value: "+ firstField.getText());
//       String ar1;

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
//////--Easiest solutio n---
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h2[@class = 'ng-binding']"), "2"));  //textToBePresentInElement(By.xpath("//h2[@class = 'ng-binding']"), "2"));
        String textResult = driver.findElement(By.xpath("//h2[@class = 'ng-binding']")).getText();

//////---Better solution-----//////
//        String previousText = null;
//        WebElement result;
//        for (int i = 0; i < 6; i++) {
//
//            result = driver.findElement(By.xpath("//h2[@class = 'ng-binding']"));
//            previousText = result.getText();
//            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//h2[@class = 'ng-binding']"), previousText));
//        }
//        String textResult = previousText;

        Assert.assertEquals(textResult, "2");
    }

//    @AfterClass
//    public void tearDown(){
//        driver.close();
//    }

}
