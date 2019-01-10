package org.apitooling.analysis;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apitooling.model.ApiContent;
import org.apitooling.model.ApiField;
import org.apitooling.model.ApiMediaType;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiOperation;
import org.apitooling.model.ApiParameter;
import org.apitooling.model.ApiPath;
import org.apitooling.model.ApiRequestBody;
import org.apitooling.model.ApiResponse;
import org.apitooling.model.ApiSchema;

public class ApiAnalysis {

	private ApiModel model;
	ParamName2PathName param2Path = new ParamName2PathName();
	
	
	public ApiAnalysis(ApiModel model) {
		this.model = model;
		analyse(model);
	}

	private ParamName2PathName analyse(ApiModel model) {
		analyseModel(model);
		return param2Path;
	}

	public ParamName2PathName getParam2Path() {
		return param2Path;
	}

	public void setParam2Path(ParamName2PathName param2Path) {
		this.param2Path = param2Path;
	}

	public ApiModel getModel() {
		return model;
	}

	private void analyseModel(ApiModel model) {
		//analyseApiHeader(model);
		
		if (model.getPaths().size() != 0) {
			for (ApiPath path : model.getPaths()) {
				analysePath(path);
			}
			analyseComponents(model);
		}
	}

	private void analyseComponents(ApiModel model) {
		if (model.getComponents() != null) {
			analyseComponentsSchemas(model);
			analyseComponentsParameters(model);
			analyseComponentsRequestBodies(model);
			analyseComponentsResponses(model);
		}
	}

	private void analyseComponentsSchemas(ApiModel model) {
		if ((model.getComponents().getSchemas() != null) && (model.getComponents().getSchemas().size() > 0)){
			String title = "Global Schemas ";
			Map<String, ApiSchema> schemas = model.getComponents().getSchemas();
			Set<String> keys = schemas.keySet();
			for (String key : keys) {							
				ApiSchema s = schemas.get(key);
				//sb.append(TAB).append(s.getName()).append(LF);
				Set<String> skeys = s.getProperties().keySet();
				for (String skey : skeys) {
					ApiField f = s.getProperties().get(skey);
					analyseSchema(skey, f);
				}							
			}
			
		}
	}

	private void analyseComponentsParameters(ApiModel model) {
		if ((model.getComponents().getParameters() != null) && (model.getComponents().getParameters().size() > 0)) {
			String title = "Global Parameters ";
			Map<String, ApiParameter> parameters = model.getComponents().getParameters();
			Set<String> keys = parameters.keySet();
			for (String key : keys) {							
				ApiParameter p = parameters.get(key);
				analyseParamAttributes(p);
			}					
		}
	}

	private void analyseComponentsRequestBodies(ApiModel model) {
		if ((model.getComponents().getRequestBodies() != null) && (model.getComponents().getRequestBodies().size() > 0)) {
			String title = "Global Request Bodies ";
			Map<String, ApiRequestBody> rb = model.getComponents().getRequestBodies();
			Set<String> keys = rb.keySet();
			for (String key : keys) {							
				ApiRequestBody body = rb.get(key);
				analyseRequestBody(body);
			}					
		}
	}

	private void analyseComponentsResponses(ApiModel model) {
		if ((model.getComponents().getResponses() != null) && (model.getComponents().getResponses().size() > 0)) {
			String title = "Global Responses ";
			Map<String, ApiResponse> ress = model.getComponents().getResponses();
			Set<String> keys = ress.keySet();
			for (String key : keys) {							
				ApiResponse res = ress.get(key);
				analyseResponse(key, res);
			}					
		}
	}

	private void analysePath(ApiPath path) {
		if (path.getOperations().size() > 0) {
			for (ApiOperation op : path.getOperations()) {
				analyseOperation(path, op);				
			}
		}
	}

	private void analyseOperation(ApiPath path, ApiOperation op) {
		analyseOperationParams(path, op, "Header Attributes ", op.getHeaderAttributes());
		analyseOperationParams(path, op, "Path Parameters ", op.getPathParams());
		analyseOperationParams(path, op, "Query Parameters ", op.getQueryParams());
		//analyseRequestBody(op.getRequestBody());
		//analyseResponses(op.getResponses());
	}

