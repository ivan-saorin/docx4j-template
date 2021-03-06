package org.apitooling.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.RequestBody;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.parameters.BodyParameter;

public class ApiRequestBody extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiRequestBody.class);
	
	public String name;
	public String description;
	public boolean required = false;
	public ApiContent content;
	
	public ApiRequestBody(ApiModel parent, ApiType modelVersion, OpenAPI model, String name, RequestBody requestBody) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, requestBody.getClass().getName(), requestBody.getExtensions());
		describeModel(modelVersion, model, name, requestBody);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String name, RequestBody requestBody) {
		this.name = name;
		if (requestBody.getDescription() != null) {
			this.description = requestBody.getDescription();
		}
		this.describeExtensions(requestBody.getExtensions());
		if (requestBody.getRequired() != null) {
			this.required = requestBody.getRequired();
		}
		if (requestBody.getContent() != null) {
			this.content = new ApiContent(this.getModel(), modelVersion, model, name, requestBody.getContent());
		}
	}
	
	public ApiRequestBody(ApiModel parent, ApiType modelVersion, Swagger model, String string, List<String> consumes, BodyParameter param) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, param.getClass().getName(), param.getVendorExtensions());
		describeModel(modelVersion, model, name, consumes, param);
	}

	private void describeModel(ApiType modelVersion, Swagger model, String name, List<String> consumes, BodyParameter param) {
		this.name = param.getName();
		
		if (param.getDescription() != null) {
			this.description = param.getDescription();
		}
		this.describeExtensions(param.getVendorExtensions());
		this.required = param.getRequired();		
		this.content = new ApiContent(this.getModel(), modelVersion, model, consumes, name, param.getSchema());
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRequired() {
		return required;
	}

	public ApiContent getContent() {
		return content;
	}
	
}
