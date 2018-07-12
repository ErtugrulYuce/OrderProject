package com.weborder;

import static org.testng.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import io.github.bonigarcia.wdm.WebDriverManager;

public class webOrder {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void close() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}

	@Test(priority = 1)
	public void WebOrderLogin() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		// driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
		WebElement userName;
		WebElement password;
		WebElement login;
		userName = driver.findElement(By.name("ctl00$MainContent$username"));
		userName.clear();
		userName.sendKeys("Tester");
		password = driver.findElement(By.name("ctl00$MainContent$password"));
		password.clear();
		password.sendKeys("test");
		login = driver.findElement(By.id("ctl00_MainContent_login_button"));
		login.click();
		String usual = driver.findElement(By.xpath("//div[contains(text(),'Welcome')]")).getText();
		String expected = "Welcome, Tester! | Logout";
		assertEquals(usual, expected);

	}

	@Test(priority = 2)
	public void Order() {
		WebElement order;
		order = driver.findElement(By.xpath("//a[.='Order']"));
		order.click();
		WebElement quantity;
		quantity = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity"));
		quantity.clear();
		quantity.sendKeys(random1to100());
		WebElement name;
		name = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName"));

		name.sendKeys("John " + middleName() + " Smith");

		WebElement street = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2"));
		WebElement city = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3"));
		WebElement state = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4"));
		WebElement zipcode = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5"));
		street.sendKeys("123 Any st");
		city.sendKeys("Anytown");
		state.sendKeys("Virginia");
		zipcode.sendKeys(random10000to99999());
		String type = random0to2();
		//System.out.println(type);
		WebElement cardType = driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_" + type));
		cardType.click();
		WebElement cardNumber = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6"));
		//System.out.println(randomCardNumber(type));
		cardNumber.sendKeys(randomCardNumber(type));
		WebElement date = driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1"));
		date.sendKeys(datemmyy());
		WebElement process= driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton"));
		process.click();
		String expected ="New order has been successfully added.";
		String actual = driver.findElement(By.xpath("//strong[contains(text(),'New order has been successfully added.')]")).getText();
		assertEquals(actual, expected);
		
		
		
		

	}
	
	public String datemmyy(){
		String mm = Integer.parseInt(random1to100())%12+"";
		while (mm.equals("0")) {
			mm = Integer.parseInt(random1to100())%12+"";
		}
		if(Integer.parseInt(mm)<10) {
			mm="0"+mm;
		}
		//System.out.println(mm);
		int yy=2;
		while(yy<18) {
			yy=Integer.parseInt(random1to100());
			while(yy>28) {
				yy=Integer.parseInt(random1to100());
			}
		}
		//System.out.println(yy);
		return mm+"/"+yy;
	}

	private String randomCardNumber(String k) {
		if (k.equals("0")) {
			return random16DigitsW4();
		} else if (k.equals("1")) {
			return random16DigitsW5();
		} else {
			return random15DigitsW3();
		}

	}

	public String random1to100() {
		int number = (int) (Math.random() * (100 - 1) + 1);
	
		Random rand = new Random();
		int numberRandom = rand.nextInt(99) + 1; //0 - 98 -> 1, 9
		
		return number + "";

	}

	public String random10000to99999() {
		int number = (int) (Math.random() * (100000 - 1) + 1);
		return number + "";

	}

	public String random0to2() {
		int number = (int) (Math.random() * 10) % 3;
		return number + "";

	}

	public String middleName() {
		Faker faker = new Faker();
		return faker.name().firstName();
	}

	public String random16DigitsW5() {
		long first14 = (long) (Math.random() * 1000000000000000L);
		long number = 5000000000000000L + first14;
		return number + "";

	}

	public String random16DigitsW4() {
		long first14 = (long) (Math.random() * 1000000000000000L);
		long number = 4000000000000000L + first14;
		return number + "";

	}

	public String random15DigitsW3() {
		long first14 = (long) (Math.random() * 100000000000000L);
		long number = 300000000000000L + first14;
		return number + "";

	}
}
