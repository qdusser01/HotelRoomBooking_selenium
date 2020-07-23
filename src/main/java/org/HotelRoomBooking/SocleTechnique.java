package org.HotelRoomBooking;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;

public class SocleTechnique {
	
	static long chrono = 0;

	public static void renseignerChamps(WebElement we, String s) {
		we.clear();
		we.sendKeys(s);
	}

	public static boolean isElementPresent(WebElement we, Logger log) {
		boolean resultat = we.isDisplayed();
		if (resultat == false) {
			log.error(we + " indisponible");
		} else {
			log.info(we + " présent");
		}
		return resultat;
	}
	
/**
 * TODO : remettre au gout de l'application 
 * @param liste_lignes
 * @param valeur_connue
 * @return

	public static int retournerNumeroDeLigne(List<WebElement> liste_lignes, String valeur_connue) {
		int numero_de_ligne = 1;
		for (WebElement ligne : liste_lignes) {
			System.out.println(numero_de_ligne);
			List<WebElement> liste_cellules = ligne.findElements(By.xpath("td"));
			for (WebElement cellule : liste_cellules) {
				if (cellule.getText().equals(valeur_connue)) {
					System.out.println("J'ai trouvé ! " + numero_de_ligne);
					return numero_de_ligne;
				}
			}
			numero_de_ligne++;
		}
		return -1;
	}
	 */

	/**
	 * 
	 * @param logger
	 * @param navigateur ne peut être qu'une valeur parmi ces options : "f" = firef
	 * @return
	 */
	public static WebDriver choisirNavigateur(Logger logger, ENavigateur navigateur) {
		switch (navigateur) {
		case f:
			System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
			return new FirefoxDriver();
		case c:
			System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
			return new ChromeDriver();
		case e:
			System.setProperty("webdriver.edge.driver", "src/main/resources/driver/msedgedriver.exe");
			return new EdgeDriver();
		case ie:
			System.setProperty("webdriver.ie.driver", "src/main/resources/driver/IEDriverServer.exe");
			return new InternetExplorerDriver();
		default:
			logger.warn("le navigateur choisi n'existe pas");
			return null;
		}
	}
	
	public static void Go_Chrono() {
		chrono = System.currentTimeMillis();
	}
	
	public static long Stop_Chrono() {
		long chrono2 = System.currentTimeMillis();
		long temps = chrono2 - chrono;
		System.out.println("Temps ecoule = " + temps + " ms");
		return temps;
		}
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public static String capturerHeure() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM HH_mm_ss");
		String text = date.format(formatter);
		return text;
	}

}
