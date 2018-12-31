package org.apitooling.support;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {
	
	public static String fromFile(String path, Charset encoding) throws IOException {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}

	public static String fromFile(File file, Charset encoding) throws IOException {
		  byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		  return new String(encoded, encoding);
	}	
}
















