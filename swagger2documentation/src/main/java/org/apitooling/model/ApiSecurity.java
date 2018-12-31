package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;

public class ApiSecurity extends LinkedHashMap<String, List<String>> {

	public ApiSecurity(ApiType modelVersion, OpenAPI model, SecurityRequirement security) {
		super();
		describeModel(modelVersion, model, security);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, SecurityRequirement security) {
		this.clear();
		this.putAll(security);
	}

}
