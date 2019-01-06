package org.apitooling.utils;

import org.junit.Assert;
import org.junit.Test;

public class JsonUtilsTest {

	private static final String yamlExample="{consentStatus=received, consentId=1234-wertiq-983, _links={scaRedirect={href=<a href=\"https://www.testbank.com/authentication/1234-wertiq-983\">https://www.testbank.com/authentication/1234-wertiq-983</a>}}}";
	private static final String expectedJson="{\"consentStatus\":\"received\", \"consentId\":\"1234-wertiq-983\", \"_links\":{\"scaRedirect\":{\"href\":\"href=<a href=\\\"https://www.testbank.com/authentication/1234-wertiq-983\\\">https://www.testbank.com/authentication/1234-wertiq-983</a>\"}}}";
	private static final String expectedDocbook="<para>{</para><para>&nbsp;&nbsp;&nbsp;&nbsp;\"consentStatus\":\"received\", </para><para>&nbsp;&nbsp;&nbsp;&nbsp;\"consentId\":\"1234-wertiq-983\", </para><para>&nbsp;&nbsp;&nbsp;&nbsp;\"_links\":</para><para>&nbsp;&nbsp;&nbsp;&nbsp;{</para><para>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"scaRedirect\":</para><para>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{</para><para>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"href\":\"href=&lt;a href=\\\"https://www.testbank.com/authentication/1234-wertiq-983\\\"&gt;https://www.testbank.com/authentication/1234-wertiq-983&lt;/a&gt;\"</para><para>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}</para><para>&nbsp;&nbsp;&nbsp;&nbsp;}</para><para>}</para>";

	@Test
	public void testYamlJsonConversion() {
		String json = JsonUtils.ex2json(yamlExample);
		System.err.println(json);
		System.err.println("");
		Assert.assertEquals(expectedJson, json);
		json = JsonUtils.prettyJSON(json);
		System.err.println(json);		
	}

	@Test
	public void testYamlDocBookConversion() {
		String docbook = JsonUtils.ex2docbook(yamlExample);
		System.err.println(docbook);
		System.err.println("");
		Assert.assertEquals(expectedDocbook, docbook);				
	}

}
