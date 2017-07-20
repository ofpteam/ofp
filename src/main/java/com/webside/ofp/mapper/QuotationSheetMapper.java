package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.QuotationSheetEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface QuotationSheetMapper extends BaseMapper<QuotationSheetEntity, Long>{
	public QuotationSheetEntity findByCustomerId(String id);
}