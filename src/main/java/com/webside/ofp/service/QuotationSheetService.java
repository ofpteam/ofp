package com.webside.ofp.service;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.model.QuotationSheetEntity;

public interface QuotationSheetService extends BaseService<QuotationSheetEntity, Long>{
	public QuotationSheetEntity findByCustomerId(String id);
}
