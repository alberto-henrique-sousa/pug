package com.pugsource.dao;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Class HibernateUtil
 * 
 * Classe responsável pela leitura e configuração das propriedades do Hibernate.
 * 
 * @author Alberto Henrique Sousa
 *
 */
public class HibernateUtil {

	private SessionFactory factory;
	private String properties;
	private String[] ORACLE_SESSION;
	public static boolean DEBUG = false; // monitorar conexões através do console	
		
	public HibernateUtil(String properties) {
		this.properties = properties;
	}

	/**
	 * Faz a leitura e configuração das propriedades do Hibernate
	 */
	private void config() {
		if (factory == null) {
			try {
				this.properties = this.properties == null || this.properties.isEmpty() ? "pug" : this.properties;
				Configuration conf = new Configuration();
				boolean resourceExternal = this.properties.indexOf("\\")> -1;
				File fileCfgXML = null;
				String cfgXML = null;				
				if (resourceExternal) {
					Properties prop = new Properties();
					InputStream input = new FileInputStream(this.properties); 
					prop.load(input);			
					String alias = prop.getProperty("hibernate.file.alias");
					input.close();
					if (!alias.trim().isEmpty())
						alias = "." + alias;
					File propFile = new File(this.properties);
					String pathCfg = propFile.getCanonicalPath();
					pathCfg = pathCfg.replace(propFile.getName(), "hibernate"+alias+".cfg.xml");
					fileCfgXML = new File(pathCfg);
					conf.configure(fileCfgXML);
					conf.getProperties().setProperty("session_factory_name", "");
					System.out.println("======");
					System.out.println("Prop: " + conf.getProperties().getProperty("hibernate.session_factory_name"));
					System.out.println("======");
				} else {	
					ResourceBundle bundle = ResourceBundle.getBundle(this.properties);
					String alias = bundle.getString("hibernate.file.alias");
					if (!alias.trim().isEmpty())
						alias = "." + alias;
					cfgXML = "hibernate"+alias+".cfg.xml";
					conf.configure(cfgXML);
				}	
				DEBUG = Boolean.valueOf(conf.getProperty("hibernate.pug.debug"));
				String oracle_session = conf.getProperty("hibernate.pug.oracle.session");
				if (oracle_session != null && !oracle_session.isEmpty())
					ORACLE_SESSION = oracle_session.split(";");
				final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
						.configure().applySettings(conf.getProperties())
						.build();			
				factory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
	        	if (DEBUG)
	        		factory.getStatistics().setStatisticsEnabled(true);
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
	} 

	/**
	 * Retorna a abertura de uma sessão.
	 * 
	 * @return Retorna uma sessão aberta
	 */
	public Session getSession() {
		config();
		Session session = null;
		try {
        	if (DEBUG)
        		System.out.println("[");
			session = factory.openSession();
	    	if (DEBUG)
        		System.out.println("	New open session, ID.........: " + session.hashCode());
	    	if (ORACLE_SESSION != null) {
	    		for (String oracleSession : ORACLE_SESSION) {
	    			if (!oracleSession.isEmpty()) {
	    				if (DEBUG)
	    					System.out.println("	Oracle session, alter SESSION: " + oracleSession);
	    				session.createSQLQuery("alter SESSION set "+oracleSession).executeUpdate();
	    			}	
	    		}
	    	}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	/**
	 * Retorna a instância do factory.
	 * 
	 * @return Retorna uma instância SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		config();
		return factory;
	}    	
}