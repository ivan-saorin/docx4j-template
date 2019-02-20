package org.apitooling.export;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.export.output.ExporterOuptput;
import org.apitooling.export.output.MsExcelExporterOuptput;
import org.apitooling.model.ApiContact;
import org.apitooling.model.ApiExternalDocs;
import org.apitooling.model.ApiLicense;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiOperation;
import org.apitooling.model.ApiParameter;
import org.apitooling.model.ApiPath;
import org.apitooling.model.ApiRequestBody;
import org.apitooling.model.ApiResponse;
import org.apitooling.model.ApiSecurity;
import org.apitooling.model.ApiServer;
import org.apitooling.model.ApiServerVariable;
import org.apitooling.model.ApiTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class MsExcelExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(MsExcelExporter.class);
	private static String LF = System.lineSeparator() + "\t";
	private static String TAB = "\t";

	private ApiModel model;
	private File output;
	private WritableCellFormat normal;
	private WritableCellFormat bold;
	private WritableCellFormat title;
	private WritableCellFormat boldTitle;
	
	protected MsExcelExporter() {		

	}	
	
	public MsExcelExporter(ApiModel model, File output) {
		this();
		this.model = model;
		this.output = output;

		normal = new WritableCellFormat();
        WritableFont font = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD);
        normal.setFont(font);		

        bold = new WritableCellFormat();
        font = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD);
        bold.setFont(font);

		title = new WritableCellFormat();
        font = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.NO_BOLD);
        title.setFont(font);
        
        boldTitle = new WritableCellFormat();
        font = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.BOLD);
        boldTitle.setFont(font);
        
        // 2.1. Prepare the template input:
		/*
        context.put("model", this.model);
        context.put("pathsById", ApiUtils.groupByApi(model.getPaths()));
        if (model.getModelVersion().equals(ApiType.SWAGGER_20))
        	context.put("modelVersion", "Swagger 2.x");
        else
        	context.put("modelVersion", "Open API 3.x");
        
        context.put("pubDate", new Date(System.currentTimeMillis()));
        */
	}

	@Override
	public String getStandardFileExtension() {
		return ".xls";
	}

	protected String changeExtension(String name, String extWithDot) {
		int i = name.lastIndexOf('.');
		if (i > -1) {
			name = name.substring(0, i) + extWithDot;
		}
		return name;
	}

	@Override
	public ExporterOuptput getOuptput() throws IOException {
		if (model == null) {
			throw new WebApiRuntimeException("Missing model");
		}

		WritableWorkbook workbook = outputWorkbook(model);
		
        // 2.3. Generate the output
		//outputApi(model);
		return new MsExcelExporterOuptput(workbook);
	}

	private WritableWorkbook outputWorkbook(ApiModel model) throws IOException {
		//1. Create an Excel file
        WritableWorkbook workbook = null;
        try {

            workbook = Workbook.createWorkbook(output);

            // create an Excel sheet
            WritableSheet sheet = workbook.createSheet("API", 0);

            outputApi(model, sheet);
            
            return workbook;
        } catch (IOException cause) {
        	throw new IOException(cause);
        }	
      }
	
	private void outputApi(ApiModel model, WritableSheet sheet) {
		outputModel(model, sheet);
	}

	private void outputModel(ApiModel model, WritableSheet sheet) {
		int index = 1;
		
		try {
			index = outputApiHeader(index, model, sheet);
		
			if (model.getPaths().size() == 0) {
				insertPlain(++index, "No Content", false, sheet);
			}
			else {
				index = insertTuple(++index, "Method", "Path", "Deprecated", true, sheet);
				for (ApiPath path : model.getPaths()) {
					index = outputPath(index, path, sheet);
				}
				
				//index = outputComponents(index, sheet);				
			}		
		} catch (RowsExceededException cause) {
			throw new RuntimeException(cause);
		} catch (WriteException cause) {
			throw new RuntimeException(cause);
		}
	}

	/*
	private void outputComponents(int index, ApiModel model, WritableSheet sheet) {
		if (model.getComponents() != null) {
			index = outputComponentsSchemas(index, model, sheet);
			index = outputComponentsParameters(index, model, sheet);
			index = outputComponentsExamples(index, model, sheet);
			index = outputComponentsRequestBodies(index, model, sheet);
			index = outputComponentsResponses(index, model, sheet);
			index = outputComponentsLinks(index, model, sheet);			
		}
	}

	private int outputComponentsSchemas(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getSchemas() != null) && (model.getComponents().getSchemas().size() > 0)){
			String title = "Global Schemas ";
			
			Map<String, ApiSchema> schemas = model.getComponents().getSchemas();
			Set<String> keys = schemas.keySet();
			for (String key : keys) {							
				ApiSchema s = schemas.get(key);
				sb.append(TAB).append(s.getName()).append(LF);
				Set<String> skeys = s.getProperties().keySet();
				for (String skey : skeys) {
					ApiField f = s.getProperties().get(skey);
					outputSchema(sb, skey, f);
				}							
			}
			
		}
		return index;
	}

	private int outputComponentsParameters(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getParameters() != null) && (model.getComponents().getParameters().size() > 0)) {			
			Map<String, ApiParameter> parameters = model.getComponents().getParameters();
			Set<String> keys = parameters.keySet();
			for (String key : keys) {							
				ApiParameter p = parameters.get(key);
				outputParamAttributes(sb, p);
			}					
		}
		return index;
	}

	private int outputComponentsExamples(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getExamples() != null) && (model.getComponents().getExamples().size() > 0)) {
			Map<String, ApiExample> examples = model.getComponents().getExamples();
			outputExamples(sb, examples);
		}
		return index;
	}

	private int outputComponentsRequestBodies(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getRequestBodies() != null) && (model.getComponents().getRequestBodies().size() > 0)) {
			Map<String, ApiRequestBody> rb = model.getComponents().getRequestBodies();
			Set<String> keys = rb.keySet();
			for (String key : keys) {							
				ApiRequestBody body = rb.get(key);
				outputRequestBody(sb, body);
			}					
		}
		return index;
	}

	private int outputComponentsResponses(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getResponses() != null) && (model.getComponents().getResponses().size() > 0)) {
			Map<String, ApiResponse> ress = model.getComponents().getResponses();
			Set<String> keys = ress.keySet();
			for (String key : keys) {							
				ApiResponse res = ress.get(key);
				outputResponse(sb, key, res);
			}					
		}
		return index;
	}
	
	@SuppressWarnings("unused")
	private int outputComponentsLinks(int index, ApiModel model, WritableSheet sheet) {
		if ((model.getComponents().getLinks() != null) && (model.getComponents().getLinks().size() > 0)) {
			Map<String, ApiLink> links = model.getComponents().getLinks();
			Set<String> keys = links.keySet();
			for (String key : keys) {							
				ApiLink link = links.get(key);
				*/
				/* Usage of links not clear - Not supported in this moment */
				//outputLink(sb, key, link);
				/*
			}					
		}
		return index;
	}
	*/

	private int outputPath(int index, ApiPath path, WritableSheet sheet) throws RowsExceededException, WriteException {
        if (path.getOperations().size() == 0) {
        	insertPlain(index, "No Content", false, sheet);
		}
		else {			
			for (ApiOperation op : path.getOperations()) {
				index = outputOperation(index++, path, op, sheet);
			}
		}
		return index;
	}

	private int outputOperation(int index, ApiPath path, ApiOperation op, WritableSheet sheet) throws RowsExceededException, WriteException {
		index = outputOperationHeader(index, path, op, sheet);
		index = outputOperationExternalDocs(index, op, sheet);		
		index = outputOperationParams(index, op, "Path Parameters ", op.getPathParams(), sheet);
		index = outputOperationParams(index, op, "Query Parameters ", op.getQueryParams(), sheet);
		index = outputOperationParams(index, op, "Header Attributes ", op.getHeaderAttributes(), sheet);				
		index = outputRequestBody(index, op.getRequestBody(), sheet);
		index = outputResponses(index, op.getResponses(), sheet);
		return index;
	}

	private int outputOperationParams(int index, ApiOperation op, String title, ArrayList<ApiParameter> list, WritableSheet sheet) throws RowsExceededException, WriteException {		
		String header;
		if (list.size() > 0) {
			index = insertPlain(index, title, true, sheet);
			index = outputParamsList(index, list, sheet);
		}
		return index;
	}

	private int outputOperationExternalDocs(int index, ApiOperation op, WritableSheet sheet) {
		ApiExternalDocs extDocs;
		extDocs = op.getExternalDocs();
		if (extDocs != null) {
			//sb.append("External Docs:").append(TAB).append(extDocs.getDescription()).append(" - ").append(extDocs.getUrl()).append(LF);
		}
		return index;
	}

	private int outputOperationHeader(int index, ApiPath path, ApiOperation op, WritableSheet sheet) throws RowsExceededException, WriteException {
		index = insertTuple(index, op.getMethod(), path.getPath(), new Boolean(op.isDeprecated()).toString(), false, sheet);
		
		if (op.getSummary() != null) {
			//sb.append(op.getSummary()).append(LF);
		}
		if (op.getDescription() != null) {
			//sb.append(op.getDescription()).append(LF);
		}
		return index;
	}

	private int outputApiHeader(int index, ApiModel model, WritableSheet sheet) throws RowsExceededException, WriteException {
		index = outputIntroduction(index, model, sheet);
		index = outputTOS(index, model, sheet);
		index = outputContact(index, model, sheet);
		index = outputLicense(index, model, sheet);
		index = outputApiExternalDocs(index, model, sheet);
		index = outputApiTags(index, model, sheet);		
		index = outputSecurityAndServers(index, model, sheet);
		return index;
	}

	private int outputSecurityAndServers(int index, ApiModel model, WritableSheet sheet) {
		ArrayList<ApiServer> servers = model.getServers();
		ArrayList<ApiSecurity> security = model.getSecurity();
		if (((servers != null) && (servers.size() > 0)) || ((security != null) && (security.size() > 0))){
			//outputServers(sb, servers);
			//outputSecurity(sb, security);
		}
		return index;
	}

	private int outputSecurity(int index, ArrayList<ApiSecurity> security) {
		if ((security != null) && (security.size() > 0)) {
			//sb.append("Security:").append(LF);
			for (ApiSecurity s : security) {
				Set<String> keys = s.keySet();
				for (String key : keys) {
					//sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
					List<String> list = s.get(key);
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							//sb.append(TAB).append(TAB).append(TAB).append(TAB).append(list.get(i)).append(LF);
						}
					}
				}
			}			
		}
		return index;
	}

	private int outputServers(int index, ArrayList<ApiServer> servers) {
		if ((servers != null) && (servers.size() > 0)) {
			//sb.append("Servers:").append(LF);
			for (ApiServer server : servers) {
				//sb.append(TAB).append(TAB).append(TAB);
				if (server.getDescription() != null)
					//sb.append(server.getDescription()).append(" - ");
				//sb.append(server.getUrl()).append(LF);
				
				if ((server.getVariables() != null) && (server.getVariables().size() > 0)) {
					//sb.append(TAB).append(TAB).append(TAB).append("Server Variables:").append(LF);
					Set<String> keys = server.getVariables().keySet();
					for (String key : keys) {
						ApiServerVariable var = server.getVariables().get(key);
						//sb.append(TAB).append(TAB).append(TAB).append(key);
						//sb.append(TAB).append(TAB).append(TAB).append(TAB).append(var.getDescription()).append(" - ").append(var.getEnum()).append(" - ").append(var.getDefault()).append(LF);
					}
				}
			}
		}
		return index;
	}

	private int outputApiTags(int index, ApiModel model, WritableSheet sheet) {
		ArrayList<ApiTag> tags = model.getTags();
		if ((tags != null) && (tags.size() > 0)) {
			//sb.append("Tags:").append(LF);
			for (ApiTag t : tags) {
				//sb.append(TAB).append(TAB).append(TAB).append(t.getName()).append(" - ").append(t.getDescription()).append(LF);
				if (t.getExternalDocs() != null) {
					//sb.append(TAB).append(TAB).append(TAB).append(TAB).append(t.getExternalDocs()).append(LF);
				}
			}			
		}
		return index;
	}

	private int outputApiExternalDocs(int index, ApiModel model, WritableSheet sheet) {
		ApiExternalDocs extDocs = model.getExternalDocs();
		if (extDocs != null) {
			//sb.append("External Docs:").append(TAB).append(TAB).append(extDocs.getDescription()).append(" - ").append(extDocs.getUrl()).append(LF);
		}
		return index;
	}

	private int outputLicense(int index, ApiModel model, WritableSheet sheet) {
		ApiLicense license = model.getLicense();
		if (license != null) {
			//sb.append("License:").append(TAB).append(TAB).append(license.getName()).append(" - ").append(license.getUrl()).append(LF);
		}
		return index;
	}

	private int outputContact(int index, ApiModel model, WritableSheet sheet) {
		ApiContact contact = model.getContact();
		if (contact != null) {
			//sb.append("Contact:").append(TAB).append(TAB).append(contact.getName()).append(" (").append(contact.getEmail()).append(")").append(" - ").append(contact.getUrl()).append(LF);
		}
		return index;
	}

	private int outputTOS(int index, ApiModel model, WritableSheet sheet) {
		if (model.getTermOfService() != null) {			
			//sb.append("Terms Of Service:").append(TAB).append(model.getTermOfService()).append(LF);
		}
		return index;
	}

	private int outputIntroduction(int index, ApiModel model, WritableSheet sheet) throws RowsExceededException, WriteException {		
		index = insertSimple(index, "Title:", model.getTitle(), boldTitle, title, sheet);
		index = insertSimple(index, "Model Version:", model.getVersion(), sheet);
		return index;
	}

	private int insertTuple(int index, String v1, String v2, String v3, boolean isBold, WritableSheet sheet) throws RowsExceededException, WriteException {
		return insertMultiple(index, new String[] {v1,  v2,  v3}, isBold, sheet);	
	}

	private int insertMultiple(int index, String[] values, boolean isBold, WritableSheet sheet) throws RowsExceededException, WriteException {
		WritableCellFormat f = normal;
		if (isBold) f = bold;		
		index++;
		int i = 1;
		for (String value : values) {
			Label label = new Label(i++, index, value, f);
	        sheet.addCell(label);		
		}
		return index;
	}
	
	private int insertSimple(int index, String title, String value, WritableCellFormat titleBoldFormat, WritableCellFormat titleFormat, WritableSheet sheet) throws RowsExceededException, WriteException {
		index++; 
		Label label = new Label(1, index, title, titleBoldFormat);
        sheet.addCell(label);
		label = new Label(2, index, value, titleFormat);
        sheet.addCell(label);        
		return index;	
	}
	
	private int insertSimple(int index, String title, String value, WritableSheet sheet) throws WriteException, RowsExceededException {
		return this.insertSimple(index, title, value, this.boldTitle, this.title, sheet);
	}
	private int insertPlain(int index, String value, boolean isBold, WritableSheet sheet) throws WriteException, RowsExceededException {
		WritableCellFormat f = normal;
		if (isBold) f = bold;				
		Label label = new Label(1, ++index, value, f);
        sheet.addCell(label);
		return index;
	}

	private int outputRequestBody(int index, ApiRequestBody body, WritableSheet sheet) {
		if (body != null) {
			/*
			String title;
			String header;
			title = "Request Body ";
			if (body.isRequired())
				title += "(required) ";
			if (body.getDescription() != null) {
				sb.append(TAB).append(body.getDescription()).append(LF);
			}
	
			outputContent(sb, body.getContent());
			*/
		}
		return index;
	}
	
	private int outputResponses(int index, Map<String, ApiResponse> responses, WritableSheet sheet) {
		if (responses != null) {
			/*
			Set<String> keys = responses.keySet();
			for (String key : keys) {
				ApiResponse res = responses.get(key);
				outputResponse(sb, key, res);
			}
			*/
		}
		return index;
	}
	
	/*
	private void outputResponse(int index, String key, ApiResponse res) {
		String title;
		String header;
		title = "Response " + key + " ";
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
			*/
				//sb.append(TAB).append("*/*").append(LF);
			/*
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

	private void outputContent(int index, ApiContent content) {
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

	private void outputSchema(int index, ApiSchema schema) {		
		sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.getName()).append(LF);
		if (schema.getProperties().size() > 0) {
			Set<String> keys = schema.getProperties().keySet();
			for (String key : keys) {
				sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
				ApiField field = schema.getProperties().get(key);
				outputSchema(sb, field);
			}
		}
	}

	private void outputSchema(int index, ApiField schema) {
		sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void outputSchema(int index, String key, ApiField schema) {
		sb.append(TAB).append(TAB).append(key).append(":").append(TAB).append(TAB).append(schema.toString()).append(LF);		
	}

	private void outputExamples(int index, Map<String, ApiExample> examples) {
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
	*/
	
	private int outputParamsList(int index, ArrayList<ApiParameter> params, WritableSheet sheet) throws RowsExceededException, WriteException {
		insertMultiple(index, new String[] {"Name", "Required", "Deprecated", "DataType", "Enum", "Description", "Original BG", "Current UCG"}, true, sheet);
		index++;
		for (ApiParameter p : params) {
			String enumeration = "";
			
			if ((p.getEnumValues() != null) && (p.getEnumValues().size() > 0)) {
				for (String v : p.getEnumValues()) {
					enumeration += v + System.lineSeparator();
				}
			}
			
			String required = (p.isRequired()) ? "required" : "";
			String deprecated = (p.isDeprecated()) ? "yes" : "no";
			String description = (p.getDescription() != null) ? p.getDescription(): "";
			String original = "";
			String current = "";
			
			if (p.getxImplementation() != null) {
				Object oCondition = p.getxImplementation().get("condition");
				if (oCondition != null) {
					if (oCondition instanceof Map) {
						Map<String, Object> condition = (Map<String, Object>) oCondition;
						Object oOriginal = condition.get("original");
						Object oCurrent = condition.get("current");
						original = (oOriginal != null) ? oOriginal.toString() : "";
						current = (oCurrent != null) ? oCurrent.toString() : "";
					}
				}
			}
			
			index = insertMultiple(index, new String[] {p.getName(), required, deprecated, p.getDataType().toString(), enumeration, description, original, current}, false, sheet);
		}		
		return index;
	}
}
