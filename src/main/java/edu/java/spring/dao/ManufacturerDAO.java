package edu.java.spring.dao;

import java.util.List;

import edu.java.spring.model.Manufacturer;

public interface ManufacturerDAO {
	
	public Manufacturer loadManufacturer(int manufacturerId);
	
	public List<Manufacturer> listManufacturer();
	
	public void delete(int manufacturerId);
	
	public void insert(final Manufacturer manu);
	
	public void update(Manufacturer newManufacturer);
	
	
}
