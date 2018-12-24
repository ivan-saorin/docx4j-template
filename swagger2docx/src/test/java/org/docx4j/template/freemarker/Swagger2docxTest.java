/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.docx4j.template.freemarker;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.FileTemplateLoader;

public class Swagger2docxTest extends TestDataSource {

	private static Logger logger = LoggerFactory.getLogger(Swagger2docxTest.class);
	
	//protected WordprocessingMLFreemarkerTemplate freemarkerTemplate = null;
	
	@Before
	public void Before() {
		
        variables();
		
        /*
        freemarkerTemplate = new WordprocessingMLFreemarkerTemplate(false );
        
        try {
        	File dirFile = new File(Swagger2docxTest.class.getResource("/tpl/").getPath());
			freemarkerTemplate.setPreTemplateLoaders(new FileTemplateLoader(dirFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        */
	}
	
	@Test
	public void test() throws Exception {
		
		File[] yamls = load();
		
		for (File yaml : yamls) {
			test(yaml);
		}
		
	}
	
	private void test(File yaml) throws Exception {
		if (logger.isWarnEnabled()) logger.warn("loading {}", yaml.getAbsolutePath());
		/*
		SwaggerModel model = SwaggerParser.load(yaml);
		variables.put("model", model);
		variables.put("title", "title");
		variables.put("content", "content");
		
		WordprocessingMLPackage wordMLPackage = freemarkerTemplate.process("freemarker.tpl", variables);
		
		File outputDocx = new File("src/test/resources/output/freemarkerTemplate_output.docx");
		wordMLPackage.save(outputDocx);
		*/
	}

	@After
	public void after() {
		//freemarkerTemplate = null;
	}
	
}
