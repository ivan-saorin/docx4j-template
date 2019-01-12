package org.apitooling.export.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocBookExporterOuptput implements ExporterOuptput {
	
	private Template template;
	private Map<String, Object> context;
	
	public DocBookExporterOuptput(Map<String, Object> context, Template template) {
		super();
		this.context = context;
		this.template = template;
	}

	@Override
	public void toFile(File output) throws IOException {
		if (template != null) {
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
	}
	
	public String asString() {
		return template.toString();
	}
	
	@Override
	public String toString() {
		return this.asString();
	}

}
