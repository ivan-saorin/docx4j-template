package org.swaggertooling.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;

public class Asciidoc {

	private Asciidoc() {
	}
	
	public static void commonsHttpErrorsTable(MarkupDocBuilder builder) {
		builder
		.sectionTitleLevel1("Common Http Errors")
		.textLine("This section list common Http Errors that could be emitted by each API any time.");
		List<List<String>> tableCells = new ArrayList<List<String>>();
		tableCells.add(Arrays.asList("*Error Code*", "*Description*"));
		tableCells.add(Arrays.asList("_304_","link:http-errors.html#304-not-modified[Not Modified]"));
		tableCells.add(Arrays.asList("_307_","link:http-errors.html#307-temporary-redirect[Temporary Redirect]"));
		tableCells.add(Arrays.asList("_308_","link:http-errors.html#308-permanent-redirect-experiemental[Permanent Redirect]"));
		tableCells.add(Arrays.asList("_400_","link:http-errors.html#400-bad-request[Bad Request]"));
		tableCells.add(Arrays.asList("_401_","link:http-errors.html#401-unauthorized[Unauthorized]"));
		tableCells.add(Arrays.asList("_403_","link:http-errors.html#403-forbidden[Forbidden]"));
		tableCells.add(Arrays.asList("_405_","link:http-errors.html#405-method-not-allowed[Method Not Allowed]"));
		tableCells.add(Arrays.asList("_406_","link:http-errors.html#406-not-acceptable[Not Acceptable]"));
		tableCells.add(Arrays.asList("_408_","link:http-errors.html#408-request-timeout[Request Timeout]"));
		tableCells.add(Arrays.asList("_415_","link:http-errors.html#415-unsupported-media-type[Unsupported Media Type]"));
		tableCells.add(Arrays.asList("_422_","link:http-errors.html#422-unprocessable-entity-webdav[Unprocessable Entity]"));
		tableCells.add(Arrays.asList("_500_","link:http-errors.html#500-internal-server-error[Internal Server Error]"));
		tableCells.add(Arrays.asList("_503_","link:http-errors.html#503-service-unavailable[Service Unavailable]"));
		builder.table(tableCells);
		builder.newLine();
	}
	
	public static void requestParametersTable(MarkupDocBuilder builder, List<ParamsRow> requestParams) {
		if (requestParams != null && requestParams.size() > 1) {
			builder.sectionTitleLevel3("Request");
			builder.textLine(".Request Parameters");
			//List<List<String>> tableCells = new ArrayList<>();
			builder.textLine("[options=\"header\"]");
			builder.textLine("|===");
			boolean first = true;
			for (ParamsRow row : requestParams) {
				if (first) {
					builder.textLine(row.toHeaderRow());
					//tableCells.add(row.toHeaderCells());
					first = false;
					continue;
				}
				//tableCells.add(row.toCells());
				builder.textLine(row.toRow());
			}
			//builder.table(tableCells);
			builder.textLine("|===");
		}
	}
}
