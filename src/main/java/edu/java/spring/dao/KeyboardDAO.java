package edu.java.spring.dao;

import java.util.List;

import edu.java.spring.model.Keyboard;

public interface KeyboardDAO {
	public void insertKeyboard(final Keyboard keyboard);
	
	public List<Keyboard> listKeyboard();
	
	public Keyboard loadKeyBoard(int productId);
	
	public void updateKeyboard(Keyboard keyboard);
	
	public void deleteKeyboard(Integer productId);
	
	public void shutdown();
	
	public List<Keyboard> searchKeyboardByName(String keyboardName);
	
	
}
