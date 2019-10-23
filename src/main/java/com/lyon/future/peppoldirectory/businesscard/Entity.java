package com.lyon.future.peppoldirectory.businesscard;

public class Entity {
	
	private String countryCode;
	private String name;
	
	public Entity() {
	}
	
	public Entity(String countrycode, String name) {
		super();
		this.countryCode = countrycode;
		this.name = name;
	}
	
	public String getCountrycode() {
		return countryCode;
	}
	public void setCountrycode(String countrycode) {
		this.countryCode = countrycode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
