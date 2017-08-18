package com.webside.ofp.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.exception.AjaxException;
import com.webside.exception.SystemException;
import com.webside.loginfo.model.LogInfoEntity;
import com.webside.loginfo.service.LogInfoService;
import com.webside.ofp.common.util.StrUtil;
import com.webside.ofp.model.ItemTypeEntity;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.service.ItemTypeService;
import com.webside.ofp.service.ProductTypeService;
import com.webside.role.model.RoleEntity;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.util.PageUtil;
import com.webside.dtgrid.model.Pager;

@Controller
@Scope("prototype")
@RequestMapping(value = "/producttype/")
public class ProductTypeController extends BaseController {
	@Autowired
	private ProductTypeService productTypeService;

	@RequestMapping("index.html")
	public String listUI() {
		try {
			return Common.BACKGROUND_PATH + "/ofp/producttype/index";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager) throws Exception {
		//获取当前登录用户
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		int roleId = user.getRole().getId().intValue();
		return productTypeService.findAllProductTypeTreeJsonString(roleId);
	}

	@RequestMapping("addUI.html")
	public String addUI(Model model, HttpServletRequest request, Long id) {
		// 获取大类
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("level", 2);
		List<ProductTypeEntity> list = productTypeService.queryListAll(parameter);
		model.addAttribute("productTypeList", list);
		return Common.BACKGROUND_PATH + "/ofp/producttype/form";
	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(ProductTypeEntity productTypeEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			productTypeEntity.setIsDelete(0);
			productTypeEntity.setOrderby(1);
			productTypeEntity.setParentId(productTypeEntity.getProductTypeId());
			ProductTypeEntity fathEntity=productTypeService.findById((long)productTypeEntity.getProductTypeId());
			productTypeEntity.setLevel(fathEntity.getLevel()+1);
			productTypeEntity.setCreateTime(new Date());
			productTypeEntity.setCreateUser(ShiroAuthenticationManager.getUserId().intValue());
			int result = productTypeService.insert(productTypeEntity);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping(value = "/editUI.html", method = RequestMethod.GET)
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try {
			// 获取大类
			ProductTypeEntity productTypeEntity = productTypeService.findById(id);
			model.addAttribute("productTypeEntity", productTypeEntity);
			return Common.BACKGROUND_PATH + "/ofp/producttype/form";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(ProductTypeEntity productTypeEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StrUtil.noVal(productTypeEntity.getCnName())) {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "中文名称不能为空");
			} else {
				productTypeEntity.setIsDelete(0);
				productTypeEntity.setOrderby(1);
				productTypeEntity.setModifyTime(new Date());
				productTypeEntity.setModifyUser(ShiroAuthenticationManager.getUserId().intValue());
				int result = productTypeService.update(productTypeEntity);
				if (result == 1) {
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "添加成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败");
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

}
