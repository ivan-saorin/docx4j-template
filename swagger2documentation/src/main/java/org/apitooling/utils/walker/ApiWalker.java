package org.apitooling.utils.walker;

import java.io.File;

import org.apitooling.exceptions.WebApiException;
import org.apitooling.export.Exporter;
import org.apitooling.export.ExporterFactory;
import org.apitooling.export.ExporterOuptput;
import org.apitooling.export.ExporterType;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiToolingParser;
import org.apitooling.support.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(ApiWalker.class);
	
	private final File sourceDir;
	private final File destinationDir;
	private final File temporaryDir;
	private final ExporterType[] exporters;
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir, ExporterType exporter) {
		this(directory, sourceDir, destinationDir, temporaryDir, new ExporterType[] {exporter});
	}
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir, ExporterType[] exporters) {
		super(directory, false);
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
		System.err.printf("SWAGGER WALKER PROCESS %s %n", file);
		if (file.getName().endsWith(".yaml") || file.getName().endsWith(".json") && file.length() > 0) {
			try {
				logger.info("processing swagger: " + file.getName() + " [" + file.getAbsolutePath() + "]");
				
				ApiModel model = ApiToolingParser.load(file);
				
				//String name = changeExtension(file.getName(), ".xml");
				for (ExporterType eType : exporters) {
					Exporter exporter = ExporterFactory.export(eType, model, this.temporaryDir, file);
					String extension = exporter.getStandardFileExtension();
					ExporterOuptput out = exporter.getOuptput();
					if (extension != null) {
						String name = changeExtension(file.getName(), extension);
						out.toFile(new File(this.destinationDir, name));
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
