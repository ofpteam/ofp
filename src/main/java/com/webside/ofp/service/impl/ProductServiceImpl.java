package com.webside.ofp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.mapper.ProductMapper;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.service.ProductService;

@Service("productService")
public class ProductServiceImpl extends AbstractService<ProductEntity, Long> implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(productMapper);
	}

	@Override
	public ProductEntity findByTypeId(String id) {
		return productMapper.findByTypeId(id);
	}
	
	public ProductEntityWithBLOBs findByIdWithBLOBS(String id){
		return productMapper.findByIdWithBLOBS(id);
	}

}
