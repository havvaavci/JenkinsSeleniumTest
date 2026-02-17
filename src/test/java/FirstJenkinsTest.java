import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;

public class FirstJenkinsTest {
    @Test
    public void testGoogle() {
        // 1. ADIM: Jenkins'ten (Maven) gelen parametreyi oku
        String browser = System.getProperty("browser");

        // Eğer parametre boş gelirse (yerelde çalıştırırken) varsayılan olarak chrome olsun
        if (browser == null) {
            browser = "chrome";
        }

        WebDriver driver;

        // 2. ADIM: Karar Mekanizması (İşte aradığın if blokları)
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless"); // AWS için şart
            driver = new FirefoxDriver(options);
        } else {
            // Varsayılan Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        }

        try {
            driver.get("https://www.google.com");
            // Hata yaptırmak için yanlış bir assertion ekleyelim (Opsiyonel)
            Assertions.assertEquals("Amazon", driver.getTitle());
        } catch (Exception e) {
            // Hata olduğunda screenshot al
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                // Dosyayı target klasörüne kaydet ki Jenkins bulabilsin
                FileUtils.copyFile(scrFile, new File("target/screenshot.png"));
                System.out.println("Hata oluştu, screenshot alındı!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            throw e; // Testin başarısız olduğunu Jenkins'e bildir
        } finally {
            driver.quit();
        }
    }
}
