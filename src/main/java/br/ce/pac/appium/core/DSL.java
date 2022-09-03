package br.ce.pac.appium.core;

import static br.ce.pac.appium.core.DriverFactory.getDriver;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

public class DSL {
	
	public void escrever(By by, String texto) throws MalformedURLException {
		getDriver().findElement(by).sendKeys(texto);
	}

	public String obterTexto(By by) throws MalformedURLException {
		return getDriver().findElement(by).getText();
	}
}
