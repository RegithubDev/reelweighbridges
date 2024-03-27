package com.resustainability.reisp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resustainability.reisp.model.BMW;
import com.resustainability.reisp.model.BrainBox;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.service.BMWService;

@RestController
@RequestMapping("/reone")
public class BMWSummeryDataCOntroller {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    } 
	Logger logger = Logger.getLogger(BMWSummeryDataCOntroller.class);
	
	@Autowired
	BMWService service;
	
	
	
	@RequestMapping(value = "/push-bmw-summery-data", method = {RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@JsonProperty
	public String pushBMWList(  @RequestBody BMW obja, BMW obj1,BrainBox obj,HttpSession session,HttpServletResponse response , Errors filterErrors) throws JsonProcessingException {
		List<BrainBox> companiesList = null;
		 String json = null;
		 HashMap<String, String> data = new HashMap<String, String>();
		 ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			 if(true ) {
				 String  ids = "";
				 LocalDate currentDate = LocalDate.now();
			     String date = currentDate.toString();
			     date = date.replaceAll("-", "");
			     String unique = date;
				 String id = null;
				 boolean handsUp = true;
				    // int count = service.getCountOfBMWSummeryRecords(bmw);
				     DashBoardWeighBridge  DB = new DashBoardWeighBridge();
				     if(handsUp) {
				    	 List<DashBoardWeighBridge> objsList = service.getTransactionsSummeryList(obj1,DB);
						
							 data = new HashMap<String, String>();
							 data.put("Success", objsList+"");
							json = objectMapper.writeValueAsString(data);
				     }else {
					 companiesList = new ArrayList<BrainBox>(1);
				 }
			 }else {
				 data = new HashMap<String, String>();
			     companiesList = new ArrayList<BrainBox>(1);
			 }
		  
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if("Index 0 out of bounds for length 0".contentEquals(e.getMessage())) {
				data = new HashMap<String, String>();
				 data.put("500","Please enter User Name and Password!");
				json = objectMapper.writeValueAsString(data);
			}else if("Conversion failed when converting the".contentEquals(e.getMessage())){
				data = new HashMap<String, String>();
				data.put("500",""+e.getMessage()+"");
				json = objectMapper.writeValueAsString(data);
			}
			else {
				data = new HashMap<String, String>();
				data.put("500",getStackTraceAsString(e));
				json = objectMapper.writeValueAsString(data);
			}
			logger.error("getBMWList : " + e.getMessage());
		}
		return json;
	}
	
	public static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
	
}
