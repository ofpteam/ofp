package com.webside.ofp.mapper;

import com.webside.base.basemapper.BaseMapper;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends BaseMapper<ProductEntity, Long>{
	public List<ProductEntity> findByTypeId(String id);
	
	public ProductEntityWithBLOBs findByIdWithBLOBS(long id);
	
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);
}