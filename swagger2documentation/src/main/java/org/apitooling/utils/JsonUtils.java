package org.apitooling.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private static final String JSON_ESC_CHAR_REGEX = "\\\\[\"\\\\/bfnrt]|\\\\u[0-9A-Fa-f]{4}";
	private static final String JSON_NORMAL_CHARACTER_REGEX = "[^\"\\\\]";
	private static final String JSON_STRING_REGEX = "\"(" + JSON_ESC_CHAR_REGEX + "|" + JSON_NORMAL_CHARACTER_REGEX + ")*\"";
	// This 'other value' regex is deliberately weak, even accepting an empty string, to be useful when reporting malformed data.
	private static final String JSON_OTHER_VALUE_REGEX = "[^\\{\\[\\]\\}\\,]*";
	private static final String JSON_VALUE_OR_PAIR_REGEX = "((" + JSON_STRING_REGEX + "\\s*:)?\\s*(" + JSON_STRING_REGEX + "|" + JSON_OTHER_VALUE_REGEX + ")\\s*,?\\s*)";
	private static final Pattern JSON_VALUE_OR_PAIR_PATTERN = Pattern.compile(JSON_VALUE_OR_PAIR_REGEX);	

	private static final String EX_ESC_CHAR_REGEX = "\\\\[\"\\\\/bfnrt]|\\\\u[0-9A-Fa-f]{4}";
	private static final String EX_NORMAL_CHARACTER_REGEX = "[^\"\\\\]";
	private static final String EX_STRING_REGEX = "\"(" + EX_ESC_CHAR_REGEX + "|" + EX_NORMAL_CHARACTER_REGEX + ")*\"";
	// This 'other value' regex is deliberately weak, even accepting an empty string, to be useful when reporting malformed data.
	private static final String EX_OTHER_VALUE_REGEX = "[^\\{\\[\\]\\}\\,]*";
	private static final String EX_VALUE_OR_PAIR_REGEX = "((" + EX_STRING_REGEX + "\\s*=)?\\s*(" + EX_STRING_REGEX + "|" + JSON_OTHER_VALUE_REGEX + ")\\s*,?\\s*)";
	private static final Pattern EX_VALUE_OR_PAIR_PATTERN = Pattern.compile(EX_VALUE_OR_PAIR_REGEX);	
	
	// It might be useful also to make this available in the 'Request' tab, for
	// when posting JSON.
	public static String prettyJSON(String json) {
		
		StringBuffer pretty = new StringBuffer(json.length() * 2); // Educated guess
	
		final String tab = "    "; 
		StringBuffer index = new StringBuffer(""); 
		String nl = ""; 
	
		Matcher valueOrPair = JSON_VALUE_OR_PAIR_PATTERN.matcher(json);
	
		boolean misparse = false;
	
		for (int i = 0; i < json.length(); ) {
			if ((json.charAt(i) == '{') || (json.charAt(i) == '[')) {
				//pretty.append(nl).append(index).append(json.charAt(i));
				pretty.append(index).append(json.charAt(i));
				i++;
				index.append(tab);
				misparse = false;
			}
			else if ((json.charAt(i) == '}') || (json.charAt(i) == ']')) {
				if (index.length() > 0) {
					index.delete(0, tab.length());
				}
				pretty.append(nl).append(index).append(json.charAt(i));
				i++;
				int j = i;
				while ((j < json.length()) && Character.isWhitespace(json.charAt(j))) {
					j++;
				}
				if ((j < json.length()) && (json.charAt(j) == ',')) {
					pretty.append(",");
					i=j+1;
				}
				misparse = false;
			}
			else if (valueOrPair.find(i) && valueOrPair.group().length() > 0) {
				pretty.append(nl).append(index).append(valueOrPair.group());
				i=valueOrPair.end();
				misparse = false;
			}
			else {
				if (!misparse) {
					pretty.append(nl).append("- Parse failed from:");
				}
				pretty.append(json.charAt(i));
				i++;
				misparse = true;
			}
			nl = "\n";
		}
		return pretty.toString();
	}

	public static String ex2json(String example) {
		example = example.replaceAll("\"", "");
		StringBuffer pretty = new StringBuffer(example.length() * 2); // Educated guess
		
		StringBuffer index = new StringBuffer(""); 
		String quotes = "\"";
		Matcher valueOrPair = EX_VALUE_OR_PAIR_PATTERN.matcher(example);
	
		boolean misparse = false;
	
		for (int i = 0; i < example.length(); ) {
			if ((example.charAt(i) == '{') || (example.charAt(i) == '[')) {
				//pretty.append(nl).append(index).append(json.charAt(i));
				pretty.append(index).append(example.charAt(i));				
				i++;
				misparse = false;
			}
			else if ((example.charAt(i) == '}') || (example.charAt(i) == ']')) {
				pretty.append(index).append(example.charAt(i));
				i++;
				int j = i;
				while ((j < example.length()) && Character.isWhitespace(example.charAt(j))) {
					j++;
				}
				if ((j < example.length()) && (example.charAt(j) == ',')) {
					pretty.append(",");
					i=j+1;
				}			
				misparse = false;
			}
			else if (valueOrPair.find(i) && valueOrPair.group().length() > 0) {				
				String possiblePair = valueOrPair.group();
				String[] tokens = possiblePair.split("=");
				if (tokens.length > 2) {
					pretty.append(quotes);
					pretty.append(index).append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
					pretty.append(quotes);					
					String tmp = example.substring(valueOrPair.start(), valueOrPair.end()).replaceAll("\\\"", "\\\\\"");
					pretty.append(tmp);
					pretty.append(quotes);
				}
				else if (tokens.length == 2) {
					pretty.append(quotes);
					pretty.append(index).append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
					pretty.append(quotes);
					if (tokens[1].endsWith(", ")) {
						pretty.append(tokens[1].substring(0, tokens[1].length()-2));
						pretty.append(quotes);
						pretty.append(", ");
					}
					else {
						pretty.append(index).append(tokens[1]);
						pretty.append(quotes);
					}										
				}
				else {
					pretty.append(quotes);
					pretty.append(index).append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
				}
				
				i=valueOrPair.end();
				misparse = false;
			}
			else {
				if (!misparse) {
					pretty.append("- Parse failed from:");
				}
				pretty.append(quotes);
				pretty.append(example.charAt(i));				
				i++;
				misparse = true;
			}
		}
		return pretty.toString();
	}

	public static String ex2docbook(String example) {
		StringBuffer pretty = new StringBuffer(example.length() * 3); // Educated guess
		
		String ident = "&nbsp;&nbsp;&nbsp;&nbsp;";
		StringBuffer index = new StringBuffer(""); 
		String quotes = "\"";
		String open = "<para>";
		String close = "</para>";
		
		Matcher valueOrPair = EX_VALUE_OR_PAIR_PATTERN.matcher(example);
	
		boolean misparse = false;
	
		for (int i = 0; i < example.length(); ) {
			if ((example.charAt(i) == '{') || (example.charAt(i) == '[')) {
				//pretty.append(nl).append(index).append(json.charAt(i));
				pretty.append(open).append(index).append(example.charAt(i)).append(close);
				index.append(ident);
				i++;
				misparse = false;
			}
			else if ((example.charAt(i) == '}') || (example.charAt(i) == ']')) {
				index.delete(0, ident.length());
				pretty.append(open).append(index).append(example.charAt(i)).append(close);
				i++;
				int j = i;
				while ((j < example.length()) && Character.isWhitespace(example.charAt(j))) {
					j++;
				}
				if ((j < example.length()) && (example.charAt(j) == ',')) {
					pretty.append(",");
					i=j+1;
				}				
				misparse = false;
			}
			else if (valueOrPair.find(i) && valueOrPair.group().length() > 0) {	
				pretty.append(open).append(index);
				String possiblePair = valueOrPair.group();
				String[] tokens = possiblePair.split("=");
				if (tokens.length > 2) {
					pretty.append(quotes);
					pretty.append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
					pretty.append(quotes);					
					String tmp = example.substring(valueOrPair.start(), valueOrPair.end()).replaceAll("\\\"", "\\\\\"");
					tmp = (String) XmlUtils.xmlEscaping(tmp);
					pretty.append(tmp);
					pretty.append(quotes);
				}
				else if (tokens.length == 2) {
					pretty.append(quotes);
					pretty.append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
					pretty.append(quotes);
					if (tokens[1].endsWith(", ")) {
						pretty.append(tokens[1].substring(0, tokens[1].length()-2));
						pretty.append(quotes);
						pretty.append(", ");
					}
					else {
						pretty.append(index).append(tokens[1]);
						pretty.append(quotes);
					}										
				}
				else {
					pretty.append(quotes);
					pretty.append(tokens[0]);
					pretty.append(quotes);
					pretty.append(":");
				}
				pretty.append(close);
				i=valueOrPair.end();
				misparse = false;
			}
			else {
				if (!misparse) {
					pretty.append("- Parse failed from:");
				}
				pretty.append(quotes);
				System.out.println(">" + example.charAt(i));
				pretty.append(example.charAt(i));				
				i++;
				misparse = true;
			}
		}
		return pretty.toString();
	}

	public static Object ex2docbook2(Object object) {
		if (object != null) {
			return JsonUtils.ex2docbook(object.toString());
		}
		return null;
	}

	public static Object ex2json2(Object object) {
		if (object != null) {
			String input = object.toString();
			int i = input.indexOf('=');
			if (i > -1) {
				//if (logger.isInfoEnabled()) logger.info("example>{}", input);
				return JsonUtils.prettyJSON(JsonUtils.ex2json(object.toString()));
			}
			else {
				return JsonUtils.prettyJSON(object.toString());				
			}
		}
		return null;
	}
}
