package edu.java.spring.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class Keyboard {
	@NotBlank
	@Size(min=5,max=100) 
	private String name;
	
	@Range(min=1)
	private int productId;
	
	@Range(min=1)
	private int manufacturerId;
	
	@Range(min=1)
	private int switchId;
	
	@Range(min=1)
	private int profileId;
	
	@Range(min=1)
	private int price;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public int getSwitchId() {
		return switchId;
	}
	public void setSwitchId(int switchId) {
		this.switchId = switchId;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
