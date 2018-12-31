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

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;

public class ApiWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(ApiWalker.class);
	
	private MarkupDocBuilder builder;
	private final String sourceDir;
	private final String destinationDir;
	private final String temporaryDir;
	
	public ApiWalker(File directory, String sourceDir, String destinationDir, String temporaryDir) {
		super(directory, false);
		this.destinationDir = destinationDir;
		File d = new File(directory, destinationDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.sourceDir = sourceDir;		
		d = new File(directory, sourceDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.setDirectory(d);
		this.temporaryDir = temporaryDir;		
		Globals.set(Globals.PROJECT_BASEDIR_FILE, directory.getParentFile());
		d = new File(directory, temporaryDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		initialize();
	}

	@Override
	public void process(File file) throws WebApiException {
		System.err.printf("SWAGGER WALKER PROCESS %s %n", file);
		if (file.getName().endsWith(".yaml") || file.getName().endsWith(".json") && file.length() > 0) {
			try {
				logger.info("processing swagger: " + file.getName() + " [" + file.getAbsolutePath() + "]");
				
				ApiModel model = ApiToolingParser.load(file);
				
				Exporter exporter = ExporterFactory.export(Exporters.LOGGER, model);
				exporter.getOuptput();
			} catch (Throwable cause) {
				throw new WebApiException(cause);
			}
		}
		
	}

	private void initialize() {
		//yaml = new GeneratorYaml(); 
		
		//JsonApiBuilder apiBuilder = new JsonApiBuilder();
		//fsm = new RamlFSM(new Context(this.sourceDir, this.destinationDir, this.jsonDestinationDir), builder, apiBuilder);
	}

}
