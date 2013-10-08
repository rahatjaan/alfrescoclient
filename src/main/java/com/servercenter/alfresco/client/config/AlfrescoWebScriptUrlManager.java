package com.servercenter.alfresco.client.config;

import java.io.IOException;
import java.util.Properties;

public class AlfrescoWebScriptUrlManager {
	static String loginUrl = null;
	static Properties prop = new Properties();
	private static String uploadUrl;
	private static String siteUrl;
	private static String BASE_URL = "http://localhost:9763/alfresco/";
	
	static{
		try {
			prop.load(ConfigLoader.class.getClassLoader().getResourceAsStream("webscripts.properties"));
//			BASE_URL = ConfigLoader.getAlfrescoBaseUrl();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static String getLoginUrl(){
		if(loginUrl==null)
			loginUrl=ConfigLoader.getAlfrescoBaseUrl()+getAlfrescoLoginUrl();
		loginUrl=loginUrl.replace("{1}", ConfigLoader.getAlfrescoUserName());
		loginUrl=loginUrl.replace("{2}", ConfigLoader.getAlfrescoPassword());
		return loginUrl;
	}
	
	public static String getUploadUrl(){
		if(uploadUrl==null)
			uploadUrl=ConfigLoader.getAlfrescoBaseUrl()+getAlfrescoUploadUrl();
		return uploadUrl;
	}
	
	public static String getSiteUrl(){
		if(siteUrl==null)
			siteUrl=ConfigLoader.getAlfrescoBaseUrl()+getAlfrescoSiteUrl();
		return siteUrl;
	}
	
	private static String getAlfrescoLoginUrl(){		
		return prop.getProperty(ALFRESCO_LOGIN_URL_KEY);
	}
	
	private static String getAlfrescoUploadUrl(){
		return prop.getProperty(ALFRESCO_UPLOAD_URL_KEY);
	}
	
	private static String getAlfrescoSiteUrl(){
		return prop.getProperty(ALFRESCO_SITE_URL_KEY);
	}
	private static final String ALFRESCO_LOGIN_URL_KEY = "alfresco.login.url";
	private static final String ALFRESCO_UPLOAD_URL_KEY = "alfresco.upload.url";
	private static final String ALFRESCO_SITE_URL_KEY = "alfresco.site.url";

}
