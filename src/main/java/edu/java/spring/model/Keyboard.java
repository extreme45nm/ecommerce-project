package edu.java.spring.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@XmlRootElement
@Entity
@Table(name="keyboard",uniqueConstraints={@UniqueConstraint(columnNames="productId")})
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
	
	
	@XmlElement
	@Column(name="name",nullable=false,length=200)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	@Id
	@Column(name="productId",unique=true,nullable=false)
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@XmlElement
	@Column(name="manufacturerId",nullable=false)
	public int getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	
	@XmlElement
	@Column(name="switchId",nullable=false)
	public int getSwitchId() {
		return switchId;
	}
	public void setSwitchId(int switchId) {
		this.switchId = switchId;
	}
	
	@XmlElement
	@Column(name="profileId",nullable=false)
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	
	@XmlElement
	@Column(name="price",nullable=false)
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
