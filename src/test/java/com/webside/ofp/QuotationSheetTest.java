package com.webside.ofp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.base.BaseJunit;
import com.webside.ofp.common.config.OfpConfig;
import com.webside.ofp.common.util.OfpExportUtils;
import com.webside.ofp.model.CustomerEntity;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;
import com.webside.ofp.service.QuotationSheetService;

public class QuotationSheetTest extends BaseJunit {
	@Autowired
	private QuotationSheetService quotationSheetService;
	
	public void setQuotationSheetService(QuotationSheetService quotationSheetService) {
		this.quotationSheetService = quotationSheetService;
	}
	
	/*@Test
	public void testInsert() {
		QuotationSheetEntity quotationSheetEntity = new QuotationSheetEntity();
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(1);
		quotationSheetEntity.setCustomer(customerEntity);
		quotationSheetEntity.setInterestRateId(1);
		quotationSheetEntity.setQuotationSheetCode("20170802001");
		quotationSheetEntity.setQuotationDate(new Date());
		quotationSheetEntity.setPriceTerms("decet");
		quotationSheetEntity.setCurrency("USD");
		quotationSheetEntity.setExchangeRate(6.766);
		quotationSheetEntity.setExpirationDate(30);
		quotationSheetEntity.setPayMode("现金");
		quotationSheetEntity.setResource("hefei");
		quotationSheetEntity.setDest("calis");
		quotationSheetEntity.setDeliveryDate(60);
		quotationSheetEntity.setInsuranceCost(1.203);
		quotationSheetEntity.setForeignGreight(125.23);
		quotationSheetEntity.setHomeGreight(22.321);
		quotationSheetEntity.setOperationCost(1.5);
		quotationSheetEntity.setCommission(22.312);
		quotationSheetEntity.setRebate(1.21);
		quotationSheetEntity.setTotalCbm(21843.21);
		quotationSheetEntity.setProfit(9999.212);
		quotationSheetEntity.setSwapRate(0d);
		quotationSheetEntity.setInterestMonth(6);
		quotationSheetEntity.setCreateUser(4);
		quotationSheetEntity.setDescription("测试插入");
		List<QuotationSubSheetEntity> subSheetList = new ArrayList<QuotationSubSheetEntity>();
		QuotationSubSheetEntity quotationSubSheetEntity = new QuotationSubSheetEntity();
		quotationSubSheetEntity.setProductId(23);
		quotationSubSheetEntity.setBuyPrice(1d);
		quotationSubSheetEntity.setUsdPrice(1.15);
		quotationSubSheetEntity.setUnit("SET");
		quotationSubSheetEntity.setWeight(230);
		quotationSubSheetEntity.setVolume(390);
		quotationSubSheetEntity.setTop(75l);
		quotationSubSheetEntity.setBottom(48l);
		quotationSubSheetEntity.setHeight(104l);
		quotationSubSheetEntity.setPacking("6 pos window box,12 sets/ctn");
		quotationSubSheetEntity.setPackingRate(8d);
		quotationSubSheetEntity.setNumber(6000);
		quotationSubSheetEntity.setPackNum(625);
		quotationSubSheetEntity.setTotalcbm(33.125);
		quotationSubSheetEntity.setGw(11.5);
		quotationSubSheetEntity.setTotalGw(7187.5);
		subSheetList.add(quotationSubSheetEntity);
		QuotationSubSheetEntity quotationSubSheetEntity2 = new QuotationSubSheetEntity();
		quotationSubSheetEntity2.setProductId(22);
		quotationSubSheetEntity2.setBuyPrice(5.85);
		quotationSubSheetEntity2.setUsdPrice(1.15);
		quotationSubSheetEntity2.setUnit("SET");
		quotationSubSheetEntity2.setWeight(192);
		quotationSubSheetEntity2.setVolume(249);
		quotationSubSheetEntity2.setTop(75l);
		quotationSubSheetEntity2.setBottom(51l);
		quotationSubSheetEntity2.setHeight(88l);
		quotationSubSheetEntity2.setPacking("6 pos window box,12 sets/ctn");
		quotationSubSheetEntity2.setPackingRate(12d);
		quotationSubSheetEntity2.setNumber(8000);
		quotationSubSheetEntity2.setPackNum(666);
		quotationSubSheetEntity2.setTotalcbm(34.632);
		quotationSubSheetEntity2.setGw(15.32);
		quotationSubSheetEntity2.setTotalGw(10203.12);
		subSheetList.add(quotationSubSheetEntity2);
		quotationSheetEntity.setSubSheetList(subSheetList);
		
//		quotationSheetService.insert(quotationSheetEntity);
		
		quotationSheetService.insertSheetWithSubSheet(quotationSheetEntity);
	}*/
	
