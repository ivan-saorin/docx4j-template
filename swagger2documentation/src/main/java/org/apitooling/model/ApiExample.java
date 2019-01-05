package org.apitooling.model;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;

public class ApiExample extends ApiElement {

    private String summary = null;
    private String description = null;
    private String value = null;

	public ApiExample(ApiType modelVersion, OpenAPI model, String key, Example example) {
		super();
		defineModel(modelVersion, model, key, example);
	}
	
	private void defineModel(ApiType modelVersion, OpenAPI model, String key, Example example) {
		if (example.getSummary() != null) {
			this.summary = example.getSummary();
		}
		else {
			this.summary = key;
		}
		this.description = example.getDescription();
		if (example.getValue() != null) {
			this.value = example.getValue().toString();
		} else if (example.getExternalValue() != null) {
			this.value = example.getExternalValue();
		}
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public String getValue() {
		return value;
	}
	
}
