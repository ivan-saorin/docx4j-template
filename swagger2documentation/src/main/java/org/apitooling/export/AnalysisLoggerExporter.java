package org.apitooling.export;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apitooling.analysis.ApiAnalysis;
import org.apitooling.analysis.ParamName2PathName;
import org.apitooling.analysis.PathParameters;
import org.apitooling.exceptions.WebApiRuntimeException;
import org.apitooling.model.ApiModel;
import org.apitooling.model.ApiParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisLoggerExporter implements Exporter {

	private static Logger logger = LoggerFactory.getLogger(AnalysisLoggerExporter.class);
	private static String API_SEPARATOR = 				"**************************************************************************************************************************************";
	private static String API_METHOD_SEPARATOR = 		"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
	private static String API_OPERATION_SEPARATOR = 	"--------------------------------------------------------------------------------------------------------------------------------------";
	private static String API_SEPARATOR_SEPARATOR = 	"::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::";
	private static String API_RESPONSE_SEPARATOR = 		"======================================================================================================================================";
	private static String LF = System.lineSeparator() + "   ";
	private static String TAB = "   ";
	
	public ApiAnalysis analysis;
	
	public AnalysisLoggerExporter(ApiModel model) {		
		this.analysis = new ApiAnalysis(model);
	}


	@Override
	public ExporterOuptput getOuptput() {
		if (analysis == null) {
			throw new WebApiRuntimeException("Missing analysis");
		}

		StringBuilder sb = outputApi(analysis);
		if (logger.isInfoEnabled()) logger.info(System.lineSeparator() + sb.toString());
		
		return new ExporterOuptput(sb.toString());
	}
	
	private StringBuilder outputApi(ApiAnalysis analysis) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(LF);
		//outputModelStart(sb);
		try {
			outputAnalysis(sb, analysis);
		} catch (Throwable cause) {
			logger.error(sb.toString());
		}
		//outputModelEnd(sb);
		return sb;
	}

	private void outputAnalysis(StringBuilder sb, ApiAnalysis analysis) {
		Map<String, ApiParameter> globals = analysis.getModel().getComponents().getParameters();
		sb.append(API_SEPARATOR).append(LF);
		ParamName2PathName p2p = analysis.getParam2Path();
		Set<String> keys1 = p2p.keySet();
		for (String key1 : keys1) {
			sb.append(key1).append(LF);
			PathParameters pathParams = p2p.get(key1);
			Set<String> keys2 = pathParams.keySet();
			for (String key2 : keys2) {
				sb.append(TAB).append(TAB).append(key2).append(LF);
				ArrayList<ApiParameter> params = pathParams.get(key2);
				for (ApiParameter param : params) {
					ApiParameter global = globals.get(param.getName());					
					sb.append(TAB).append(TAB).append(TAB);
					if ((global != null) && (param.equals(global))) {
						sb.append("=");
					}
					if (param.getDataType().isReference() || param.getDataType().isItemsReference()) {
						sb.append(">");
					}
					if (param.isRequired()) {
						sb.append("*");
					}
					sb.append(param.getName()).append(":").append(param.getDataType()).append((global != null) ? "(global" : "").append(LF);
					sb.append(TAB).append(TAB).append(TAB).append(param.getDescription()).append(LF);
				}				
			}			
		}
		sb.append(API_SEPARATOR).append(LF);
	}


	@Override
	public String getStandardFileExtension() {
		return null;
	}

}
