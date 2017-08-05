package com.webside.ofp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.webside.ofp.common.util.StrUtil;
import com.webside.ofp.model.CustomerEntity;
import com.webside.ofp.model.InterestRateEntity;
import com.webside.ofp.model.ProductEntity;
import com.webside.ofp.model.ProductEntityWithBLOBs;
import com.webside.ofp.model.ProductTypeEntity;
import com.webside.ofp.model.QuotationSheetEntity;
import com.webside.ofp.model.QuotationSubSheetEntity;
import com.webside.ofp.service.CustomerService;
import com.webside.ofp.service.InterestRateService;
import com.webside.ofp.service.ProductService;
import com.webside.ofp.service.QuotationSheetService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.PageUtil;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;

@Controller
@Scope("prototype")
@RequestMapping(value = "/quotationsheet/")
public class QuotationSheetController extends BaseController {

	@Autowired
	private QuotationSheetService quotationSheetService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;
	@Autowired
	private InterestRateService interestRateService;

	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try {
			PageUtil page = new PageUtil();
			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/ofp/quotationsheet/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dt
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters.size() < 0) {
			parameters.put("CUSTOMER_NAME", null);
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<QuotationSheetEntity> list = quotationSheetService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), true);
			List<QuotationSheetEntity> list = quotationSheetService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
			return parameters;
		}
	}

	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			Map<String, Object> parameter = new HashMap<String, Object>();
			List<InterestRateEntity> interestRateEntities = interestRateService.queryListAll(parameter);
			model.addAttribute("interestRateId", interestRateEntities.get(0).getRate());
			return Common.BACKGROUND_PATH + "/ofp/quotationsheet/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}

	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(QuotationSheetEntity quotationSheetEntity, CustomerEntity customerEntity,
			String quotationSubSheetEntities) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<QuotationSubSheetEntity> quotationSubSheetList = JSON.parseArray(quotationSubSheetEntities,
					QuotationSubSheetEntity.class);
			if (quotationSubSheetList != null && quotationSubSheetList.size() > 0) {
				StringBuilder sb = this.validateSubmitVal(quotationSheetEntity);
				if (sb.length() == 0) { // 后台验证
					// quotationSheetEntity.setSubSheetList(quotationSubSheetList);
					// 绑定客户信息
					quotationSheetEntity.setCustomer(customerEntity);
					// 获取当时的利率
					InterestRateEntity interestRateEntity = interestRateService.findById((long) 1);
					quotationSheetEntity.setInterestRateId(interestRateEntity.getRate().intValue());

					Map<String, Double> mapResult = this.CalculationProfit(quotationSheetEntity, quotationSubSheetList,
							interestRateEntity.getRate().intValue());
					// 计算利润
					double profit = mapResult.get("profit");
					// 美金总额
					double usPricteTotal = mapResult.get("usPricteTotal");
					// 换汇率 = 利润 / 美金总额
					double swapRate = profit / usPricteTotal;
					quotationSheetEntity.setProfit(profit);
					quotationSheetEntity.setSwapRate(swapRate);
					quotationSheetService.insertSheetWithSubSheet(quotationSheetEntity);
					// if (result == 1) {
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "添加成功");
					/*
					 * } else { map.put("success", Boolean.FALSE);
					 * map.put("data", null); map.put("message", "添加失败"); }
					 */
				} else {// 校验错误
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败" + sb.toString());
				}
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "请添加至少一种商品");
			}

		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 计算利润
	 * 
	 * @return
	 */
	private Map<String, Double> CalculationProfit(QuotationSheetEntity quotationSheetEntity,
			List<QuotationSubSheetEntity> quotationSubSheetEntities, Integer Rate) {
		Map<String, Double> mapResult = new HashMap<>();
		InterestRateEntity interestRateEntity = interestRateService.findById((long) 2);
		double usPricteTotal = 0;// 美金总额 = 美金单价 * 数量
		double buyPriceTotal = 0;// 收购总价
		double profit = 0;// 利润 = 美金总额*汇率 - 收购单价*数量 +
		// （收购单价*数量 ） /（1+增值税率）*退税率 – 美金总额*管理费率 – 国外运费*汇率 – 国内运费 –
		// 美金总额 * 保险费率 –美金总额*折扣率 – 收购单价 * 数量 * 计息月 * 利率-佣金

		for (QuotationSubSheetEntity quotationSubSheetEntity : quotationSubSheetEntities) {
			usPricteTotal += quotationSubSheetEntity.getUsdPrice() * quotationSubSheetEntity.getNumber();
			buyPriceTotal += quotationSubSheetEntity.getBuyPrice() * quotationSubSheetEntity.getNumber();
		}
		// 美金总额
		double p1 = usPricteTotal;
		// 佣金=佣金率*美金总额(默认0)
		double p2 = quotationSheetEntity.getCommission() * p1 / 100;
		// 保费=保险费率*美金总额(默认0)
		double p3 = quotationSheetEntity.getInsuranceCost() * p1 / 100;
		// 管理费
		double p4 = quotationSheetEntity.getOperationCost() / 100;
		// 国外运费
		double p5 = quotationSheetEntity.getForeignGreight();
		// 折扣率
		double p6 = quotationSheetEntity.getRebate();
		// 汇率
		double p7 = Rate;
		// 收购单价*数量
		double p8 = buyPriceTotal;
		// （收购单价*数量 ） /（1+增值税率）*退税率
		double p9 = buyPriceTotal / ((1 + interestRateEntity.getRate().intValue() / 100) * 13);
		// 国外运费
		double p10 = quotationSheetEntity.getHomeGreight();
		// 收购单价 * 数量 * 计息月 * 利率
		double p11 = buyPriceTotal * quotationSheetEntity.getInterestMonth() * Rate;
		profit = (p1 - p2 - p3 - p4 - p5 - p6) * p7 - p8 + p9 - p10 - p11;
		mapResult.put("profit", profit);
		mapResult.put("usPricteTotal", usPricteTotal);
		return mapResult;
	}

	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try {
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/ofp/quotationsheet/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(QuotationSheetEntity quotationSheetEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置创建者姓名
			int result = 1;
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("findProductById.html")
	@ResponseBody
	public Object findProductById(ProductEntity productEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 设置创建者姓名
			ProductEntity model = productService.findById((long) productEntity.getProductId());
			if (model != null) {
				map.put("success", Boolean.TRUE);
				map.put("data", model);
				map.put("message", "查询成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "没有查询到任何信息");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 校验提交的数据
	 * 
	 * @param productEntityWithBLOBs
	 * @param productTypeEntity
	 * @return
	 */
	private StringBuilder validateSubmitVal(QuotationSheetEntity quotationSheetEntity) {
		StringBuilder sb = new StringBuilder();
		if (quotationSheetEntity.getQuotationDate() == null) {
			sb.append("报价日期不能为空,");
		}
		if (StrUtil.noVal(quotationSheetEntity.getCurrency()) || quotationSheetEntity.getCurrency().length() > 20) {
			sb.append("币种不能为空且长度不能超过20,");
		}
		if (quotationSheetEntity.getExchangeRate() == null || quotationSheetEntity.getExchangeRate() < 0) {
			sb.append("汇率不能为空且大于0,");
		}
		if (StrUtil.noVal(quotationSheetEntity.getPayMode()) || quotationSheetEntity.getPayMode().length() > 20) {
			sb.append("付款方式不能为空且长度不能超过20,");
		}
		if (StrUtil.noVal(quotationSheetEntity.getResource()) || quotationSheetEntity.getResource().length() > 50) {
			sb.append("起运地不能为空且长度不能超过50,");
		}
		if (StrUtil.noVal(quotationSheetEntity.getDest()) || quotationSheetEntity.getDest().length() > 50) {
			sb.append("目的地不能为空且长度不能超过50,");
		}
		if (quotationSheetEntity.getDeliveryDate() < 0) {
			sb.append("交货期限大于0,");
		}
		if (quotationSheetEntity.getInsuranceCost() == null || quotationSheetEntity.getInsuranceCost() < 0) {
			sb.append("保险费不能为空，且保险费大于0,");
		}
		if (quotationSheetEntity.getInsuranceCost() == null || quotationSheetEntity.getInsuranceCost() < 0) {
			sb.append("保险费不能为空，且保险费大于0,");
		}
		if (quotationSheetEntity.getForeignGreight() == null || quotationSheetEntity.getForeignGreight() < 0) {
			sb.append("国外运费不能为空且大于0,");
		}
		if (quotationSheetEntity.getHomeGreight() == null || quotationSheetEntity.getHomeGreight() < 0) {
			sb.append("国内运费不能为空且大于0,");
		}
		if (quotationSheetEntity.getOperationCost() == null || quotationSheetEntity.getOperationCost() < 0) {
			sb.append("管理费不能为空且大于0,");
		}
		if (quotationSheetEntity.getCommission() == null || quotationSheetEntity.getCommission() < 0) {
			sb.append("佣金不能为空且大于0,");
		}
		if (quotationSheetEntity.getRebate() == null || quotationSheetEntity.getRebate() < 0) {
			sb.append("折扣率不能为空且大于0,");
		}
		if (quotationSheetEntity.getInterestMonth() == null || quotationSheetEntity.getInterestMonth() < 0) {
			sb.append("计息月不能为空且大于0,");
		}
		return sb;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	// true:允许输入空值，false:不能为空值

	/**
	 * ajax获取报价单明星
	 * 
	 * @param dt
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "getSubSheet.html")
	@ResponseBody
	public Object getSubSheet(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("iTotalRecords", 0);
		jsonMap.put("iTotalDisplayRecords", 0);
		List<QuotationSubSheetEntity> datas = new ArrayList<>();
		/*
		 * QuotationSubSheetEntity model = null; for (int i = 0; i < 57; i++) {
		 * model = new QuotationSubSheetEntity(); ProductEntityWithBLOBs product
		 * = new ProductEntityWithBLOBs(); product.setProductId(i);
		 * model.setProduct(product); model.setBuyPrice((double) 2);
		 * model.setUsdPrice((double) 20); model.setUnit("件");
		 * model.setTop((long) 22); model.setBottom((long) 123);
		 * model.setHeight((long) 33); model.setWeight(18); model.setVolume(7);
		 * model.setPacking("packing"); model.setPackingRate((double) 51);
		 * model.setNumber(2); model.setPackNum(2); model.setTotalcbm((double)
		 * 90); model.setGw((double) 2); model.setTotalGw((double) 56);
		 * 
		 * datas.add(model); }
		 */
		jsonMap.put("aaData", datas);
		/*
		 * String obj=JSON.toJSONString(jsonMap); logger.info(obj);
		 */
		return jsonMap;
	}

	/**
	 * 获取所有客户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCustomers.html", method = RequestMethod.POST)
	@ResponseBody
	public Object getCustomers() throws Exception {
		List<CustomerEntity> customers = customerService.queryListAll(null);
		Map<String, Object> map = new HashMap<String, Object>();
		if (customers != null && customers.size() > 0) {
			map.put("success", Boolean.TRUE);
			map.put("data", customers);
			map.put("message", "成功");
		} else {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "没有获取到客户列表");
		}
		return map;
	}

	/**
	 * 根据id获取客户信息
	 * 
	 * @param ids
	 * @return
	 *//*
		 * @RequestMapping("findById.html")
		 * 
		 * @ResponseBody public Object findById(CustomerEntity customerEntity) {
		 * Map<String, Object> result = new HashMap<String, Object>(); try { if
		 * (customerEntity.getCustomerId() != null) { CustomerEntity
		 * getCustomerEntity = customerService.findById((long)
		 * customerEntity.getCustomerId()); if (getCustomerEntity != null) {
		 * result.put("success", true); result.put("data", getCustomerEntity);
		 * result.put("message", "查询成功"); } else { result.put("success", false);
		 * result.put("data", null); result.put("message", "查询失败"); } }
		 * 
		 * } catch (Exception e) { throw new AjaxException(e); } return result;
		 * }
		 */
}
