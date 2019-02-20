package org.apitooling.export;

import java.io.File;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiModel;

public class ExporterFactory {

	public static Exporter export(ExporterType exporters, ApiModel model) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

	public static Exporter export(ExporterType exporters, ApiModel model, File temporaryDir) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		case MD: return new SimplePandocExporter(exporters, model, temporaryDir);
		case PDF: return new SimplePandocExporter(exporters, model, temporaryDir);
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

	public static Exporter export(ExporterType exporters, ApiModel model, File temporaryDir, File file) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		case MD: return new SimplePandocExporter(exporters, model, temporaryDir, file);
		case PDF: return new SimplePandocExporter(exporters, model, temporaryDir, file);
		case MSWORD: return new MsWordExporter(model, temporaryDir, file);
		case MSEXCEL: return new MsExcelExporter(model, file);
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}

	public static Exporter export(ExporterType exporters, ApiModel model, File temporaryDir, File file, File wordTemplate) {
		switch (exporters) {
		case LOGGER: return new LoggerExporter(model);
		case ANALYSIS_LOGGER: return new AnalysisLoggerExporter(model);
		case MD: return new SimplePandocExporter(exporters, model, temporaryDir, file);
		case PDF: return new SimplePandocExporter(exporters, model, temporaryDir, file);
		case MSWORD: return new MsWordExporter(model, temporaryDir, file, wordTemplate);
		case MSEXCEL: return new MsExcelExporter(model, file);
		default:
			throw new WebApiRuntimeException("Unexpected value: Exporters > " + exporters);
		}		
	}
	
}
