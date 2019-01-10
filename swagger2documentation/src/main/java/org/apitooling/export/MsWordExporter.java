package org.apitooling.export;

import java.io.File;
import java.io.IOException;

import org.apitooling.model.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsWordExporter extends DocBookExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(MsWordExporter.class);

	private File temporaryDir;
	private File input;

	protected MsWordExporter() {
		
	}	
	
	public MsWordExporter(ApiModel model, File temporaryDir, File input) {
		super(model, input);
		this.temporaryDir = temporaryDir;
		this.input = input;
	}

	@Override
	public String getStandardFileExtension() {
		return ".docx";
	}
	
	@Override
	public ExporterOuptput getOuptput() throws IOException {
		ExporterOuptput asciidoc = super.getOuptput();
		String name = this.changeExtension(input.getName(), ".xml");
		if (logger.isWarnEnabled()) logger.warn("name: {}", name);
		File temp = new File(temporaryDir, name);
		asciidoc.toFile(temp);
		return new ExporterOuptput(temp);
	}

}
