package com.webside.ofp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.webside.exception.ServiceException;
import com.webside.ofp.model.CustomerEntity;
import com.webside.ofp.model.InterestRateEntity;
import com.webside.ofp.service.CustomerService;
import com.webside.ofp.service.InterestRateService;
import com.webside.role.model.RoleEntity;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.PageUtil;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;

@Controller
@Scope("prototype")
@RequestMapping(value = "/interestRate/")
public class InterestRateController extends BaseController {

	@Autowired
	private InterestRateService interestRateService;

	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try {
			Map<String, Object> parameter = new HashMap<>();
			List<InterestRateEntity> list = interestRateService.queryListAll(parameter);
			if (list != null && list.size() > 0) {
				model.addAttribute("interestRateEntity", list.get(0));
			} else {
				model.addAttribute("interestRateEntity", null);
			}

			return Common.BACKGROUND_PATH + "/ofp/interestrate/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(InterestRateEntity interestRateEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (interestRateEntity.getRate() == null || interestRateEntity.getRate() <= 0) {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "利率不能为空且利率必须大于0");
			} else {
				int result = interestRateService.insert(interestRateEntity);
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
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(InterestRateEntity interestRateEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (interestRateEntity.getRate() == null || interestRateEntity.getRate() <= 0) {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "利率不能为空且利率必须大于0");
			} else {
				int result = interestRateService.update(interestRateEntity);
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
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

}
