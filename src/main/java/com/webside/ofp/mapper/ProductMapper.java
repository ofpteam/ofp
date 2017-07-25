package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends BaseMapper<ProductEntity, Long>{
	public ProductEntity findByTypeId(String id);
	
	public ProductEntityWithBLOBs findByIdWithBLOBS(String id);
}