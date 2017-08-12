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
	/**
	 * 报价单查询页面
	 */
	public List<Map<String, Object>> selectByPage(Map<String, Object> paramet);
	
	/**
	 * 根据产品id集合批量查找产品集合
	 * @param productIds 产品ID集合
	 * @return List<ProductEntityWithBLOBs> 产品信息
	 */
	public List<ProductEntityWithBLOBs> findByIdsWithBLOBS(List<Integer> productIds);
}