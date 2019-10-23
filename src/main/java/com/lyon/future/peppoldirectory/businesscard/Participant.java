package com.lyon.future.peppoldirectory.businesscard;

public class Participant {
	
	private String icd;
	private String enterpriseNumber;
	private Entity entity;
	
	public Participant() {
		
	}
	
	public Participant(String icd, String enterpriseNumber, Entity entity) {
		super();
		this.icd = icd;
		this.enterpriseNumber = enterpriseNumber;
		this.entity = entity;
	}
	
	public String getIcd() {
		return icd;
	}
	public void setIcd(String icd) {
		this.icd = icd;
	}
	public String getEnterpriseNumber() {
		return enterpriseNumber;
	}
	public void setEnterpriseNumber(String enterpriseNumber) {
		this.enterpriseNumber = enterpriseNumber;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	

}
