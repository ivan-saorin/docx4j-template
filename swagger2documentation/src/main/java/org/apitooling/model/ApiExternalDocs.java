package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import v2.io.swagger.models.ExternalDocs;
import v2.io.swagger.models.Swagger;

public class ApiExternalDocs extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiExternalDocs.class);
	
	private String description;
	private String url;
	
	public ApiExternalDocs(ApiType modelVersion, OpenAPI model, ExternalDocumentation externalDocs) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, externalDocs.getClass().getName(), externalDocs.getExtensions());
		describeModel(modelVersion, model, externalDocs);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, ExternalDocumentation externalDocs) {
		this.describeExtensions(externalDocs.getExtensions());
		this.description = externalDocs.getDescription();
		this.url = externalDocs.getUrl();
	}

	public ApiExternalDocs(ApiType modelVersion, Swagger model, ExternalDocs externalDocs) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, externalDocs.getClass().getName(), externalDocs.getVendorExtensions());
		describeModel(modelVersion, model, externalDocs);
	}

	private void describeModel(ApiType modelVersion, Swagger model, ExternalDocs externalDocs) {
		this.describeExtensions(externalDocs.getVendorExtensions());
		this.description = externalDocs.getDescription();
		this.url = externalDocs.getUrl();
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}
}
