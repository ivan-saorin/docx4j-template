package org.swaggertooling.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.swaggertooling.jsonschema.ValidationMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.net.MediaType;

public class ValidationException extends WebApiException {

	private static final long serialVersionUID = 8661637019950092578L;

	List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
	List<String> validationMessages = new ArrayList<String>();

	private MediaType contentType = null;
	private JsonNode errorJson = null;

	public ValidationException(List<ValidationMessage> errors) {
		super(
			"API",
			"VALIDATION",
			"422-999",
			getDescription("The server cannot deal with the request made by the client.", errors));
		this.errors.addAll(errors);
	}

	private static String getDescription(String message, List<ValidationMessage> errors) {
		StringBuffer sb = new StringBuffer();
		sb.append(message);
		if (!errors.isEmpty()) {
			sb.append("\r\n");
		}
		for (ValidationMessage e : errors) {
			sb.append(" * ").append(e);
		}
		return sb.toString();
	}

	public ValidationException(String message, List<String> errorDescriptions) {
		super(
			"API",
			"VALIDATION",
			"422-998",
			"The server cannot deal with the request made by the client.");
			this.validationMessages.addAll(errorDescriptions);
	}

	public ValidationException(MediaType contentType, JsonNode errorJson) {
		super(
			"API",
			"VALIDATION",
			"422-997",
			"The server cannot deal with the request made by the client.");
			this.contentType = contentType;
			this.errorJson = errorJson;
	}

	public ValidationException(String message) {
		super(
				"API",
				"VALIDATION",
				"422-997",
				message);
	}

	@Override
	public JsonNode getJson() {
		ObjectNode rootNode = (ObjectNode) super.getJson();
		if (errorJson != null) {
			if (contentType != null) {
				rootNode.set("contentType", new TextNode(contentType.toString()));				
			}
			rootNode.set("error", errorJson);
		}
		else {
			ArrayNode errorsNode = mapper.createArrayNode();
			for (ValidationMessage detail : errors) {
				errorsNode.add(detail.asJson());
			}
			for (String message : validationMessages) {
				errorsNode.add(new TextNode(message));
			}		
			rootNode.set("errors", errorsNode);
		}
		return rootNode;
	}

}
