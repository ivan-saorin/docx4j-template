package org.apitooling.utils;

import net.steppschuh.markdowngenerator.MarkdownBuilder;
import net.steppschuh.markdowngenerator.link.Link;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.emphasis.ItalicText;

public class Markdown {

	private Markdown() {
	}
	
	public static void commonsHttpErrorsTable(MarkdownBuilder builder) {
		builder
		.heading("Common Http Errors")
		.newParagraph()
		.append("This section list common Http Errors that could be emitted by each API any time.")
		.newLines(2);
		
		Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Error Code", "Description");

        tableBuilder.addRow(new ItalicText("304"), new Link("Not Modified", "http-errors.html#304-not-modified"));
        tableBuilder.addRow(new ItalicText("307"), new Link("Temporary Redirect", "http-errors.html#307-temporary-redirect"));
        tableBuilder.addRow(new ItalicText("308"), new Link("Permanent Redirect", "http-errors.html#308-permanent-redirect-experiemental"));
        tableBuilder.addRow(new ItalicText("400"), new Link("Bad Request", "http-errors.html#400-bad-request"));
        tableBuilder.addRow(new ItalicText("401"), new Link("Unauthorized", "http-errors.html#401-unauthorized"));
        tableBuilder.addRow(new ItalicText("403"), new Link("Forbidden", "http-errors.html#403-forbidden"));
        tableBuilder.addRow(new ItalicText("405"), new Link("Method Not Allowed", "http-errors.html#405-method-not-allowed"));
        tableBuilder.addRow(new ItalicText("406"), new Link("Not Acceptable", "http-errors.html#406-not-acceptable"));
        tableBuilder.addRow(new ItalicText("408"), new Link("Request Timeout", "http-errors.html#408-request-timeout"));
        tableBuilder.addRow(new ItalicText("415"), new Link("Unsupported Media Type", "http-errors.html#415-unsupported-media-type"));
        tableBuilder.addRow(new ItalicText("422"), new Link("Unprocessable Entity", "http-errors.html#422-unprocessable-entity-webdav"));
        tableBuilder.addRow(new ItalicText("500"), new Link("Internal Server Error", "http-errors.html#500-internal-server-error"));
        tableBuilder.addRow(new ItalicText("503"), new Link("Service Unavailable", "http-errors.html#503-service-unavailable"));
		builder.newLine();
		builder.append(tableBuilder.build());
	}
	
}
