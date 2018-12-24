package org.swaggertooling.swagger.support;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	public  static <T> T find(T[] a, T f) {
		for (T s : a) {
			if (s.equals(f)) {
				return f;
			}
		}
		return null;
	}

	public static String[] parseUriParameters(String href) {
		ArrayList<String> result = new ArrayList<>();
		Pattern p = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
		Matcher matcher = p.matcher(href);
		while (matcher.find()) {
			result.add(matcher.group(1));
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>found
			// match:"+matcher.group(1));
		}
		String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static void log(Object[] params) {
		if (params == null) {
			logger.info("params: null");
			return;
		}
		if (params.length == 0) {
			logger.info("params: []");
			return;
		}
		
		StringBuilder sb = new StringBuilder(255);
		for (Object param : params) {
			sb
				.append(param.toString())
				.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		logger.info("params: [" + sb.toString() + "]");
	}

	public static boolean in(String[] a, String f) {
		return (find(a, f) != null);
	}

}
