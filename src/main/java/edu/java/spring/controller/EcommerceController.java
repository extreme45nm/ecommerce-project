package edu.java.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.java.spring.dao.KeyboardDAO;
import edu.java.spring.dao.ManufacturerDAO;
import edu.java.spring.dao.SwitchDAO;
import edu.java.spring.model.Keyboard;

@Controller
public class EcommerceController {
	
	@Autowired
	private KeyboardDAO kbDao;
	
	@Autowired
	private ManufacturerDAO manuDAO;
	
	@Autowired
	private SwitchDAO swDAO;
	
	@RequestMapping(value="/keyboard/images/{productId}",method=RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable int productId,HttpServletRequest request) throws IOException{
		ServletContext servletContext = request.getSession().getServletContext();
		String absoluteDiskpath = servletContext.getRealPath("/");
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		
		File folder = new File(absoluteDiskpath+"/"+"images"+"/");
		if(folder.exists()){
			File file = new File(folder,String.valueOf(productId)+".jpg");
			if(file.exists()){
				FileInputStream stream = new FileInputStream(file);
				int readedBytes;
				byte[] buff = new byte[1024];
				while((readedBytes = stream.read(buff)) >0){
					byteOutput.write(buff,0,readedBytes);					
				}
				stream.close();
			}
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(byteOutput.toByteArray(),headers,HttpStatus.CREATED);				
	}
	
	@RequestMapping(value="/keyboard/images/save",method=RequestMethod.POST)
	public String handleFormUpload(@RequestParam("productId")int productId,
			@RequestParam("file")MultipartFile file,
			HttpServletRequest request) throws IOException{
		
		if(file.isEmpty()){
			return "KeyboardError";
		}else{
			ServletContext servletContext = request.getSession().getServletContext();
			String absoluteDiskPath = servletContext.getRealPath("/");
			
			File folder = new File(absoluteDiskPath+"/"+"images"+"/");
			if(!folder.exists()) folder.mkdirs();
			File avatarFile = new File(folder,String.valueOf(productId)+".jpg");
			if(!avatarFile.exists()) avatarFile.createNewFile();
			
			FileOutputStream outputStream = null;
			try{
				outputStream = new FileOutputStream(avatarFile);
				outputStream.write(file.getBytes());
			}finally{
				if(outputStream != null) outputStream.close();
			}
			return "redirect:/keyboard/view/"+String.valueOf(productId);
		}
	}
	
	@RequestMapping(value="/keyboard/delete/{productId}",method=RequestMethod.GET)
	public ModelAndView deleteKeyboard(@PathVariable Integer productId){
		kbDao.deleteKeyboard(productId);
		ModelAndView mav = new ModelAndView("../KeyboardList");
		mav.addObject("keyboard", kbDao.listKeyboard());
		return mav;
	}
	
	@RequestMapping(value="/keyboard/edit/{productId}",method=RequestMethod.GET)
	public ModelAndView editKeyboard(@PathVariable Integer productId){
		Keyboard keyboard = kbDao.loadKeyBoard(productId);
		ModelAndView mav = new ModelAndView("../KeyboardForm","command",keyboard);
		mav.getModelMap().put("productId",keyboard.getProductId());
		mav.getModelMap().put("name",keyboard.getName());
		mav.getModelMap().put("manufacturerId",keyboard.getManufacturerId());
		mav.getModelMap().put("switchId",keyboard.getSwitchId());
		mav.getModelMap().put("profileId",keyboard.getProfileId());
		mav.getModelMap().put("price",keyboard.getPrice());

		return mav;
	}
	
	@RequestMapping(value="/keyboard/view/{productId}",method=RequestMethod.GET)
	public String loadKeyboard(@PathVariable Integer productId, ModelMap modelMap){
		Keyboard kb = kbDao.loadKeyBoard(productId);
		modelMap.put("productId", kb.getProductId());
		modelMap.put("name", kb.getName());
		modelMap.put("manufacturerId", kb.getManufacturerId());
		modelMap.put("switchId", kb.getSwitchId());
		modelMap.put("profileId", kb.getProfileId());
		modelMap.put("price", kb.getPrice());
		
		return "KeyboardView";
	}
	
	@RequestMapping(value="/keyboard/list",method=RequestMethod.GET)
	public ModelAndView listKeyboard(@RequestParam(value="querry",required=false)String query){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("KeyboardList");
		if(query == "" || query == null){
			mav.addObject("keyboard", kbDao.listKeyboard());
			mav.addObject("manufacturer",manuDAO.listManufacturer());
			mav.addObject("switchh", swDAO.listSwitch());
		}else{
			mav.addObject("keyboard", kbDao.searchKeyboardByName(query));
			mav.addObject("manufacturer",manuDAO.listManufacturer());
			mav.addObject("switchh", swDAO.listSwitch());
		}
		
		return mav;
	}
	
	
	@RequestMapping(value="/keyboard/add",method=RequestMethod.POST)
	public ModelAndView addKeyboard(@Valid@ModelAttribute("command")Keyboard keyboard, BindingResult result){
		
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()){
			System.out.println("bi loi!!");
			mav = new ModelAndView("KeyboardForm","command",keyboard);
			mav.addObject("errors", result);
			return mav;
		}
		
		List<Keyboard> allKeyboard = kbDao.listKeyboard();
		int flag = 0;
		for(Keyboard kb:allKeyboard){
			if(keyboard.getProductId() == kb.getProductId()){
				System.out.println("\n\n EDIT keyboard with productId = "+keyboard.getProductId());
				kbDao.updateKeyboard(keyboard);
				flag = 1;
				break;
			}
		}
		
		if(flag == 0)	kbDao.insertKeyboard(keyboard);
		
		mav = new ModelAndView("redirect:/keyboard/list");
		return mav;
	}
	
	@RequestMapping(path="hello",method=RequestMethod.GET)
	public ModelAndView printMessage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("message","Ban phim co - Mechanical Keyboard");
		return mav;
	}
	
	@RequestMapping(path="keycap",method=RequestMethod.GET)
	public ModelAndView keycap(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("keycap");
		mav.addObject("message","Only for keycap");
		return mav;
	}
	
	@RequestMapping(path="/keyboard/form",method=RequestMethod.GET)
	public ModelAndView keyboard(){
		return new ModelAndView("KeyboardForm","command",new Keyboard());
	}
	
	
	
}
