package org.apitooling.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import v2.io.swagger.models.ArrayModel;
import v2.io.swagger.models.Model;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.properties.Property;

public class ApiComponents extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiComponents.class);
	
	private LinkedHashMap<String, ApiSchema> simpleTypes = new LinkedHashMap<String, ApiSchema>();
	private LinkedHashMap<String, ApiSchema> schemas = new LinkedHashMap<String, ApiSchema>();
	private LinkedHashMap<String, ApiResponse> responses = new LinkedHashMap<String, ApiResponse>();
	private LinkedHashMap<String, ApiParameter> parameters = new LinkedHashMap<String, ApiParameter>();
	private LinkedHashMap<String, ApiExample> examples = new LinkedHashMap<String, ApiExample>();
	private LinkedHashMap<String, ApiRequestBody> requestBodies = new LinkedHashMap<String, ApiRequestBody>();
	// private Map<String, SecurityScheme> securitySchemes = null;
	private LinkedHashMap<String, ApiLink> links = new LinkedHashMap<String, ApiLink>();
	// private Map<String, APiCallback> callbacks = null;

	public ApiComponents(ApiType modelVersion, OpenAPI model, Components components) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, components.getClass().getName(), components.getExtensions());
		describeModel(modelVersion, model, components);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, Components components) {
		this.describeExtensions(components.getExtensions());
		if (components.getSchemas() != null) {
			Set<String> keys = components.getSchemas().keySet();
			int i = 0;
			for (String key : keys) {
				Schema s = components.getSchemas().get(key);
				LinkedHashMap<String, ApiSchema> map = isSimpleType(s) ? this.simpleTypes : this.schemas;
				map.put(key, new ApiSchema(i++, modelVersion, model, this, key, s));
			}
		}
		if (components.getResponses() != null) {
			Set<String> keys = components.getResponses().keySet();
			int i = 0;
			for (String key : keys) {
				this.responses.put(key, new ApiResponse(i++, modelVersion, model, key, components.getResponses().get(key)));
			}			
		}
		if (components.getParameters() != null) {
			Set<String> keys = components.getParameters().keySet();
			int i = 0;
			for (String key : keys) {
				Parameter p = components.getParameters().get(key);
				String in = p.getIn();
				ApiParameterType type = ApiParameterType.PATH;
				if (in.equalsIgnoreCase("header")) {
					type = ApiParameterType.HEADER;
				} else if (in.equalsIgnoreCase("query")) {
					type = ApiParameterType.QUERY;
				}
				this.parameters.put(key, new ApiParameter(i++, modelVersion, model, type, p));
			}			
		}
		if (components.getExamples() != null) {
			Set<String> keys = components.getExamples().keySet();
			for (String key : keys) {
				this.examples.put(key, new ApiExample(modelVersion, model, key, components.getExamples().get(key)));
			}			
		}
		if (components.getRequestBodies() != null) {
			Set<String> keys = components.getRequestBodies().keySet();
			for (String key : keys) {
				this.requestBodies.put(key, new ApiRequestBody(modelVersion, model, key, components.getRequestBodies().get(key)));
			}			
		}
		if (components.getHeaders() != null) {
			Set<String> keys = components.getHeaders().keySet();
			int i = 0;
			for (String key : keys) {
				this.parameters.put(key, new ApiParameter(i++, modelVersion, model, key, ApiParameterType.HEADER, components.getHeaders().get(key)));
			}
		}
		if (components.getLinks() != null) {
			Set<String> keys = components.getLinks().keySet();
			int i = 0;
			for (String key : keys) {
				this.links.put(key, new ApiLink(i++, modelVersion, model, key, components.getLinks().get(key)));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isSimpleType(Schema schema) {
		boolean b = !schema.getType().equals("object");
		if (b) {
			if (schema.getType().equals("array") && schema instanceof ArraySchema) {
				ArraySchema array = (ArraySchema) schema;
				Schema items = array.getItems();
				b = !items.getType().equals("object");
			}
		}
		return b;
	}

	public ApiComponents(ApiType modelVersion, Swagger model, Map<String, Model> definitions) {
		super();
		describeModel(modelVersion, model, definitions);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, Map<String, Model> definitions) {
		if (definitions != null) {
			Set<String> keys = definitions.keySet();
			int i = 0;
			for (String key : keys) {
				Model m = definitions.get(key);
				LinkedHashMap<String, ApiSchema> map = isSimpleType(m) ? this.simpleTypes : this.schemas;
				map.put(key, new ApiSchema(i++, modelVersion, model, this, key, m));
			}
		}
		
	}

	private boolean isSimpleType(Model model) {
		boolean b = (model.getProperties() == null);
		if (b) {
			if (model instanceof ArrayModel) {
				ArrayModel array = (ArrayModel) model;
				Property items = array.getItems();
				b = !items.getType().equals("object");
			}
		}
		return b;
	}

	public Map<String, ApiSchema> getSimpleTypes() {
		return simpleTypes;
	}

	public Map<String, ApiSchema> getSchemas() {
		return schemas;
	}

	public Map<String, ApiResponse> getResponses() {
		return responses;
	}

	public Map<String, ApiParameter> getParameters() {
		return parameters;
	}

	public Map<String, ApiExample> getExamples() {
		return examples;
	}

	public Map<String, ApiRequestBody> getRequestBodies() {
		return requestBodies;
	}

	public Map<String, ApiLink> getLinks() {
		return links;
	}

}
