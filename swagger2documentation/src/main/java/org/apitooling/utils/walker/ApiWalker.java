package org.apitooling.utils.walker;

import java.io.File;

import org.apitooling.exceptions.WebApiException;
import org.apitooling.export.Exporter;
import org.apitooling.export.ExporterFactory;
import org.apitooling.export.ExporterType;
import org.apitooling.export.output.ExporterOuptput;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiToolingParser;
import org.apitooling.support.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ApiWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(ApiWalker.class);
	
	private final File sourceDir;
	private final File destinationDir;
	private final File temporaryDir;
	private final ExporterType[] exporters;
	private final String[] extensions;
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir, String[] extensions, ExporterType exporter) {
		this(directory, sourceDir, destinationDir, temporaryDir, extensions, new ExporterType[] {exporter});
	}
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir, String[] extensions, ExporterType[] exporters) {
		super(directory, false);
		this.extensions = extensions;
		this.exporters = exporters;
		// dest
		File d = new File(directory, destinationDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.destinationDir = d;
		// source
		d = new File(directory, sourceDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.setDirectory(d);
		this.sourceDir = d;
		// temp
		Globals.set(Globals.PROJECT_BASEDIR_FILE, directory.getParentFile());
		d = new File(directory, temporaryDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.temporaryDir = d;
		initialize();
	}

	@Override
	public void process(File file) throws WebApiException {
		if (logger.isWarnEnabled()) logger.warn("SWAGGER WALKER EVALUATING FILE: {}", file.getName());
		
		if (file.length() > 0) {
			boolean match = false;
			for (String ext : extensions) {
				match = file.getName().endsWith(ext);
				if (match) break;
			}
			if (match) {
				try {
					MDC.put("logFileName", file.getName());
					if (logger.isWarnEnabled()) logger.warn("SWAGGER WALKER PROCESSING FILE: {}", file.getName());
					//logger.info("processing swagger: " + file.getName() + " [" + file.getAbsolutePath() + "]");
					
					ApiModel model = ApiToolingParser.load(file);
					
					//String name = changeExtension(file.getName(), ".xml");
					for (ExporterType eType : exporters) {
						Exporter exporter;
						String extension = eType.getExtension();
						String name = changeExtension(file.getName(), extension);
						File output = new File(this.destinationDir, name);
						
						if (eType.equals(ExporterType.MSWORD)) {
							File wordTemplate = new File(this.sourceDir, "Template.docx");
							if (!wordTemplate.isDirectory() && wordTemplate.exists()) {
								exporter = ExporterFactory.export(eType, model, this.temporaryDir, file, wordTemplate);
							}
							else {
								exporter = ExporterFactory.export(eType, model, this.temporaryDir, file);
							}
						}
						else if (eType.equals(ExporterType.MSEXCEL)) {
							exporter = ExporterFactory.export(eType, model, this.temporaryDir, output);
						}
						else {
							exporter = ExporterFactory.export(eType, model, this.temporaryDir, file);
						}
						ExporterOuptput out = exporter.getOuptput();
						if (extension != null) {
							out.toFile(output);
						}
					}
					/*
					FileUtils.writeStringToFile(
							new File(new File(this.temporaryDir), file.getName()), 
							exporter.getOuptput().asString(), 
							"UTF-8", 
							false *append*);
					*/
				} catch (Throwable cause) {
					throw new WebApiException(cause);
				} finally {
					MDC.remove("logFileName");
				}
			}
		}		
	}

	private String changeExtension(String name, String extWithDot) {
		int i = name.lastIndexOf('.');
		if (i > -1) {
			name = name.substring(0, i) + extWithDot;
		}
		return name;
	}

	private void initialize() {
		//yaml = new GeneratorYaml(); 
		
		//JsonApiBuilder apiBuilder = new JsonApiBuilder();
		//fsm = new RamlFSM(new Context(this.sourceDir, this.destinationDir, this.jsonDestinationDir), builder, apiBuilder);
	}

	public File getSourceDir() {
		return sourceDir;
	}

	public File getDestinationDir() {
		return destinationDir;
	}

	public File getTemporaryDir() {
		return temporaryDir;
	}

	public ExporterType[] getExporters() {
		return exporters;
	}
}
