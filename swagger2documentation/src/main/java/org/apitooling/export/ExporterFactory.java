package org.apitooling.export;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiModel;

public class ExporterFactory {

	public static Exporter export(Exporters exporters, ApiModel model) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ASCIIDOC: return new AsciidocExporter(model);
		case PDF:
			return null;
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

}
