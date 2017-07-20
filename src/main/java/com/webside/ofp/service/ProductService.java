package com.webside.ofp.service;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.model.ProductEntity;

public interface ProductService extends BaseService<ProductEntity, Long>{
	public ProductEntity findByTypeId(String id);
}
