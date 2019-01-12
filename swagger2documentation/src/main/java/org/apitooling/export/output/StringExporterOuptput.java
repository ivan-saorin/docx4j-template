package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

public class StringExporterOuptput implements ExporterOuptput {
	
private String data = "";
	
	public StringExporterOuptput(String string) {
		super();
		this.data = string;
	}

	public void toFile(File output) throws IOException {
		FileUtils.writeStringToFile(output, this.data, StandardCharsets.UTF_8);
	}
	
	public String asString() {
		return data;
	}
	
	@Override
	public String toString() {
		return data;
	}
}
