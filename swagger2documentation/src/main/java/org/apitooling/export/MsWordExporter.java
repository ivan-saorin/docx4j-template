package org.apitooling.export;

import java.io.File;
import java.io.IOException;

import org.apitooling.export.output.ExporterOuptput;
import org.apitooling.export.output.MsWordExporterOutput;
import org.apitooling.model.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsWordExporter extends DocBookExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(MsWordExporter.class);

	private File temporaryDir;
	private File input;
	private File wordTemplate;

	protected MsWordExporter() {
		
	}	
	
	public MsWordExporter(ApiModel model, File temporaryDir, File input) {
		super(model, input);
		this.temporaryDir = temporaryDir;
		this.input = input;
	}

	public MsWordExporter(ApiModel model, File temporaryDir, File file, File wordTemplate) {
		this(model, temporaryDir, file);
		this.wordTemplate = wordTemplate;
	}

	@Override
	public String getStandardFileExtension() {
		return ".docx";
	}
	
	@Override
	public ExporterOuptput getOuptput() throws IOException {
		ExporterOuptput docbook = super.getOuptput();
		String name = this.changeExtension(input.getName(), ".xml");
		if (logger.isWarnEnabled()) logger.warn("name: {}", name);
		File temp = new File(temporaryDir, name);
		docbook.toFile(temp);
		return new MsWordExporterOutput(temp, ExporterType.MSWORD, wordTemplate);
	}

}
