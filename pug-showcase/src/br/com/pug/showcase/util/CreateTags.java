package br.com.pug.showcase.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.com.pug.showcase.dao.Component;

public class CreateTags {

	public static void main(String[] args) {

		try {
			LoadTags loadTags = new LoadTags();
			
			File fileComponents = new File("data.js");
			List<String> lines = new ArrayList<String>();
			for (Component component : loadTags.getListComponents()) {
				lines.add("    \""+component.getName()+"\": {");
				lines.add("        attrs: {");
				for (int i = 0; i < component.getAttributes().size(); i++) {
					lines.add("          \""+component.getAttributes().get(i)+"\": null" + (i+1 < component.getAttributes().size() ? "," : ""));					
				}
				lines.add("        }");
				lines.add("    },");
				System.out.println(component.getName());
			}
			FileUtils.writeLines(fileComponents, lines);
			System.out.println(fileComponents.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
