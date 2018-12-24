package org.swagger.model;

import org.jsoup.safety.Whitelist;

import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;

public abstract class SwaggerElement {

	private static Options OPTIONS = new Options();
	
	public SwaggerElement() {
		super();

		/*  Specify options
		 *  gfm	true	Enable GitHub Flavored Markdown.
		 *  tables	true	Enable GFM tables. This option requires the gfm option to be true.
		 *  breaks	false	Enable GFM line breaks. This option requires the gfm option to be true.
		 *  sanitize	false	Ignore any HTML that has been input.
		 *  langPrefix	"lang-"	Prefix of class attribute of code block
		 *  headerPrefix	""	Prefix of id attribute of header
		 *  whitelist	See Options.java	Whitelist of HTML tags.
		 */
		
		OPTIONS.setSanitize(true);
		OPTIONS.setGfm(true);
		OPTIONS.setTables(true);
		OPTIONS.setBreaks(true);
		OPTIONS.setLangPrefix("en");
		OPTIONS.setHeaderPrefix("md-");
		OPTIONS.setWhitelist(Whitelist.basic());
		
	}	
	
	protected String md2html(String md) {
		String marked = Marked.marked(md, OPTIONS);
		//marked = marked.replaceAll("(\r\n|\n\r|\r|\n)", "<br>");
		return marked;
	}
	
}
