import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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

        // 3. ADIM: Test Adımları (Aynı kalıyor)
        driver.get("https://www.google.com");
        System.out.println("Şu an çalışan tarayıcı: " + browser);
        System.out.println("Başarıyla bağlandık! Sayfa başlığı: " + driver.getTitle());

        driver.quit();
    }
}
