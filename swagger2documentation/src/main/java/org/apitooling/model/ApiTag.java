package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import v2.io.swagger.models.Swagger;

public class ApiTag extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiTag.class);
	private String name;
	private String description;
	private ApiExternalDocs externalDocs;
	
	public ApiTag(ApiType modelVersion, OpenAPI model, Tag tag) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, tag.getClass().getName(), tag.getExtensions());
		describeModel(modelVersion, model, tag);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Tag tag) {
		if (tag.getName() != null) {
			this.name = tag.getName();
		}
		
		if (tag.getDescription() != null) {
			this.description = tag.getDescription();
		}
		
		this.describeExtensions(tag.getExtensions());
		
		if (tag.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(modelVersion, model, tag.getExternalDocs());
		}
	}

	public ApiTag(ApiType modelVersion, Swagger model, v2.io.swagger.models.Tag tag) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, tag.getClass().getName(), tag.getVendorExtensions());
		describeModel(modelVersion, model, tag);
	}

	private void describeModel(ApiType modelVersion, Swagger model, v2.io.swagger.models.Tag tag) {
		if (tag.getName() != null) {
			this.name = tag.getName();
		}
		
		if (tag.getDescription() != null) {
			this.description = tag.getDescription();
		}
		
		this.describeExtensions(tag.getVendorExtensions());
		
		if (tag.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(modelVersion, model, tag.getExternalDocs());
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ApiExternalDocs getExternalDocs() {
		return externalDocs;
	}
}
