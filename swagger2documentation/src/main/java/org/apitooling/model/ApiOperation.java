package org.apitooling.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import v2.io.swagger.models.HttpMethod;
import v2.io.swagger.models.Response;
import v2.io.swagger.models.Swagger;
import v2.io.swagger.models.parameters.BodyParameter;
import v2.io.swagger.models.parameters.Parameter;

public class ApiOperation extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiOperation.class);
	
	public int idx;
	private String method;
	private String id;
	private String summary;
	private String description;
	private boolean deprecated = false;	
	private ApiRequestBody requestBody;
	private HashMap<String, ApiResponse> responses = new LinkedHashMap<String, ApiResponse>();
	private ApiExternalDocs externalDocs;
	private ArrayList<String> consumes = new ArrayList<String>();
	private ArrayList<String> produces = new ArrayList<String>();
	private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<ApiParameter> pathParams = new ArrayList<ApiParameter>();
	private ArrayList<ApiParameter> headerAttributes = new ArrayList<ApiParameter>();
	private ArrayList<ApiParameter> queryParams = new ArrayList<ApiParameter>();
	
	public ApiOperation(ApiModel parent, int idx, ApiType modelVersion, OpenAPI model, io.swagger.v3.oas.models.PathItem.HttpMethod method, Operation op) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, op.getClass().getName(), op.getExtensions());
		this.idx = idx;
		describeModel(modelVersion, model, method, op);
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, io.swagger.v3.oas.models.PathItem.HttpMethod method, Operation op) {		
		this.method = method.toString();
		this.getStats().incOperations(this.method);
		this.id = op.getOperationId();		
		this.description = op.getDescription();
		
		this.describeExtensions(op.getExtensions());
		
		this.summary = op.getSummary();
		if (op.getDeprecated() != null) {
			this.deprecated = op.getDeprecated();
		}
		if (op.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(this.getModel(), modelVersion, model, op.getExternalDocs());
		}

		if (op.getParameters() != null) {
			int idxPath = 0;
			int idxHeader = 0;
			int idxQuery = 0;
			for (io.swagger.v3.oas.models.parameters.Parameter param : op.getParameters()) {
				if (param.getIn().equalsIgnoreCase("path")) {
					this.getStats().incPathParams();
					pathParams.add(new ApiParameter(this.getModel(), idxPath++, modelVersion, model, ApiParameterType.PATH, param));
				}
				else if (param.getIn().equalsIgnoreCase("header")) {
					this.getStats().incHeaderAttrs();
					headerAttributes.add(new ApiParameter(this.getModel(), idxHeader++, modelVersion, model, ApiParameterType.HEADER, param));
				}
				else if (param.getIn().equalsIgnoreCase("query")) {
					this.getStats().incQueryParams();
					queryParams.add(new ApiParameter(this.getModel(), idxQuery++, modelVersion, model, ApiParameterType.QUERY, param));
				}
				else if (param.getIn().equalsIgnoreCase("body")) {
					if (logger.isInfoEnabled()) logger.info("  {} {}", "body", param);
				}
			}
		}		

		if (op.getRequestBody() != null) {
			this.requestBody = new ApiRequestBody(this.getModel(), modelVersion, model, "requestBody", op.getRequestBody());
		}
		if ((op.getResponses() != null) && (op.getResponses().size() > 0)) {
			Set<String> keys = op.getResponses().keySet();
			int idxResponse = 0;
			for (String key : keys) {
				io.swagger.v3.oas.models.responses.ApiResponse res = op.getResponses().get(key);
				this.responses.put(key, new ApiResponse(this.getModel(), idxResponse++, modelVersion, model, key, res));
			}
		}
	}

	public ApiOperation(ApiModel parent, int idx, ApiType modelVersion, Swagger model, HttpMethod method, v2.io.swagger.models.Operation op) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, op.getClass().getName(), op.getVendorExtensions());
		this.idx = idx;
		describeModel(modelVersion, model, method, op);
	}
	
	private void describeModel(ApiType modelVersion, Swagger model, HttpMethod method, v2.io.swagger.models.Operation op) {
		this.method = method.toString();
		this.getStats().incOperations(this.method);
		this.id = op.getOperationId();		
		this.description = op.getDescription();
		
		this.describeExtensions(op.getVendorExtensions());
		
		this.summary = op.getSummary();
		if (op.isDeprecated() != null) {
			this.deprecated = op.isDeprecated();
		}		
		if (op.getExternalDocs() != null) {
			this.externalDocs = new ApiExternalDocs(this.getModel(), modelVersion, model, op.getExternalDocs());
		}

		int idxPath = 0;
		int idxHeader = 0;
		int idxQuery = 0;

		for (Parameter param : op.getParameters()) {
			if (param.getIn().equalsIgnoreCase("path")) {
				this.getStats().incPathParams();
				pathParams.add(new ApiParameter(this.getModel(), idxPath++, modelVersion, model, ApiParameterType.PATH, param));
			}
			else if (param.getIn().equalsIgnoreCase("header")) {
				this.getStats().incHeaderAttrs();
				headerAttributes.add(new ApiParameter(this.getModel(), idxHeader++, modelVersion, model, ApiParameterType.HEADER, param));
			}
			else if (param.getIn().equalsIgnoreCase("query")) {
				this.getStats().incQueryParams();
				queryParams.add(new ApiParameter(this.getModel(), idxQuery++, modelVersion, model, ApiParameterType.QUERY, param));
			}
			else if (param.getIn().equalsIgnoreCase("body")) {
				this.requestBody = new ApiRequestBody(this.getModel(), modelVersion, model, "requestBody", op.getConsumes(), (BodyParameter) param);
			}
		}
		
		if ((op.getResponses() != null) && (op.getResponses().size() > 0)) {
			Set<String> keys = op.getResponses().keySet();
			int idxResponse = 0;
			for (String key : keys) {
				Response res = op.getResponses().get(key);
				this.responses.put(key, new ApiResponse(this.getModel(), idxResponse++, modelVersion, model, op.getProduces(), key, res));
			}
		}
		
	}

	public int getIndex() {
		return idx;
	}

	public String getMethod() {
		return method;
	}

	public String getId() {
		return id;
	}

	public String getSummary() {
		return summary;
	}
	
	public String getDescription() {
		return description;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public ApiRequestBody getRequestBody() {
		return requestBody;
	}

	public HashMap<String, ApiResponse> getResponses() {
		return responses;
	}

	public ApiExternalDocs getExternalDocs() {
		return externalDocs;
	}

	public ArrayList<String> getConsumes() {
		return consumes;
	}

	public ArrayList<String> getProduces() {
		return produces;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public ArrayList<ApiParameter> getPathParams() {
		return pathParams;
	}

	public ArrayList<ApiParameter> getHeaderAttributes() {
		return headerAttributes;
	}

	public ArrayList<ApiParameter> getQueryParams() {
		return queryParams;
	}

}
