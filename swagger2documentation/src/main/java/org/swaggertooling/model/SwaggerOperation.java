package org.swaggertooling.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Parameter;

public class SwaggerOperation extends SwaggerElement {

	private static Logger logger = LoggerFactory.getLogger(SwaggerOperation.class);
	
	public int idx;
	private String method;
	private String id;
	private String summary;
	private ArrayList<SwaggerParameter> pathParams = new ArrayList<SwaggerParameter>();
	private ArrayList<SwaggerParameter> headerAttributes = new ArrayList<SwaggerParameter>();
	private ArrayList<SwaggerParameter> queryParams = new ArrayList<SwaggerParameter>();
	
	public SwaggerOperation(int idx, Operation op) {
		super();
		this.idx = idx;
		describeModel(op);
	}

	private void describeModel(Operation op) {
		this.method = Overlay.of(op).getPathInParent().toUpperCase();
		this.id = op.getOperationId();
		this.summary = this.md2html(op.getSummary());		
		System.out.printf("  %s: [%s] %s\n", Overlay.of(op).getPathInParent().toUpperCase(),
				op.getOperationId(), op.getSummary());
		int idxPath = 0;
		int idxHeader = 0;
		int idxQuery = 0;
		for (Parameter param : op.getParameters()) {
			if (param.getIn().equalsIgnoreCase("path")) {
				pathParams.add(new SwaggerParameter(idxPath++, SwggerParameterType.PATH, param));
			}
			else if (param.getIn().equalsIgnoreCase("header")) {
				headerAttributes.add(new SwaggerParameter(idxHeader++, SwggerParameterType.HEADER, param));
			}
			else if (param.getIn().equalsIgnoreCase("query")) {
				queryParams.add(new SwaggerParameter(idxQuery++, SwggerParameterType.QUERY, param));
			}
			else {
				
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

	public ArrayList<SwaggerParameter> getPathParams() {
		return pathParams;
	}

	public ArrayList<SwaggerParameter> getHeaderAttributes() {
		return headerAttributes;
	}

	public ArrayList<SwaggerParameter> getQueryParams() {
		return queryParams;
	}

}
