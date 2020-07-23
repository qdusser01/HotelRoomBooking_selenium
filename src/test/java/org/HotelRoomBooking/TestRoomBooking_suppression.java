package org.HotelRoomBooking;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.HotelRoomBooking.*;

public class TestRoomBooking_suppression {

	String jdd_nom_reservation = "resa 1";

	static Logger logger = LoggerFactory.getLogger(TestRoomBooking_suppression.class);
	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	WebElement resa1;

	@Before
	public void setUp() throws Exception {
		BddOutils.deleteAllData("src/main/resources/JDD/nettoyage_table_reservation.xml");
		BddOutils.insertData("src/main/resources/JDD/reservation_room1.xml");
		driver = SocleTechnique.choisirNavigateur(logger, ENavigateur.c);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 7000);
		action = new Actions(driver);		
		
	}

	@After
	public void tearDown() throws Exception {
		BddOutils.deleteAllData("src/main/resources/JDD/nettoyage_table_reservation.xml");
		driver.quit();
	}

	@Test
	public void test() throws Exception {
		
		//accès à l'application Hotel Room Booking 
		driver.get("http://127.0.0.1/TutorialHtml5HotelPhp/");
		
		resa1 = driver.findElement(By.xpath("//div[@class='scheduler_default_event_inner']"));
		action.moveToElement(resa1).build().perform();
		driver.findElement(By.xpath("//div[contains(@class,'scheduler_default_event_delete')]")).click();
		SocleTechnique.Go_Chrono();
		WebElement message_delete = driver.findElement(By.xpath("//div[@class='scheduler_default_message' and contains(.,'Deleted')]"));
		wait.until(ExpectedConditions.visibilityOf(message_delete));
		try {
			assertTrue(message_delete.isDisplayed());
		}
		catch(AssertionError ae) {
			SocleTechnique.takeSnapShot(driver, ".\\target\\snapshots\\"+SocleTechnique.capturerHeure()+" "+this+".png");
			throw ae;
		}
		assertTrue(wait.until(ExpectedConditions.invisibilityOf(message_delete)));
		long temps = SocleTechnique.Stop_Chrono();
		assertTrue(temps > 5000 && temps < 7000);
		

	}

}
