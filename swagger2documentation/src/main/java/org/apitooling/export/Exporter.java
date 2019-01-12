package org.apitooling.export;

import java.io.IOException;

public interface Exporter {

	String getStandardFileExtension();
	
	ExporterOuptput getOuptput() throws IOException;

}
