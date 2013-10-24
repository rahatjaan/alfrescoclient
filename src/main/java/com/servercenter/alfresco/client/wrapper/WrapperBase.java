package com.servercenter.alfresco.client.wrapper;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;

import com.servercenter.alfresco.client.config.ConfigLoader;

public class WrapperBase {
	
	 protected static String USERNAME = "admin";
	 protected static String PASSWORD = "admin";
	    
	    /** The store used throughout the samples */
	 protected static final Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
	 protected static final String companyHome = "/app:company_home";
	   
	 
	
	private static boolean loadedCredentials = false; 
	public void startSession() throws AuthenticationFault{
		if(!loadedCredentials)
			loadCredentials();
	    AuthenticationUtils.startSession(USERNAME, PASSWORD);
	 }
	
	public void endSession() throws AuthenticationFault{
		if(!loadedCredentials)
			loadCredentials();
	    AuthenticationUtils.startSession(USERNAME, PASSWORD);
	 }
	private static void loadCredentials(){
		USERNAME = ConfigLoader.getAlfrescoUserName();
	   	PASSWORD = ConfigLoader.getAlfrescoPassword();
	   	loadedCredentials=true;
	}
}
