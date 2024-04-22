package com.resustainability.reisp.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.resustainability.reisp.model.BrainBox;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.ProjectLocation;
import com.resustainability.reisp.model.SBU;

@Repository
public class DashBoardWeighBridgeDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	DataSourceTransactionManager transactionManager;

	public List<DashBoardWeighBridge> getDashboards(DashBoardWeighBridge obj) throws Exception{
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null  "; 
			int arrSize1 = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize1++;
				}
			 qry = qry + " order by sbu desc ";
			 Object[] pValues1 = new Object[arrSize1];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			
			for(DashBoardWeighBridge siteIDs : objsList) {
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "");
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				String all_sites = "'"+siteIDs.getWb_site_id().replace("'", "")+"'";
				String data = new String("");
				String setQry = "SET NOCOUNT ON;";
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+"SELECT top 1 [id] as UID,'bmw' as dmNmae,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name , "
							+ " (select top (1) (cast([visitDayTime] as time(0))) from [ALL_BMW_Sites].[dbo].bmw_detailed   where company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' order by ServerDateTime desc ) as visitDayTime,(select top 1 count(*)   from [ALL_BMW_Sites].[dbo].bmw_detailed "
							+ "where  company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as count,"
							+ "   (select top 1 min(convert(varchar, ServerDateTime, 23))   from [ALL_BMW_Sites].[dbo].bmw_detailed "
							+ "where  company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as min,"
							+ " (select top 1  max(convert(varchar, ServerDateTime, 23)) from [ALL_BMW_Sites].[dbo].bmw_detailed where company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as DATEIN  "
							+ "      ,(select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name ,"
									+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
											+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project, "
															+ "(select company from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as company,convert(varchar, getdate(), 23) as curDAte  "
							+ ",  (select count(*) from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) "
							+ "as dailyCount"
						
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed] ";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					objsList2.addAll(objsList1);
				}else {
				data = ""
						//+ "BEGIN TRY   SET NOCOUNT ON; "
						+ "select top 1 UID,TRNO,"
						+ " (select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name,"
						+ "VEHICLENO ,"+all_sites+" as all_sites,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name ,"
						+ "(select min(TRY_CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" "
								+ "where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as min,"
										+ "(select max(TRY_CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()
										+ " where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as DATEIN,convert(varchar, getdate(), 23) as curDAte"
												+ ",(select top (1) (cast([TIMEIN] as time(0))) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+"  "
												+ " where SITEID in ("+siteIDs.getWb_site_id()+") "
												+ "order by TRNO desc ) as TIMEIN"
										+ ",(select count(TRNO) from "+siteIDs.getDb_name()+""
										+ ".[dbo]."+siteIDs.getTable_name()+"  where SITEID='"+siteID+"' and NETWT <> '' and NETWT is not null and NETWT <> 0) as count,"
												+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project,"
												+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
								+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,FIRSTWEIGHT,USER1,"
								+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
						+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
						+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
						+ "		   ,REMARKS,CONTAINERID from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" tt "
								+ "where TRNO is not null and NETWT is not null and NETWT <> ''  ";
								
				int arrSize = 0;
			    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
			    	data = data + " and SITEID in ("+siteIDs.getWb_site_id()+") ";
					arrSize++;
				}
			    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
			 
				Object[] pValues = new Object[arrSize];
				int i = 0;
				if(!StringUtils.isEmpty(siteIDs.getWb_site_id()) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
					pValues[i++] = siteIDs.getWb_site_id();
				}
				objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				objsList2.addAll(objsList1);
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList2;
	}

	public List<DashBoardWeighBridge> getSBUsList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT distinct sbu  FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.sbu is not null and  mt.sbu <> '' and  project_status <> 'InActive' and wb_site_id is not null order by sbu desc"; 
			objsList = jdbcTemplate.query( qry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getDashboardsDaily(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList3 = new ArrayList<DashBoardWeighBridge>();

		try {
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null order by sbu desc"; 
			int arrSize1 = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize1++;
				}
			 Object[] pValues1 = new Object[arrSize1];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			int counts = 0;
			for(DashBoardWeighBridge siteIDs : objsList) {
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "").trim();
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				String pattern = "MM-dd-yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				date = date+" 00:00:00";
				
				String pattern3 = "dd-MM-yyyy";
				SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
				String date3 = simpleDateFormat3.format(new Date());
				date3 = date3+" 00:00:00";
				
				String pattern2 = "M/d/yyyy";
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
				String date2 = simpleDateFormat2.format(new Date());
				date2 = date2+" 12:00:00 AM";
				
				String pattern4 = "d/M/yyyy";
				SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
				String date4 = simpleDateFormat4.format(new Date());
				date4 = date4+" 12:00:00 AM";
				
				String data = new String("");
				String setQry = "SET NOCOUNT ON;";
				String all_sites = "'"+siteIDs.getWb_site_id().replace("'", "")+"'";
				LocalDate today = LocalDate.now();

		        // Subtract one day from the current date to get yesterday's date
		        LocalDate yesterday = today.minusDays(1);

		        // Get the year, month, and day for yesterday
		        int year = yesterday.getYear();
		        int month = yesterday.getMonthValue();
		        int day = yesterday.getDayOfMonth();

		       String yesDAte = year + "-" + month + "-" + day;
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+"SELECT top 1 [id] as UID,'bmw' as dmNmae,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name , "
							+ "   (select top 1 convert(varchar, ServerDateTime, 23)   from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) as DATEIN  "
							+ "      ,(select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name ,"
									+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
											+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project, "
															+ "(select company from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as company,convert(varchar, getdate(), 23) as curDAte  "
							+ ",  (select count(*) from [ALL_BMW_Sites].[dbo].bmw_detailed where  convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23)"
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%' ) "
							+ "as dailyCount   "
						
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed]";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					objsList2.addAll(objsList1);
				}else {
					data = ""
							
							//+ "BEGIN TRY   SET NOCOUNT ON; "
							+ "select top 1 UID,TRNO,VEHICLENO ,"+all_sites+" as all_sites,"+siteIDs.getNo_of_wb()+" as no_of_wbR, (select project_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_name,"
							+ "(select min(TRY_CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" "
									+ "where DATEIN is not null and DATEIN <> '' and SITEID='"+siteID+"')  as min,(select indicator_name from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as indicator_name ,"
											+ "(Select count(*) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where "
											+ " datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where SITEID in ("+siteIDs.getWb_site_id()+") order by trno desc) "
											+ "	and  SITEID  in ("+siteIDs.getWb_site_id()+")  and NETWT is not null and NETWT <> '' ) as dailyCount,"
											+ "(select max(TRY_CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'  ELSE DATEIN  END AS DATE)) from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()
											+ " where DATEIN is not null and DATEIN <> '' and SITEID in ("+siteIDs.getWb_site_id()+") and datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+"  where SITEID in ("+siteIDs.getWb_site_id()+") order by TRY_CAST( CASE  WHEN DATEIN LIKE '%00:00:00%' THEN CONVERT(DATETIME,datein, 103)+' 12:00:00 AM'"
													+ "ELSE DATEIN  END AS DATE) desc))  as DATEIN,convert(varchar, getdate(), 23) as curDAte"
											+ ",(select count(TRNO) from "+siteIDs.getDb_name()+""
											+ ".[dbo]."+siteIDs.getTable_name()+"  where SITEID='"+siteID+"' and NETWT <> '' and NETWT is not null and NETWT <> 0) as count,"
													+ "(select project from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project,"
													+ "(select project_status from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as project_status,"
													+ "(select sbu from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as sbu,"
									+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
									+ "(select count(*) from  [MasterDB].[dbo].[master_table] where sbu like '"+siteIDs.getSbu()+"' and project_status='Active') as all_wb,"
									+ "CONVERT(varchar(9), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
							+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
							+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
							+ "		   ,REMARKS,CONTAINERID from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" tt "
									+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";

					int arrSize = 0;
				    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
				    	data = data + " and SITEID in ("+siteIDs.getWb_site_id()+") ";
						arrSize++;
					}
				    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
				 
					Object[] pValues = new Object[arrSize];
					int i = 0;
					if(!StringUtils.isEmpty(siteIDs.getWb_site_id()) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
						pValues[i++] = siteIDs.getWb_site_id();
					}		
					
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					if(!siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					String WBQry = "SELECT SITEID as greenWB,(select no_of_wb from [MasterDB].[dbo].[master_table] where wb_site_id like '%"+siteID+"%') as no_of_wb2 FROM  "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" t1 where SITEID in ("+siteIDs.getWb_site_id()+") and datein = (select top (1) datein from "+siteIDs.getDb_name()+".[dbo]."+siteIDs.getTable_name()+" where SITEID in ("+siteIDs.getWb_site_id()+") order by trno desc) "
							+ "GROUP BY SITEID";
					objsList3 = jdbcTemplate.query( WBQry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
					 String commaSeparatedString = objsList3.stream()
				                .map(DashBoardWeighBridge::getGreenWB)
				                .collect(Collectors.joining(","));
				
					objsList2.addAll(objsList1);
					if(!StringUtils.isEmpty(commaSeparatedString) && objsList3.size() != 0){
						if(counts == 14) {
							System.out.println("sfd");
						}
						objsList2.get(counts).setGreenWB(commaSeparatedString);
						 if(!StringUtils.isEmpty(objsList3.get(0).getNo_of_wb2())) {
								objsList2.get(counts).setNo_of_wb2(""+objsList3.size()+"");
						 }
					}
					counts++;
					}
				}
		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList2;
	}

	public List<DashBoardWeighBridge> getMSWDataWithSiteID(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			date = date+" 00:00:00";
			

			String pattern3 = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
			String date3 = simpleDateFormat3.format(new Date());
			date3 = date3+" 00:00:00";
			
			String pattern2 = "M/d/yyyy";
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
			String date2 = simpleDateFormat2.format(new Date());
			date2 = date2+" 12:00:00 AM";
			
			String pattern4 = "d/M/yyyy";
			SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
			String date4 = simpleDateFormat4.format(new Date());
			date4 = date4+" 12:00:00 AM";
			String data = new String("");
			String dbNAme = "All_MSW_SITES";
			if("CND".equalsIgnoreCase(obj.getSbu())) {
				dbNAme = "All_CnD_Sites";
			}
			data = ""
					+ "select TRNO,VEHICLENO , CASE WHEN CHARINDEX(' ', DATEIN) > 0  AND LEN(LEFT(DATEIN, CHARINDEX(' ', DATEIN) - 1)) > 10  THEN CONVERT(varchar(9), DATEIN, 105) ELSE CONVERT(varchar(10), DATEIN, 105) END as  DATEIN,"
							+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
							+ "CASE WHEN CHARINDEX(' ', DATEOUT) > 0  AND LEN(LEFT(DATEOUT, CHARINDEX(' ', DATEOUT) - 1)) > 10  THEN CONVERT(varchar(9), DATEOUT, 105) ELSE CONVERT(varchar(10), DATEOUT, 105) END AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
					+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
					+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
					+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
							+ "where TRNO is not null and NETWT is not null and NETWT <> '' and ( datein = '"+date+ "' or datein = '"+date2+"' or  datein = '"+date3+ "' or datein = '"+date4+"' ) ";
							
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSITEID())) {
		    	data = data + " and SITEID in("+obj.getSITEID()+")";
				arrSize++;
			}
		    data = data + " order by TRNO ";
		 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj.getWb_site_id()) && !StringUtils.isEmpty(obj.getWb_site_id())) {
				pValues[i++] = obj.getWb_site_id();
			}
			objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getCNDDataWithSiteID(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String data = new String("");
			data = ""
					+ "(select  CASE WHEN CHARINDEX(' ', DATEIN) > 0  AND LEN(LEFT(DATEIN, CHARINDEX(' ', DATEIN) - 1)) > 10  THEN CONVERT(varchar(9), DATEIN, 105) ELSE CONVERT(varchar(10), DATEIN, 105) END as  DATEIN,"
							+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
							+ " CASE WHEN CHARINDEX(' ', DATEOUT) > 0  AND LEN(LEFT(DATEOUT, CHARINDEX(' ', DATEOUT) - 1)) > 10  THEN CONVERT(varchar(9), DATEOUT, 105) ELSE CONVERT(varchar(10), DATEOUT, 105) END AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
					+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
					+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
					+ "		   ,REMARKS,CONTAINERID from [All_CnD_Sites].[dbo].[WEIGHT] tt "
							+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";
							
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getWb_site_id())) {
		    	data = data + " and SITEID = "+obj.getWb_site_id();
				arrSize++;
			}
		    data = data + " order by CONVERT(varchar(9), DATEIN, 103) desc ";
		 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj.getWb_site_id()) && !StringUtils.isEmpty(obj.getWb_site_id())) {
				pValues[i++] = obj.getWb_site_id();
			}
			objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getProjectssList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		try {
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			date = date+" 00:00:00";
			

			String pattern3 = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
			String date3 = simpleDateFormat3.format(new Date());
			date3 = date3+" 00:00:00";
			
			String pattern2 = "M/d/yyyy";
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
			String date2 = simpleDateFormat2.format(new Date());
			date2 = date2+" 12:00:00 AM";
			
			String pattern4 = "d/M/yyyy";
			SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
			String date4 = simpleDateFormat4.format(new Date());
			date4 = date4+" 12:00:00 AM";
			
			
			String qry = "SELECT REPLACE(REPLACE([company], '\r', ''), '\n', '') AS company,REPLACE(REPLACE([sbu], '\r', ''), '\n', '') AS sbu,"
					+ "REPLACE(REPLACE([project], '\r', ''), '\n', '') AS project,REPLACE(REPLACE([project_name], '\r', ''), '\n', '') AS project_name,"
					+ "REPLACE(REPLACE([location], '\r', ''), '\n', '') AS location,REPLACE(REPLACE([project_status], '\r', ''), '\n', '') AS project_status,"
					+ "no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
					+ ",developed_by,convert(varchar, getdate(), 23) as curDAte FROM [MasterDB].[dbo].[master_table] mt "
					+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null and project_status <> 'Inactive' "; 
			int arrSize = 0;
		    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
		    	qry = qry + " and sbu = ? ";
				arrSize++;
			}
			qry = qry + "order by project desc"; 
			Object[] pValues = new Object[arrSize];
			int i = 0;
			if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				pValues[i++] = obj.getSbu();
			}
			String dbNAme = "All_MSW_SITES";
			if("CND".equalsIgnoreCase(obj.getSbu())) {
				dbNAme = "All_CnD_Sites";
			}
			objsList = jdbcTemplate.query( qry,pValues, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			for (DashBoardWeighBridge siteIDs : objsList) {
				String data = "";
				String siteID = siteIDs.getWb_site_id().replaceAll("'", "").trim();
				if(siteID.contains(",")) {
					String [] siteIDss = siteID.split(",");
					siteID = siteIDss[0];
					if(siteID.equals("WB1")){
						siteID = "WB3";
					}
				}
				if(siteIDs.getDb_name().equals("ALL_BMW_Sites")) {
					data = ""
							+ "Select  id "
							+ "      ,company "
							+ "      ,plant "
							+ "      ,route "
							+ "      ,CustomerDistrict "
							+ "      ,CustomerTown as customerTown "
							+ "      ,VehicleRegNo as vehicleRegNo "
							+ "      ,CustomerName "
							+ "      ,CustomerCABSCode "
							+ "      ,CustomerSAPCode "
							+ "      ,TypeofEstablishment "
							+ "      ,ManifestNo "
							+ "      ,ActualVisitDate as actualVisitDate "
							+ "      ,ActualVisitMonth "
							+ "      ,ActualVisitYear "
							+ "      ,VisitDayTime "
							+ "      ,ServiceFrequency "
							+ "      ,BlueCount "
							+ "      ,BlueWeight "
							+ "      ,RedCount "
							+ "      ,RedWeight "
							+ "      ,YellowCount "
							+ "      ,YellowWeight "
							+ "      ,CytotoxicCount "
							+ "      ,CytotoxicWeight "
							+ "      ,WhitesCount "
							+ "      ,WhitesWeight "
							+ "      ,TotalCount "
							+ "      ,TotalWeight "
							+ "      ,CollectionLatitude "
							+ "      ,CollectionLongitude "
							+ "      ,ServerDateTime "
							+ "      ,APIType as aPIType "
							+ "      ,CabsTransID as cabsTransID "
							+ "      ,APIID as aPIID "
							+ "  FROM [ALL_BMW_Sites].[dbo].[bmw_detailed] where convert(varchar, ServerDateTime, 23) = convert(varchar, getdate(), 23) "
							+ " and company like '%"+siteIDs.getCompany().trim()+"%' and plant like '%"+siteIDs.getProject_name()+"%'";
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				}else {
					 data = ""
							+ "select TRNO,VEHICLENO ,CONVERT(varchar(10), DATEIN, 105) AS DATEIN,"
									+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
									+ "CONVERT(varchar(10), DATEOUT, 105) AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
							+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
							+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
							+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
									+ "where TRNO is not null and NETWT is not null and NETWT <> '' and ( datein = '"+date+ "' or datein = '"+date2+"' or  datein = '"+date3+ "' or datein = '"+date4+"' ) ";
									
					int arrSize1 = 0;
				    if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
				    	data = data + " and SITEID in("+siteIDs.getWb_site_id()+")";
						arrSize1++;
					}
				    data = data + " order by TRNO desc  ";
				 
					Object[] pValues1 = new Object[arrSize1];
					int j = 0;
					if(!StringUtils.isEmpty(siteIDs) && !StringUtils.isEmpty(siteIDs.getWb_site_id())) {
						pValues[j++] = siteIDs.getWb_site_id();
					}
					objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				}

				siteIDs.setTransactionsList(objsList1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getLogsList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		List<DashBoardWeighBridge> objsList1 = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = "SELECT sbu,db_name,table_name,status,project , convert(varchar, getdate(), 106) as curDAte FROM MasterDB.dbo.log_master mt  "
					+ " where  mt.table_name is not null and  mt.table_name <> '' and  table_name is not null and status <> 'Inactive' "; 
			int arrSize = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					qry = qry + " AND sbu = ? ";
					arrSize++;
				}
				qry = qry + " order by sbu desc ";
			 Object[] pValues1 = new Object[arrSize];
				int j = 0;
				 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
					 pValues1[j++] = obj.getSbu();
				}
			objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
			for (DashBoardWeighBridge list : objsList) {
				String data = ""
						+ "select TOP (1) convert(varchar, PTCDT, 0) as PTCDT1, convert(varchar, getdate(), 106) as curDAte,DATEDIFF(day, [PTCDT], getdate()) AS days_diff from ["+list.getDb_name()+"].[dbo].["+list.getTable_name()+"] tt ";
			    data = data + " order by PTCDT desc  ";
				objsList1 = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
				list.setTransactionsList(objsList1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getWeighBridgeList(DashBoardWeighBridge obj) {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		String qry = "SELECT company,sbu,project,project_name,location,project_status,no_of_wb,wb_site_id,db_name,table_name ,api_status,api_consumed_by"
				+ ",developed_by FROM [MasterDB].[dbo].[master_table] mt "
				+ " where  mt.location is not null and  mt.location <> '' and  wb_site_id is not null "; 
		int arrSize1 = 0;
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				qry = qry + " AND sbu = ? ";
				arrSize1++;
			}
			qry = qry + " order by sbu desc ";
		 Object[] pValues1 = new Object[arrSize1];
			int j = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSbu())) {
				 pValues1[j++] = obj.getSbu();
			}
		objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		return objsList; 
	}

	public List<DashBoardWeighBridge> getMSWManualDataWithSiteID(DashBoardWeighBridge obj) {
		 String  fromDateDash = null;
		 String  fromDateSlash = null;
		 String toDateSlash = null;
		 String toDateDash = null;
		 
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		 
		List<DashBoardWeighBridge> objsList2 = new ArrayList<DashBoardWeighBridge>();
		String data = new String("");
		String dbNAme = "All_MSW_SITES";
		if("CND".equalsIgnoreCase(obj.getSbu())) {
			dbNAme = "All_CnD_Sites";
		}
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getFrom_date())) {
		data = ""
				+ "select TRNO,VEHICLENO ,CASE WHEN CHARINDEX(' ', DATEIN) > 0  AND LEN(LEFT(DATEIN, CHARINDEX(' ', DATEIN) - 1)) > 10  THEN CONVERT(varchar(9), DATEIN, 105) ELSE CONVERT(varchar(10), DATEIN, 105) END as  DATEIN,"
						+ "MATERIAL,PARTY,TRANSPORTER,BILLDCNO,BILLWEIGHT,TIMEIN,FIRSTWEIGHT,USER1,"
						+ "CASE WHEN CHARINDEX(' ', DATEOUT) > 0  AND LEN(LEFT(DATEOUT, CHARINDEX(' ', DATEOUT) - 1)) > 10  THEN CONVERT(varchar(9), DATEOUT, 105) ELSE CONVERT(varchar(10), DATEOUT, 105) END AS DATEOUT,TIMEOUT,SECONDWEIGHT,USER2,SITEID,STATUS,FIRSTFRONTPOTO"
				+ "		   ,FIRSTBACKPOTO,SECONDFRONTPOTO,SECONDBACKPOTO,NETWT,SW_SITEID,TRIPNO,SHIFTNO,TRANSFERWASTEIE ,TRANSFERWASTE,MANIFESTNUMBER ,MANIFESTWEIGHT,MEMBERSHIPCODE"
				+ "		   ,INGATEPASSNO ,INMETERREADING,OUTGATEPASSNO,OUTMETERREADING ,TRANSFERID,TYPEOFWASTE,TOTALKMSTRAVELLED ,BILLABLEWEIGHT,TOTALTRANSPORTCHARGES ,BARCODENUM"
				+ "		   ,REMARKS,CONTAINERID from ["+dbNAme+"].[dbo].[WEIGHT] tt "
						+ "where TRNO is not null and NETWT is not null and NETWT <> '' ";
						 
		int arrSize1 = 1;
	    if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getSITEID())) {
	    	data = data + " and SITEID in("+obj.getSITEID()+")";
			//arrSize1++;
		}
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getFrom_date())) {
			   fromDateDash = obj.getFrom_date();
			   fromDateSlash = obj.getFrom_date();
			 
			  ////  String originalFormat = "yyyy-MM-dd";
		      //  LocalDate date = LocalDate.parse(fromDateSlash, DateTimeFormatter.ofPattern(originalFormat));
		      //  String desiredFormat = "M/d/yyyy";
		     //   fromDateSlash = date.format(DateTimeFormatter.ofPattern(desiredFormat))+" 12:00:00 AM";
		
		        
			 if(StringUtils.isEmpty(obj.getTo_date())) {
				 obj.setTo_date(obj.getFrom_date());
			 }else {
				    toDateSlash = obj.getTo_date();
				    toDateDash = obj.getTo_date();
				 //   originalFormat =  "yyyy-MM-dd";
			   ///     date = LocalDate.parse(toDateSlash, DateTimeFormatter.ofPattern(originalFormat));
			    //    desiredFormat = "M/d/yyyy";
			   //     toDateSlash = date.format(DateTimeFormatter.ofPattern(desiredFormat))+" 12:00:00 AM";
			     
			 }
			 	data = data + " AND (TRY_CAST(DATEIN AS date) between '"+fromDateSlash+"' and '"+toDateSlash+"')  ";
				arrSize1++;
				arrSize1++;
			
			}
	    data = data + " order by TRNO ";
	
		objsList = jdbcTemplate.query( data, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
	
		
		 }
		return objsList;
	}


	public List<DashBoardWeighBridge> getMonthList(DashBoardWeighBridge obj) throws Exception{
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		String qry = "SELECT ActualVisitMonth, LEFT(ActualVisitMonth, CHARINDEX(' ', ActualVisitMonth + ' ') - 1) AS split_value FROM [bmw_detailed] group by ActualVisitMonth"
				+ ""; 
		objsList = jdbcTemplate.query( qry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		return objsList;
	}

	public List<DashBoardWeighBridge> getCustomerCodeList(DashBoardWeighBridge obj)throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		String qry = "SELECT CustomerSAPCode FROM [bmw_detailed] group by CustomerSAPCode"
				+ ""; 
		objsList = jdbcTemplate.query( qry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		return objsList;
	}

	public List<DashBoardWeighBridge> getTransactionsList1(DashBoardWeighBridge obj) throws Exception{
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		String qry = "SELECT top(10) [company_code]"
				+ "      ,[company]"
				+ "      ,[CustomerCode]"
				+ "      ,[plant_name]"
				+ "      ,[profit_center]"
				+ "      ,[profit_center_name]"
				+ "      ,[ActualVisitMonth]"
				+ "      ,[TypeofEstablishment]"
				+ "      ,[ServiceFrequency]"
				+ "      ,[visitsPerMonth]"
				+ "      ,[CustomerStatus]"
				+ "      ,[BlueCount]"
				+ "      ,[BlueWeight]"
				+ "      ,[RedCount]"
				+ "      ,[RedWeight]"
				+ "      ,[YellowCount]"
				+ "      ,[YellowWeight]"
				+ "      ,[CytotoxicCount]"
				+ "      ,[CytotoxicWeight]"
				+ "      ,[WhitesCount]"
				+ "      ,[WhitesWeight]"
				+ "      ,[TotalCount]"
				+ "      ,[TotalWeight]"
				+ "      ,[last_modified]"
				+ "  FROM [ALL_BMW_Sites].[dbo].[transactions_summary] where company_code is not null  "; 
		int arrSize1 = 0;
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getCustomerSAPCode())) {
				qry = qry + " AND CustomerCode like %"+obj.getCustomerSAPCode()+"%";
			}
		 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getActualVisitMonth())) {
				qry = qry + " AND ActualVisitMonth = ? ";
				arrSize1++;
			}
		 Object[] pValues1 = new Object[arrSize1];
			int j = 0;
			 if(!StringUtils.isEmpty(obj) && !StringUtils.isEmpty(obj.getActualVisitMonth())) {
				 pValues1[j++] = obj.getActualVisitMonth();
				 
			}
		objsList = jdbcTemplate.query( qry,pValues1, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		return objsList;
	}

	public List<DashBoardWeighBridge> getTransactionsList(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(obj);
			String ERQ2 = "  UPDATE ALL_BMW_Sites.dbo.bmw_detailed SET [plant] = LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE([plant], "
					+ "CHAR(10), CHAR(32)), CHAR(13), CHAR(32)), CHAR(160), CHAR(32)),CHAR(9),CHAR(32))))";
			paramSource = new BeanPropertySqlParameterSource(obj);	 
			namedParamJdbcTemplate.update(ERQ2, paramSource);
			
			String ERQ3 = "  UPDATE ALL_BMW_Sites.dbo.bmw_detailed SET [CustomerSAPCode] = LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE([CustomerSAPCode], "
					+ "CHAR(10), CHAR(32)), CHAR(13), CHAR(32)), CHAR(160), CHAR(32)),CHAR(9),CHAR(32))))";
			paramSource = new BeanPropertySqlParameterSource(obj);	 
			namedParamJdbcTemplate.update(ERQ3, paramSource);
			
			String ERQ = "  UPDATE [MasterDB].[dbo].[master_table] SET [project_code] = LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE([project_code], "
					+ "CHAR(10), CHAR(32)), CHAR(13), CHAR(32)), CHAR(160), CHAR(32)),CHAR(9),CHAR(32))))";
			paramSource = new BeanPropertySqlParameterSource(obj);	 
			namedParamJdbcTemplate.update(ERQ, paramSource);
			
			String ERQ1 = "  UPDATE [MasterDB].[dbo].[master_table] SET [project_name] = LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE([project_name], "
					+ "CHAR(10), CHAR(32)), CHAR(13), CHAR(32)), CHAR(160), CHAR(32)),CHAR(9),CHAR(32))))";
			paramSource = new BeanPropertySqlParameterSource(obj);	 
			namedParamJdbcTemplate.update(ERQ1, paramSource);
			
			
			String deleteQRY = "delete from [ALL_BMW_Sites].[dbo].[transactions_summary] where company_code is not null";
			DashBoardWeighBridge dObj = new DashBoardWeighBridge();
			dObj.setId("0");
			paramSource = new BeanPropertySqlParameterSource(dObj);		 
		    namedParamJdbcTemplate.update(deleteQRY, paramSource);

		    String qry = "MERGE [ALL_BMW_Sites].[dbo].[transactions_summary] AS target "
				+ "USING ( "
				+ "    SELECT (select profit_center from [MasterDB].[dbo].[master_table] where company = MAX(d.company)) AS profit_center,		 "
				+ "(select profit_center_name from [MasterDB].[dbo].[master_table] where company = MAX(d.company)) AS profit_center_name, "
				+ "MAX(d.company) AS company,(select company_code from [MasterDB].[dbo].[master_table] where company = MAX(d.company)) AS company_code, "
				+ "MAX(d.plant) AS plant_name,				MAX(d.TypeofEstablishment) AS TypeofEstablishment,			 "
				+ "MAX(d.ServiceFrequency) AS ServiceFrequency,				MAX(d.ActualVisitMonth) AS ActualVisitMonth,	 "
				+ "MAX(d.CustomerStatus) AS CustomerStatus,CustomerSAPCode, "
				+ "count(CustomerSAPCode) as CustomerSAPCodeCount, "
				+ "SUM(TRY_CAST(BlueCount AS FLOAT )) as BlueCount, "
				+ "SUM(TRY_CAST(BlueWeight AS FLOAT )) as BlueWeight, "
				+ "SUM(TRY_CAST(RedCount AS FLOAT )) as RedCount, "
				+ "SUM(TRY_CAST(RedWeight AS FLOAT )) as RedWeight, "
				+ "SUM(TRY_CAST(YellowCount AS FLOAT )) as YellowCount, "
				+ "SUM(TRY_CAST(YellowWeight AS FLOAT )) as YellowWeight, "
				+ "SUM(TRY_CAST(CytotoxicCount AS FLOAT )) as CytotoxicCount, "
				+ "SUM(TRY_CAST(CytotoxicWeight AS FLOAT )) as CytotoxicWeight, "
				+ "SUM(TRY_CAST(WhitesCount AS FLOAT )) as WhitesCount, "
				+ "SUM(TRY_CAST(WhitesWeight AS FLOAT )) as WhitesWeight, "
				+ "SUM(TRY_CAST(TotalCount AS FLOAT )) as TotalCount, "
				+ "SUM(TRY_CAST(TotalWeight AS FLOAT )) as TotalWeight  FROM  ( "
				+ "    SELECT DISTINCT([ManifestNo]) "
				+ "	  ,[company] "
				+ "      ,[plant] "
				+ "      ,[CustomerDistrict] "
				+ "      ,[CustomerTown] "
				+ "      ,[VehicleRegNo] "
				+ "      ,[CustomerName] "
				+ "      ,[CustomerSAPCode] "
				+ "      ,[TypeofEstablishment] "
				+ "      ,[ActualVisitDate] "
				+ "      ,[ActualVisitMonth] "
				+ "      ,[VisitDayTime] "
				+ "      ,[ServiceFrequency] "
				+ "      ,[BlueCount] "
				+ "      ,[BlueWeight] "
				+ "      ,[RedCount] "
				+ "      ,[RedWeight] "
				+ "      ,[YellowCount] "
				+ "      ,[YellowWeight] "
				+ "      ,[CytotoxicCount] "
				+ "      ,[CytotoxicWeight] "
				+ "      ,[WhitesCount] "
				+ "      ,[WhitesWeight] "
				+ "      ,[TotalCount] "
				+ "      ,[TotalWeight] "
				+ "      ,[CustomerStatus] "
				+ "    FROM ALL_BMW_Sites.dbo.[bmw_detailed] "
				+ ") as d group by CustomerSAPCode,ActualVisitMonth  "
				+ ") AS source "
				+ "ON target.CustomerCode = source.CustomerSAPCode "
				+ "WHEN MATCHED THEN "
				+ "    UPDATE SET company = source.company, profit_center = source.profit_center, profit_center_name = source.profit_center_name, company_code = source.company_code, "
				+ "	plant_name = source.plant_name "
				+ "	, TypeofEstablishment = source.TypeofEstablishment, ServiceFrequency = source.ServiceFrequency, ActualVisitMonth = source.ActualVisitMonth, "
				+ "	CustomerStatus = source.CustomerStatus, CustomerCode = source.CustomerSAPCode "
				+ "	, visitsPerMonth = source.CustomerSAPCodeCount, BlueCount = source.BlueCount, BlueWeight = source.BlueWeight, RedCount = source.RedCount, "
				+ "	RedWeight = source.RedWeight "
				+ "	, YellowCount = source.YellowCount, YellowWeight = source.YellowWeight, CytotoxicCount = source.CytotoxicCount,  "
				+ "	CytotoxicWeight = source.CytotoxicWeight, WhitesCount = source.WhitesCount, WhitesWeight = source.WhitesWeight,  "
				+ "	TotalCount = source.TotalCount,TotalWeight = source.TotalWeight,last_modified = getdate() "
				+ "WHEN NOT MATCHED THEN "
				+ "    INSERT (company, profit_center, profit_center_name, company_code, plant_name, TypeofEstablishment, ServiceFrequency, "
				+ "	ActualVisitMonth, CustomerStatus, CustomerCode, visitsPerMonth, BlueCount, BlueWeight "
				+ "	, RedCount, RedWeight, YellowCount, YellowWeight, CytotoxicCount, CytotoxicWeight, WhitesCount, WhitesWeight,  "
				+ "	TotalCount,TotalWeight,last_modified) "
				+ "    VALUES (source.company, source.profit_center, source.profit_center_name, source.company_code, source.plant_name, source.TypeofEstablishment, source.ServiceFrequency "
				+ "	, source.ActualVisitMonth, source.CustomerStatus, source.CustomerSAPCode, source.CustomerSAPCodeCount, source.BlueCount, source.BlueWeight, "
				+ "	source.RedCount, source.RedWeight "
				+ "	, source.YellowCount, source.YellowWeight, source.CytotoxicCount, source.CytotoxicWeight, source.WhitesCount, source.WhitesWeight,  "
				+ "	source.TotalCount,source.TotalWeight,getdate()); "
				+ " "
				+ ""; 
		
		    paramSource = new BeanPropertySqlParameterSource(dObj);		 
		    namedParamJdbcTemplate.update(qry, paramSource);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getLogsReportBMW(DashBoardWeighBridge obj) throws Exception {
		List<DashBoardWeighBridge> objsList = new ArrayList<DashBoardWeighBridge>();
		try {
			String qry = " ";
			if("msw_bilaspur_logs".contentEquals(obj.getSbu())) {
				 qry = "SELECT "
					 		+ "    CONVERT(DATE, PTCDT) AS PTCDT,"
					 		+ "		  STUFF(("
					 		+ "        SELECT DISTINCT ', ' + [VEHICLENO] "
					 		+ "        FROM [All_MSW_SITES].[dbo].[msw_bilaspur_logs] b "
					 		+ "        WHERE CONVERT(DATE, b.PTCDT) = CONVERT(DATE, a.PTCDT) "
					 		+ "        FOR XML PATH('')), 1, 2, '') AS uniqueAPIIDs FROM [All_MSW_SITES].[dbo].[msw_bilaspur_logs] a "
					 		+ "GROUP BY CONVERT(DATE, PTCDT) order by PTCDT desc"; 
				
			}else if("hyd_logs".contentEquals(obj.getSbu())) {
				 qry = "    SELECT CONVERT(DATE, PTCDT) AS PTCDT, "
				 		+ "   "
				 		+ "		  STUFF(( SELECT DISTINCT ', ' + [VEHICLENO] FROM [All_MSW_SITES].[dbo].[hyd_logs] b "
				 		+ "        WHERE CONVERT(DATE, b.PTCDT) = CONVERT(DATE, a.PTCDT) "
				 		+ "        FOR XML PATH('')), 1, 2, '') AS uniqueAPIIDs FROM [All_MSW_SITES].[dbo].[hyd_logs] a "
				 		+ "GROUP BY CONVERT(DATE, PTCDT) order by PTCDT desc "
				 		+ ""; 
			}else if("hyd_cnd_logs".contentEquals(obj.getSbu())) {
				 qry = "    SELECT  "
				 		+ "    CONVERT(DATE, PTCDT) AS PTCDT, "
				 		+ "   "
				 		+ "		  STUFF(( "
				 		+ "        SELECT DISTINCT ', ' + [VEHICLENO] "
				 		+ "        FROM [All_CnD_Sites].[dbo].[hyd_cnd_logs] b "
				 		+ "        WHERE CONVERT(DATE, b.PTCDT) = CONVERT(DATE, a.PTCDT) "
				 		+ "        FOR XML PATH('')), 1, 2, '') AS uniqueAPIIDs FROM [All_CnD_Sites].[dbo].[hyd_cnd_logs] a "
				 		+ "GROUP BY CONVERT(DATE, PTCDT) order by PTCDT desc "
					 		+ ""; 
			}else if("noida_site_log".contentEquals(obj.getSbu())) {
				 qry = "   SELECT  "
				 		+ "    CONVERT(DATE, PTCDT) AS PTCDT, "
				 		+ "   "
				 		+ "		  STUFF(( "
				 		+ "        SELECT DISTINCT ', ' + [VEHICLENO] "
				 		+ "        FROM [All_CnD_Sites].[dbo].[noida_site_log] b "
				 		+ "        WHERE CONVERT(DATE, b.PTCDT) = CONVERT(DATE, a.PTCDT) "
				 		+ "        FOR XML PATH('')), 1, 2, '') AS uniqueAPIIDs FROM [All_CnD_Sites].[dbo].[noida_site_log] a "
				 		+ "GROUP BY CONVERT(DATE, PTCDT) order by PTCDT desc "
					 		+ ""; 
			}else if("bmw_logs".contentEquals(obj.getSbu())) {
				 qry = "   SELECT  "
				 		+ "    CONVERT(DATE, PTCDT) AS PTCDT, "
				 		+ "    STUFF(( "
				 		+ "        SELECT DISTINCT ', ' + APIID "
				 		+ "        FROM ALL_BMW_Sites.dbo.bmw_logs b "
				 		+ "        WHERE CONVERT(DATE, b.PTCDT) = CONVERT(DATE, a.PTCDT) "
				 		+ "        FOR XML PATH('')), 1, 2, '') AS uniqueAPIIDs "
				 		+ "FROM ALL_BMW_Sites.dbo.bmw_logs a "
				 		+ "GROUP BY CONVERT(DATE, PTCDT) order by PTCDT desc"
					 		+ ""; 
			}
			objsList = jdbcTemplate.query( qry, new BeanPropertyRowMapper<DashBoardWeighBridge>(DashBoardWeighBridge.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return objsList;
	}

	public List<DashBoardWeighBridge> getLogsReportALL(DashBoardWeighBridge obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<BrainBox> getHydCNDList(SBU obj1, BrainBox obj, HttpServletResponse response) throws Exception {
	      List<BrainBox> menuList = null;
	      boolean flag = false;
	      boolean var6 = false;

	      try {
	         String user_id = "recgwbhydcnd";
	         String password = "Xextd1298dvyzAb";
	         String Myip = "10.100.3.11";
	         String time = " 12:00:00AM";
	         String[] IP = new String[]{"10.2.24.18", "10.2.24.81", "10.2.28.164", "196.12.46.130", "117.200.48.237", "112.133.222.124", "61.0.227.124", "14.99.138.146", "34.93.149.251", Myip};
	         if (IP.length > 0) {
	            for(int i = 0; i < IP.length; ++i) {
	               if (IP[i].contentEquals(Myip) && user_id.contentEquals(obj1.getUser_id()) && password.contentEquals(obj1.getPassword())) {
	                  flag = true;
	               }
	            }

	            System.out.println(flag);
	         }

	         if (flag) {
	            String qry = "SELECT Trno as TransactionNo, Vehicleno as VehicleNo, Material as Zone, Party as Location, Transporter as Transporter, "
	            		+ "LEFT(DateOUT, CHARINDEX(' ', DateIN + ' ') - 1) AS DateIN, RIGHT(CONVERT(varchar, Timein, 24),11) AS TimeIN, LEFT(DateOUT, CHARINDEX(' ', DateOUT + ' ') - 1) AS DateOUT,"
	            		+ " RIGHT(CONVERT(varchar, Timeout, 24),11) AS TimeOUT,Firstweight as GROSSWeight, SiteID, Secondweight as TareWeight,NetWT as NetWeight, "
	            		+ "typeofwaste AS TypeofMaterial FROM [All_CnD_Sites].[dbo].weight WITH (nolock) WHERE (Trno IS NOT NULL) AND (Vehicleno IS NOT NULL) AND "
	            		+ "(Datein IS NOT NULL)AND (Timein IS NOT NULL) AND (Firstweight IS NOT NULL) AND (Dateout IS NOT NULL) AND (Timeout IS NOT NULL) AND "
	            		+ "(Secondweight IS NOT NULL) AND (NetWT IS NOT NULL) and(SiteID is not null) AND SITEID IN('HYDCnDJMT_WB1','HYDCnDFTG_WB1') "
	            		+ " and NetWT <> '' and NetWT is not null ";
	            int arrSize = 0;
	            if (!StringUtils.isEmpty(obj1) && !StringUtils.isEmpty(obj.getTransactionNo()) && !StringUtils.isEmpty(obj.getVehicleNo())) {
	               qry = qry + " AND VEHICLENO = ? and TRNO = ? ";
	               ++arrSize;
	               ++arrSize;
	            }
	            if(!StringUtils.isEmpty(obj1) && !StringUtils.isEmpty(obj1.getFrom_date())) {
			    	qry = qry + " AND CONVERT(varchar(10), DATEOUT, 105) = CONVERT(varchar(10), ?, 105) ";
					arrSize++;
				}
	            qry = qry + " ORDER BY TRNO desc offset ? rows  fetch next 1000 rows only ";
	            arrSize++;
	            Object[] pValues = new Object[arrSize];
	            int i = 0;
	            if (!StringUtils.isEmpty(obj1) && !StringUtils.isEmpty(obj.getTransactionNo()) && !StringUtils.isEmpty(obj.getVehicleNo())) {
	               int var18 = i + 1;
	               pValues[i] = obj.getVehicleNo();
	               pValues[var18++] = obj.getTransactionNo();
	            }
	            if(!StringUtils.isEmpty(obj1) && !StringUtils.isEmpty(obj1.getOffset())) {
					pValues[i++] = obj1.getOffset();;
				}else {
					pValues[i++] ="0";
				}

	            menuList = jdbcTemplate.query(qry, pValues, new BeanPropertyRowMapper<BrainBox>(BrainBox.class));
	         }

	         return menuList;
	      } catch (Exception var16) {
	         var16.printStackTrace();
	         throw new SQLException(var16.getMessage());
	      }
	   }

	   public Object getLogsOfResults(List<BrainBox> hydList, SBU obj1) throws SQLException {
	      int count = 0;

	      try {
	         NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	         BrainBox obj;
	         String insertQry;
	         BeanPropertySqlParameterSource paramSource;
	         if (hydList.size() > 0) {
	            for(Iterator var6 = hydList.iterator(); var6.hasNext(); count = namedParamJdbcTemplate.update(insertQry, paramSource)) {
	               obj = (BrainBox)var6.next();
	               obj.setGROSSWeight(obj1.getPTC_status());
	               obj.setTareWeight(obj1.getMSG());
	               obj.setDateOUT(obj1.getUser_ip());
	               insertQry = "INSERT INTO [All_CnD_Sites].[dbo].[hyd_cnd_logs] (user_ip,weight_TRNO,VEHICLENO,PTC_status,PTCDT,MSG) values (:dateOUT,:TransactionNo,:VehicleNo,:GROSSWeight,GETDATE(),:TareWeight)  ";
	               paramSource = new BeanPropertySqlParameterSource(obj);
	            }
	         } else {
	            obj = new BrainBox();
	            obj.setGROSSWeight((String)null);
	            obj.setDateOUT(obj1.getUser_ip());
	            obj.setTareWeight(obj1.getMSG());
	            String insertQry1 = "INSERT INTO [All_CnD_Sites].[dbo].[hyd_cnd_logs] (user_ip,weight_TRNO,VEHICLENO,PTC_status,PTCDT,MSG) values (:dateOUT,:TransactionNo,:VehicleNo,:GROSSWeight,GETDATE(),:TareWeight)   ";
	            BeanPropertySqlParameterSource paramSource1 = new BeanPropertySqlParameterSource(obj);
	            count = namedParamJdbcTemplate.update(insertQry1, paramSource1);
	         }
	      } catch (Exception var9) {
	         var9.printStackTrace();
	         throw new SQLException(var9.getMessage());
	      }

	      return count;
	   }

	   public int getLogInfo(SBU obj1, BrainBox obj, List<BrainBox> hydList) throws SQLException {
	      int count = 0;

	      try {
	         BrainBox obj11;
	         String checkingLogQry;
	         if (!StringUtils.isEmpty(hydList) && hydList.size() > 0) {
	            for(Iterator var6 = hydList.iterator(); var6.hasNext(); 
	            		count = (Integer)this.jdbcTemplate.queryForObject(checkingLogQry, new Object[]{obj11.getTransactionNo(), obj11.getVehicleNo()}, Integer.class)) {
	               obj11 = (BrainBox)var6.next();
	               checkingLogQry = "SELECT count(*) from [All_CnD_Sites].[dbo].[hyd_cnd_logs] where weight_TRNO= ? and VEHICLENO= ?";
	            }
	         }

	         return count;
	      } catch (Exception var8) {
	         var8.printStackTrace();
	         throw new SQLException(var8.getMessage());
	      }
	   }
	
	
	
}