	private void analyseOperationParams(ApiPath path, ApiOperation op, String title, ArrayList<ApiParameter> list) {
		String header;
		if (list.size() > 0) {
			analyseParamsList(path, op, list);
		}
	}

	private void analyseRequestBody(ApiRequestBody body) {
		if (body != null) {
			String title;
			String header;
			title = "Request Body ";
			//if (body.isRequired())
			//	title += "(required) ";
			if (body.getDescription() != null) {
				//sb.append(TAB).append(body.getDescription()).append(LF);
			}
	
			analyseContent(body.getContent());
		}
	}

	private void analyseResponses(Map<String, ApiResponse> responses) {
		if (responses != null) {
			Set<String> keys = responses.keySet();
			for (String key : keys) {
				ApiResponse res = responses.get(key);
				analyseResponse(key, res);
			}
		}
	}

	private void analyseResponse(String key, ApiResponse res) {
		String title;
		String header;
		title = "Response " + key + " ";
		if (res.getDescription() != null) {
			//sb.append(TAB).append(res.getDescription()).append(LF);
		}
		
		if (res.getProduces() != null) {
			if (res.getProduces().size() > 0) {
				for (String produce : res.getProduces()) {
					//sb.append(TAB).append(produce).append(LF);
				}
			}
			else {
				//sb.append(TAB).append("*/*").append(LF);
			}
		}
		
		if (res.getRef() != null) {
			//sb.append(TAB).append(TAB).append("Ref:").append(TAB).append(TAB).append(res.getRef()).append(LF);
		}
		if (res.getHeaderAttributes() != null) {
			analyseParamsList(res.getHeaderAttributes());
		}			
		if ((res.getLinks() != null) && (res.getLinks().size() > 0)){
			//sb.append(TAB).append(TAB).append("(Has Links)").append(LF);
		}

		if (res.getExamples() != null) {
			if (res.getExamples().size() > 0) {
				//sb.append(TAB).append(TAB).append("examples:");
				Set<String> ekeys = res.getExamples().keySet();
				for (String ekey : ekeys) {
					//sb.append(TAB).append(TAB).append(TAB).append(ekey).append(":").append(TAB).append(TAB).append(res.getExamples().get(ekey)).append(LF);
				}
			}
		}
		
		if (res.getSchema() != null) {
			analyseSchema(res.getSchema());
		}
		analyseContent(res.getContent());
	}

	private void analyseContent(ApiContent content) {
		if (content == null) {
			return;
		}
		
		Set<String> keys = content.keySet();
		for (String key : keys) {
			//sb.append(TAB).append(key).append(LF);
			ApiMediaType m = content.get(key);
			if (m.getSchema()!= null) {
				analyseSchema(m.getSchema());				
			}
		}
	}

	private void analyseSchema(ApiSchema schema) {		
		//sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.getName()).append(LF);
		if (schema.getProperties().size() > 0) {
			Set<String> keys = schema.getProperties().keySet();
			for (String key : keys) {
				//sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
				ApiField field = schema.getProperties().get(key);
				analyseSchema(field);
			}
		}
	}

	private void analyseSchema(ApiField schema) {
		//sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void analyseSchema(String key, ApiField schema) {
		//sb.append(TAB).append(TAB).append(key).append(":").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void analyseParamsList(ArrayList<ApiParameter> headerAttributes) {
		// TODO Auto-generated method stub
		
	}

	private void analyseParamsList(ApiPath path, ApiOperation op, ArrayList<ApiParameter> params) {
		for (ApiParameter p : params) {
			//sb.append(TAB).append(p.getName()).append(":").append(TAB).append(p.getDataType());
			analyseParamAttributes(path, op, p);
		}		
	}
	
	private void analyseParamAttributes(ApiParameter p) {
		// TODO Auto-generated method stub
		
	}

	private void analyseParamAttributes(ApiPath path, ApiOperation op, ApiParameter p) {
		String key = op.getMethod() + " " + path.getPath();
		String name = p.getName();
				
		PathParameters pathParams = param2Path.getOrNew(name);
		ArrayList<ApiParameter> params = pathParams.getOrNew(key, p.getType());
		params.add(p);
	}
	
}
