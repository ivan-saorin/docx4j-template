package org.apitooling.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.models.media.Encoding;

public class ApiEncoding extends ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiEncoding.class);
	private String encoding;
	
	public ApiEncoding(ApiModel parent, ApiType modelVersion, Encoding encoding) {
		super(parent);
		//if (logger.isInfoEnabled()) logger.info("{} > {} estensions: {}", modelVersion, encoding.getClass().getName(), encoding.getExtensions());
		describeModel(modelVersion, encoding);
	}

	private void describeModel(ApiType modelVersion, Encoding encoding) {
		this.describeExtensions(encoding.getExtensions());
		this.encoding = encoding.getContentType();
	}

	public ApiEncoding(ApiModel parent, ApiType modelVersion, String encoding) {
		super(parent);
		describeModel(modelVersion, encoding);
	}
	
	private void describeModel(ApiType modelVersion, String encoding) {
		this.encoding = encoding;		
	}

	public String getEncoding() {
		return encoding;
	}

}
