package edu.java.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.java.spring.dao.ManufacturerDAO;
import edu.java.spring.model.Manufacturer;

@Controller
public class ManufacturerController {

	@Autowired
	private ManufacturerDAO manuDAO;
	
	@RequestMapping(value="manufacturer/form",method=RequestMethod.GET)
	public ModelAndView manufacturerForm(){
		ModelAndView model = new ModelAndView("ManufacturerForm","command",new Manufacturer());
		return model;
	}
	
	@RequestMapping(value="manufacturer/add",method=RequestMethod.POST)
	public ModelAndView addManufacturer(@Valid@ModelAttribute("command")Manufacturer manu,
									BindingResult result){
		ModelAndView model = null;
		if(result.hasErrors()){
			System.out.println("\nErorrorororoo\n");
			model = new ModelAndView("Manufacturer","command",manu);
			model.addObject("errors",result);
			return model;
		}
		
		if(manu.getManufacturerId()==0){
			manuDAO.insert(manu);
		}else{
			manuDAO.update(manu);
		}
		model = new ModelAndView("redirect:/manufacturer/list");
		return model;
	}
	
	@RequestMapping(value="manufacturer/list",method=RequestMethod.GET)
	public ModelAndView listManufacturer(){
		
		ModelAndView mav = new ModelAndView("ManufacturerList");
		mav.addObject("manufacturer",manuDAO.listManufacturer());
		return mav;
	}
	
	
	
}
