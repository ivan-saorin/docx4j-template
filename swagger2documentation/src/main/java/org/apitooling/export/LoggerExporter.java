package org.apitooling.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiContact;
import org.apitooling.model.ApiContent;
import org.apitooling.model.ApiExample;
import org.apitooling.model.ApiExternalDocs;
import org.apitooling.model.ApiField;
import org.apitooling.model.ApiLicense;
import org.apitooling.model.ApiLink;
import org.apitooling.model.ApiMediaType;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiOperation;
import org.apitooling.model.ApiParameter;
import org.apitooling.model.ApiPath;
import org.apitooling.model.ApiRequestBody;
import org.apitooling.model.ApiResponse;
import org.apitooling.model.ApiSchema;
import org.apitooling.model.ApiSecurity;
import org.apitooling.model.ApiServer;
import org.apitooling.model.ApiServerVariable;
import org.apitooling.model.ApiTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(LoggerExporter.class);
	private static String API_SEPARATOR = 				"**************************************************************************************************************************************";
	private static String API_METHOD_SEPARATOR = 		"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
	private static String API_OPERATION_SEPARATOR = 	"--------------------------------------------------------------------------------------------------------------------------------------";
	private static String API_SEPARATOR_SEPARATOR = 	"::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::";
	private static String API_RESPONSE_SEPARATOR = 		"======================================================================================================================================";
	private static String LF = System.lineSeparator() + "\t";
	private static String TAB = "\t";
	
	public ApiModel model;
	
	public LoggerExporter(ApiModel model) {
		this.model = model;
	}

	@Override
	public ExporterOuptput getOuptput() {
		if (model == null) {
			throw new WebApiRuntimeException("Missing model");
		}

		StringBuilder sb = outputApi(model);
		if (logger.isInfoEnabled()) logger.info(System.lineSeparator() + sb.toString());
		
		return new ExporterOuptput(sb.toString());
	}

	private StringBuilder outputApi(ApiModel model2) {
		StringBuilder sb = new StringBuilder(1024);
		outputModelStart(sb);
		outputModel(sb, model);
		outputModelEnd(sb);
		return sb;
	}

	private void outputModelStart(StringBuilder sb) {
		sb.append(LF);
		sb.append(API_SEPARATOR);
		sb.append(LF);
	}

	private void outputModel(StringBuilder sb, ApiModel model) {
		outputApiHeader(sb, model);
		
		sb.append(LF);
		sb.append(API_OPERATION_SEPARATOR);
		sb.append(LF);
		if (model.getPaths().size() == 0) {
			sb.append("No content").append(LF);
		}
		else {
			for (ApiPath path : model.getPaths()) {
				outputPath(sb, path);
			}
			sb.append(API_OPERATION_SEPARATOR);				
			sb.append(LF);
			
			outputComponents(sb, model);
			
			sb.append(API_OPERATION_SEPARATOR);
			sb.append(LF);								
			
		}
	}

	private void outputComponents(StringBuilder sb, ApiModel model) {
		if (model.getComponents() != null) {
			outputComponentsSchemas(sb, model);
			outputComponentsParameters(sb, model);
			outputComponentsExamples(sb, model);
			outputComponentsRequestBodies(sb, model);
			outputComponentsResponses(sb, model);
			outputComponentsLinks(sb, model);			
		}
	}

	private void outputComponentsSchemas(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getSchemas() != null) && (model.getComponents().getSchemas().size() > 0)){
			String title = "Global Schemas ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiSchema> schemas = model.getComponents().getSchemas();
			Set<String> keys = schemas.keySet();
			for (String key : keys) {							
				ApiSchema s = schemas.get(key);
				sb.append(TAB).append(s.getTypeName()).append(LF);
				Set<String> skeys = s.keySet();
				for (String skey : skeys) {
					ApiField f = s.get(skey);
					outputSchema(sb, skey, f);
				}							
			}
			
		}
	}

	private void outputComponentsParameters(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getParameters() != null) && (model.getComponents().getParameters().size() > 0)) {
			String title = "Global Parameters ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiParameter> parameters = model.getComponents().getParameters();
			Set<String> keys = parameters.keySet();
			for (String key : keys) {							
				ApiParameter p = parameters.get(key);
				outputParamAttributes(sb, p);
			}					
		}
	}

	private void outputComponentsExamples(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getExamples() != null) && (model.getComponents().getExamples().size() > 0)) {
			String title = "Global Examples ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiExample> examples = model.getComponents().getExamples();
			outputExamples(sb, examples);
		}
	}

	private void outputComponentsRequestBodies(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getRequestBodies() != null) && (model.getComponents().getRequestBodies().size() > 0)) {
			String title = "Global Request Bodies ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiRequestBody> rb = model.getComponents().getRequestBodies();
			Set<String> keys = rb.keySet();
			for (String key : keys) {							
				ApiRequestBody body = rb.get(key);
				outputRequestBody(sb, body);
			}					
		}
	}

	private void outputComponentsResponses(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getResponses() != null) && (model.getComponents().getResponses().size() > 0)) {
			String title = "Global Responses ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiResponse> ress = model.getComponents().getResponses();
			Set<String> keys = ress.keySet();
			for (String key : keys) {							
				ApiResponse res = ress.get(key);
				outputResponse(sb, key, res);
			}					
		}
	}

	@SuppressWarnings("unused")
	private void outputComponentsLinks(StringBuilder sb, ApiModel model) {
		if ((model.getComponents().getLinks() != null) && (model.getComponents().getLinks().size() > 0)) {
			String title = "Global Links ";
			String header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			
			Map<String, ApiLink> links = model.getComponents().getLinks();
			Set<String> keys = links.keySet();
			for (String key : keys) {							
				ApiLink link = links.get(key);
				/** Usage of links not clear - Not supported in this moment **/
				//outputLink(sb, key, link);
			}					
		}
	}

	private void outputPath(StringBuilder sb, ApiPath path) {
		sb.append(path.getPath()).append(LF);
		sb.append(API_METHOD_SEPARATOR);
		sb.append(LF);				
		
		if (path.getOperations().size() == 0) {
			sb.append("No content").append(LF);
		}
		else {
			for (ApiOperation op : path.getOperations()) {
				outputOperation(sb, path, op);				
			}
		}
	}

	private void outputOperation(StringBuilder sb, ApiPath path, ApiOperation op) {
		outputOperationHeader(sb, path, op);
		outputOperationExternalDocs(sb, op);
		outputOperationParams(sb, op, "Header Attributes ", op.getHeaderAttributes());
		outputOperationParams(sb, op, "Path Parameters ", op.getPathParams());
		outputOperationParams(sb, op, "Query Parameters ", op.getQueryParams());
		outputRequestBody(sb, op.getRequestBody());
		outputResponses(sb, op.getResponses());
				
		sb.append(API_METHOD_SEPARATOR);
		sb.append(LF);
	}

	private void outputOperationParams(StringBuilder sb, ApiOperation op, String title, ArrayList<ApiParameter> list) {
		String header;
		if (list.size() > 0) {
			header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);											
			outputParamsList(sb, list);
		}
	}

	private void outputOperationExternalDocs(StringBuilder sb, ApiOperation op) {
		ApiExternalDocs extDocs;
		extDocs = op.getExternalDocs();
		if (extDocs != null) {
			sb.append("External Docs:").append(TAB).append(extDocs.getDescription()).append(" - ").append(extDocs.getUrl()).append(LF);
		}
	}

	private void outputOperationHeader(StringBuilder sb, ApiPath path, ApiOperation op) {
		sb.append(op.getMethod()).append(" ").append(path.getPath()).append(TAB).append(op.getId());
		if (op.isDeprecated())
			sb.append(" (deprecated) ");
		sb.append(LF);
		
		if (op.getSummary() != null) {
			sb.append(op.getSummary()).append(LF);
		}
		if (op.getDescription() != null) {
			sb.append(op.getDescription()).append(LF);
		}
		
	}

	private void outputApiHeader(StringBuilder sb, ApiModel model) {
		outputIntroduction(sb, model);
		outputTOS(sb, model);
		outputContact(sb, model);
		outputLicense(sb, model);
		outputApiExternalDocs(sb, model);
		outputApiTags(sb, model);		
		outputSecurityAndServers(sb, model);
	}

	private void outputSecurityAndServers(StringBuilder sb, ApiModel model) {
		ArrayList<ApiServer> servers = model.getServers();
		ArrayList<ApiSecurity> security = model.getSecurity();
		if (((servers != null) && (servers.size() > 0)) || ((security != null) && (security.size() > 0))){
			outputServers(sb, servers);
			outputSecurity(sb, security);
		}
	}

	private void outputSecurity(StringBuilder sb, ArrayList<ApiSecurity> security) {
		if ((security != null) && (security.size() > 0)) {
			sb.append("Security:").append(LF);
			for (ApiSecurity s : security) {
				Set<String> keys = s.keySet();
				for (String key : keys) {
					sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
					List<String> list = s.get(key);
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							sb.append(TAB).append(TAB).append(TAB).append(TAB).append(list.get(i)).append(LF);
						}
					}
				}
			}			
		}
	}

	private void outputServers(StringBuilder sb, ArrayList<ApiServer> servers) {
		sb.append(LF);
		if ((servers != null) && (servers.size() > 0)) {
			sb.append("Servers:").append(LF);
			for (ApiServer server : servers) {
				sb.append(TAB).append(TAB).append(TAB);
				if (server.getDescription() != null)
					sb.append(server.getDescription()).append(" - ");
				sb.append(server.getUrl()).append(LF);
				
				if ((server.getVariables() != null) && (server.getVariables().size() > 0)) {
					sb.append(TAB).append(TAB).append(TAB).append("Server Variables:").append(LF);
					Set<String> keys = server.getVariables().keySet();
					for (String key : keys) {
						ApiServerVariable var = server.getVariables().get(key);
						sb.append(TAB).append(TAB).append(TAB).append(key);
						sb.append(TAB).append(TAB).append(TAB).append(TAB).append(var.getDescription()).append(" - ").append(var.getEnum()).append(" - ").append(var.getDefault()).append(LF);
					}
				}
			}
		}
	}

	private void outputApiTags(StringBuilder sb, ApiModel model) {
		ArrayList<ApiTag> tags = model.getTags();
		if ((tags != null) && (tags.size() > 0)) {
			sb.append("Tags:").append(LF);
			for (ApiTag t : tags) {
				sb.append(TAB).append(TAB).append(TAB).append(t.getName()).append(" - ").append(t.getDescription()).append(LF);
				if (t.getExternalDocs() != null) {
					sb.append(TAB).append(TAB).append(TAB).append(TAB).append(t.getExternalDocs()).append(LF);
				}
			}			
		}
	}

	private void outputApiExternalDocs(StringBuilder sb, ApiModel model) {
		ApiExternalDocs extDocs = model.getExternalDocs();
		if (extDocs != null) {
			sb.append("External Docs:").append(TAB).append(TAB).append(extDocs.getDescription()).append(" - ").append(extDocs.getUrl()).append(LF);
		}
	}

	private void outputLicense(StringBuilder sb, ApiModel model) {
		ApiLicense license = model.getLicense();
		if (license != null) {
			sb.append("License:").append(TAB).append(TAB).append(license.getName()).append(" - ").append(license.getUrl()).append(LF);
		}
	}

	private void outputContact(StringBuilder sb, ApiModel model) {
		ApiContact contact = model.getContact();
		if (contact != null) {
			sb.append("Contact:").append(TAB).append(TAB).append(contact.getName()).append(" (").append(contact.getEmail()).append(")").append(" - ").append(contact.getUrl()).append(LF);
		}
	}

	private void outputTOS(StringBuilder sb, ApiModel model) {
		if (model.getTermOfService() != null) {			
			sb.append("Terms Of Service:").append(TAB).append(model.getTermOfService()).append(LF);
		}
	}

	private void outputIntroduction(StringBuilder sb, ApiModel model) {
		sb.append(LF);
		sb.append("ImportedModelVersion:").append(TAB).append(model.getModelVersion()).append(LF);
		sb.append("ModelVersion:").append(TAB).append(TAB).append(model.getVersion()).append(LF);
		sb.append("Title:").append(TAB).append(TAB).append(TAB).append(model.getTitle()).append(LF);
		sb.append("Api version:").append(TAB).append(TAB).append(model.getApiVersion()).append(LF);
		sb.append(model.getDescription()).append(LF);
	}


	private void outputRequestBody(StringBuilder sb, ApiRequestBody body) {
		if (body != null) {
			String title;
			String header;
			title = "Request Body ";
			if (body.isRequired())
				title += "(required) ";
			header = API_SEPARATOR_SEPARATOR.substring(title.length());
			sb.append(title + header);
			sb.append(LF);																		
			if (body.getDescription() != null) {
				sb.append(TAB).append(body.getDescription()).append(LF);
			}
	
			outputContent(sb, body.getContent());
		}
	}

	private void outputResponses(StringBuilder sb, Map<String, ApiResponse> responses) {
		if (responses != null) {
			Set<String> keys = responses.keySet();
			for (String key : keys) {
				ApiResponse res = responses.get(key);
				outputResponse(sb, key, res);
			}
		}
	}

	private void outputResponse(StringBuilder sb, String key, ApiResponse res) {
		String title;
		String header;
		title = "Response " + key + " ";
		header = API_RESPONSE_SEPARATOR.substring(title.length());
		sb.append(title + header);
		sb.append(LF);						
		if (res.getDescription() != null) {
			sb.append(TAB).append(res.getDescription()).append(LF);
		}
		
		if (res.getProduces() != null) {
			if (res.getProduces().size() > 0) {
				for (String produce : res.getProduces()) {
					sb.append(TAB).append(produce).append(LF);
				}
			}
			else {
				sb.append(TAB).append("*/*").append(LF);
			}
		}
		
		if (res.getRef() != null) {
			sb.append(TAB).append(TAB).append("Ref:").append(TAB).append(TAB).append(res.getRef()).append(LF);
		}
		if (res.getHeaderAttributes() != null) {
			outputParamsList(sb, res.getHeaderAttributes());
		}			
		if ((res.getLinks() != null) && (res.getLinks().size() > 0)){
			sb.append(TAB).append(TAB).append("(Has Links)").append(LF);
		}

		if (res.getExamples() != null) {
			if (res.getExamples().size() > 0) {
				sb.append(TAB).append(TAB).append("examples:");
				Set<String> ekeys = res.getExamples().keySet();
				for (String ekey : ekeys) {
					sb.append(TAB).append(TAB).append(TAB).append(ekey).append(":").append(TAB).append(TAB).append(res.getExamples().get(ekey)).append(LF);
				}
			}
		}
		
		if (res.getSchema() != null) {
			outputSchema(sb, res.getSchema());
		}
		outputContent(sb, res.getContent());
	}

	private void outputContent(StringBuilder sb, ApiContent content) {
		if (content == null) {
			return;
		}
		
		Set<String> keys = content.keySet();
		for (String key : keys) {
			sb.append(TAB).append(key).append(LF);
			ApiMediaType m = content.get(key);
			if (m.getExample() != null)
				sb.append(TAB).append(TAB).append("Description:").append(TAB).append(TAB).append(m.getExample()).append(LF);
			if (m.getExamples() != null)
				outputExamples(sb, m.getExamples());
			if (m.getSchema()!= null) {
				outputSchema(sb, m.getSchema());				
			}
		}
	}

	private void outputSchema(StringBuilder sb, ApiField schema) {
		sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void outputSchema(StringBuilder sb, String key, ApiField schema) {
		sb.append(TAB).append(TAB).append(key).append(":").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void outputExamples(StringBuilder sb, Map<String, ApiExample> examples) {
		if (examples.size() > 0) {
			sb.append(TAB).append(TAB).append("examples:");
			Set<String> keys = examples.keySet();
			for (String key : keys) {
				sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
				ApiExample example = examples.get(key);
				sb.append(TAB).append(TAB).append(TAB).append("Summary:").append(TAB).append(TAB).append(example.getSummary()).append(LF);
				sb.append(TAB).append(TAB).append(TAB).append("Description:").append(TAB).append(TAB).append(example.getDescription()).append(LF);
				sb.append(TAB).append(TAB).append(TAB).append("Value:").append(TAB).append(TAB).append(example.getValue()).append(LF);
			}
		}
	}

	private void outputParamsList(StringBuilder sb, ArrayList<ApiParameter> params) {
		for (ApiParameter p : params) {
			sb.append(TAB).append(p.getName()).append(":").append(TAB).append(p.getDataType());
			outputParamAttributes(sb, p);
		}		
	}

	private void outputParamAttributes(StringBuilder sb, ApiParameter p) {
		if (p.isRequired() || p.isReadOnly() || p.isAllowEmptyValues() || p.isAllowReserved() || p.isDeprecated()) {
			sb.append(" - (");
			if (p.isRequired())
				sb.append("required");
			if (p.isReadOnly() || p.isAllowEmptyValues() || p.isAllowReserved() || p.isDeprecated())
			sb.append(",");
			if (p.isReadOnly())
				sb.append("readOnly");
			if (p.isAllowEmptyValues() || p.isAllowReserved() || p.isDeprecated())
			sb.append(",");
			if (p.isAllowEmptyValues())
				sb.append("allowEmptyValues");
			if (p.isAllowReserved() || p.isDeprecated())
			sb.append(",");
			if (p.isAllowReserved())
				sb.append("allowReserved");
			if (p.isDeprecated())
			sb.append(",");
			if (p.isDeprecated())
				sb.append("deprecated");
			sb.append(")");
		}
		if (p.getDescription() != null) {
			sb.append(" - ").append(p.getDescription());
		}		
		sb.append(LF);
		
		if (p.getDefaultValue() != null) {
			sb.append(TAB).append(TAB).append("DefaultValue:").append(TAB).append(TAB).append(p.getDefaultValue()).append(LF);
		}

		if (p.getEnumValue() != null) {
			sb.append(TAB).append(TAB).append("Enum:").append(TAB).append(TAB).append(p.getEnumValue()).append(LF);
		}

		if (p.getContent() != null) {
			sb.append(TAB).append(TAB).append("Content:").append(TAB).append(TAB).append(p.getContent()).append(LF);
		}

		if (p.getExample() != null) {
			sb.append(TAB).append(TAB).append("Example:").append(TAB).append(TAB).append(p.getExample()).append(LF);
		}

		if (p.getExamples() != null) {
			outputExamples(sb, p.getExamples());
		}
	}

	private void outputModelEnd(StringBuilder sb) {
		sb.append(API_SEPARATOR);
		sb.append(LF);
	}

}
