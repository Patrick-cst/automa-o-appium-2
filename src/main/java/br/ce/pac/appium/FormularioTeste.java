package br.ce.pac.appium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.ce.pac.appium.core.DSL;
import br.ce.pac.appium.core.DriverFactory;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FormularioTeste {
	
	private AndroidDriver<MobileElement> driver;
	
	private DSL dsl = new DSL();
	
	@Before
	public void inicializarAppium()throws MalformedURLException{
		driver = DriverFactory.getDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() {
		DriverFactory.killDriver();
	}
	
	@Test
	public void devePreencherCampoTexto() throws MalformedURLException {
		List<MobileElement> elementosEncontrados = driver.findElements(By.className("android.widget.TextView"));
//		for (MobileElement elemento: elementosEncontrados) {
//			System.out.println(elemento.getText());			
//		}
		elementosEncontrados.get(1).click();
		
		dsl.escrever(MobileBy.AccessibilityId("nome"), "Patrick");
		
		assertEquals("Patrick", dsl.obterTexto(MobileBy.AccessibilityId("nome")));
	}
	
	@Test
	public void interagirCombo() throws MalformedURLException {
		driver.findElement(By.xpath("//android.widget.TextView[@text='Formulário']")).click();
		
		driver.findElement(MobileBy.AccessibilityId("console")).click();
		
		driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Nintendo Switch']")).click();
		
		String texto = driver.findElement(By.xpath("//android.widget.Spinner//android.widget.TextView")).getText();
		assertEquals("Nintendo Switch", texto);
	}
	
	@Test
	public void interagirComCheckboxESwitch() throws MalformedURLException {
		driver.findElement(By.xpath("//*[@text='Formulário']")).click();
		
		MobileElement check = driver.findElement(By.className("android.widget.CheckBox"));
		MobileElement switc = driver.findElement(MobileBy.AccessibilityId("switch"));
		
		assertTrue(check.getAttribute("checked").equals("false"));
		assertTrue(switc.getAttribute("checked").equals("true"));
		
		check.click();
		switc.click();
		
		assertFalse(check.getAttribute("checked").equals("false"));
		assertFalse(switc.getAttribute("checked").equals("true"));
	}
	
	
	@Test
	public void desafio() throws MalformedURLException {
		
		String nome = "Patrick";
		String console = "PS4";
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='Formulário']")).click();
		
		driver.findElement(MobileBy.AccessibilityId("nome")).sendKeys(nome);
		
		driver.findElement(MobileBy.AccessibilityId("console")).click();
		
		driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='"+console+"']")).click();
		
		MobileElement checkbox = driver.findElement(MobileBy.AccessibilityId("check"));
		MobileElement switc = driver.findElement(MobileBy.AccessibilityId("switch"));
		
		assertTrue(checkbox.getAttribute("checked").equals("false"));
		assertTrue(switc.getAttribute("checked").equals("true"));
		
		checkbox.click();
		switc.click();
		
		assertTrue(checkbox.getAttribute("checked").equals("true"));
		assertTrue(switc.getAttribute("checked").equals("false"));
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='SALVAR']")).click();
		
		MobileElement validarNome = driver.findElement(By.xpath("//android.widget.TextView[@text='Nome: "+nome+"']"));
		assertEquals("Nome: Patrick", validarNome.getText());
		
		MobileElement validarConsole = driver.findElement(By.xpath("//android.widget.TextView[starts-with(@text, 'Console:')]"));
		assertEquals("Console: ps4", validarConsole.getText());
		
		MobileElement validarSwitch = driver.findElement(By.xpath("//android.widget.TextView[starts-with(@text, 'Switch:')]"));
		assertTrue(validarSwitch.getText().endsWith("Off"));
		
		MobileElement validarCheckbox = driver.findElement(By.xpath("//android.widget.TextView[starts-with(@text, 'Checkbox:')]"));
		assertTrue(validarCheckbox.getText().endsWith("Marcado"));
	}

}
