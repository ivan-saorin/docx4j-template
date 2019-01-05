package org.apitooling.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import club.caliope.udc.DocumentConverter;
import club.caliope.udc.InputFormat;
import club.caliope.udc.OutputFormat;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.steppschuh.markdowngenerator.MarkdownBuilder;

public class ExporterOuptput {
	
	private File input;
	private String data = "";
	private Template template;
	private Map<String, Object> context;
	
	public ExporterOuptput() {
		super();
	}

	public ExporterOuptput(String string) {
		super();
		this.data = string;
	}

	public ExporterOuptput(MarkdownBuilder builder) {
		super();
		this.data= builder.toString(); 
	}

	public ExporterOuptput(File input) {
		super();
		this.input = input;
	}

	public ExporterOuptput(Map<String, Object> context, Template template) {
		super();
		this.context = context;
		this.template = template;
	}

	public void toFile(File output) throws IOException {
		if (input != null) {			
			new DocumentConverter()
		     .fromFile(input, InputFormat.DOCBOOK.toString() + "+hard_line_breaks+autolink_bare_uris" )
		     .toFile(output, OutputFormat.DOCX)
		     .addOption("--toc")
		     .addOption("--toc-depth=3")
		     //.addOption("--wrap=auto|none|preserve")
		     .addOption("--wrap=preserve")
		     //.addOption("--standalone") //.addOption("-s") // The default for DOCX
		     .convert();			
		} else if (template != null) {
	        // For the sake of example, also write output into a file:
	        Writer fileWriter = new FileWriter(output);
            try {
				template.process(context, fileWriter);
			} catch (TemplateException cause) {
				throw new IOException(cause);
	        } finally {
	            fileWriter.close();
	        }					
		}
		else {
			FileUtils.writeStringToFile(output, this.data, StandardCharsets.UTF_8);
		}
	}
	
	public String asString() {
		return data;
	}
	
	@Override
	public String toString() {
		return data;
	}

}
