package flightRate;

import org.testng.annotations.Test;

import utilities.Constants;
import utilities.ExcelUtility;
import utilities.GenericMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;


public class expediaWithExcel {
	private WebDriver driver;
	private GenericMethods gm;
	 @BeforeMethod
	  public void beforeMethod() throws Exception
	 {
		  System.setProperty("webdriver.gecko.driver","D:\\Nidhi\\Workspace\\WebDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			gm = new GenericMethods(driver);
			
			
			// Maximize the browser's window
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		// Tell the code about the location of Excel file
		ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name, "TravelDates");
	  }
	 
	 @DataProvider(name = "expedia")
		public Object[][] dataProvider()
	    {
			Object[][] testData = ExcelUtility.getTestData("Dates");
			return testData;
		}
  @Test(dataProvider="expedia")
  public void f(String origin, String destination,String leavingMonth,String leavingDate ,String arrivalMonth,String arrivalDate) throws Exception
  {
	     driver.get(Constants.URL);
		// Click on Flight Tab
		WebElement flightTab= gm.getElement("id","tab-flight-tab-hp");
		flightTab.click();
		// Click on roundtripFlight tab
		
		WebElement roundTripTab = gm.getElement("id","flight-type-roundtrip-label-hp-flight");
		roundTripTab.click();
		
		// Enter origin City
		WebElement originCity = gm.getElement("id","flight-origin-hp-flight");
		originCity.sendKeys(origin);
		
		// Enter destination City
		WebElement destinationCity = gm.getElement("id","flight-destination-hp-flight");
		destinationCity.sendKeys(destination);
		Thread.sleep(2000);
	    // Enter Departure Date					
		WebElement departDate = gm.getElement("id","flight-departing-hp-flight");
		 departDate.click();
		
		 List<WebElement> departMonth = gm.getElementList("xpath","//div[@class='datepicker-cal-month']");
		  for(WebElement month :departMonth)
		  {
			  if(month.getText().contains(leavingMonth))
			  {
				 List<WebElement> allValidDates = gm.getElementList("xpath", "//html//div[3]/table[1]/tbody[1]/tr/td/button");
			     for (WebElement date :allValidDates)
			     {
			    	 if(date.getText().equals(leavingDate))
			    	 {
			    		 
			    		 date.click();   		 
			    		 Thread.sleep(2000);
			    		 break;
			    	 }
			     }
			  }  
		  }
		
		
	//Enter Arrival Date
		WebElement returningDate = gm.getElement("id","flight-returning-hp-flight");
		returningDate.click();
		
		List<WebElement> returnMonth = gm.getElementList("xpath","//div[@class='datepicker-cal-month']");
		  for(WebElement month :returnMonth)
		  {
			  if(month.getText().contains(arrivalMonth))
			  {
				 List<WebElement> allValidDates = gm.getElementList("xpath", "//html//div[3]/table[1]/tbody[1]/tr/td/button");
			     for (WebElement date :allValidDates)
			     {
			    	 if(date.getText().equals(arrivalDate))
			    	 {
			    		 
			    		 date.click();   		 
			    		 Thread.sleep(2000);
			    		 break;
			    	 }
			     }
			  }  
		  }
		// Click Search button
		WebElement searchButton = gm.getElement("xpath","//*[@id=\"gcw-flights-form-hp-flight\"]/div[8]/label/button");
		searchButton.click();
		Thread.sleep(2000);
		
//		// Storing the results
//		WebDriverWait wait = new WebDriverWait(driver, 3);
//		WebElement flightName = wait.until(
//				ExpectedConditions.visibilityOfElementLocated(By.xpath(
//						 "//li[@id='flight-module-2018-06-12t19:"
//								+ "08:00-07:00-coach-sea-lax-dl-1437_2018-06-16t19:30:00-07:00-coach-lax-sea-dl-701_']"
//								+ "//div[@class='grid-container standard-padding']")));
//		String flightNo = flightName.getText();
//		System.out.println("The Lowest fare flight for given iteniary :" +flightNo);
  }
 

  @AfterMethod
  public void afterMethod() throws InterruptedException {
	  Thread.sleep(2000);
	  
  }

}
