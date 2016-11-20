package edu.java.spring.model;

public class Switch {
	
	private int switchId;
	private int manufacturerId;
	private String switchName;
	private int switchForce;
	
	public Switch(){}

	public int getSwitchId() {
		return switchId;
	}

	public void setSwitchId(int switchId) {
		this.switchId = switchId;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String name) {
		this.switchName = name;
	}

	public int getSwitchForce() {
		return switchForce;
	}

	public void setSwitchForce(int switchForce) {
		this.switchForce = switchForce;
	}
}
