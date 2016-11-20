package edu.java.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.java.spring.dao.SwitchDAO;
import edu.java.spring.model.Switch;

@Controller
public class SwitchController {
	
	@Autowired
	private SwitchDAO swDAO;
	
	
	@RequestMapping(value="switch/form",method=RequestMethod.GET)
	public ModelAndView switchForm(){
		return new ModelAndView("SwitchForm","command",new Switch());
	}
	
	@RequestMapping(value="switch/add",method=RequestMethod.POST)
	public ModelAndView addSwitch(@Valid@ModelAttribute("command")Switch sw,BindingResult result){
		
		ModelAndView model = null;
		if(result.hasErrors()){
			model = new ModelAndView("SwitchForm","command",sw);
			model.addObject("errors",result);
			return model;
		}
		if(sw.getSwitchId() == 0){
			swDAO.insertSwitch(sw);
		}else{
			swDAO.updateSwitch(sw);
		}
		model = new ModelAndView("redirect:/switch/list");
		return model;
	}
	
	@RequestMapping(value="switch/list",method=RequestMethod.GET)
	public ModelAndView listSwitch(){
		
		ModelAndView mav = new ModelAndView("SwitchList");
		mav.addObject("switchh", swDAO.listSwitch());
		return mav;
	}
	
}
