package com.lyon.future.peppoldirectory.businesscard.common;

import java.io.File;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

public class PeppolDataSource {
	
	private static Document peppolDoc;
	
	public static Document getPeppolXMLData() {
		
		// Load previously populated peppol XML Doc.
		// *Comment-out if need to read xml file always to get the updated xml file data
		if (Objects.nonNull(peppolDoc)) {
			return peppolDoc;
		}
		
		try {
			File peppolXMLFile = ResourceUtils.getFile("src/main/resources/peppol/directory-export-business-cards.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(peppolXMLFile);
			doc.getDocumentElement().normalize();
			peppolDoc = doc;
			return peppolDoc;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
