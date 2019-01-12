package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;

public abstract class InputFileExporterOuptput implements ExporterOuptput {

	private File input;
	
	public InputFileExporterOuptput(File input) {
		super();
		this.input = input;
	}
	
	public abstract void toFile(File output) throws IOException;
	
	public String asString() {
		return this.input.getAbsolutePath();
	}
	
	@Override
	public String toString() {
		return this.asString();
	}

	public File getInput() {
		return input;
	}

}
