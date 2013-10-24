package com.servercenter.alfresco.client.wrapper;

import java.rmi.RemoteException;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

public class FoldersWrapper extends WrapperBase {

	public static void main(String[] args) throws Exception {
		//FOR base folder use thsi
//		new FoldersWrapper().createFolder("david2","david site2",null,WrapperBase.companyHome);
		
		//a2306c22-ed07-410f-b70d-a46b7266db04 david
		//fefc36f8-2fc2-4e6d-9237-a45cc4421735 david 2
		//28d1e2c1-43ef-449b-b0b6-efb691fbc8b5 subfolder1 david2
		Reference ref=new FoldersWrapper().createFolder("sub_folder1","sub_folder1","fefc36f8-2fc2-4e6d-9237-a45cc4421735",null);
		System.out.println("Folder UUID:" + ref.getUuid());

	}

	public Reference createFolder(String folderName,String folderTitle,String parentUuid,String parentPath) {
		try {
			startSession();
		} catch (AuthenticationFault e1) {
			e1.printStackTrace();
		}
		ParentReference parentReference = new ParentReference(STORE, parentUuid,
				parentPath, Constants.ASSOC_CONTAINS,
				Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL,
						folderName));
		// Create folder
		NamedValue[] properties = new NamedValue[] { Utils.createNamedValue(
				Constants.PROP_NAME, folderTitle) };
		CMLCreate create = new CMLCreate("1", parentReference, null, null,
				null, Constants.TYPE_FOLDER, properties);
		CML cml = new CML();
		cml.setCreate(new CMLCreate[] { create });
		UpdateResult[] results;
		Reference newFolder=null;
		try {
			results = WebServiceFactory.getRepositoryService().update(cml);
			newFolder = results[0].getDestination();
		} catch (RepositoryFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			endSession();
		} catch (AuthenticationFault e) {
			e.printStackTrace();
		}
		
		return newFolder;
	}
}
