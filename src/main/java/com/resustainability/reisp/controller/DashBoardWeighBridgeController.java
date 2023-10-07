package com.resustainability.reisp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resustainability.reisp.constants.PageConstants;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.User;
import com.resustainability.reisp.service.DashBoardWeighBridgeService;

@Controller
public class DashBoardWeighBridgeController {
	
	@InitBinder 
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
	Logger logger = Logger.getLogger(DashBoardWeighBridgeController.class);
	
	@Autowired
	DashBoardWeighBridgeService service;
	

	@Value("${common.error.message}")
	public String commonError;
	
	@Value("${record.dataexport.success}")
	public String dataExportSucess;
	
	@Value("${record.dataexport.invalid.directory}")
	public String dataExportInvalid;
	
	@Value("${record.dataexport.error}")
	public String dataExportError;
	
	@Value("${record.dataexport.nodata}")
	public String dataExportNoData;
	
	@Value("${template.upload.common.error}")
	public String uploadCommonError;
	
	@Value("${template.upload.formatError}")
	public String uploadformatError;
	
	
	@RequestMapping(value = "/dashboard-home", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView dashboardhome(@ModelAttribute User user,DashBoardWeighBridge obj, HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.home);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			 List <DashBoardWeighBridge> trasactionsSumLists = service.getTransactionsList1(obj);
				model.addObject("trasactionsSumLists", trasactionsSumLists);
			List <DashBoardWeighBridge> wbList = service.getWeighBridgeList(obj);
			model.addObject("wbList", wbList);
			
			if(StringUtils.isEmpty(obj.getSbu())) {
				obj.setSbu("MSW");
			}
		  
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	
	@RequestMapping(value = "/dashboard-wb", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView dashboard(@ModelAttribute User user,DashBoardWeighBridge obj, HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.MSWdashboard);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			List <DashBoardWeighBridge> sbuList = service.getSBUsList(obj);
			model.addObject("sbuList", sbuList);
			
			if(StringUtils.isEmpty(obj.getSbu())) {
				obj.setSbu("MSW");
			}
			List <DashBoardWeighBridge> sitesList = service.getDashboards(obj);
			model.addObject("sitesList", sitesList);
		  
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	@RequestMapping(value = "/dashboard-wb-cnd", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView dashboardCnD(@ModelAttribute User user,DashBoardWeighBridge obj, HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.CNDdashboard);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			List <DashBoardWeighBridge> sbuList = service.getSBUsList(obj);
			model.addObject("sbuList", sbuList);
			
			if(StringUtils.isEmpty(obj.getSbu())) {
				obj.setSbu("CND");
			}
			List <DashBoardWeighBridge> sitesList = service.getDashboards(obj);
			model.addObject("sitesList", sitesList);
		  
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	@RequestMapping(value = "/dashboard-wb-bmw", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView dashboardBMW(@ModelAttribute User user,DashBoardWeighBridge obj, HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.BMWdashboard);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			List <DashBoardWeighBridge> sbuList = service.getSBUsList(obj);
			model.addObject("sbuList", sbuList);
			
			if(StringUtils.isEmpty(obj.getSbu())) {
				obj.setSbu("BMW");
			}
			List <DashBoardWeighBridge> sitesList = service.getDashboards(obj);
			model.addObject("sitesList", sitesList);
		  
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	
	@RequestMapping(value = "/dashboard-wb-daily", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView dashboardDAily(@ModelAttribute User user,DashBoardWeighBridge obj, HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.dailyWeighBridge);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			List <DashBoardWeighBridge> sitesList = service.getDashboardsDaily(obj);
			model.addObject("sitesList", sitesList);
		  
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	
	@RequestMapping(value = "/ajax/getDatawithSBU", method = {RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DashBoardWeighBridge> getDatawithSBU(@ModelAttribute DashBoardWeighBridge obj,HttpSession session) {
		List<DashBoardWeighBridge> sitesList = null;
		String userId = null;
		String userName = null;
		String role = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			sitesList = service.getDashboards(obj);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("getDatawithSBU : " + e.getMessage());
		}
		return sitesList;
	}
	
	@RequestMapping(value = "/report/{sbu}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView mswReport(@ModelAttribute User user,DashBoardWeighBridge obj,@PathVariable("sbu") String sbu , HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.MSWReports);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			obj.setSbu(sbu);
			
			if(sbu.equals("BMW")) {
				model = new ModelAndView(PageConstants.BMWReports);
				List <DashBoardWeighBridge> trasactionsList = service.getTransactionsList1(obj);
				model.addObject("trasactionsList", trasactionsList);
				List <DashBoardWeighBridge> monthList = service.getMonthList(obj);
				model.addObject("monthList", monthList);
				
				List <DashBoardWeighBridge> customerCodeList = service.getCustomerCodeList(obj);
				model.addObject("customerCodeList", customerCodeList);
				
				
			}else {
				List <DashBoardWeighBridge> projectsList = service.getProjectssList(obj);
				model.addObject("projectsList", projectsList);
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	
	@RequestMapping(value = "/ajax/getMSWDataWithSiteID", method = {RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DashBoardWeighBridge> getMSWDataWithSiteID(@ModelAttribute DashBoardWeighBridge obj,HttpSession session) {
		List<DashBoardWeighBridge> sitesList = null;
		String userId = null;
		String userName = null;
		String role = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			sitesList = service.getMSWDataWithSiteID(obj);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("getMSWDataWithSiteID : " + e.getMessage());
		}
		return sitesList;
	}
	
	@RequestMapping(value = "/ajax/getCNDDataWithSiteID", method = {RequestMethod.GET,RequestMethod.POST},produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DashBoardWeighBridge> getCNDDataWithSiteID(@ModelAttribute DashBoardWeighBridge obj,HttpSession session) {
		List<DashBoardWeighBridge> sitesList = null;
		String userId = null;
		String userName = null;
		String role = null;
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			sitesList = service.getCNDDataWithSiteID(obj);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("getCNDDataWithSiteID : " + e.getMessage());
		}
		return sitesList;
	}
	

	@RequestMapping(value = "/export-project-data", method = {RequestMethod.GET,RequestMethod.POST})
	public void exportProjectData(HttpServletRequest request, HttpServletResponse response,HttpSession session,@ModelAttribute DashBoardWeighBridge obj,RedirectAttributes attributes){
		List<DashBoardWeighBridge> dataList = new ArrayList<DashBoardWeighBridge>();
		String userId = null;String userName = null;
		try {
			userId = (String) session.getAttribute("USER_ID");userName = (String) session.getAttribute("USER_NAME");
			dataList = service.getMSWDataWithSiteID(obj); 
			if(dataList != null && dataList.size() > 0){
	            XSSFWorkbook  workBook = new XSSFWorkbook ();
	            XSSFSheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(obj.getProject()));
		        workBook.setSheetOrder(sheet.getSheetName(), 0);
		        
		        byte[] blueRGB = new byte[]{(byte)0, (byte)176, (byte)240};
		        byte[] yellowRGB = new byte[]{(byte)255, (byte)192, (byte)0};
		        byte[] greenRGB = new byte[]{(byte)146, (byte)208, (byte)80};
		        byte[] redRGB = new byte[]{(byte)255, (byte)0, (byte)0};
		        byte[] whiteRGB = new byte[]{(byte)255, (byte)255, (byte)255};
		        
		        boolean isWrapText = true;boolean isBoldText = true;boolean isItalicText = false; int fontSize = 11;String fontName = "Times New Roman";
		        CellStyle blueStyle = cellFormating(workBook,blueRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle yellowStyle = cellFormating(workBook,yellowRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle greenStyle = cellFormating(workBook,greenRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle redStyle = cellFormating(workBook,redRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle whiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        CellStyle indexWhiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        isWrapText = true;isBoldText = false;isItalicText = false; fontSize = 9;fontName = "Times New Roman";
		        CellStyle sectionStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        
	            XSSFRow headingRow = sheet.createRow(0);
	        	String headerString = "TRNO,	VEHICLE NUMBER,	DATE IN,	TIME IN,	DATE OUT,	TIME OUT,	FIRST WEIGHT,	SECOND WEIGHT,	NET WEIGHT, CONTAINER ID" + 
	    				"";
	            String[] firstHeaderStringArr = headerString.split("\\,");
	            
	            for (int i = 0; i < firstHeaderStringArr.length; i++) {		        	
		        	Cell cell = headingRow.createCell(i);
			        cell.setCellStyle(greenStyle);
					cell.setCellValue(firstHeaderStringArr[i]);
				}
	            
	            short rowNo = 1;
	            for (DashBoardWeighBridge obj1 : dataList) {
	                XSSFRow row = sheet.createRow(rowNo);
	                int c = 0;
	            
	                Cell cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue(obj1.getTRNO());
					
	                cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getVEHICLENO());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getDATEIN());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTIMEIN());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getDATEOUT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTIMEOUT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getFIRSTWEIGHT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSECONDWEIGHT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getFIRSTWEIGHT());  
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCONTAINERID());
					
	                rowNo++;
	            }
	            for(int columnIndex = 0; columnIndex < firstHeaderStringArr.length; columnIndex++) {
	        		sheet.setColumnWidth(columnIndex, 25 * 170);
				}
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                Date date = new Date();
                String fileName = obj.getProject()+dateFormat.format(date);
                
	            try{
	                /*FileOutputStream fos = new FileOutputStream(fileDirectory +fileName+".xls");
	                workBook.write(fos);
	                fos.flush();*/
	            	
	               response.setContentType("application/.csv");
	 			   response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	 			   response.setContentType("application/vnd.ms-excel");
	 			   // add response header
	 			   response.addHeader("Content-Disposition", "attachment; filename=" + fileName+".xlsx");
	 			   
	 			    //copies all bytes from a file to an output stream
	 			   workBook.write(response.getOutputStream()); // Write workbook to response.
		           workBook.close();
	 			    //flushes output stream
	 			    response.getOutputStream().flush();
	            	
	                
	                attributes.addFlashAttribute("success",dataExportSucess);
	            	//response.setContentType("application/vnd.ms-excel");
	            	//response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
	            	//XSSFWorkbook  workbook = new XSSFWorkbook ();
	            	// ...
	            	// Now populate workbook the usual way.
	            	// ...
	            	//workbook.write(response.getOutputStream()); // Write workbook to response.
	            	//workbook.close();
	            }catch(FileNotFoundException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportInvalid);
	            }catch(IOException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportError);
	            }
         }else{
        	 response.sendError(313);
        	 attributes.addFlashAttribute("error",dataExportNoData);
         }
		}catch(Exception e){	
			e.printStackTrace();
			logger.error("exportCompany : : User Id - "+userId+" - User Name - "+userName+" - "+e.getMessage());
			attributes.addFlashAttribute("error", commonError);			
		}
		//return view;
	}
	
	@RequestMapping(value = "/export-project-manual-data", method = {RequestMethod.GET,RequestMethod.POST})
	public void exportProjectManualData(HttpServletRequest request, HttpServletResponse response,HttpSession session,@ModelAttribute DashBoardWeighBridge obj,RedirectAttributes attributes){
		List<DashBoardWeighBridge> dataList = new ArrayList<DashBoardWeighBridge>();
		String userId = null;String userName = null;
		try {
			userId = (String) session.getAttribute("USER_ID");userName = (String) session.getAttribute("USER_NAME");
			dataList = service.getMSWManualDataWithSiteID(obj); 
			if(dataList != null && dataList.size() > 0){
	            XSSFWorkbook  workBook = new XSSFWorkbook ();
	            XSSFSheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(obj.getProject()));
		        workBook.setSheetOrder(sheet.getSheetName(), 0);
		        
		        byte[] blueRGB = new byte[]{(byte)0, (byte)176, (byte)240};
		        byte[] yellowRGB = new byte[]{(byte)255, (byte)192, (byte)0};
		        byte[] greenRGB = new byte[]{(byte)146, (byte)208, (byte)80};
		        byte[] redRGB = new byte[]{(byte)255, (byte)0, (byte)0};
		        byte[] whiteRGB = new byte[]{(byte)255, (byte)255, (byte)255};
		        
		        boolean isWrapText = true;boolean isBoldText = true;boolean isItalicText = false; int fontSize = 11;String fontName = "Times New Roman";
		        CellStyle blueStyle = cellFormating(workBook,blueRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle yellowStyle = cellFormating(workBook,yellowRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle greenStyle = cellFormating(workBook,greenRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle redStyle = cellFormating(workBook,redRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle whiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        CellStyle indexWhiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        isWrapText = true;isBoldText = false;isItalicText = false; fontSize = 9;fontName = "Times New Roman";
		        CellStyle sectionStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        
	            XSSFRow headingRow = sheet.createRow(0);
	        	String headerString = "TRNO,	VEHICLE NUMBER,Material,Party,Transporter,	DATE IN,	TIME IN,FIRST WEIGHT,User1,	DATE OUT,	TIME OUT,SECOND WEIGHT,"
	        			+ "User2,SiteID,Status,	NET WEIGHT,SW_SiteID,TripNo,ShiftNo,Remarks,TypeOfWaste, CONTAINER ID" + 
	    				"";
	            String[] firstHeaderStringArr = headerString.split("\\,");
	            
	            for (int i = 0; i < firstHeaderStringArr.length; i++) {		        	
		        	Cell cell = headingRow.createCell(i);
			        cell.setCellStyle(greenStyle);
					cell.setCellValue(firstHeaderStringArr[i]);
				}
	            
	            short rowNo = 1;
	            for (DashBoardWeighBridge obj1 : dataList) {
	                XSSFRow row = sheet.createRow(rowNo);
	                int c = 0;
	            
	                Cell cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue(obj1.getTRNO());
					
	                cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getVEHICLENO());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getMATERIAL());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getPARTY());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTRANSPORTER());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getDATEIN());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTIMEIN());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getFIRSTWEIGHT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getUSER1());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getDATEOUT());  
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTIMEOUT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSECONDWEIGHT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getUSER2());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSITEID());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSTATUS());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getNETWT());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSW_SITEID());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTRIPNO());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getSHIFTNO());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getRemarks());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTYPEOFWASTE());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCONTAINERID());
					
	                rowNo++;
	            }
	            if(dataList.size() == 0) {
	            	 XSSFRow row = sheet.createRow(rowNo);
		                int c = 0;
		                Cell cell = row.createCell(c++);
						cell.setCellStyle(null);
						cell.setCellValue("No Data");
	            }
	            for(int columnIndex = 0; columnIndex < firstHeaderStringArr.length; columnIndex++) {
	        		sheet.setColumnWidth(columnIndex, 25 * 170);
				}
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                Date date = new Date();
                String fileName = obj.getProject()+dateFormat.format(date);
                
	            try{
	                /*FileOutputStream fos = new FileOutputStream(fileDirectory +fileName+".xls");
	                workBook.write(fos);
	                fos.flush();*/
	            	
	               response.setContentType("application/.csv");
	 			   response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	 			   response.setContentType("application/vnd.ms-excel");
	 			   // add response header
	 			   response.addHeader("Content-Disposition", "attachment; filename=" + fileName+".xlsx");
	 			   
	 			    //copies all bytes from a file to an output stream
	 			   workBook.write(response.getOutputStream()); // Write workbook to response.
		           workBook.close();
	 			    //flushes output stream
	 			    response.getOutputStream().flush();
	            	
	                
	                attributes.addFlashAttribute("success",dataExportSucess);
	            	//response.setContentType("application/vnd.ms-excel");
	            	//response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
	            	//XSSFWorkbook  workbook = new XSSFWorkbook ();
	            	// ...
	            	// Now populate workbook the usual way.
	            	// ...
	            	//workbook.write(response.getOutputStream()); // Write workbook to response.
	            	//workbook.close();
	            }catch(FileNotFoundException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportInvalid);
	            }catch(IOException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportError);
	            }
         }else{
        	 response.sendError(313);
        	 attributes.addFlashAttribute("error",dataExportNoData);
         }
		}catch(Exception e){	
			e.printStackTrace();
			logger.error("exportCompany : : User Id - "+userId+" - User Name - "+userName+" - "+e.getMessage());
			attributes.addFlashAttribute("error", commonError);			
		}
		//return view;
	}
	

	private CellStyle cellFormating(XSSFWorkbook workBook,byte[] rgb,HorizontalAlignment hAllign, VerticalAlignment vAllign, boolean isWrapText,boolean isBoldText,boolean isItalicText,int fontSize,String fontName) {
		CellStyle style = workBook.createCellStyle();
		//Setting Background color  
		//style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		if (style instanceof XSSFCellStyle) {
		   XSSFCellStyle xssfcellcolorstyle = (XSSFCellStyle)style;
		   xssfcellcolorstyle.setFillForegroundColor(new XSSFColor(rgb, null));
		}
		//style.setFillPattern(FillPatternType.ALT_BARS);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setAlignment(hAllign);
		style.setVerticalAlignment(vAllign);
		style.setWrapText(isWrapText);
		
		Font font = workBook.createFont();
        //font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        font.setFontHeightInPoints((short)fontSize);  
        font.setFontName(fontName);  //"Times New Roman"
        
        font.setItalic(isItalicText); 
        font.setBold(isBoldText);
        // Applying font to the style  
        style.setFont(font); 
        
        return style;
	}
	
	@RequestMapping(value = "/logs/{sbu}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView Logs(@ModelAttribute User user,DashBoardWeighBridge obj,@PathVariable("sbu") String sbu , HttpSession session) {
		ModelAndView model = new ModelAndView(PageConstants.logsPage);
		String userId = null;
		String userName = null;
		String role = null;
		try {   
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			role = (String) session.getAttribute("BASE_ROLE");
			String email = (String) session.getAttribute("USER_EMAIL");
			obj.setSbu(sbu);
			List <DashBoardWeighBridge> logsList = service.getLogsList(obj);
			model.addObject("logsList", logsList);
		
		} catch (Exception e) {
			e.printStackTrace();
		};
		return model;
	}
	
	
	@RequestMapping(value = "/export-bmw-data", method = {RequestMethod.GET,RequestMethod.POST})
	public void exportBMWSummery(HttpServletRequest request, HttpServletResponse response,HttpSession session,@ModelAttribute DashBoardWeighBridge obj,RedirectAttributes attributes){
		List<DashBoardWeighBridge> dataList = new ArrayList<DashBoardWeighBridge>();
		String userId = null;String userName = null;
		try {
			userId = (String) session.getAttribute("USER_ID");userName = (String) session.getAttribute("USER_NAME");
			dataList = service.getTransactionsList1(obj); 
			if(dataList != null && dataList.size() > 0){
	            XSSFWorkbook  workBook = new XSSFWorkbook ();
	            XSSFSheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(obj.getProject()));
		        workBook.setSheetOrder(sheet.getSheetName(), 0);
		        
		        byte[] blueRGB = new byte[]{(byte)0, (byte)176, (byte)240};
		        byte[] yellowRGB = new byte[]{(byte)255, (byte)192, (byte)0};
		        byte[] greenRGB = new byte[]{(byte)146, (byte)208, (byte)80};
		        byte[] redRGB = new byte[]{(byte)255, (byte)0, (byte)0};
		        byte[] whiteRGB = new byte[]{(byte)255, (byte)255, (byte)255};
		        
		        boolean isWrapText = true;boolean isBoldText = true;boolean isItalicText = false; int fontSize = 11;String fontName = "Times New Roman";
		        CellStyle blueStyle = cellFormating(workBook,blueRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle yellowStyle = cellFormating(workBook,yellowRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle greenStyle = cellFormating(workBook,greenRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle redStyle = cellFormating(workBook,redRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        CellStyle whiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.CENTER,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        CellStyle indexWhiteStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        isWrapText = true;isBoldText = false;isItalicText = false; fontSize = 9;fontName = "Times New Roman";
		        CellStyle sectionStyle = cellFormating(workBook,whiteRGB,HorizontalAlignment.LEFT,VerticalAlignment.CENTER,isWrapText,isBoldText,isItalicText,fontSize,fontName);
		        
		        
	            XSSFRow headingRow = sheet.createRow(0);
	        	String headerString = "Company,Plant Name,Profit Center,Customer Code,Type of Establishment,Service Frequency,No of Visits Per Month,Blue Count,Blue Weight,Red Count,Red Weight,Yellow Count,Yellow Weight,Cytotoxic Count,Cytotoxic Weight,White Count,White Weight,Total Count,Total Weight,Last Modified\r\n"
	        			+ "" + 
	    				"";
	            String[] firstHeaderStringArr = headerString.split("\\,");
	            
	            for (int i = 0; i < firstHeaderStringArr.length; i++) {		        	
		        	Cell cell = headingRow.createCell(i);
			        cell.setCellStyle(greenStyle);
					cell.setCellValue(firstHeaderStringArr[i]);
				}
	            
	            short rowNo = 1;
	            for (DashBoardWeighBridge obj1 : dataList) {
	                XSSFRow row = sheet.createRow(rowNo);
	                int c = 0;
	            
	                Cell cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue(obj1.getCompany());
					
	                cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getPlant());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getProfit_center_name());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCustomerSAPCode());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTypeofEstablishment());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getServiceFrequency());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCustomerSAPCodeCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getBlueCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getBlueWeight());  
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getRedCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getRedWeight());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getYellowCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getYellowWeight());  
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCytotoxicCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getCytotoxicWeight());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getWhitesCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getWhitesWeight());  
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTotalCount());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getTotalWeight());
					
					cell = row.createCell(c++);
					cell.setCellStyle(sectionStyle);
					cell.setCellValue (obj1.getServerDateTime());
				
	                rowNo++;
	            }
	            for(int columnIndex = 0; columnIndex < firstHeaderStringArr.length; columnIndex++) {
	        		sheet.setColumnWidth(columnIndex, 25 * 170);
				}
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                Date date = new Date();
                String fileName = obj.getProject()+dateFormat.format(date);
                
	            try{
	                /*FileOutputStream fos = new FileOutputStream(fileDirectory +fileName+".xls");
	                workBook.write(fos);
	                fos.flush();*/
	            	
	               response.setContentType("application/.csv");
	 			   response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	 			   response.setContentType("application/vnd.ms-excel");
	 			   // add response header
	 			   response.addHeader("Content-Disposition", "attachment; filename=" + fileName+".xlsx");
	 			   
	 			    //copies all bytes from a file to an output stream
	 			   workBook.write(response.getOutputStream()); // Write workbook to response.
		           workBook.close();
	 			    //flushes output stream
	 			    response.getOutputStream().flush();
	            	
	                
	                attributes.addFlashAttribute("success",dataExportSucess);
	            	//response.setContentType("application/vnd.ms-excel");
	            	//response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
	            	//XSSFWorkbook  workbook = new XSSFWorkbook ();
	            	// ...
	            	// Now populate workbook the usual way.
	            	// ...
	            	//workbook.write(response.getOutputStream()); // Write workbook to response.
	            	//workbook.close();
	            }catch(FileNotFoundException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportInvalid);
	            }catch(IOException e){
	                //e.printStackTrace();
	                attributes.addFlashAttribute("error",dataExportError);
	            }
         }else{
        	 response.sendError(313);
        	 attributes.addFlashAttribute("error",dataExportNoData);
         }
		}catch(Exception e){	
			e.printStackTrace();
			logger.error("exportCompany : : User Id - "+userId+" - User Name - "+userName+" - "+e.getMessage());
			attributes.addFlashAttribute("error", commonError);			
		}
		//return view;
	}
}
