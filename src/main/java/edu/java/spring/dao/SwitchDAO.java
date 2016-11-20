package edu.java.spring.dao;

import java.util.List;

import edu.java.spring.model.Switch;

public interface SwitchDAO {
	
	public void insertSwitch(final Switch sw);
	
	public Switch loadSwitch(int switchId);
	
	public List<Switch> listSwitch();
	
	public void deleteSwitch(Integer switchId);
	
	public void updateSwitch(Switch sw);
	
}
