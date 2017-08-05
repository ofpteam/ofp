package com.webside.ofp.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.mapper.ProductMapper;
import com.webside.ofp.mapper.QuotationSheetMapper;
import com.webside.ofp.mapper.QuotationSubSheetMapper;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;
import com.webside.ofp.service.ProductService;
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
	
	@Autowired
	private QuotationSubSheetMapper quotationSubSheetMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public List<QuotationSheetEntity> findByCustomerId(String id) {
		// TODO Auto-generated method stub
		return quotationSheetMapper.findByCustomerId(id);
	}

	@Override
	public void updateWithSubSheet(QuotationSheetEntity quotationSheetEntity) {
		quotationSheetMapper.update(quotationSheetEntity);
		quotationSubSheetMapper.deleteBySheetIdPhysical(quotationSheetEntity.getQuotationSheetId());
		quotationSubSheetMapper.insertBatch(quotationSheetEntity.getSubSheetList());
	}
	
	public int insertSheetWithSubSheet(QuotationSheetEntity quotationSheetEntity){
		int i = quotationSheetMapper.insert(quotationSheetEntity);
		for(QuotationSubSheetEntity quotationSubSheetEntity:quotationSheetEntity.getSubSheetList()){
			quotationSubSheetEntity.setQuotationSheetId(quotationSheetEntity.getQuotationSheetId());
		}
		return quotationSubSheetMapper.insertBatch(quotationSheetEntity.getSubSheetList());
	}
	
	@Override
	public QuotationSheetEntity findQuotationSheetWithProducts(long id){
		QuotationSheetEntity quotationSheetEntity = quotationSheetMapper.findById(id);
		List<QuotationSubSheetEntity> subList = quotationSheetEntity.getSubSheetList();
		for(QuotationSubSheetEntity quotationSubSheetEntity : subList){
			if(quotationSubSheetEntity.getProductId() != null){
				ProductEntityWithBLOBs product = productService.findByIdWithBLOBS(quotationSubSheetEntity.getProductId());
				quotationSubSheetEntity.setProduct(product);
			}
		}
		return quotationSheetEntity;
	}

}
