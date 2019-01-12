package org.apitooling.export.output;

import java.io.File;
import java.io.IOException;

import org.apitooling.export.ExporterType;

import club.caliope.udc.DocumentConverter;
import club.caliope.udc.InputFormat;
import club.caliope.udc.OutputFormat;

public class PandocExporterOutput extends InputFileExporterOuptput {

	protected OutputFormat outputFormat;
	
	public PandocExporterOutput(File input) {
		super(input);
	}
	
	public PandocExporterOutput(File input, ExporterType exporterType) {
		super(input);
		switch (exporterType) {
		case MD: outputFormat = OutputFormat.MARKDOWN_GITHUB; break;
		case PDF: outputFormat = OutputFormat.PDF; break;
		default:
			throw new UnsupportedOperationException(exporterType.toString());
		}
	}

	protected void setOutputFormat(ExporterType exporterType) {
		switch (exporterType) {
		case MD: outputFormat = OutputFormat.MARKDOWN_GITHUB; break;
		case PDF: outputFormat = OutputFormat.PDF; break;
		default:
		}
	}

	@Override
	public void toFile(File output) throws IOException {
		if (this.getInput() != null) {			
			new DocumentConverter()
		     .fromFile(this.getInput(), InputFormat.DOCBOOK.toString() + "+hard_line_breaks+autolink_bare_uris" )
		     .toFile(output, this.outputFormat)
		     .addOption("-s") 
		     .convert();			
		}
	}


}
