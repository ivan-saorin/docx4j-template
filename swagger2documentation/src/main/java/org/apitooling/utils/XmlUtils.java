package org.apitooling.utils;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtils {

	private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    private final static HashMap<String, String> conversionTable = new HashMap<String, String>();
	static {
		conversionTable.put("<", "&lt;");
		conversionTable.put(">", "&gt;");
	}
	
	private XmlUtils() {
	}
	
	public static Object xmlEscaping(Object object) {
		String string = object.toString();
		//if (logger.isInfoEnabled()) logger.info("xmlEscaping in: {}", string);

		Set<String> keys = conversionTable.keySet();
		for (String key : keys) {
			String replacement = conversionTable.get(key);
			string = string.replaceAll(key, replacement);
		}
		
		//if (logger.isInfoEnabled()) logger.info("xmlEscaping out: {}", string);
		
		return string;
	}
	
}
