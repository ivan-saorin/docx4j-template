package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;

public class ApiExample extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiExample.class);
	
	private String mediaType = null;
    private String summary = null;
    private String description = null;
    private String note = null;
    private String value = null;

	public ApiExample(ApiModel parent, ApiType modelVersion, OpenAPI model, String key, Example example) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, example.getClass().getName(), example.getExtensions());
		defineModel(modelVersion, model, key, example);
	}

	
	public ApiExample(ApiModel parent, String summary, String description, String note, String value) {
		super(parent);
		this.summary = summary;
		this.description = description;
		this.note = note;
		this.value = value;
		this.getStats().incExamples();
	}


	public ApiExample(ApiModel model, String mediaType, String summary, String description, String note, String exampleCode) {
		super(model);
		this.mediaType = mediaType;
		this.summary = summary;
		this.description = description;
		this.note = note;
		this.value = exampleCode;
		this.getStats().incExamples();
	}


	private void defineModel(ApiType modelVersion, OpenAPI model, String key, Example example) {
		if (example.getSummary() != null) {
			this.summary = example.getSummary();
		}
		else {
			this.summary = key;
		}
		this.description = example.getDescription();
		this.describeExtensions(example.getExtensions());
		if (example.getValue() != null) {
			this.value = example.getValue().toString();
		} else if (example.getExternalValue() != null) {
			this.value = example.getExternalValue();
		}
		this.getStats().incExamples();
	}

	public String getMediaType() {
		return mediaType;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}
	
	public String getNote() {
		return note;
	}

	public String getValue() {
		return value;
	}
	
}
