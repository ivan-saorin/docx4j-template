package org.swaggertooling.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Path;

public class SwaggerModel extends SwaggerElement {

	private static Logger logger = LoggerFactory.getLogger(SwaggerModel.class);	
	private String openApiVersion;
	private String apiVersion;	
	private SwaggerContact contact;
	private SwaggerLicense license;	
	private String termOfService;
	private String title;
	private String description;
	private ArrayList<SwaggerPath> paths = new ArrayList<SwaggerPath>();
	
	public SwaggerModel(OpenApi3 openApi) {
		super();
		describeModel(openApi);
	}

	private void describeModel(OpenApi3 model) {
		if (logger.isInfoEnabled()) logger.info("openApiVersion: {}", model.getOpenApi());
		if (logger.isInfoEnabled()) logger.info("apiVersion: {}", model.getInfo().getVersion());
		if (logger.isInfoEnabled()) logger.info("title: {}", model.getInfo().getTitle());
		if (logger.isInfoEnabled()) logger.info("termOfService: {}", model.getInfo().getTermsOfService());
		if (logger.isInfoEnabled()) logger.info("description: {}", model.getInfo().getDescription());		
		if (logger.isInfoEnabled()) logger.info("contact: {}", model.getInfo().getContact());
		if (logger.isInfoEnabled()) logger.info("license: {}", model.getInfo().getLicense());
		
		this.openApiVersion = model.getOpenApi();
		this.apiVersion = model.getInfo().getVersion();		
		this.title = this.md2html(model.getInfo().getTitle());
		if (model.getInfo().getDescription() != null) {
			this.description = this.md2html(model.getInfo().getDescription());
		}
		if (model.getInfo().getTermsOfService() != null) {
			this.termOfService = this.md2html(model.getInfo().getTermsOfService());
		}
		if (model.getInfo().getContact() != null) {
			this.contact = new SwaggerContact(model.getInfo().getContact());
		}
		if (model.getInfo().getLicense() != null) {
			this.license = new SwaggerLicense(model.getInfo().getLicense());
		}
		
		int idx = 0;
		for (Path path : model.getPaths().values()) {
			paths.add(new SwaggerPath(idx++, path));
		}
	}

	public String getTitle() {
		return title;
	}
	
	public String getOpenApiVersion() {
		return openApiVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public String getDescription() {
		return description;
	}

	public String getTermOfService() {
		return termOfService;
	}

	public SwaggerContact getContact() {
		return contact;
	}

	public SwaggerLicense getLicense() {
		return license;
	}

	public ArrayList<SwaggerPath> getPaths() {
		return paths;
	}
	
}
