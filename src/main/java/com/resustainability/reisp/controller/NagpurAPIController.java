package com.resustainability.reisp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resustainability.reisp.model.BrainBox;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.SBU;
import com.resustainability.reisp.service.BMWService;

@RestController
@RequestMapping("/reone")
public class NagpurAPIController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    } 
	Logger logger = Logger.getLogger(NagpurAPIController.class);
	   
	@Autowired
	BMWService service;
	
	@RequestMapping(value = "/getNagpurList", method = {RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getNagpurList(@RequestHeader("Authorization") String authentication, @RequestBody DashBoardWeighBridge obj1,BrainBox obj,HttpSession session,HttpServletResponse response , Errors filterErrors) throws JsonProcessingException {
		List<BrainBox> companiesList = null;
		 String json = null;
		 boolean flag = false;
		 boolean call_service = true;
		 boolean log = true;
		 int logInfo = 0;
		 HashMap<String, String> data = new HashMap<String, String>();
		 ObjectMapper objectMapper = new ObjectMapper();
		try {
			if(!StringUtils.isEmpty(obj1.getFrom_date())) {
				obj1.setFrom_date(obj1.getFrom_date());
			}
			 String user_id1 = "rechwbhingpr";
			 String password1 = "Y1298extvbddyzB";
			 String pair=new String(Base64.decodeBase64(authentication.substring(6)));
		     String userName=pair.split(":")[0];
		     String password=pair.split(":")[1];
		     obj1.setUser_id(userName);
		     obj1.setPassword(password);
		
			 if(flag) {
				 if(!user_id1.contentEquals(obj1.getUser_id()) || !password1.contentEquals(obj1.getPassword())) {
					 call_service = false;
					 data = new HashMap<String, String>();
					 data.put("200","User Name or Password Incorrect!");
					 json = objectMapper.writeValueAsString(data);
					 obj1.setMSG("User Name or Password Incorrect!");
				 }
				 else if(StringUtils.isEmpty(obj1.getFrom_date())) {
					 call_service = false;
					 data = new HashMap<String, String>();
					 data.put("200","Date not mentioned! Please mention this format : from_date : { m/d/yyyy }");
					 json = objectMapper.writeValueAsString(data);
					 obj1.setMSG("Date not mentioned!");
				 }
				 if(call_service) {
					 companiesList = service.getNagpurCNDList(obj1,obj,response);
					 logInfo = service.getLogInfo(obj1,obj,companiesList);
					 if(companiesList.size() > 0 && logInfo == 0 ){
						 json = objectMapper.writeValueAsString(companiesList);
						 obj1.setMSG(companiesList.size()+" Data synched");
						 obj1.setPTC_status("Y");
						 log = true;
					 }else if(companiesList.size() > 0 &&  logInfo == 0 && !StringUtils.isEmpty(obj1.getRepulled()) && "Yes".equalsIgnoreCase(obj1.getRepulled()) ){
						 json = objectMapper.writeValueAsString(companiesList);
						 obj1.setMSG(companiesList.size()+" Data synched");
						 obj1.setPTC_status("Y");
						 log = true;
					 }else if(companiesList.size() > 0 &&  logInfo > 0 && !StringUtils.isEmpty(obj1.getRepulled()) && "Yes".equalsIgnoreCase(obj1.getRepulled()) ){
						 json = objectMapper.writeValueAsString(companiesList);
						 obj1.setMSG(companiesList.size()+" Data synched");
						 obj1.setPTC_status("Y");
						 log = true;
					 }else if(companiesList.size() > 0 &&  logInfo > 0 && !StringUtils.isEmpty(obj1.getRepulled()) && "No".equalsIgnoreCase(obj1.getRepulled()) ){
						 data = new HashMap<String, String>();
						 data.put("200","Data Already pulled before! If you want to pull again Change header (repulled : Yes)");
						 json = objectMapper.writeValueAsString(data);
						 log = false;
					 }else if(companiesList.size() > 0 &&  logInfo > 0 && StringUtils.isEmpty(obj1.getRepulled()) ){
						 data = new HashMap<String, String>();
						 data.put("200","Data Already pulled before! If you want to pull again, Add header (repulled : Yes)");
						 json = objectMapper.writeValueAsString(data);
						 log = false;
					
					 }else {
						 companiesList = new ArrayList<BrainBox>(1);
						 data = new HashMap<String, String>();
						 data.put("200", "No New Records are Available For the Selected Date! Data Already pulled before! If you want to pull again, Add header (repulled : Yes)");
							 json = objectMapper.writeValueAsString(data);
							 obj1.setMSG("No New Records are Available For the Selected Date! Data Already pulled before! If you want to pull again, Add header (repulled : Yes)");
					  }
				 }else {
					 companiesList = new ArrayList<BrainBox>(1);
				 }
				
			 }else {
				 data = new HashMap<String, String>();
			     json = objectMapper.writeValueAsString(data);
			     obj1.setMSG("No Access for this IP Address");
			     companiesList = new ArrayList<BrainBox>(1);
			 }
			 if(log) {service.getLogsOfResults(companiesList,obj1);}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if("Index 0 out of bounds for length 0".contentEquals(e.getMessage())) {
				data = new HashMap<String, String>();
				 data.put("200","Please enter User Name and Password!");
				json = objectMapper.writeValueAsString(data);
			}else {
				data = new HashMap<String, String>();
				data.put("200","Internal Error! Please contact Support");
				json = objectMapper.writeValueAsString(data);
			}
			logger.error("getNagpurList : " + e.getMessage());
		}
		return json;
	}
	
}
