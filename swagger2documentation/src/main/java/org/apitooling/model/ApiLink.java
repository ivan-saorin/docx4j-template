package org.apitooling.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.links.Link;

public class ApiLink extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiLink.class);
	
	private String ref;
	private String description;
	private ArrayList<ApiParameter> headerAttributes = new ArrayList<ApiParameter>();
	private String operationId;
	private String operationRef;
	private HashMap<String, String> parameters = new LinkedHashMap<String, String>();
	private Object requestBody;
	private ApiServer server;
	
	public ApiLink(ApiModel parent, int index, ApiType modelVersion, OpenAPI model, String key, Link link) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, link.getClass().getName(), link.getExtensions());
		describeModel(index, modelVersion, model, key, link);
	}

	private void describeModel(int index, ApiType modelVersion, OpenAPI model, String key, Link link) {
		if (link.get$ref() != null) {
			this.ref = link.get$ref();
		}
		if (link.getDescription() != null) {
			this.description = link.getDescription();
		}
		
		this.describeExtensions(link.getExtensions());
		
		if (link.getHeaders() != null) {
			Set<String> keys = link.getHeaders().keySet();
			int idxHeader = 0;
			for (String hkey : keys) {
				headerAttributes.add(new ApiParameter(this.getModel(), idxHeader++, modelVersion, model, hkey, ApiParameterType.HEADER, link.getHeaders().get(hkey)));
			}			
		}
		if (link.getOperationId() != null) {
			this.operationId = link.getOperationId();
		}
		if (link.getOperationRef() != null) {
			this.operationRef = link.getOperationRef();
		}
		if (link.getParameters() != null) {
			this.parameters.putAll(link.getParameters());
		}
		if (link.getRequestBody() != null) {
			this.requestBody = link.getRequestBody();
		}
		if (link.getServer() != null) {
			this.server = new ApiServer(this.getModel(), modelVersion, model, link.getServer());
		}
		
	}

	public String getRef() {
		return ref;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<ApiParameter> getHeaderAttributes() {
		return headerAttributes;
	}

	public String getOperationId() {
		return operationId;
	}

	public String getOperationRef() {
		return operationRef;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public Object getRequestBody() {
		return requestBody;
	}

	public ApiServer getServer() {
		return server;
	}
	
}
