package com.lyon.future.peppoldirectory.businesscard;

import java.util.Objects;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lyon.future.peppoldirectory.businesscard.common.PeppolDataSource;

@RestController
public class BusinessCardController {
	
	public static final int XML_BASE_INDEX = 0;
	
	public Logger logger = LoggerFactory.getLogger(BusinessCardController.class);
	
	
	@RequestMapping(value = "/businesscard/participant/{icd}/{entNum}", method = RequestMethod.GET)
	public Participant getById(@PathVariable String icd, @PathVariable String entNum) {
		return getById(PeppolDataSource.getPeppolXMLData(), icd, entNum);
	}
	
	
	@RequestMapping(value = "/businesscard/entity/name/search/{searchName}", method = RequestMethod.GET)
	public String searchByName(@PathVariable String searchName) {
		return searchByName(PeppolDataSource.getPeppolXMLData(), searchName);
	}
	
	
	@RequestMapping(value = "/businesscard/entity/name/get/{name}", method = RequestMethod.GET)
	public String getByName(@PathVariable String name) {
		return getByName(PeppolDataSource.getPeppolXMLData(), name);
	}
	
	
	/**
	 * Get BusinessCard using participant value
	 * 
	 * @param peppolDoc
	 * @param icd
	 * @param entNum
	 * @return
	 */
	private Participant getById(Document peppolDoc, String icd, String entNum) {
		
		// Log and return empty JSON
		if (Objects.isNull(peppolDoc)) { 
			logger.error("Error reading Peppol XML file");
			return null;
		}
		
		NodeList nodeList = peppolDoc.getElementsByTagName("businesscard");
		
		for (int i = XML_BASE_INDEX; i < nodeList.getLength(); i++) {

			Node nNode = nodeList.item(i);
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				
				if (((Element) eElement.getElementsByTagName("participant").item(XML_BASE_INDEX)).getAttribute("value").equals(icd+":"+entNum)) {
					Element entityElement = (Element) eElement.getElementsByTagName("entity").item(XML_BASE_INDEX); 
					String countryCode = entityElement.getAttribute("countrycode");
					String name = ((Element) entityElement.getElementsByTagName("name").item(XML_BASE_INDEX)).getAttribute("name");
					return new Participant(icd, entNum, new Entity(countryCode, name));
				}

			}
		}
	
		logger.info("No ID found: " + icd + ":" + entNum);
		return null;
	}
	
	
	/**
	 * Search BusinessCard by name
	 * 
	 * @param peppolDoc
	 * @param searchName
	 * @return
	 */
	private String searchByName(Document peppolDoc, String searchName) {
		
		// Log and return empty JSON
		if (Objects.isNull(peppolDoc)) { 
			logger.error("Error reading Peppol XML file");
			return "{}";
		}
		
		NodeList nodeList = peppolDoc.getElementsByTagName("businesscard");
		
		JSONArray jArr = new JSONArray();
		
		for (int i = XML_BASE_INDEX; i < nodeList.getLength(); i++) {

			Node nNode = nodeList.item(i);
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				Element nameElement = (Element) eElement.getElementsByTagName("name").item(XML_BASE_INDEX);
				String nameStr = nameElement.getAttribute("name").toLowerCase();
				
				String patternString = ".*" + searchName.toLowerCase() + ".*";
		        Pattern pattern = Pattern.compile(patternString);
				
				if ( pattern.matcher(nameStr).matches() ) {
					Element participantElement = (Element) eElement.getElementsByTagName("participant").item(XML_BASE_INDEX); 
					Element entityElement = (Element) eElement.getElementsByTagName("entity").item(XML_BASE_INDEX); 
					String name = nameElement.getAttribute("name");
					String[] participantValArr = participantElement.getAttribute("value").split(":");
					String countryCode = entityElement.getAttribute("countrycode");
					Participant participant = new Participant(participantValArr[0], participantValArr[1], new Entity(countryCode, name));
					JSONObject jObj = new JSONObject();
					jObj.put("Name", participant.getEntity().getName());
					jObj.put("EnterpriseNumber", participant.getEnterpriseNumber());
					jObj.put("CountryCode", participant.getEntity().getCountrycode());
					
					jArr.put(jObj);
				}
			}
		}
		
		logger.info("Found: " + jArr.length() + " item(s)");
		return jArr.toString();
		
	}
	
	
	/**
	 * Get BusinessCard by name
	 * 
	 * @param peppolDoc
	 * @param name
	 * @return
	 */
	private String getByName(Document peppolDoc, String name) {
		
		// Log and return empty JSON
		if (Objects.isNull(peppolDoc)) { 
			logger.error("Error reading Peppol XML file");
			return "{}";
		}
		
		NodeList nodeList = peppolDoc.getElementsByTagName("businesscard");
		
		for (int i = XML_BASE_INDEX; i < nodeList.getLength(); i++) {

			Node nNode = nodeList.item(i);
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				Element nameElement = (Element) eElement.getElementsByTagName("name").item(XML_BASE_INDEX);
				
				if ( nameElement.getAttribute("name").toLowerCase().equals(name.toLowerCase())) {
					Element participantElement = (Element) eElement.getElementsByTagName("participant").item(XML_BASE_INDEX); 
					Element entityElement = (Element) eElement.getElementsByTagName("entity").item(XML_BASE_INDEX); 
					String entityName = nameElement.getAttribute("name");
					String[] participantValArr = participantElement.getAttribute("value").split(":");
					String countryCode = entityElement.getAttribute("countrycode");
					Participant participant = new Participant(participantValArr[0], participantValArr[1], new Entity(countryCode, entityName));
					JSONObject jObj = new JSONObject();
					jObj.put("ICD", participant.getIcd());
					jObj.put("EnterpriseNumber", participant.getEnterpriseNumber());
					jObj.put("Value", participant.getIcd() + ":" + participant.getEnterpriseNumber());
					
					return jObj.toString();
				}

			}
		}
		
		logger.info("No Name found: " + name);
		return null;
	}
	
	
}
