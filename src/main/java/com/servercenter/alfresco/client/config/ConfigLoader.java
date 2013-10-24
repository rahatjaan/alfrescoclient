package com.servercenter.alfresco.client.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

	private static final String ALFRESCO_BASE_URL_KEY = "alfresco.base.url";
	private static final String ALFRESCO_USER_KEY = "alfresco.user";
	private static final String ALFRESCO_USER_PASSWORD_KEY = "alfresco.password";
	
	static Properties prop = new Properties();
	
	static{
		try {
			prop.load(ConfigLoader.class.getClassLoader().getResourceAsStream("alfresco/webserviceclient.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties(){
		return prop;
	}
	
	public static String getProperty(String key){		
		return prop.getProperty(key);
	}
	
	public static String getAlfrescoBaseUrl(){		
		return prop.getProperty(ALFRESCO_BASE_URL_KEY);
	}
	public static String getAlfrescoUserName(){		
		return prop.getProperty(ALFRESCO_USER_KEY);
	}
	public static String getAlfrescoPassword(){		
		return prop.getProperty(ALFRESCO_USER_PASSWORD_KEY);
	}
	
}
