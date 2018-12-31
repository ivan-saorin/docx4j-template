package org.apitooling.model;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import v2.io.swagger.models.Swagger;

public class ApiTag extends ApiElement{

	private String name;
	private String description;
	private ApiExternalDocs externalDocs;
	
	public ApiTag(ApiType modelVersion, OpenAPI model, Tag tag) {
		super();
		describeModel(modelVersion, model, tag);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Tag tag) {
		if (tag.getName() != null) {
			this.name = tag.getName();
		}
		
		if (tag.getDescription() != null) {
			this.description = tag.getDescription();
		}
		
		if (tag.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(modelVersion, model, tag.getExternalDocs());
		}
	}

	public ApiTag(ApiType modelVersion, Swagger model, v2.io.swagger.models.Tag tag) {
		super();
		describeModel(modelVersion, model, tag);
	}

	private void describeModel(ApiType modelVersion, Swagger model, v2.io.swagger.models.Tag tag) {
		if (tag.getName() != null) {
			this.name = tag.getName();
		}
		
		if (tag.getDescription() != null) {
			this.description = tag.getDescription();
		}
		
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
