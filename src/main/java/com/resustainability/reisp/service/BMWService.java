package com.resustainability.reisp.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resustainability.reisp.dao.BMWDao;
import com.resustainability.reisp.model.BMW;
import com.resustainability.reisp.model.BrainBox;
import com.resustainability.reisp.model.DashBoardWeighBridge;
import com.resustainability.reisp.model.SBU;

@Service
public class BMWService {

	@Autowired
	BMWDao dao;

	public List<BrainBox> getHydCNDList(SBU obj1, BrainBox obj, HttpServletResponse response)  throws Exception{
		return dao.getHydCNDList(obj1,obj,response);
	}

	public Object getLogsOfResults(List<BrainBox> hydList, SBU obj1) throws Exception{
		return dao.getLogsOfResults(hydList,obj1);
		
	}

	public int getLogInfo(SBU obj1, BrainBox obj, List<BrainBox> companiesList) throws SQLException {
		return dao.getLogInfo(obj1,obj,companiesList);
		
	}

	public boolean pushBMWList(BMW obj1, BrainBox obj, List<BMW> pushedRecords, HttpServletResponse response) throws SQLException {
		return dao.pushBMWList(obj1,obj,pushedRecords,response);
	}

	public int getCountOfRecords(BMW obj1) throws SQLException{
		return dao.getCountOfRecords(obj1);
	}

	public int getCountOfBMWSummeryRecords(BMW bmw)throws SQLException{
		return dao.getCountOfBMWSummeryRecords(bmw);
	}

	public List<DashBoardWeighBridge> getTransactionsList(BMW bmw, DashBoardWeighBridge dB)throws SQLException{
		return dao.getTransactionsList(bmw,dB);
	}

	public List<DashBoardWeighBridge> getTransactionsSummeryList(BMW bmw, DashBoardWeighBridge dB) throws SQLException{
		return dao.getTransactionsSummeryList(bmw,dB);
	}

	public List<BrainBox> getNagpurCNDList(DashBoardWeighBridge obj1, BrainBox obj, HttpServletResponse response) throws SQLException{
		return dao.getNagpurCNDList(obj1,obj,response);
	}

	public int getLogInfo(DashBoardWeighBridge obj1, BrainBox obj, List<BrainBox> companiesList) throws SQLException{
		return dao.getLogInfo(obj1,obj,companiesList);
	}

	public Object getLogsOfResults(List<BrainBox> companiesList, DashBoardWeighBridge obj1) throws SQLException{
		return dao.getLogsOfResults(companiesList,obj1);
		
	}
}
