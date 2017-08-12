package com.webside.ofp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.ofp.bean.OfpTreeBean;
import com.webside.ofp.mapper.ProductMapper;
import com.webside.ofp.mapper.ProductTypeMapper;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ProductTypeService;

@Service("productTypeService")
public class ProductTypeServiceImpl extends AbstractService<ProductTypeEntity, Long>implements ProductTypeService {
	@Autowired
	private ProductTypeMapper productTypeMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(productTypeMapper);
	}

	@Override
	public List<OfpTreeBean> findAllProductTypeTree(int roleId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 这里id为0必须设置为字符串，不然查询所有
		parameter.put("parentId", "0");
		//parameter.put("roleId", roleId);//暂时去掉权限，逻辑有问题
		List<ProductTypeEntity> list = productTypeMapper.queryListAll(parameter);
		List<OfpTreeBean> allOfpTrees = new ArrayList<OfpTreeBean>();
		for (ProductTypeEntity productTypeEntity : list) {
			OfpTreeBean subOfpTreeBean = new OfpTreeBean();
			subOfpTreeBean.setText(productTypeEntity.getCnName());
			subOfpTreeBean.setId(productTypeEntity.getProductTypeId());
			/*
			 * parameter.clear(); parameter.put("productTypeId",
			 * productTypeEntity.getProductTypeId());
			 * subOfpTreeBean.setProductEntities(productMapper.queryListAll(
			 * parameter));
			 */
			// 递归查找大类下所有子类型
			this.findProductTypeTreeByParentId(subOfpTreeBean, roleId);
			allOfpTrees.add(subOfpTreeBean);
		}
		return allOfpTrees;
	}

	@Override
	public void findProductTypeTreeByParentId(OfpTreeBean ofpTreeBean, int roleId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("parentId", ofpTreeBean.getId());
		//parameter.put("roleId", roleId);//逻辑有问题

		List<ProductTypeEntity> list = productTypeMapper.queryListAll(parameter);
		if (list != null && list.size() > 0) {
			List<OfpTreeBean> subProductTypeTreeBean = new ArrayList<OfpTreeBean>();
			for (ProductTypeEntity productTypeEntity : list) {
				OfpTreeBean subOfpTreeBean = new OfpTreeBean();
				subOfpTreeBean.setText(productTypeEntity.getCnName());
				subOfpTreeBean.setId(productTypeEntity.getProductTypeId());
				this.findProductTypeTreeByParentId(subOfpTreeBean, roleId);
				subProductTypeTreeBean.add(subOfpTreeBean);
			}
			ofpTreeBean.setNodes(subProductTypeTreeBean);
		}
	}

	@Override
	public String findAllProductTypeTreeJsonString(int roleId) {
		List<OfpTreeBean> ofpTreeBeans = this.findAllProductTypeTree(roleId);
		String treeJsonString = JSON.toJSONString(ofpTreeBeans);
		return treeJsonString;
	}

	/**
	 * 获取所有产品类型
	 * @param parameter
	 * @return
	 */
	public List<ProductTypeEntity> queryAllProductTypeList(Map<String, Object> parameter) {
		List<ProductTypeEntity> ProductTypes = productTypeMapper.queryListAll(parameter);
		return ProductTypes;
	}

	@Override
	public List<ProductTypeEntity> findProductTypeByParentId(int parentId) {
		List<ProductTypeEntity> ProductTypes = productTypeMapper.findByParentId(parentId);
		return ProductTypes;
	}
}
