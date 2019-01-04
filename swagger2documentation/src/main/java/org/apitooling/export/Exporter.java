package org.apitooling.export;

import java.io.IOException;

public interface Exporter {

	ExporterOuptput getOuptput() throws IOException;

}
