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

public class TestRoomBooking_creation {

	String jdd_nom_reservation = "resa 1";

	static Logger logger = LoggerFactory.getLogger(TestRoomBooking_creation.class);
	WebDriver driver;
	WebDriverWait wait;
	Actions action;

	@Before
	public void setUp() {
		driver = SocleTechnique.choisirNavigateur(logger, ENavigateur.c);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 7000);
		action = new Actions(driver);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void test() {
		//accès à l'application Hotel Room Booking / vérification
		driver.get("http://127.0.0.1/TutorialHtml5HotelPhp/");
		assertEquals("HTML5 Hotel Room Booking (JavaScript/PHP)", driver.findElement(By.xpath("//h1/a")).getText());
		
		//Création d'une réservation sur le premier jour du mois (Room1)
		driver.findElement(By.xpath(
				"//div[@class='scheduler_default_cell scheduler_default_cell_business'][contains(@style,'left: 0px; top: 0px')]"))
				.click();
		driver.switchTo().frame(0);
		SocleTechnique.renseignerChamps(driver.findElement(By.id("name")), jdd_nom_reservation);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.switchTo().defaultContent();
		
		// vérification de la création
		WebElement resa1 = driver.findElement(By.xpath("//div[@class='scheduler_default_event_inner']"));
		assertTrue(resa1.getText().contains(jdd_nom_reservation));
		
	}

}
