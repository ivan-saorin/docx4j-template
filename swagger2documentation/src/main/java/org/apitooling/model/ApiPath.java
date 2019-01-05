package org.apitooling.model;

import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import v2.io.swagger.models.HttpMethod;
import v2.io.swagger.models.Path;
import v2.io.swagger.models.Swagger;

public class ApiPath extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiPath.class);
	
	public int idx;
	public String path;
	public ArrayList<ApiOperation> operations = new ArrayList<ApiOperation>(); 
	
	public ApiPath(int idx, ApiType modelVersion, OpenAPI model, String key, PathItem path) {
		super();
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, path.getClass().getName(), path.getExtensions());
		this.idx = idx;
		describeModel(modelVersion, model, key, path);
	}

	public ApiPath(int idx2, ApiType modelVersion, Swagger model, String key, Path path) {
		this.path = key;
		//if (logger.isInfoEnabled()) logger.info("Path {}:", this.path);
		this.describeExtensions(path.getVendorExtensions());
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, path.getClass().getName(), path.getVendorExtensions());
		int idx = 0;
		
		Set<HttpMethod> keys = path.getOperationMap().keySet();
		for (HttpMethod method : keys) {
			operations.add(new ApiOperation(idx++, modelVersion, model, method, path.getOperationMap().get(method)));
		}		
	}

	private void describeModel(ApiType modelVersion, OpenAPI model, String key, PathItem path) {		
		this.path = key;
		//if (logger.isInfoEnabled()) logger.info("Path {}:", this.path);
		this.describeExtensions(path.getExtensions());
		int idx = 0;
		if (path.getHead() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.HEAD, path.getHead()));
		}
		if (path.getOptions() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.OPTIONS, path.getOptions()));
		}
		if (path.getGet() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.GET, path.getGet()));
		}
		if (path.getPost() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.POST, path.getPost()));
		}		
		if (path.getPut() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.PUT, path.getPut()));
		}
		if (path.getPatch() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.PATCH, path.getPatch()));
		}
		if (path.getDelete() != null) {
			operations.add(new ApiOperation(idx++, modelVersion, model, io.swagger.v3.oas.models.PathItem.HttpMethod.DELETE, path.getDelete()));
		}
	}

	public int getIndex() {
		return idx;
	}

	public String getPath() {
		return path;
	}

	public ArrayList<ApiOperation> getOperations() {
		return operations;
	}
	
}
