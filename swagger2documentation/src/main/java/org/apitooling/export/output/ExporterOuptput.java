package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;

public interface ExporterOuptput {
	
	public void toFile(File output) throws IOException;
	
	public String asString();
	
	public String toString();

}