	/*@Test
	public void testUpdate() {
		QuotationSheetEntity quotationSheetEntity = new QuotationSheetEntity();
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(1);
		quotationSheetEntity.setQuotationSheetId(10);
		quotationSheetEntity.setCustomer(customerEntity);
		quotationSheetEntity.setInterestRateId(1);
		quotationSheetEntity.setQuotationSheetCode("20170723002");
		quotationSheetEntity.setQuotationDate(new Date());
		quotationSheetEntity.setPriceTerms("");
		quotationSheetEntity.setCurrency("USD");
		quotationSheetEntity.setExchangeRate(6.79);
		quotationSheetEntity.setExpirationDate(60);
		quotationSheetEntity.setPayMode("现金");
		quotationSheetEntity.setResource("上海");
		quotationSheetEntity.setDest("埃塞俄比亚");
		quotationSheetEntity.setDeliveryDate(90);
		quotationSheetEntity.setInsuranceCost(1.213);
		quotationSheetEntity.setForeignGreight(125.23);
		quotationSheetEntity.setHomeGreight(22.321);
		quotationSheetEntity.setOperationCost(1.5);
		quotationSheetEntity.setCommission(22.312);
		quotationSheetEntity.setRebate(1.21);
		quotationSheetEntity.setTotalCbm(21843.21);
		quotationSheetEntity.setProfit(9999.212);
		quotationSheetEntity.setSwapRate(0d);
		quotationSheetEntity.setInterestMonth(6);
		quotationSheetEntity.setModifyUser(4);
		quotationSheetEntity.setDescription("测试修改");
		List<QuotationSubSheetEntity> subSheetList = new ArrayList<QuotationSubSheetEntity>();
		QuotationSubSheetEntity quotationSubSheetEntity = new QuotationSubSheetEntity();
		quotationSubSheetEntity.setQuotationSubSheetId(7);
		quotationSubSheetEntity.setProductId(1);
		quotationSubSheetEntity.setBuyPrice(1d);
		quotationSubSheetEntity.setUsdPrice(1.25);
		quotationSubSheetEntity.setUnit("SET");
		quotationSubSheetEntity.setWeight(230);
		quotationSubSheetEntity.setVolume(390);
		quotationSubSheetEntity.setTop(75l);
		quotationSubSheetEntity.setBottom(48l);
		quotationSubSheetEntity.setHeight(104l);
		quotationSubSheetEntity.setPacking("6 pos window box,12 sets/ctn");
		quotationSubSheetEntity.setPackingRate(10d);
		quotationSubSheetEntity.setNumber(7000);
		quotationSubSheetEntity.setPackNum(625);
		quotationSubSheetEntity.setTotalcbm(33.1252);
		quotationSubSheetEntity.setGw(11.5);
		quotationSubSheetEntity.setTotalGw(854287.52);
		subSheetList.add(quotationSubSheetEntity);
		QuotationSubSheetEntity quotationSubSheetEntity2 = new QuotationSubSheetEntity();
		quotationSubSheetEntity2.setQuotationSubSheetId(8);
		quotationSubSheetEntity2.setProductId(2);
		quotationSubSheetEntity2.setBuyPrice(18.85);
		quotationSubSheetEntity2.setUsdPrice(1.35);
		quotationSubSheetEntity2.setUnit("SET");
		quotationSubSheetEntity2.setWeight(192);
		quotationSubSheetEntity2.setVolume(249);
		quotationSubSheetEntity2.setTop(75l);
		quotationSubSheetEntity2.setBottom(51l);
		quotationSubSheetEntity2.setHeight(88l);
		quotationSubSheetEntity2.setPacking("6 pos window box,12 sets/ctn");
		quotationSubSheetEntity2.setPackingRate(12d);
		quotationSubSheetEntity2.setNumber(8000);
		quotationSubSheetEntity2.setPackNum(666);
		quotationSubSheetEntity2.setTotalcbm(34.6332);
		quotationSubSheetEntity2.setGw(15.32);
		quotationSubSheetEntity2.setTotalGw(1021203.12);
		subSheetList.add(quotationSubSheetEntity2);
		quotationSheetEntity.setSubSheetList(subSheetList);
		
//		quotationSheetService.insert(quotationSheetEntity);
		
		quotationSheetService.updateWithSubSheet(quotationSheetEntity);
	}
	
	@Test
	public void testFindById() {
		QuotationSheetEntity quotationSheetEntity = quotationSheetService.findById(9l);
		System.out.println("code:" + quotationSheetEntity.getQuotationSheetCode());
	}
	
	@Test
	public void testFindQuotationSheetWithProducts(){
		long id = 11l;
		String basePath = "C:\\Users\\Administrator\\git\\ofp\\src\\main\\resources";
		QuotationSheetEntity quotationSheetEntity = quotationSheetService.findQuotationSheetWithProducts(id);
		System.out.println("sss:" + quotationSheetEntity.getQuotationSheetCode());
		FileOutputStream outPut;
		try {
			outPut = new FileOutputStream(new File("D:\\export.xls"));
			OfpExportUtils.exportQuotationSheetExcel(outPut, quotationSheetEntity,basePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testQueryListByPage() {
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("start",0);
		parameters.put("pageNumber",10);
		List<QuotationSheetEntity> quotationSheetEntitys = quotationSheetService.queryListByPage(parameters);
		System.out.println("size:" + quotationSheetEntitys.size());
	}*/
	
