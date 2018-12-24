package org.apifever.utils.walker;

import java.io.File;

import org.apifever.exceptions.WebApiException;
import org.apifever.raml.support.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;
import io.github.swagger2markup.markup.builder.MarkupDocBuilders;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

public class SwaggerWalker extends DirectoryWalker {

	private static Logger logger = LoggerFactory.getLogger(SwaggerWalker.class);
	
	private MarkupDocBuilder builder;
	private final String sourceDir;
	private final String destinationDir;
	private final String jsonDestinationDir;
	
	public SwaggerWalker(File directory, String sourceDir, String destinationDir, String jsonDestinationDir) {
		super(directory, false);
		this.destinationDir = destinationDir;
		File d = new File(destinationDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		this.sourceDir = sourceDir;		
		d = new File(sourceDir);
		if (!d.exists()) {
			d.mkdirs();
		}		
		this.jsonDestinationDir = jsonDestinationDir;		
		Globals.set(Globals.PROJECT_BASEDIR_FILE, directory.getParentFile());
		d = new File(jsonDestinationDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		initialize();
	}

	@Override
	public void process(File file) throws WebApiException {
		System.err.printf("RAML WALKER PROCESS %s %n", file);
		if (file.getName().endsWith(".raml") && file.length() > 0) {
			try {
				logger.info("processing raml: " + file.getName() + " [" + file.getAbsolutePath() + "]");
				
				/*
				Object object = yaml.load(new FileReader(file));			
				
				logger.info("-->" + object.toString());
				
				fsm.apply(RamlFSM.Event.START, object);
				
				fsm.apply(RamlFSM.Event.END, file);
				
				*/
				
				initialize();
			} catch (Throwable cause) {
				throw new WebApiException(cause);
			}
		}
		
	}

	private void initialize() {
		//yaml = new GeneratorYaml(); 
		MarkupDocBuilder builder = MarkupDocBuilders.documentBuilder(MarkupLanguage.ASCIIDOC);
		//JsonApiBuilder apiBuilder = new JsonApiBuilder();
		//fsm = new RamlFSM(new Context(this.sourceDir, this.destinationDir, this.jsonDestinationDir), builder, apiBuilder);
	}

}
