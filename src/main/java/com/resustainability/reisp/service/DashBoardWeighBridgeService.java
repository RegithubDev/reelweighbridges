package com.resustainability.reisp.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resustainability.reisp.dao.DashBoardWeighBridgeDao;
import com.resustainability.reisp.model.BrainBox;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.SBU;
@Service
public class DashBoardWeighBridgeService {
	@Autowired
	DashBoardWeighBridgeDao dao;

	public List<DashBoardWeighBridge> getDashboards(DashBoardWeighBridge obj) throws Exception{
		return dao.getDashboards(obj);
	}

	public List<DashBoardWeighBridge> getSBUsList(DashBoardWeighBridge obj)  throws Exception{
		return dao.getSBUsList(obj);
	}

	public List<DashBoardWeighBridge> getDashboardsDaily(DashBoardWeighBridge obj) throws Exception {
		return dao.getDashboardsDaily(obj);
	}

	public List<DashBoardWeighBridge> getMSWDataWithSiteID(DashBoardWeighBridge obj)  throws Exception {
		return dao.getMSWDataWithSiteID(obj);
	}

	public List<DashBoardWeighBridge> getCNDDataWithSiteID(DashBoardWeighBridge obj)   throws Exception {
		return dao.getCNDDataWithSiteID(obj);
	}

	public List<DashBoardWeighBridge> getProjectssList(DashBoardWeighBridge obj)    throws Exception {
		return dao.getProjectssList(obj);
	}

	public List<DashBoardWeighBridge> getLogsList(DashBoardWeighBridge obj) throws Exception {
		return dao.getLogsList(obj);
	
	}

	public List<DashBoardWeighBridge> getWeighBridgeList(DashBoardWeighBridge obj) {
		return dao.getWeighBridgeList(obj);
	}

	public List<DashBoardWeighBridge> getMSWManualDataWithSiteID(DashBoardWeighBridge obj) throws Exception {
		return dao.getMSWManualDataWithSiteID(obj);
	}

	public List<DashBoardWeighBridge> getTransactionsList(DashBoardWeighBridge obj)  throws Exception {
		return dao.getTransactionsList(obj);
	}

	public List<DashBoardWeighBridge> getMonthList(DashBoardWeighBridge obj) throws Exception {
		return dao.getMonthList(obj);
	}

	public List<DashBoardWeighBridge> getCustomerCodeList(DashBoardWeighBridge obj) throws Exception {
		return dao.getCustomerCodeList(obj);
	}

	public List<DashBoardWeighBridge> getTransactionsList1(DashBoardWeighBridge obj) throws Exception {
		return dao.getTransactionsList1(obj);
	}

	public List<DashBoardWeighBridge> getLogsReportBMW(DashBoardWeighBridge obj) throws Exception {
		return dao.getLogsReportBMW(obj);
	}

	public List<DashBoardWeighBridge> getLogsReportALL(DashBoardWeighBridge obj) throws Exception {
		return dao.getLogsReportALL(obj);
	}
	
	public List<BrainBox> getHydCNDList(SBU obj1, BrainBox obj, HttpServletResponse response) throws Exception {
      return this.dao.getHydCNDList(obj1, obj, response);
   }

   public Object getLogsOfResults(List<BrainBox> hydList, SBU obj1) throws Exception {
      return this.dao.getLogsOfResults(hydList, obj1);
   }

   public int getLogInfo(SBU obj1, BrainBox obj, List<BrainBox> hydList) throws SQLException {
      return this.dao.getLogInfo(obj1, obj, hydList);
   }
}
