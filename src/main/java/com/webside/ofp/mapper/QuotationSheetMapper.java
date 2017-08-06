package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.QuotationSheetEntity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface QuotationSheetMapper extends BaseMapper<QuotationSheetEntity, Long>{
	/**
	 * 根据客户id查询报价单
	 * @param id
	 * @return
	 */
	public List<QuotationSheetEntity> findByCustomerId(String id);
	
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);
}