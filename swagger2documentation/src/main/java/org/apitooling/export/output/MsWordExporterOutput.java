package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;

import org.apitooling.export.ExporterType;

import club.caliope.udc.DocumentConverter;
import club.caliope.udc.InputFormat;
import club.caliope.udc.OutputFormat;

public class MsWordExporterOutput extends PandocExporterOutput {

	private OutputFormat outputFormat;
	private File wordTemplate;

	public MsWordExporterOutput(File input, ExporterType exporterType) {
		this(input, exporterType, null);
	}
	
	public MsWordExporterOutput(File input, ExporterType exporterType, File wordTemplate) {
		super(input);		
		this.wordTemplate = wordTemplate;
		switch (exporterType) {
		case MSWORD: outputFormat = OutputFormat.DOCX; break;
		default:
			throw new UnsupportedOperationException(exporterType.toString());
		}
	}

	@Override
	public void toFile(File output) throws IOException {
		if (this.getInput() != null) {			
			DocumentConverter converter = new DocumentConverter()
		     .fromFile(this.getInput(), InputFormat.DOCBOOK.toString() + "+hard_line_breaks+autolink_bare_uris" )
		     .toFile(output, this.outputFormat)
		     .addOption("--toc")
		     .addOption("--toc-depth=3")
		     .addOption("--wrap=preserve");
			if (wordTemplate != null) {
				converter.addOption("--reference-doc=" + wordTemplate.getAbsolutePath());
			}
		     //.addOption("--standalone") //.addOption("-s") // The default for DOCX
			converter.convert();			
		}
	}


}
