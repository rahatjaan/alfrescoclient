package com.servercenter.alfresco.client.wrapper;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import com.servercenter.alfresco.client.config.AlfrescoWebScriptUrlManager;

public class UploadFileWrapper {

	public static void main(String[] args) throws IOException{
		// you can first call login service to get this token and then use onward web calls using same token
//		String loginUrl = AlfrescoWebScriptUrlManager.getLoginUrl();
//		WebTarget target = client.target(loginUrl);
//		Response response = target.request(MediaType.TEXT_HTML).get();
//		String xmlResponse = response.readEntity(String.class);
//		System.out.println(xmlResponse);
		File file=new File("C:\\Shakeel\\Important Work\\Rahat Bhai's Work\\Shakeel.xls");
		String alf_ticket = "TICKET_67e9a9bd4b46dce8692be618161e7a93b3f2c8e3";// this is login ticket for authentication
		UploadFileWrapper wrapper = new UploadFileWrapper();
		//shakeel in this case is the sub folder in the site
		wrapper.uploadFile(file, alf_ticket,"shakeel","shakeel","application/xml","1.0","yes");
		// elixir is site id which does not exist in alfresco right now
//		wrapper.uploadFile(file, alf_ticket,"elixir","CompanyHome","application/xml","1.0","yes");
	}
	
	public boolean uploadFile(File file,String alf_ticket,String siteId,String containerId,String contentType,String majorVersion,String overrite){
		try {
			Client client = ClientBuilder.newBuilder()
		            .register(MultiPartFeature.class)
		            .build();
			WebTarget target = client.target(AlfrescoWebScriptUrlManager.getUploadUrl()+"?alf_ticket="+alf_ticket);
			Response response;
			final FileDataBodyPart filePart = new FileDataBodyPart("filedata",file);
			FormDataMultiPart form = new FormDataMultiPart();
			form.field("siteid",siteId);
			form.field("containerid",containerId);
			form.field("description","file description");
			form.field("contenttype",contentType);
			form.field("majorversion",majorVersion);
			form.field("overrite",overrite);
			form.field("thumbnails", "yes");
			form.bodyPart(filePart);
			response = target.request().post(Entity.entity(form, form.getMediaType()));
			System.out.println(response.getStatus());
			System.out.println(response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		  }		
		return false;

	}
}

