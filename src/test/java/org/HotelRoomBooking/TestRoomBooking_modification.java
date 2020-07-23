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

public class TestRoomBooking_modification {

	String jdd_nom_reservation = "resa 1";

	static Logger logger = LoggerFactory.getLogger(TestRoomBooking_modification.class);
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
	public void test() {
		driver.get("http://127.0.0.1/TutorialHtml5HotelPhp/");
		//Déplacement de la reservation par drag and drop
		WebElement celluletarget = driver.findElement(By.xpath("//div[contains(@class,'cell_business')][contains(@style,'left: 40px; top: 0px;')]"));
		resa1 = driver.findElement(By.xpath("//div[@class='scheduler_default_event_inner']"));
		action.dragAndDrop(resa1, celluletarget).build().perform();
		
		//vérification de l'apparition puis de la disparition du message de confirmation de la modification de la reservation 
		WebElement messagesuccessful = driver
				.findElement(By.xpath("//div[@class='scheduler_default_message' and contains(.,'Update successful')]"));
		SocleTechnique.Go_Chrono();
		wait.until(ExpectedConditions.visibilityOf(messagesuccessful));
		assertTrue(messagesuccessful.isDisplayed());
		assertTrue(wait.until(ExpectedConditions.invisibilityOf(messagesuccessful)));
		long temps = SocleTechnique.Stop_Chrono();
		assertTrue(temps > 2000 && temps < 7000);

	}

}
