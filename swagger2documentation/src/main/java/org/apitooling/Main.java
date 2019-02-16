package org.apitooling;

import java.io.File;
import java.io.FileNotFoundException;

import org.apitooling.exceptions.WebApiException;
import org.apitooling.export.ExporterType;
import org.apitooling.utils.walker.ApiWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	private File BASE = new File(".\\target\\classes\\");

	public Main() {
		try {
			if (!BASE.exists()) {
					throw new FileNotFoundException("The directory do not exist: " + BASE.getAbsolutePath());
			}
			logger.warn("Base set to: {}", BASE.getAbsolutePath());
			new ApiWalker(BASE, "api", "output", "xml", ExporterType.MSWORD).walk();
		} catch (FileNotFoundException cause) {
			throw new RuntimeException("ERROR", cause);
		} catch (WebApiException cause) {
			throw new RuntimeException("ERROR", cause);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
