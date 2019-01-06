package org.apitooling.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ApiElement {

	private static final String XIMPLEMENTATION_KEY = "x-implementation";
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
	protected LinkedHashMap<String, Object> xImplementation = new LinkedHashMap<String, Object>();  
	
	public ApiElement() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void describeExtensions(Map<String, Object> extensions) {
		if (extensions == null) {
			return;
		}
		
		if (extensions.containsKey(XIMPLEMENTATION_KEY)) {
			Map<String, Object> extension = (Map<String, Object>) extensions.get(XIMPLEMENTATION_KEY);
			xImplementation.putAll(extension);
			if (xImplementation.containsKey("date")) {
				//"2018-12-27T00:00:00.000+0000"
				String date = xImplementation.get("date").toString();
				date = date.substring(0,11);
				try {
					xImplementation.put("date", DATEFORMAT.parse(date));
				} catch (ParseException cause) {
					cause.printStackTrace();
				}				
			}
			
		}		
	}
	
	public Map<String, Object> getxImplementation() {
		return xImplementation;
	}	
	
}
