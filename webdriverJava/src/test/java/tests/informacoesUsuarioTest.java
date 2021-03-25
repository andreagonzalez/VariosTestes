package tests;


import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "informacoesUsuarioTest.csv")
public class informacoesUsuarioTest {
    private  WebDriver navegador;
    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();

        //Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o formulário de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com name "login" que está dentro do formulário de id "signinbox" o texto "avgonzalez"
        formularioSignInBox.findElement(By.name("login")).sendKeys("avgonzalez");

        //Digitar no campo com name "password" que está dentro do formulário de id "signinbox" e digitar o texto "Julia110805"
        formularioSignInBox.findElement(By.name("password")).sendKeys("Julia110805");

        //Clicar no link com o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar no link onde tem a classe "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que tem o texto "More data about you"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name="mensagem")String mensagemesperada){
        //Clicar no botão através do seu xpath "//button[@data-target="addmoredata"] "
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //Identificar o popup onde está o formulário de id addmoredata
        WebElement popupAddMoredata = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "type" escolher a opção "Phone"
        WebElement campoType = popupAddMoredata.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name "contact" digitar "+5511999999999"
        popupAddMoredata.findElement(By.name("contact")).sendKeys(contato);

        //Clicar no link de text "SAVE" que está na poupup
        popupAddMoredata.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"
        WebElement mensagemPoup = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPoup.getText();
        Assert.assertEquals(mensagemesperada, mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario(){
        // Clicar no elemento pelo seu xpath "//span[text()="+551133334444"]/following-sibling:a"
        navegador.findElement(By.xpath("//span[text()=\"+551133334444\"]/following-sibling::a")).click();

        //Confirmar a janela JavaScript
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi "Rest in peace, dear phone!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        Assert.assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "/home/avgonzlez/test-report/" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        //Aguardar até 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link com texto "Logout"
        navegador.findElement(By.linkText("Logout")).click();
    }
    @After
    public void tearDown(){
        //Fechar o navegador
        navegador.quit();
    }
}
