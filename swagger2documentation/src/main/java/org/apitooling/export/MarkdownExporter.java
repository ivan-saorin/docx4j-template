package org.apitooling.export;

import java.io.File;
import java.io.IOException;
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
import org.apitooling.model.ApiType;
import org.apitooling.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.steppschuh.markdowngenerator.MarkdownBuilder;
import net.steppschuh.markdowngenerator.link.Link;
import net.steppschuh.markdowngenerator.list.ListBuilder;
import net.steppschuh.markdowngenerator.rule.HorizontalRule;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.TextBuilder;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.emphasis.ItalicText;

public class MarkdownExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(MarkdownExporter.class);
	private static String API_SEPARATOR = 				"**************************************************************************************************************************************";
	private static String API_METHOD_SEPARATOR = 		"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
	private static String API_OPERATION_SEPARATOR = 	"--------------------------------------------------------------------------------------------------------------------------------------";
	private static String API_SEPARATOR_SEPARATOR = 	"::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::";
	private static String API_RESPONSE_SEPARATOR = 		"======================================================================================================================================";
	private static String LF = System.lineSeparator() + "\t";
	private static String TAB = "\t";

	private File input;
	private ApiModel model;
	private MarkdownBuilder builder;
	
	protected MarkdownExporter() {		
		builder = new TextBuilder();
	}	
	
	public MarkdownExporter(ApiModel model) {
		super();
		builder = new TextBuilder();
		this.model = model;
	}

	public MarkdownExporter(ApiModel model, File input) {
		super();
		builder = new TextBuilder();
		this.model = model;
		this.input = input;
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

		outputApi(model);
		
		/*
        this.builder
                .heading("Markdown Builder")
                .append("Demonstrating: ")
                .append(this.builder.bold("Bold Text"))
                .newParagraph()
                .beginList()
                    .append("I should be an item")
                    .append(this.builder.italic("I should be an italic item"))
                .end()
                .newParagraph()
                .beginQuote()
                    .append("I should be a quote").newLine()
                    .append("I should still be a quote")
                .end()
                .newParagraph()
                .beginCodeBlock(CodeBlock.LANGUAGE_JAVA)
                    .append("// I should be code").newLine()
                    .append("dummyMethod(this);")
                .end()
                .newParagraph()
                .append("Over.")
                .newLines(2);   
        
        MarkdownBuilder b2 = new ListBuilder()
                .text("Item 1")
                .bold("Item 2")
                .beginList()
                    .text("Item 2.1")
                    .bold("Item 2.2")
                    .beginList()
                        .text("Item 2.2.1")
                        .bold("Item 2.2.2")
                        .text("Item 2.2.3")
                    .end()
                    .text("Item 2.3")
                .end()
                .text("Item 3");
		
		this.builder.append(b2.end().build());

        Markdown.commonsHttpErrorsTable(this.builder);
        */
		
		return new ExporterOuptput(this.builder);
	}

	private void outputApi(ApiModel model) {
		outputModel(model, 1);
	}

	private void outputModel(ApiModel model, int lvl) {
		outputApiHeader(model);
		
		outputDatatypeFieldDisclaimer();

		if (model.getPaths().size() == 0) {
			this.builder.append(this.builder.bold("No content")).newLine();
		}
		else {
			Map<String, ArrayList<ApiPath>> apis = ApiUtils.groupByApi(model.getPaths());
			Set<String> keys = apis.keySet();
			for (String key : keys) {
				String title = "";
				if (key.length() > 0) {
					title = key.substring(0,1).toUpperCase() + key.substring(1);
				}
				this.builder.heading(title + " API", lvl++);
				List<ApiPath> paths = apis.get(key);
				for (ApiPath path : paths) {
					outputPath(path, lvl);
				}				
			}
			
			outputComponents(model, lvl);			
		}
	}

	private void outputComponents(ApiModel model, int lvl) {
		if (model.getComponents() != null) {
			outputComponentsSchemas(model, lvl);
			outputComponentsParameters(model, lvl);
			outputComponentsExamples(model, lvl);
			outputComponentsRequestBodies(model, lvl);
			outputComponentsResponses(model, lvl);
			outputComponentsLinks(model, lvl);			
		}
	}

	private void outputComponentsSchemas(ApiModel model, int lvl) {
		if ((model.getComponents().getSchemas() != null) && (model.getComponents().getSchemas().size() > 0)){
			this.builder.heading("Global Schemas ", lvl++);

			Map<String, ApiSchema> schemas = model.getComponents().getSchemas();
			Set<String> keys = schemas.keySet();
			for (String key : keys) {							
				ApiSchema s = schemas.get(key);
				this.builder.heading(s.getTypeName(), lvl);
				
				Set<String> skeys = s.keySet();
				if (keys.size() > 0) {
					Table.Builder tableBuilder = new Table.Builder()
			                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT)
			                .addRow("", "Data Type");
					
					for (String skey : skeys) {
						ApiField f = s.get(skey);
						tableBuilder.addRow(new ItalicText(skey), new Text(f.toString()));
					}
					builder.newLine();
					builder.append(tableBuilder.build());
				}
			}
			
		}
	}

	private void outputComponentsParameters(ApiModel model, int lvl) {
		if ((model.getComponents().getParameters() != null) && (model.getComponents().getParameters().size() > 0)) {
			this.builder.heading("Global Parameters ", lvl++);
			
			Map<String, ApiParameter> parameters = model.getComponents().getParameters();
			Set<String> keys = parameters.keySet();
			if (keys.size() > 0) {
				Table.Builder tableBuilder = new Table.Builder()
		                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_LEFT)
		                .addRow("Fieldname", "Data Type", "Description");

				for (String key : keys) {							
					ApiParameter p = parameters.get(key);					
					outputParamAttributes(tableBuilder, p);
				}
				
				builder.newLine();
				builder.append(tableBuilder.build());

			}
		}
	}

	private void outputComponentsExamples(ApiModel model, int lvl) {
		if ((model.getComponents().getExamples() != null) && (model.getComponents().getExamples().size() > 0)) {
			this.builder.heading("Global Examples ", lvl++);
			
			Map<String, ApiExample> examples = model.getComponents().getExamples();
			outputExamples(examples, lvl++);
		}
	}

	private void outputComponentsRequestBodies(ApiModel model, int lvl) {
		if ((model.getComponents().getRequestBodies() != null) && (model.getComponents().getRequestBodies().size() > 0)) {
			this.builder.heading("Global Request Bodies ", lvl++);
			
			Map<String, ApiRequestBody> rb = model.getComponents().getRequestBodies();
			Set<String> keys = rb.keySet();
			for (String key : keys) {							
				ApiRequestBody body = rb.get(key);
				outputRequestBody(body, lvl);
			}					
		}
	}

	private void outputComponentsResponses(ApiModel model, int lvl) {
		if ((model.getComponents().getResponses() != null) && (model.getComponents().getResponses().size() > 0)) {
			this.builder.heading("Global Responses ", lvl++);
			
			Map<String, ApiResponse> ress = model.getComponents().getResponses();
			Set<String> keys = ress.keySet();
			for (String key : keys) {							
				ApiResponse res = ress.get(key);
				outputResponse(key, res, lvl);
			}					
		}
	}

	@SuppressWarnings("unused")
	private void outputComponentsLinks(ApiModel model, int lvl) {
		if ((model.getComponents().getLinks() != null) && (model.getComponents().getLinks().size() > 0)) {
			this.builder.heading("Global Links ", lvl++);
			
			Map<String, ApiLink> links = model.getComponents().getLinks();
			Set<String> keys = links.keySet();
			for (String key : keys) {							
				ApiLink link = links.get(key);
				/** Usage of links not clear - Not supported in this moment **/
				//outputLink(sb, key, link);
			}					
		}
	}

	private void outputPath(ApiPath path, int lvl) {
		this.builder.heading(path.getPath(), lvl++);
		if (path.getOperations().size() == 0) {
			this.builder.append(new BoldText("No content")).newLine();
		}
		else {
			for (ApiOperation op : path.getOperations()) {
				outputOperation(path, op, lvl);				
			}
		}
	}

	private void outputOperation(ApiPath path, ApiOperation op, int lvl) {
		outputOperationHeader(path, op, lvl);
		outputOperationExternalDocs(op, lvl);
		outputOperationParams(op, "Header Attributes ", op.getHeaderAttributes(), lvl);
		outputOperationParams(op, "Path Parameters ", op.getPathParams(), lvl);
		outputOperationParams(op, "Query Parameters ", op.getQueryParams(), lvl);
		outputRequestBody(op.getRequestBody(), lvl);
		outputResponses(op.getResponses(), lvl);
	}

	private void outputOperationParams(ApiOperation op, String title, ArrayList<ApiParameter> list, int lvl) {
		if (list.size() > 0) {
			this.builder.heading(title, lvl);
			outputParamsList(list, lvl);
		}
	}

	private void outputOperationExternalDocs(ApiOperation op, int lvl) {
		ApiExternalDocs extDocs = op.getExternalDocs();
		if (extDocs != null) {
			this.builder.heading("External Doucuments", lvl);	
			if (extDocs.getUrl() != null) {
				this.builder.newParagraph().link(extDocs.getDescription(), extDocs.getUrl());
			}
			else {
				this.builder.newParagraph().append(extDocs.getDescription());
			}
		}
	}

	private void outputOperationHeader(ApiPath path, ApiOperation op, int lvl) {
		String title = op.getMethod().toUpperCase(); // + " " + path.getPath()
		if (op.getId() != null) {
			title += " (" + op.getId() + ")";
		}
		if (op.isDeprecated()) {
			title += " [deprecated] ";
		}
		this.builder.heading(title, lvl);
		
		if (op.getSummary() != null) {
			this.builder.newParagraph().append(op.getSummary());
		}
		if (op.getDescription() != null) {
			this.builder.newParagraph().append(op.getDescription());
		}
		
	}

	private void outputApiHeader(ApiModel model) {
		outputIntroduction(model);		
		outputContact(model);
		outputLicense(model);
		outputTOS(model);
		outputApiExternalDocs(model);
		outputApiTags(model);	
		outputSecurityAndServers(model);
	}

	private void outputSecurityAndServers(ApiModel model) {
		ArrayList<ApiServer> servers = model.getServers();
		ArrayList<ApiSecurity> security = model.getSecurity();
		if (((servers != null) && (servers.size() > 0)) || ((security != null) && (security.size() > 0))){
			outputServers(servers);
			outputSecurity(security);
		}
	}

	private void outputSecurity(ArrayList<ApiSecurity> security) {
		if ((security != null) && (security.size() > 0)) {
			this.builder.heading("Security", 3)
			.newParagraph();
			MarkdownBuilder b2 = new ListBuilder();
			for (ApiSecurity s : security) {
				Set<String> keys = s.keySet();
				for (String key : keys) {
					b2.text(key);
					List<String> list = s.get(key);
					if (list != null) {
						b2.beginList();
						for (int i = 0; i < list.size(); i++) {
							b2.text(list.get(i));
						}
						b2.end();
					}
					
				}
			}
			this.builder.append(b2.end().build());
		}
	}

	private void outputServers(ArrayList<ApiServer> servers) {
		//sb.append(LF);
		if ((servers != null) && (servers.size() > 0)) {
			this.builder.heading("Servers", 3)
				.newParagraph();
			MarkdownBuilder b2 = new ListBuilder();
			for (ApiServer server : servers) {
				String value = "";
				if (server.getDescription() != null) {
					value += server.getDescription() + " - ";
					b2.text(new Link(server.getDescription(), server.getUrl()));	
				}
				else {
					b2.text(new Link(server.getUrl()));	
				}				
					
				if ((server.getVariables() != null) && (server.getVariables().size() > 0)) {
					b2.bold("Server variables:");
					b2.beginList();
					Set<String> keys = server.getVariables().keySet();
					for (String key : keys) {
						ApiServerVariable var = server.getVariables().get(key);
						b2.text(key);
						b2.beginList();
						b2.text(var.getDescription() + " - " + var.getDefault());
						b2.text(key);
						b2.beginList();
						for (String s : var.getEnum()) {
							b2.text(s);
						}
						b2.end();
						b2.end();
					}
					b2.end();
				}
			}
			this.builder.append(b2.end().build());
		}
	}

	private void outputApiTags(ApiModel model) {
		ArrayList<ApiTag> tags = model.getTags();
		if ((tags != null) && (tags.size() > 0)) {
			this.builder.heading("External Documentation", 3)
			.newParagraph();

	        MarkdownBuilder b2 = new ListBuilder();
			for (ApiTag t : tags) {
				b2.text(t.getName() + " - " + t.getDescription());
				//sb.append(TAB).append(TAB).append(TAB).append(t.getName()).append(" - ").append(t.getDescription()).append(LF);
				outputExternalDocs(b2, t.getExternalDocs(), 4);
			}
			this.builder.append(b2.end().build());
		}
	}

	private void outputApiExternalDocs(ApiModel model) {
		ApiExternalDocs extDocs = model.getExternalDocs();
		outputExternalDocs(this.builder, extDocs, 3);
	}

	private void outputExternalDocs(MarkdownBuilder builder, ApiExternalDocs extDocs, int lvl) {
		if (extDocs != null) {
			//sb.append("External Docs:").append(TAB).append(TAB).append(extDocs.getDescription()).append(" - ").append(extDocs.getUrl()).append(LF);
			builder.heading("External Documentation", lvl)
			.newParagraph();
		if (extDocs.getUrl() != null)
			builder.append(new Link(extDocs.getDescription(), extDocs.getUrl()));			
		else
			builder.append(extDocs.getDescription());
			
		}
	}

	private void outputLicense(ApiModel model) {
		ApiLicense license = model.getLicense();
		if (license != null) {
			//sb.append("License:").append(TAB).append(TAB).append(license.getName()).append(" - ").append(license.getUrl()).append(LF);
			this.builder.heading("License", 3)
				.newParagraph();
			if (license.getUrl() != null)
				this.builder.append(new Link(license.getName(), license.getUrl()));			
			else
				this.builder.append(license.getName());
		}
	}

	private void outputContact(ApiModel model) {
		ApiContact contact = model.getContact();
		if (contact != null) {
			this.builder.heading("Contact", 3)
				.newParagraph();
			if (contact.getEmail() != null)
				this.builder.append(new Link(contact.getName(), "mailto:" + contact.getEmail()));
			else
				this.builder.append(contact.getName());
			if (contact.getUrl() != null)
				this.builder.append(" - ").append(new Link(contact.getUrl()));			
		}
	}

	private void outputTOS(ApiModel model) {
		if (model.getTermOfService() != null) {			
			this.builder.heading("Terms of Service", 3)
				.newParagraph()
				.append(new Link(model.getTermOfService()));			
		}
	}

	private void outputIntroduction(ApiModel model) {
		this.builder.heading(model.getTitle(), 1).newLines(2);
		this.builder.heading("Version " + model.getApiVersion(), 2).newLines(2);
		this.builder.append(new HorizontalRule());
		String modelVersion = "Open API 3.x";
		if (model.getModelVersion().equals(ApiType.SWAGGER_20)) {
			modelVersion = "Swagger 2.x";
		}
		this.builder
			.newParagraph()
			.append("Automatically generated from: ").append(new ItalicText(this.input.getName()))
			.append(" (It was in ").append(new ItalicText(modelVersion)).append(" format)").newLines(2)
			.newParagraph()
			.append(model.getDescription());
	}

	private void outputRequestBody(ApiRequestBody body, int lvl) {
		if (body != null) {
			String title = "Request Body ";
			if (body.isRequired())
				title += "(required) ";
			this.builder.heading(title, lvl++);

			if (body.getDescription() != null) {
				this.builder.newParagraph().append(body.getDescription());
			}
	
			outputContent(body.getContent(), lvl);
		}
	}

	private void outputResponses(Map<String, ApiResponse> responses, int lvl) {
		if (responses != null) {
			this.builder.heading("Responses", lvl++);
			Set<String> keys = responses.keySet();
			for (String key : keys) {
				ApiResponse res = responses.get(key);
				outputResponse(key, res, lvl);
			}
		}
	}

	private void outputResponse(String key, ApiResponse res, int lvl) {
		String title;
		title = "Response " + key + " ";
		this.builder.heading(title, lvl++);
		if (res.getDescription() != null) {
			this.builder
				.newParagraph()
				.append(res.getDescription())
				.end();
		}
		
		if (res.getProduces() != null) {
			if (res.getProduces().size() > 0) {
				this.builder
					.unorderedList(res.getProduces())
					.newLines(2);
				for (String produce : res.getProduces()) {
					//sb.append(TAB).append(produce).append(LF);
				}
				
			}
			else {
				String all = "*/*";
				all = all.replaceAll("[*]", "\\\\*");
				this.builder
					.newParagraph()
					.append(all)
					.end()
					.newLines(2);
			}
		}
		
		if (res.getRef() != null) {
			//sb.append(TAB).append(TAB).append("Ref:").append(TAB).append(TAB).append(res.getRef()).append(LF);
		}
		if (res.getHeaderAttributes() != null) {
			outputParamsList(res.getHeaderAttributes(), lvl);
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
			this.builder.newLines(2);
			outputSchema(res.getSchema(), lvl);
		}
		this.builder.newLines(2);
		outputContent(res.getContent(), lvl);
	}

	private void outputSchema(ApiSchema schema, int lvl) {
		//sb.append(TAB).append(TAB).append("Schema:").append(TAB).append(TAB).append(schema.getTypeName()).append(LF);
		this.builder.heading(schema.getTypeName(), lvl++);
		if (schema.size() > 0) {
			Set<String> keys = schema.keySet();
			for (String key : keys) {
				//sb.append(TAB).append(TAB).append(TAB).append(key).append(LF);
				this.builder.heading(key, lvl++);
				ApiField field = schema.get(key);
				outputSchema(field, lvl);
			}
		}
		
	}

	private void outputContent(ApiContent content, int lvl) {
		if (content == null) {
			return;
		}
		
		Set<String> keys = content.keySet();
		for (String key : keys) {
			ApiMediaType m = content.get(key);
			//this.builder.heading(key, lvl++);
			String title = key;
			title = title.replaceAll("[*]", "\\\\*");			
			if (m.getSchema()!= null) {
				title += " " + m.getSchema();
			}
			this.builder.newParagraph().quote(title);
			if (m.getExample() != null) {
				this.builder.heading("Example", lvl);
				this.builder.newParagraph().append(m.getExample());
			}
			if (m.getExamples() != null)
				outputExamples(m.getExamples(), lvl);
		}
	}

	private void outputSchema(ApiField schema, int lvl) {
		this.builder.heading("Schema", lvl).newParagraph().append(schema.toString());
	}

	private void outputExamples(Map<String, ApiExample> examples, int lvl) {
		if (examples.size() > 0) {
			this.builder.heading("Examples", lvl);
			
			MarkdownBuilder b2 = new ListBuilder();
			Set<String> keys = examples.keySet();
			for (String key : keys) {				
				ApiExample example = examples.get(key);
				b2.bold(key)
                .beginList()
	                .text(example.getSummary())
	                .text(example.getDescription())
	                .text(example.getValue())
                .end();				
			}
			this.builder.append(b2.end().build());			
		}
	}

	private void outputParamsList(ArrayList<ApiParameter> params, int lvl) {
		Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_LEFT)
                .addRow("Fieldname", "Data Type \\*", "Description");
		
		for (ApiParameter p : params) {
			//tableBuilder.addRow(new ItalicText(p.getName()), p.getDataType(), p.getDescription());
			outputParamAttributes(tableBuilder, p);
		}
		
		builder
			.newLine()
			.append(tableBuilder.build())
			.newLines(2);
	}

	private void outputDatatypeFieldDisclaimer() {
		
		MarkdownBuilder disclaimer = new ListBuilder();
		disclaimer			
			.beginList()
				.append("Simple data type: ")
				.beginList()		
					.append("dataType\\<format\\>(pattern){minLength,maxLength}")
				.end()
				.append("Arrays: ")
				.beginList()		
					.append("array[dataType\\<format\\>(pattern){minLength,maxLength}]{minItems,maxItems}")
				.end()				
			.end();
		builder
			.heading("Notes",3)
			.append("\\* Data type column format: ")
			.newLines(2)
			.append(disclaimer.build())
			.newLines(2);
	}

	private void outputParamAttributes(Table.Builder builder, ApiParameter p) {
		
		MarkdownBuilder col1 = new TextBuilder();
		if (p.isRequired()) {
			col1.append(new BoldText(p.getName()));
		}
		else {
			col1.append(p.getName());
		}
		String params = getParamAttributes(p);
		if (params.length() > 0) {
			col1.newLine().append(params);
		}

		MarkdownBuilder col3 = new TextBuilder();
		if (p.getDescription() != null) {
			col3
				.append(p.getDescription())
				.newLine();
		}
		if (p.getDefaultValue() != null) {
			col3
				.append("Default:")
				.newLine()
				.append(p.getDefaultValue());
		}

		if (p.getEnumValues() != null) {
			col3
				.append("Enum: ")
				.newLine()
				.append(p.getEnumValues());
		}

		/* Overly complex will not support right now.
		if (p.getContent() != null) {
			col3.add("Content: ");
			Set<String> keys = p.getContent().keySet();
			for (String key : keys) {
				ApiMediaType mt = p.getContent().get(key);
				mt.getEncoding();
				mt.getExample();
				mt.getExamples();
				col3.add();
			}
		}
		*/

		if (p.getExample() != null) {
			col3
				.append("Example: ")
				.newLine()
				.append(p.getExample());
		}

		/* Overly complex will not support right now.
		if (p.getExamples() != null) {			
			col3.add("Example: ");
			Set<String> keys = p.getExamples().keySet();
			for (String key : keys) {
				ApiExample example = p.getExamples().get(key);
				example.getSummary();
				example.getDescription();
				example.getValue();
				col3.add();
			}
		}
		*/
		
		builder.addRow(col1, p.getDataType(), col3);
	}

	private String getParamAttributes(ApiParameter p) {
		StringBuilder sb = new StringBuilder();
		if (p.isRequired() || p.isReadOnly() || p.isAllowEmptyValues() || p.isAllowReserved() || p.isDeprecated()) {
			sb.append("(");
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
		return sb.toString();
	}

}
