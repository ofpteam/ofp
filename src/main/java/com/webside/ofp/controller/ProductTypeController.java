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
import com.webside.ofp.model.ItemTypeEntity;
import com.webside.ofp.service.ItemTypeService;
import com.webside.role.model.RoleEntity;
import com.webside.util.PageUtil;
import com.webside.dtgrid.model.Pager;

@Controller
@Scope("prototype")
@RequestMapping(value = "/producttype/")
public class ProductTypeController extends BaseController {

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
		Map<String, Object> parameters = null;
		return parameters;

	}

	@RequestMapping("addUI.html")
	public String addUI(Model model, HttpServletRequest request, Long id) {
		// 获取大类
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("id", "1");
		map.put("name", "杯子");
		list.add(map);
		model.addAttribute("producttype", list);
		return Common.BACKGROUND_PATH + "/ofp/producttype/form";
	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(RoleEntity roleEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			roleEntity.setCreateTime(new Date(System.currentTimeMillis()));
//			roleEntity.setStatus(0);
//			int result = roleService.insert(roleEntity);
//			if (result > 0) {
//				map.put("success", Boolean.TRUE);
//				map.put("data", null);
//				map.put("message", "添加成功");
//			} else {
//				map.put("success", Boolean.FALSE);
//				map.put("data", null);
//				map.put("message", "添加失败");
//			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try {
			/*RoleEntity roleEntity = roleService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			model.addAttribute("page", page);
			model.addAttribute("roleEntity", roleEntity);*/
			return Common.BACKGROUND_PATH + "/producttype/form";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(RoleEntity roleEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*int result = roleService.update(roleEntity);
			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}*/
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

}
