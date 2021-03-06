package com.webside.ofp.service;

import java.util.List;
import java.util.Map;

import com.webside.base.baseservice.BaseService;
import com.webside.ofp.bean.OfpTreeBean;
import com.webside.ofp.model.ProductTypeEntity;

public interface ProductTypeService extends BaseService<ProductTypeEntity, Long> {
	/**
	 * 返回所有产品类型树对象
	 * 
	 * @return
	 */
	public List<OfpTreeBean> findAllProductTypeTree(int roleId);

	/**
	 * 根据父类查找所有子类产品，递归查询所有子节点
	 * 
	 * @param ofpTreeBean
	 *            父产品类型
	 * @return
	 */
	public void findProductTypeTreeByParentId(OfpTreeBean ofpTreeBean, int roleId);

	/**
	 * 查询产品类型树，返回json字符串
	 * 
	 * @return
	 */
	public String findAllProductTypeTreeJsonString(int roleId);
 
	
	/**
	 * 根据产品大类ID 查询大类下的小类
	 * @param parentId 父类ID
	 */
	public List<ProductTypeEntity> findProductTypeByParentId(int parentId);

	/**
	 * 获取所有产品类型
	 * @param parameter
	 * @return
	 */
	public List<ProductTypeEntity> queryAllProductTypeList(Map<String, Object> parameter);
}
