import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstJenkinsTest {
    @Test
    public void testGoogle() {
        // Driver'ı otomatik kurar
        WebDriverManager.chromedriver().setup();

        // AWS (Jenkins) ekranı olmadığı için arka planda (headless) çalışmalı
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.amazon.com");
        System.out.println("Başarıyla bağlandık! Sayfa başlığı: " + driver.getTitle());

        driver.quit();
    }
}
