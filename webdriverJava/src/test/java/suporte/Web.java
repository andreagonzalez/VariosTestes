package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Web {
    public static final String USERNAME = "andreagonzlez1";
    public static final String AUTOMATE_KEY = "CHD1pH81VPjy1KpJkJC7";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static WebDriver createChrome(){
        //Abrindo o navegador
        System.setProperty("webdriver.chrome.driver", "/home/avgonzlez/drivers/chromedriver");
        WebDriver navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //maximiza a janela do navegador para poder clicar no elemento
        navegador.manage().window().maximize();

        //Navegando para a página do Taskit
        navegador.get("http://www.juliodelima.com.br/taskit");

        return navegador;
    }
    public static WebDriver createBrowserStack() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "latest");
        WebDriver navegador = null;

        try {
            navegador = new RemoteWebDriver(new URL(URL), caps);
            navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            navegador.manage().window().maximize();
            navegador.get("http://www.juliodelima.com.br/taskit");
        } catch (MalformedURLException e) {
            System.out.println("Houveram problemas com a URL: " + e.getMessage());
        }

        return navegador;
    }
}