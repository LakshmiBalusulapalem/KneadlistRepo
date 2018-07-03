package intuitionsoftech;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExcelLib {
	
	Properties prop=new Properties();
public 	WebDriver driver;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    Cell cell; 
   Row row;
   String username;
   String password;
    
    ExtentReports reports;
    ExtentTest testInfo;
    ExtentHtmlReporter htmlReporter;
    
    @BeforeTest
    public void setup() throws IOException
    {
    	
    	FileInputStream fis=new FileInputStream("C:\\Users\\laksh\\git\\KneadlistRepo\\Kneadlist\\configuration.properties");
    	prop.load(fis);
    	
    	htmlReporter=new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"TestReports.html"));
    	htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extentConfig.xml");
    	reports=new ExtentReports();
    	reports.setSystemInfo("Environment", "QA");
    	reports.attachReporter(htmlReporter);
    }
    @Test
    public void showProperties()
    {
    	System.out.println(prop.getProperty("firstname"));
    	System.out.println(prop.getProperty("lastname"));
    }
    
    
    
    @Parameters("baseUrl")
    @Test
    public void thaiMassage(String urlName) throws  IOException, InterruptedException
	{
    //	Assert.assertTrue(true);
    	
    	testInfo=reports.createTest("thaiMassage");
    
    	
    	// Import excel sheet.
   	 File src=new File("E:\\EclipseIDE\\EclipseWorkspace\\kneadlist_ExcelDataFile.xlsx");
   	 
   	 // Load the file.
   	 FileInputStream finput = new FileInputStream(src);
   	 
   	 // Load he workbook.
   	workbook = new XSSFWorkbook(finput);
   	 
        // Load the sheet in which data is stored.
   	 sheet= workbook.getSheetAt(0);
   	 
   	
	 for(int i=0;i<2;i++) {
		 for(int j=0;j<=1;j++)
		 {
			 row = sheet.getRow(i);
			 cell=row.getCell(j);
			 if(j==0) {
			 username = cell.getStringCellValue();
			 System.out.println(username);}
			 else {
				 password = cell.getStringCellValue();
				 System.out.println(password);}
			
			 }
		 login(urlName,username,password);
	}
	 }
    
    public void login(String url,String username,String password) throws InterruptedException
    {
    	System.setProperty("webdriver.chrome.driver","E:\\Selenium-Java\\chromedriver_win32\\chromedriver.exe");
		
		  driver=new ChromeDriver();
		  //To launch Knead list application
		  driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			 
   		driver.findElement(By.xpath("//*[@id=\'navbar\']/div[1]/a[1]")).click();
		WebDriverWait wait= new WebDriverWait(driver,20 );
		//Thread.sleep(2000);
		WebElement login_element=driver.findElement(By.xpath("//*[@id='loginemail']"));
		wait.until(ExpectedConditions.visibilityOf(login_element));
		login_element.sendKeys(username);
		driver.switchTo().window(driver.getWindowHandle());
   		
		// Import data for password.
   		//String cell = sheet.getRow(i).getCell(j).getStringCellValue();
   		   driver.findElement(By.xpath("//*[@id=\'loginpassword\']")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\'loginbtn\']")).click();
		//hover on 'Massage' tab and select 'Thai Massage' option
				//Click on Thai Massage link from 'Massage'
				Thread.sleep(2000);
						Actions action = new Actions(driver);
						WebElement we = driver.findElement(By.xpath("//*[@id='navbar']/ul/li[2]/a"));
						action.moveToElement(we).build().perform(); //hover the mouse on 'Massage' tab
					
					WebElement element=driver.findElement(By.xpath("//*[@id='navbar']/ul/li[2]/ul/li[1]/a"));
					
						wait.until(ExpectedConditions.visibilityOf(element));
						element.click(); 
						Thread.sleep(2000);
//						//Click on 'Book Now' button corresponding to the desired store
//						driver.findElement(By.xpath("//div[@id='resultdiv']/div[1]/div[2]/div[1]//a[contains(text(),'Book Now')]")).click();
//						Thread.sleep(2000);
//					
//									//select the service by check box
//						driver.findElement(By.xpath("//*[@id='chk5']")).click();
//						//select date and time
//						WebElement date_element=driver.findElement(By.xpath("//*[@id=\'bookdate\']"));
//						date_element.sendKeys("16-08-2018 18:00:00");
//						date_element.click();
//						//click on 'confirm' button
//						driver.findElement(By.id("popupbookingbtn")).click();
//						WebElement confirm_element=driver.findElement(By.id("confirmbtn"));
//						wait.until(ExpectedConditions.visibilityOf(confirm_element));
//						confirm_element.click();
//					//	Thread.sleep(15000);
//						String success_msg=   driver.findElement(By.xpath("//div[@id='bookNowModal']//div[@id='booking_success_alert']")).getText();
//						  System.out.println(success_msg);
//				driver.findElement(By.xpath("//*[@id='bookNowModal']/div/div/div[1]/button/span")).click();
						testInfo.log(Status.PASS,"This test case passed successfully");
						reports.flush();
						driver.close();
				
				
    }
    @Parameters("baseUrl")
    @Test
    public void japaneseMassage(String urlName)
    {
    	testInfo=reports.createTest("japaneseMassage");
    	System.setProperty("webdriver.chrome.driver","E:\\Selenium-Java\\chromedriver_win32\\chromedriver.exe");
    	driver=new ChromeDriver();
		  //To launch Knead list application
		  driver.get(urlName);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
    	
    	 //Using Actions class- Hover the mouse on 'Massage' tab in navigation bar
	  	Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("//*[@id='navbar']/ul/li[2]/a"));
		action.moveToElement(we).build().perform(); 
		//Click on 'Japanese Massage' link from dropdown
		WebElement element=driver.findElement(By.xpath("//*[@id='navbar']/ul/li[2]/ul/li[2]/a"));
		WebDriverWait JMwait=new WebDriverWait(driver,20);
		JMwait.until(ExpectedConditions.visibilityOf(element));
		element.click();
		
		/*driver.findElement(By.xpath("//*[@id='resultdiv']/div[1]/div[1]/div/div[2]/a")).click(); //click on 'Book Now'
		//switch to pop-up window to select service
		Thread.sleep(2000);
		driver.switchTo().window(driver.getWindowHandle());
		
		//select the service by check box
		driver.findElement(By.xpath("//*[@id='chk23']")).click();
		//select date and time
		WebElement date_element=driver.findElement(By.xpath("//*[@id=\'bookdate\']"));
		date_element.sendKeys("16-08-2018 18:00:00");
		date_element.click();
		//click on 'confirm' button
		driver.findElement(By.id("popupbookingbtn")).click();
		WebElement confirm_element=driver.findElement(By.id("confirmbtn"));
		JMwait.until(ExpectedConditions.visibilityOf(confirm_element));
		confirm_element.click();
		Thread.sleep(15000);
	 String success_msg=   driver.findElement(By.xpath("//div[@id='bookNowModal']//div[@id='booking_success_alert']")).getText();
	  System.out.println(success_msg);
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//*[@id='bookNowModal']/div/div/div[1]/button/span")).click();
	  Thread.sleep(2000);*/
//	driver.findElement(By.xpath("//*[@id='navbar']/div/a")).click();
//		Thread.sleep(2000);
		
		testInfo.log(Status.PASS,"This test case passed successfully");
		reports.flush();
		driver.close();
		
    }
  
@Test
public void failedTest()
{
	testInfo=reports.createTest("failedTest");
		testInfo.log(Status.FAIL,"This test case failed");
		reports.flush();
	}

//				@AfterTest
//				public void teardown() throws InterruptedException
//				{
////					Thread.sleep(2000);
////					driver.findElement(By.xpath("//*[@id='navbar']/div/a")).click();
////					Thread.sleep(2000);
//					driver.quit();
//				}
}
				
	 
