package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;

public class ApiContent extends LinkedHashMap<String, ApiMediaType> {

	private static final long serialVersionUID = 8241536101151816366L;

	public ApiContent(ApiType modelVersion, OpenAPI model, String name, Content content) {
		super();
		describeModel(modelVersion, model, name, content);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String name, Content content) {
		Set<String> keys = content.keySet();
		for (String key : keys) {
			this.put(key, new ApiMediaType(modelVersion, model, content.get(key)));
		}		
	}

	public ApiContent(ApiType modelVersion, Swagger model, List<String> consumes, String name, Model schema) {
		super();
		describeModel(modelVersion, model, consumes, name, (RefModel) schema);
	}

	private void describeModel(ApiType modelVersion, Swagger model, List<String> consumes, String name, RefModel schema) {
		if (consumes != null) {
			for (String consume : consumes) {
				this.put(consume, new ApiMediaType(modelVersion, model, consume, schema));
			}
		} else {
			this.put("*/*", new ApiMediaType(modelVersion, model, "*/*", schema));
		}
	}

}
