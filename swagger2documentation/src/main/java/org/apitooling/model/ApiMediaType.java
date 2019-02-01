package org.apitooling.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.MediaType;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;

public class ApiMediaType extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiMediaType.class);
	
	private HashMap<String, ApiEncoding> encoding = new LinkedHashMap<String, ApiEncoding>();
	private String example;
	private HashMap<String, ApiExample> examples = new LinkedHashMap<String, ApiExample>();
	private ApiField schema;
	
	public ApiMediaType(ApiModel parent, ApiType modelVersion, OpenAPI model, MediaType mediaType) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, mediaType.getClass().getName(), mediaType.getExtensions());
		describeModel(modelVersion, model, mediaType);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, MediaType mediaType) {
		//if (logger.isInfoEnabled()) logger.info("mediaType: {}", mediaType);
		
		this.describeExtensions(mediaType.getExtensions());
		if (mediaType.getEncoding() != null) {
			Set<String> keys = mediaType.getEncoding().keySet();
			for (String key : keys) {
				this.encoding.put(key, new ApiEncoding(this.getModel(), modelVersion, mediaType.getEncoding().get(key)));
			}
		}
		if (mediaType.getExample() != null) {
			this.example = mediaType.getExample().toString();
		}
		
		if (mediaType.getExamples() != null) {
			Set<String> keys = mediaType.getExamples().keySet();
			for (String key : keys) {
				this.getStats().incExamples();
				this.examples.put(key, new ApiExample(this.getModel(), modelVersion, model, key, mediaType.getExamples().get(key)));
			}
		}
		this.schema = new ApiField(this.getModel(), modelVersion, model, "", mediaType.getSchema());
	}

	public ApiMediaType(ApiModel parent, ApiType modelVersion, Swagger model, String consume, RefModel schema) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, model.getClass().getName(), model.getVendorExtensions());
		describeModel(modelVersion, model, consume, schema);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, String consume, RefModel schema) {
		//if (logger.isInfoEnabled()) logger.info("mediaType: {}", mediaType);
		
		this.describeExtensions(schema.getVendorExtensions());
		
		this.encoding.put(consume, new ApiEncoding(this.getModel(), modelVersion, consume));
		
		if (schema.getExample() != null) {
			this.example = schema.getExample().toString();
		}
		
		this.schema = new ApiField(this.getModel(), modelVersion, model, "schema", schema);
	}

	public HashMap<String, ApiEncoding> getEncoding() {
		return encoding;
	}

	public String getExample() {
		return example;
	}

	public HashMap<String, ApiExample> getExamples() {
		return examples;
	}

	public ApiField getSchema() {
		return schema;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.encoding != null) {
			sb.append("encoding: ");
			sb.append(this.encoding);
			sb.append(", ");
		}
		if (this.example != null) {
			sb.append("example: ");
			sb.append(this.example);
			sb.append(", ");
		}
		if (this.examples != null) {
			sb.append("examples: ");
			sb.append(this.examples);
			sb.append(", ");
		}
		if (this.schema != null) {
			sb.append("schema: ");
			sb.append(this.schema);
		}

		return sb.toString();
	}
}
