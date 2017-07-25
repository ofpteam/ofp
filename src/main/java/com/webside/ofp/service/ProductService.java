package com.webside.ofp.service;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;

public interface ProductService extends BaseService<ProductEntity, Long>{
	/**
	 * 根据产品类型查找产品
	 * @param id 产品类型id
	 * @return
	 */
	public ProductEntity findByTypeId(String id);
	
	public ProductEntityWithBLOBs findByIdWithBLOBS(String id);
	
}
