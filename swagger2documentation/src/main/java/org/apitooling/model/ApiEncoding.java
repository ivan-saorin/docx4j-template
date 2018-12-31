package org.apitooling.model;

import io.swagger.v3.oas.models.media.Encoding;

public class ApiEncoding extends ApiElement {

	private String encoding;
	
	public ApiEncoding(ApiType modelVersion, Encoding encoding) {
		super();
		describeModel(modelVersion, encoding);
	}

	private void describeModel(ApiType modelVersion, Encoding encoding) {
		this.encoding = encoding.getContentType();
	}

	public ApiEncoding(ApiType modelVersion, String encoding) {
		super();
		describeModel(modelVersion, encoding);
	}
	
	private void describeModel(ApiType modelVersion, String encoding) {
		this.encoding = encoding;		
	}

	public String getEncoding() {
		return encoding;
	}

}
