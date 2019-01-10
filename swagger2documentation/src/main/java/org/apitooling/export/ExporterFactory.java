package org.apitooling.export;

import java.io.File;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiModel;

public class ExporterFactory {

	public static Exporter export(ExporterType exporters, ApiModel model) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		case MD: return new MarkdownExporter(model);
		case PDF:
			return null;
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

	public static Exporter export(ExporterType exporters, ApiModel model, File temporaryDir, File file) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		case MD: return new MarkdownExporter(model);
		case MSWORD: return new MsWordExporter(model, temporaryDir, file);
		case PDF:
			return null;
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

}
