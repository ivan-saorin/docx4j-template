package org.apitooling.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apitooling.model.stats.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ApiElement {

	private static Logger logger = LoggerFactory.getLogger(ApiElement.class);  
	
	private static final String XIMPLEMENTATION_KEY = "x-implementation";
	private static final String CONDITION_KEY = "condition";
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
	protected HashMap<String, Object> xImplementation = new HashMap<String, Object>();  
	
	private static final String XEXAMPLES_KEY = "x-examples";
	//protected HashMap<String, Object> xExamples = new HashMap<String, Object>();
	protected List<Object> xExamples = new ArrayList<Object>();
	
	private static final String XHOSTS_KEY = "x-hosts";
	protected List<Object> xHosts = new ArrayList<Object>();
	
	
	protected ApiModel model;
	
	public ApiElement(ApiModel parent) {
		super();
		this.model = parent;
	}

	public static String typeName(String ref) {
		String typeName = ref;
		int l = ref.lastIndexOf('/');
		if (++l > -1) {
			typeName = ref.substring(l);
		}
		return typeName;			
	}
	
	protected void describeExtensions(Map<String, Object> extensions) {
		this.describeExtensions(null, extensions);
	}
		
	protected void describeExtensions(String name, Map<String, Object> extensions) {
		if (extensions == null) {
			return;
		}
		
		describeXImplementation(name, extensions);
		describeXExamples(name, extensions);
		describeXHosts(name, extensions);
	}

	@SuppressWarnings("unchecked")
	private void describeXImplementation(String name, Map<String, Object> extensions) {
		if (extensions.containsKey(XIMPLEMENTATION_KEY)) {
			Map<String, Object> extension = (Map<String, Object>) extensions.get(XIMPLEMENTATION_KEY);
			if (xImplementation.size() == 0) {
				xImplementation.putAll(extension);
				
				if (name != null) {
					xImplementation.put("fieldName", name);
				}
				
				if (xImplementation.containsKey("date")) {
					//"2018-12-27T00:00:00.000+0000"
					String date = xImplementation.get("date").toString();
					date = date.substring(0,10);
					try {
						xImplementation.put("date", DATEFORMAT.parse(date));
					} catch (ParseException cause) {
						//cause.printStackTrace();
					}				
				}
				if (xImplementation.containsKey(CONDITION_KEY)) {
					HashMap<String, Object> xCondition = (HashMap<String, Object>) xImplementation.get(CONDITION_KEY);
					if (xCondition.containsKey("date")) {
						//"2018-12-27T00:00:00.000+0000"
						String date = xCondition.get("date").toString();
						date = date.substring(0,10);
						try {
							xCondition.put("date", DATEFORMAT.parse(date));
						} catch (ParseException cause) {
							//cause.printStackTrace();
						}
					}
					if (xCondition.containsKey("date2")) {
						//"2018-12-27T00:00:00.000+0000"
						String date = xCondition.get("date2").toString();
						date = date.substring(0,10);
						try {
							xCondition.put("date2", DATEFORMAT.parse(date));
						} catch (ParseException cause) {
							//cause.printStackTrace();
						}
					}
					if (xCondition.containsKey("date3")) {
						//"2018-12-27T00:00:00.000+0000"
						String date = xCondition.get("date3").toString();
						date = date.substring(0,10);
						try {
							xCondition.put("date3", DATEFORMAT.parse(date));
						} catch (ParseException cause) {
							//cause.printStackTrace();
						}
					}
					
				}
			}			
		}
	}
	
	public Map<String, Object> getxImplementation() {
		return xImplementation;
	}

	@SuppressWarnings("unchecked")
	private void describeXExamples(String name, Map<String, Object> extensions) {
		if (extensions.containsKey(XEXAMPLES_KEY)) {
			if (xExamples.size() == 0) {
				List<Object> myExamples = (List<Object>) extensions.get(XEXAMPLES_KEY);
				xExamples.addAll(myExamples);				
				//if (logger.isInfoEnabled()) logger.info("examples: {} -> {}", this.hashCode(), xExamples);
			}			
		}
	}

	@SuppressWarnings("unchecked")
	private void describeXHosts(String name, Map<String, Object> extensions) {
		if (extensions.containsKey(XHOSTS_KEY)) {
			if (xHosts.size() == 0) {
				List<Object> myHosts = (List<Object>) extensions.get(XHOSTS_KEY);
				xHosts.addAll(myHosts);				
				//if (logger.isInfoEnabled()) logger.info("examples: {} -> {}", this.hashCode(), xExamples);
			}			
		}
	}

	public List<Object> getxExamples() {
		return xExamples;
	}

	public List<Object> getxHosts() {
		return xHosts;
	}

	public ApiModel getModel() {
		if (model == null) {
			throw new RuntimeException(new NullPointerException("model is null"));
		}
		return model;
	}	

	public Stats getStats() {
		if (model == null) {
			throw new RuntimeException(new NullPointerException("model is null"));
		}			
		return model.getStats();
	}	

	
}
