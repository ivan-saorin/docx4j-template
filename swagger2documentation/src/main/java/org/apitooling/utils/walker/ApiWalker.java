package org.apitooling.utils.walker;

import java.io.File;

import org.apitooling.exceptions.WebApiException;
import org.apitooling.export.Exporter;
import org.apitooling.export.ExporterFactory;
import org.apitooling.export.Exporters;
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
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir) {
		super(directory, false);
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
				
				//Exporter exporter = ExporterFactory.export(Exporters.LOGGER, model);
				//exporter.getOuptput();
				String name = changeExtension(file.getName(), ".md");
				
				Exporter exporter = ExporterFactory.export(Exporters.MSWORD, model, this.temporaryDir, file);
				name = changeExtension(file.getName(), ".docx");
				exporter.getOuptput().toFile(new File(this.destinationDir, name));
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

}
