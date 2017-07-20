package com.webside.ofp.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.mapper.QuotationSheetMapper;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.service.QuotationSheetService;

@Service("quotationSheetService")
public class QuotationSheetServiceImpl extends AbstractService<QuotationSheetEntity, Long> implements QuotationSheetService {
	@Autowired
	private QuotationSheetMapper quotationSheetMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(quotationSheetMapper);
	}

	@Override
	public QuotationSheetEntity findByCustomerId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
