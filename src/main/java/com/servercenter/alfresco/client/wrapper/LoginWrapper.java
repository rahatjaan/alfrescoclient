package com.servercenter.alfresco.client.wrapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.servercenter.alfresco.client.config.AlfrescoWebScriptUrlManager;


public class LoginWrapper {

	 public static void main(String[] args) {
			try {
				Client client = ClientBuilder.newClient();
				String loginUrl = AlfrescoWebScriptUrlManager.getLoginUrl();
				System.out.println("Login URL:"+loginUrl);
				WebTarget target = client.target(loginUrl);
				Response response = target.request(MediaType.APPLICATION_XML_TYPE).get();
				String xmlResponse = response.readEntity(String.class);
				System.out.println(xmlResponse);
			} catch (Exception e) {
				e.printStackTrace();
			  }
		 
			}
}
