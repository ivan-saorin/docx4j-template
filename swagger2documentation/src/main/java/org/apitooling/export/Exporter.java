package org.apitooling.export;

import java.io.IOException;

import org.apitooling.export.output.ExporterOuptput;

public interface Exporter {

	String getStandardFileExtension();

	ExporterOuptput getOuptput() throws IOException;

}
