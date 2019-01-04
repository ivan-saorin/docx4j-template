package org.apitooling.export;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiType;
import org.apitooling.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class DocBookExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(DocBookExporter.class);
	private static String LF = System.lineSeparator() + "\t";
	private static String TAB = "\t";

	private Configuration cfg = new Configuration(new Version(2, 3, 20));
	private File input;
	private ApiModel model;
	private Map<String, Object> context = new HashMap<String, Object>();
	
	protected DocBookExporter() {		
		// 1. Configure FreeMarker
        // Where do we load the templates from:
        //cfg.setClassForTemplateLoading(MainTest.class, "templates");
		//cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/target/test-classes/tpl"));
		//cfg.setClassLoaderForTemplateLoading(DocBookExporter.class.getClassLoader(), "target.test-classes.tpl");
    	File dirFile = new File(DocBookExporter.class.getResource("/tpl/").getPath());
		try {
			cfg.setTemplateLoader(new FileTemplateLoader(dirFile));
		} catch (IOException cause) {
			throw new RuntimeException(cause);
		}

        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);        
	}	
	
	public DocBookExporter(ApiModel model) {
		this();
		this.model = model;
        // 2.1. Prepare the template input:
        context.put("model", this.model);
        context.put("pathsById", ApiUtils.groupByApi(model.getPaths()));
        if (model.getModelVersion().equals(ApiType.SWAGGER_20))
        	context.put("modelVersion", "Swagger 2.x");
        else
        	context.put("modelVersion", "Open API 3.x");
        
        context.put("pubDate", new Date(System.currentTimeMillis()));
	}

	public DocBookExporter(ApiModel model, File input) {
		this(model);
		this.input = input;
        // 2.1. Prepare the template input:
        context.put("input", this.input.getName());				
	}

	protected String changeExtension(String name, String extWithDot) {
		int i = name.lastIndexOf('.');
		if (i > -1) {
			name = name.substring(0, i) + extWithDot;
		}
		return name;
	}

	@Override
	public ExporterOuptput getOuptput() throws IOException {
		if (model == null) {
			throw new WebApiRuntimeException("Missing model");
		}

        // 2.2. Get the template

        Template template = cfg.getTemplate("docbook.ftl");

        // 2.3. Generate the output
		//outputApi(model);
		return new ExporterOuptput(context, template);
	}
}
