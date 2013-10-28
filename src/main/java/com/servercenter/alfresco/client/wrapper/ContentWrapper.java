package com.servercenter.alfresco.client.wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

public class ContentWrapper extends WrapperBase{

	public static void main(String []args) throws Exception{
		ContentWrapper contentWrapper = new ContentWrapper();
//		InputStream viewStream = contentWrapper.getClass().getClassLoader().getResourceAsStream("org/alfresco/webservice/test/resources/test.jpg");
//		Reference contentRef = contentWrapper.createContent(viewStream,"rahat_content","28d1e2c1-43ef-449b-b0b6-efb691fbc8b5","image/jpeg");
//		System.out.println("Content uuid:"+contentRef.getUuid());
		File f = new File("d://rahat2.txt");
		Reference contentRef = contentWrapper.createContent(f,"28d1e2c1-43ef-449b-b0b6-efb691fbc8b5","txt");
		System.out.println("Content uuid:"+contentRef.getUuid());
		Content content = contentWrapper.getContent(contentRef.getUuid(), null);
		System.out.println("Content Received:"+ContentUtils.getContentAsString(content));
		InputStream stream = ContentUtils.getContentAsInputStream(content);
		OutputStream output= new FileOutputStream(new File("d://rahat_output.txt"));
		int read = 0;
		byte[] bytes = new byte[1024];
 
		while ((read = stream.read(bytes)) != -1) {
			output.write(bytes, 0, read);
		}
		stream.close();
		output.close();
	}

	public Reference createContent(File input,String parentUuid,String mimeType) throws IOException {
		InputStream is = new FileInputStream(input);
//		mimeType = URLConnection.guessContentTypeFromStream(is);
//		System.out.println("Mime Type:"+mimeType);

		return createContent(is,input.getName(),parentUuid,mimeType);
//		return null;
	}
	
	public Content getContent(String contentUuid,String path) throws ContentFault, RemoteException{
		try {
			startSession();
		} catch (AuthenticationFault e1) {
			e1.printStackTrace();
		}
		ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
		 Reference newContentReference = new Reference(STORE, contentUuid, path);
		Content[] readResult2 = contentService.read(
                 new Predicate(new Reference[]{newContentReference}, STORE, null), 
                 Constants.PROP_CONTENT);
		 Content content = readResult2[0];
		 endSession();
		 return content;
	}
	public Reference createContent(InputStream input,String contentName,String parentUuid,String mimeType) throws RepositoryFault, RemoteException {
		try {
			startSession();
		} catch (AuthenticationFault e1) {
			e1.printStackTrace();
		}
		 ParentReference parentReference = new ParentReference(
                 STORE,
                 parentUuid,
                 null,
                 Constants.ASSOC_CONTAINS, 
                 Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, contentName));
		// Create the content
		
        NamedValue[] properties = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, contentName)};
        CMLCreate create = new CMLCreate("1", parentReference, null, null, null, Constants.TYPE_CONTENT, properties);
        CML cml = new CML();
        cml.setCreate(new CMLCreate[]{create});
        UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);     
        
        // Get the created node and create the format
        Reference newContentNode = result[0].getDestination();       
        
        ContentFormat format = new ContentFormat(mimeType, "UTF-8");  
        
        // Open the file and convert to byte array
        
        byte[] bytes;
		try {
			bytes = ContentUtils.convertToByteArray(input);
			Content content = WebServiceFactory.getContentService().write(newContentNode, Constants.PROP_CONTENT, bytes, format);
			return content.getNode();
		} catch (Exception e) {
			e.printStackTrace();
		}
        // Write the content
		endSession();
        return null;
		
	}
}
