package org.apitooling.export;

import java.io.File;
import java.io.IOException;

import org.apitooling.export.output.ExporterOuptput;
import org.apitooling.export.output.PandocExporterOutput;
import org.apitooling.model.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePandocExporter extends DocBookExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(SimplePandocExporter.class);

	private ExporterType exporterType;
	private File temporaryDir;
	private File input;

	protected SimplePandocExporter(ExporterType exporterType, ApiModel model, File temporaryDir) {
		super(model);
		this.exporterType = exporterType;
	}	
	
	public SimplePandocExporter(ExporterType exporterType, ApiModel model, File temporaryDir, File input) {
		super(model, input);
		this.exporterType = exporterType;
		this.temporaryDir = temporaryDir;
		this.input = input;
	}

	@Override
	public String getStandardFileExtension() {
		return this.exporterType.getExtension();
	}
	
	@Override
	public ExporterOuptput getOuptput() throws IOException {
		ExporterOuptput docbook = super.getOuptput();
		String name = this.changeExtension(input.getName(), ".xml");
		if (logger.isWarnEnabled()) logger.warn("name: {}", name);
		File temp = new File(temporaryDir, name);
		docbook.toFile(temp);
		return new PandocExporterOutput(temp, this.exporterType);
	}

	public ExporterType getExporterType() {
		return exporterType;
	}

	public File getTemporaryDir() {
		return temporaryDir;
	}

	public File getInput() {
		return input;
	}
	
}