	/*@Test
	public void testExprotPdf(){
		String tempPath = OfpConfig.exportTempPath;
		System.out.println("tempPath:" + tempPath);
		File file = new File(tempPath);
		if(!file.exists()){
			file.mkdirs();
		}
		long id = 12l;
		String basePath = "C:\\Users\\Administrator\\git\\ofp\\src\\main\\resources";
		QuotationSheetEntity quotationSheetEntity = quotationSheetService.findQuotationSheetWithProducts(id);
		System.out.println("sss:" + quotationSheetEntity.getQuotationSheetCode());
		FileOutputStream outPut;
		try {
			outPut = new FileOutputStream(new File(tempPath+"\\export.xls"));
			OfpExportUtils.exportQuotationSheetExcel(outPut, quotationSheetEntity,basePath);
			OfpExportUtils.excel2Pdf(tempPath+"\\export.xls", tempPath+"\\export.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}*/
	
	@Test
	public void testExportOldExcel(){
		long id = 12l;
		String basePath = "C:\\Users\\Administrator\\git\\ofp\\src\\main\\resources";
		QuotationSheetEntity quotationSheetEntity = quotationSheetService.findQuotationSheetWithProducts(id);
		System.out.println("sss:" + quotationSheetEntity.getQuotationSheetCode());
		FileOutputStream outPut;
		try {
			outPut = new FileOutputStream(new File("D:\\export"+quotationSheetEntity.getQuotationSheetCode()+System.currentTimeMillis()+".xls"));
			OfpExportUtils.exportOldQuotationSheetExcel(outPut, quotationSheetEntity,basePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
