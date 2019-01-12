package org.apitooling.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import v2.io.swagger.models.Response;
import v2.io.swagger.models.Swagger;

public class ApiResponse extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiResponse.class);
	
	private String ref;
	private String key;
	private String description;
	private ApiContent content;
	private ArrayList<String> produces = null;
	private HashMap<String, String> examples = new HashMap<String, String>(); 
	private ArrayList<ApiParameter> headerAttributes = new ArrayList<ApiParameter>();
	private ArrayList<ApiLink> links = new ArrayList<ApiLink>();
	private ApiSchema schema;
	
	public ApiResponse(ApiModel parent, int index, ApiType modelVersion, OpenAPI model, String key, io.swagger.v3.oas.models.responses.ApiResponse res) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, res.getClass().getName(), res.getExtensions());
		describeModel(index, modelVersion, model, key, res);
	}

	private void describeModel(int index, ApiType modelVersion, OpenAPI model, String key, io.swagger.v3.oas.models.responses.ApiResponse res) {
		if (res.get$ref() != null) {
			this.ref = res.get$ref();
		}
		
		if (res.getContent() != null) {
			this.content = new ApiContent(this.getModel(), modelVersion, model, key, res.getContent());
		}
		
		if (res.getDescription() != null) {
			this.description = res.getDescription();
		}
		
		this.describeExtensions(res.getExtensions());
		
		if (res.getHeaders() != null) {
			Set<String> keys = res.getHeaders().keySet();
			int idxHeader = 0;
			for (String hkey : keys) {
				headerAttributes.add(new ApiParameter(this.getModel(), idxHeader++, modelVersion, model, hkey, ApiParameterType.HEADER, res.getHeaders().get(hkey)));
			}
		}
		
		if (res.getLinks() != null) {
			Set<String> keys = res.getLinks().keySet();
			int idxLink = 0;
			for (String lkey : keys) {
				links.add(new ApiLink(this.getModel(), idxLink++, modelVersion, model, lkey, res.getLinks().get(lkey)));
			}			
		}
	}

	public ApiResponse(ApiModel parent, int index, ApiType modelVersion, Swagger model, List<String> produces, String key, Response res) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, res.getClass().getName(), res.getVendorExtensions());
		describeModel(index, modelVersion, model, produces, key, res);
	}

	private void describeModel(int index, ApiType modelVersion, Swagger model, List<String> produces, String key, Response res) {
		if (res.getExamples() != null) {
			Set<String> keys = res.getExamples().keySet();
			for (String ekey : keys) {
				Object obj = res.getExamples().get(ekey);
				if (obj != null) {
					examples.put(ekey, obj.toString());
				}
			}
		}
				
		if (res.getDescription() != null) {
			this.description = res.getDescription();
		}
		
		this.describeExtensions(res.getVendorExtensions());
		
		if (res.getHeaders() != null) {
			Set<String> keys = res.getHeaders().keySet();
			int idxHeader = 0;
			for (String hkey : keys) {
				headerAttributes.add(new ApiParameter(this.getModel(), idxHeader++, modelVersion, model, hkey, ApiParameterType.HEADER, res.getHeaders().get(hkey)));
			}
		}

		if (res.getExamples() != null) {
			Set<String> keys = res.getExamples().keySet();
			int idxExample = 0;
			for (String ekey : keys) {
				Object example = res.getExamples().get(key);
				if (example != null) {
					this.getStats().incExamples();
					examples.put(ekey, example.toString());
				}
			}
		}

		this.produces = new ArrayList<String>();
		if ((produces != null) && (produces.size() > 0)) {
			this.produces.addAll(produces);
		}
		
		if (res.getSchema() != null) {
			//this.schema = new ApiField(modelVersion, model, res.getSchema());
			this.schema = new ApiSchema(this.getModel(), 0, modelVersion, model, "response", res.getSchema());
		}
		
	}

	public String getRef() {
		return ref;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public ApiContent getContent() {
		return content;
	}

	public ArrayList<ApiParameter> getHeaderAttributes() {
		return headerAttributes;
	}

	public ArrayList<ApiLink> getLinks() {
		return links;
	}

	public HashMap<String, String> getExamples() {
		return examples;
	}

	
	public ArrayList<String> getProduces() {
		return produces;
	}

	public ApiSchema getSchema() {
		return schema;
	}
	
}
