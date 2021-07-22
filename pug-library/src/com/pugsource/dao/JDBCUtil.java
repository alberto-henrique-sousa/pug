package com.pugsource.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class JDBCUtil {

	@SuppressWarnings("rawtypes")
	public static Connection Connection(String properties) {
		Connection connection = null;
		try {			
			String alias = "";
			properties = properties == null || properties.isEmpty() ? "pug.properties" : properties;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL path = classLoader.getResource(properties);
			URI uri = path.toURI();
			File f = new File(uri);
			String resource = f.getAbsolutePath();
			if (f.exists()) {
				Properties prop = new Properties();
				prop.load(new FileInputStream(resource));
				alias = prop.getProperty("hibernate.file.alias", "");
				if (!alias.trim().isEmpty()) {
					alias = "." + alias;
				}
			}	
			String hibernateCfg = "hibernate"+alias+".cfg.xml";
			path = classLoader.getResource(hibernateCfg);
			uri = path.toURI();
			f = new File(uri);
			resource = f.getAbsolutePath();
			if (f.exists()) {
				SAXBuilder sb = new SAXBuilder();   
				Document d = sb.build(f);   
				Element data = d.getRootElement();

				List elements = data.getChild("session-factory").getChildren();
				Iterator i = elements.iterator();
				
				String jdbcURL = "";
				String jdbcUser = "";
				String jdbcPass = "";
				while (i.hasNext()) {   
					Element element = (Element) i.next();
					if (element.getAttribute("name") != null) {
						if (element.getAttribute("name").getValue().equals("hibernate.connection.url"))
							jdbcURL = element.getValue();
						else if (element.getAttribute("name").getValue().equals("hibernate.connection.username"))
							jdbcUser = element.getValue();
						else if (element.getAttribute("name").getValue().equals("hibernate.connection.password"))
							jdbcPass = element.getValue();
					}
				}				
				
				if (!jdbcURL.isEmpty()) {
					connection = DriverManager.getConnection(jdbcURL + "?user=" + jdbcUser + "&password=" + jdbcPass);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}	
		return connection;
	}

}
