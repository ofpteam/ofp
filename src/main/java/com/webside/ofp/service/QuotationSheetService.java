package com.webside.ofp.service;

import java.util.List;
import java.util.Map;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.model.QuotationSheetEntity;

public interface QuotationSheetService extends BaseService<QuotationSheetEntity, Long> {
	/**
	 * 根据客户id查询客户所有未删除的报价单
	 * 
	 * @param id
	 *            客户id
	 * @return
	 */
	public List<QuotationSheetEntity> findByCustomerId(String id);

	/**
	 * 修改报价单信息，包括子单信息,子单信息先批量删除，然后再批量插入
	 * 
	 * @param quotationSheetEntity
	 *            报价单对象
	 */
	public int updateWithSubSheet(QuotationSheetEntity quotationSheetEntity);
	
	/**
	 * 插入报价单信息，包括子单信息
	 * 
	 * @param quotationSheetEntity
	 */
	public int insertSheetWithSubSheet(QuotationSheetEntity quotationSheetEntity);

	/**
	 * 根据报价单id查找报价单信息，包括报价子单关联的产品信息
	 * 
	 * @param id
	 * @return
	 */
	public QuotationSheetEntity findQuotationSheetWithProducts(long id);
	/**
	 * 报价单查询页面
	 */
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);
	
	/**
	 * 查询报价单子表
	 */
	public List<Map<String, Object>> selectSubSheets(Long id);
}
