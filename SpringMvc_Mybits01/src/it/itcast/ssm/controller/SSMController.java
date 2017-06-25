package it.itcast.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.crmcommon.Page;
import it.itcast.ssm.pojo.BaseDict;
import it.itcast.ssm.pojo.Customer;
import it.itcast.ssm.pojo.QueryVo;
import it.itcast.ssm.service.BaseDictService;

@Controller
@RequestMapping("customer")
public class SSMController {
	@Autowired
	private BaseDictService baseDictService;
	
	@Value(value = "${b.fromType}")
	private String ft;
	@Value(value = "${b.industryType}")
	private String it;
	@Value(value = "${b.levelType}")
	private String lt;
	@RequestMapping("list.action")
	public String tojsp(QueryVo queryVo,Model model){
		List<BaseDict> fromType = baseDictService.findbasedictbyid(ft);
		model.addAttribute("fromType", fromType);
		List<BaseDict> industryType = baseDictService.findbasedictbyid(it);
		model.addAttribute("industryType", industryType);
		List<BaseDict> levelType = baseDictService.findbasedictbyid(lt);
		model.addAttribute("levelType", levelType);
		Page<Customer> page = baseDictService.findpagebyqueryvo(queryVo);
		model.addAttribute("page", page);
		model.addAttribute("custName", queryVo.getCustName());
		model.addAttribute("custSource", queryVo.getCustSource());
		model.addAttribute("custIndustry", queryVo.getCustIndustry());
		model.addAttribute("custLevel", queryVo.getCustLevel());
		return "customer";
	}
	@RequestMapping("edit.action")
	public @ResponseBody
	Customer toupdate(Integer id){
		Customer customer = baseDictService.findcustomerbyid(id);
		return customer;
	}
}
