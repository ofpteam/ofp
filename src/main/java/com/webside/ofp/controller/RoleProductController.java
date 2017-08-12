package com.webside.ofp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.exception.AjaxException;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ProductTypeService;
import com.webside.role.model.RoleEntity;
import com.webside.role.service.RoleService;
import com.webside.util.PageUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/roleProduct/")
public class RoleProductController extends BaseController {
	@Autowired
	private RoleService roleService;

	@Autowired
	private ProductTypeService productTypeService;

	@RequestMapping("form.html")
	public String listUI(Model model, HttpServletRequest request) {
		return Common.BACKGROUND_PATH + "/ofp/roleproduct/form";
	}

	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllRoleList.html", method = RequestMethod.POST)
	@ResponseBody
	public Object queryAllRoleList() throws Exception {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("level", 1);
		List<RoleEntity> roles = roleService.queryAllList(parameter);
		Map<String, Object> map = new HashMap<String, Object>();
		if (roles != null && roles.size() > 0) {
			map.put("success", Boolean.TRUE);
			map.put("data", roles);
			map.put("message", "成功");
		} else {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "没有获取到角色列表");
		}
		return map;
	}

	/**
	 * 获取所有某个角色的产品权限信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProductsByRoleId.html", method = RequestMethod.POST)
	@ResponseBody
	public Object getProductsByRoleId(int roldId) throws Exception {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("r_id", roldId);
		List<Map<String, Object>> roles = roleService.getProductsByRoleId(parameter);
		Map<String, Object> map = new HashMap<String, Object>();
		if (roles != null && roles.size() > 0) {
			map.put("success", Boolean.TRUE);
			map.put("data", roles);
			map.put("message", "成功");
		} else {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "没有获取到角色列表");
		}
		return map;
	}

	/**
	 * 获取所有某个角色的产品权限信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllProductTypeList.html", method = RequestMethod.POST)
	@ResponseBody
	public Object queryAllProductTypeList() throws Exception {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("level", 1);
		List<ProductTypeEntity> ProductTypes = productTypeService.queryAllProductTypeList(parameter);
		Map<String, Object> map = new HashMap<String, Object>();
		if (ProductTypes != null && ProductTypes.size() > 0) {
			map.put("success", Boolean.TRUE);
			map.put("data", ProductTypes);
			map.put("message", "成功");
		} else {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "没有获取到角色列表");
		}
		return map;
	}

	/**
	 * 角色添加商品权限
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRoleProductTypeBatch.html", method = RequestMethod.POST)
	@ResponseBody
	public Object addRoleProductTypeBatch(int roleId, String productTypString) throws Exception {
		List<Integer> productTypeIds = new ArrayList<>();
		String[] productTyeps = productTypString.split(",");
		for (String productTyep : productTyeps) {
			productTypeIds.add(Integer.parseInt(productTyep));
			List<ProductTypeEntity> productTypes = productTypeService.findProductTypeByParentId(Integer.parseInt(productTyep));
			for(ProductTypeEntity productType:productTypes){
				productTypeIds.add(productType.getProductTypeId());
			}
		}
		Boolean flag = roleService.addRoleProductTypeBatch(roleId, productTypeIds);
		Map<String, Object> map = new HashMap<String, Object>();
		if (flag) {
			map.put("success", flag);
			map.put("data", null);
			map.put("message", "添加成功");
		} else {
			map.put("success", flag);
			map.put("data", null);
			map.put("message", "添加失败");
		}
		return map;
	}

}
