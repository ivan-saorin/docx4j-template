package org.apitooling.model;

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
	
	private LinkedHashMap<String, ApiEncoding> encoding = new LinkedHashMap<String, ApiEncoding>();
	private String example;
	private LinkedHashMap<String, ApiExample> examples = new LinkedHashMap<String, ApiExample>();
	private ApiField schema;
	
	public ApiMediaType(ApiType modelVersion, OpenAPI model, MediaType mediaType) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, mediaType.getClass().getName(), mediaType.getExtensions());
		describeModel(modelVersion, model, mediaType);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, MediaType mediaType) {
		//if (logger.isInfoEnabled()) logger.info("mediaType: {}", mediaType);
		
		this.describeExtensions(mediaType.getExtensions());
		if (mediaType.getEncoding() != null) {
			Set<String> keys = mediaType.getEncoding().keySet();
			for (String key : keys) {
				this.encoding.put(key, new ApiEncoding(modelVersion, mediaType.getEncoding().get(key)));
			}
		}
		if (mediaType.getExample() != null) {
			this.example = mediaType.getExample().toString();
		}
		
		if (mediaType.getExamples() != null) {
			Set<String> keys = mediaType.getExamples().keySet();
			for (String key : keys) {
				this.examples.put(key, new ApiExample(modelVersion, model, key, mediaType.getExamples().get(key)));
			}
		}
		this.schema = new ApiField(modelVersion, model, "", mediaType.getSchema());
	}

	public ApiMediaType(ApiType modelVersion, Swagger model, String consume, RefModel schema) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, model.getClass().getName(), model.getVendorExtensions());
		describeModel(modelVersion, model, consume, schema);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, String consume, RefModel schema) {
		//if (logger.isInfoEnabled()) logger.info("mediaType: {}", mediaType);
		
		this.describeExtensions(schema.getVendorExtensions());
		
		this.encoding.put(consume, new ApiEncoding(modelVersion, consume));
		
		if (schema.getExample() != null) {
			this.example = schema.getExample().toString();
		}
		
		this.schema = new ApiField(modelVersion, model, "schema", schema);
	}

	public LinkedHashMap<String, ApiEncoding> getEncoding() {
		return encoding;
	}

	public String getExample() {
		return example;
	}

	public LinkedHashMap<String, ApiExample> getExamples() {
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
