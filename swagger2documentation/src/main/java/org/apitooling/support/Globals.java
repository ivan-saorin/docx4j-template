package org.apitooling.support;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Globals {
	
	public static final String PROJECT_BASEDIR_FILE = "swagger2documentation.project.basedir";

	private static Map<String, Object> map = new HashMap<>();
	
	protected Globals() {
		super();
	}
	
	public static Object get(String key) {
		return map.get(key);
	}
	
	public static void set(String key, Object value) {
		// TBD: check if already set?
		map.put(key, value);
	}
	
	public static File getFile(String key) {
		Object raw = map.get(key);
		if (raw instanceof File) {
			return (File)raw;
		}
		throw new ClassCastException(String.format("[%s] not a file but [%s] instead.", key, raw));
	}
}
