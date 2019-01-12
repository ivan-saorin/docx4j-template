package org.apitooling.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.RefModel;
import v2.io.swagger.models.Swagger;

public class ApiContent extends HashMap<String, ApiMediaType> {

	private static final long serialVersionUID = 8241536101151816366L;
	private static Logger logger = LoggerFactory.getLogger(ApiContent.class);
	
	private static final String XIMPLEMENTATION_KEY = "x-implementation";	
	protected HashMap<String, Object> xImplementation = new HashMap<String, Object>();  

	private ApiModel parent;
	
	public ApiContent(ApiModel parent, ApiType modelVersion, OpenAPI model, String name, Content content) {
		super();
		this.parent = parent;
		describeModel(modelVersion, model, name, content);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String name, Content content) {
		Set<String> keys = content.keySet();
		for (String key : keys) {
			this.put(key, new ApiMediaType(parent, modelVersion, model, content.get(key)));
		}		
	}

	public ApiContent(ApiModel parent, ApiType modelVersion, Swagger model, List<String> consumes, String name, Model schema) {
		super();
		this.parent = parent;
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, schema.getClass().getName(), schema.getVendorExtensions());
		describeModel(modelVersion, model, consumes, name, (RefModel) schema);
	}

	private void describeModel(ApiType modelVersion, Swagger model, List<String> consumes, String name, RefModel schema) {
		this.describeExtensions(schema.getVendorExtensions());
		if (consumes != null) {
			for (String consume : consumes) {
				this.put(consume, new ApiMediaType(parent, modelVersion, model, consume, schema));
			}
		} else {
			this.put("*/*", new ApiMediaType(parent, modelVersion, model, "*/*", schema));
		}
	}

	@SuppressWarnings("unchecked")
	protected void describeExtensions(Map<String, Object> extensions) {
		if (extensions == null) {
			return;
		}
		
		if (extensions.containsKey(XIMPLEMENTATION_KEY)) {
			Map<String, Object> extension = (Map<String, Object>) extensions.get(XIMPLEMENTATION_KEY);
			xImplementation.putAll(extension);
		}		
	}
	
	public Map<String, Object> getxImplementation() {
		return xImplementation;
	}	

}
