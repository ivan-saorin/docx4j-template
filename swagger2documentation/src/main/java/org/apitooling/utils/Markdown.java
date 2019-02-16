package org.apitooling.utils;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;

public class Markdown {

	private static Logger logger = LoggerFactory.getLogger(Markdown.class);

    private final static HashMap<String, String> conversionTable = new HashMap<String, String>();
	private static final Options options; 
	static {
		//conversionTable.put("\\n", "");
		conversionTable.put("<p>", "<para>");
		conversionTable.put("</p>", "</para>");
		conversionTable.put("<para><para>", "<para>");
		conversionTable.put("</para></para>", "</para>");
		conversionTable.put("<para></para>", "");
		conversionTable.put("<br>", "</para><para>");
		conversionTable.put("<strong>", "<emphasis role=\"strong\">");
		conversionTable.put("</strong>", "</emphasis>");
		conversionTable.put("<em>", "<emphasis>");
		conversionTable.put("</em>", "</emphasis>");
		/*
		conversionTable.put("~~", "<emphasis role=\"strike\">");
		conversionTable.put("~~", "</emphasis>");
		*/
		
		options = new Options();
		options.setSanitize(true);
		options.setGfm(true);
		options.setBreaks(true);
		options.setTables(true);
		//whitelist	See Options.java	Whitelist of HTML tags.
		//options.setWhitelist(whitelist);
	}
	
	private Markdown() {
	}
	
	/*
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
	*/

	public static Object md2docbook(Object object) {
		String markdown = object.toString();
		//if (logger.isInfoEnabled()) logger.info("md2docbook in: {}", markdown);

		String html = Marked.marked(markdown, options);
		Set<String> keys = conversionTable.keySet();
		for (String key : keys) {
			String replacement = conversionTable.get(key);
			html = html.replaceAll(key, replacement);
		}
		
		//String html = Marked.marked(markdown, options);		
		//if (logger.isInfoEnabled()) logger.info("md2docbook out: {}", html);
		
		return html;
	}
	
	public static String compactWhiteSpace(String string) {
        String nbsp = "&nbsp;";
        
        if(string.length()==0) return "";
        string = string.replaceAll("\\s+", " ").trim();
       
        while(string.indexOf(nbsp) == 0) 
            string = string.substring(nbsp.length());
        while(string.lastIndexOf(nbsp) != -1 && string.lastIndexOf(nbsp) == string.length()-nbsp.length()) 
            string = string.substring(0, string.length()-nbsp.length());
        
        string = string.replaceAll("\\s+", " ").trim();
        if(string.length()!=0) return string;
        else return " "; // keep one whitespace
    }
	
}
