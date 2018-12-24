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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TestDataSource {
	
	protected Map<String, Object> variables = new HashMap<String, Object>();
	
	public File[] load() {
		String path = TestDataSource.class.getResource("/api/").getPath();
		File api = new File(path);
		File[] files = api.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".yaml");
			}
			
		});
		return files;
	}
	
	public void variables() {
		
		int count = 30;
        long step = 60 * 1000;
        long time = new Date().getTime() - count * step;
        List<Map<String, Object>> models = new ArrayList<Map<String, Object>>(count);
        Map<String, Object> model = null;
        for (int i = 0; i < count; i++) {
            model = new HashMap<String, Object>();
            model.put("code", 1000 + i);
            model.put("name", "\u6D4B\u8BD5\u6A21\u578B-" + i);
            model.put("date", new Date(time + i * step));
            model.put("bool", (i & 1) == 0);
            model.put("value", i * 1.3d);
            models.add(model);
        }
        variables.put("models", models);
        String source="UTF-8", target = "UTF-8";
        variables.put("source", source);
        variables.put("target", target);
        variables.put("format", false);
        
	}
	
}
