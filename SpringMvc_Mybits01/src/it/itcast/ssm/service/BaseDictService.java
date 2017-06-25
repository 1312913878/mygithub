package it.itcast.ssm.service;

import java.util.List;

import cn.itcast.crmcommon.Page;
import it.itcast.ssm.pojo.BaseDict;
import it.itcast.ssm.pojo.Customer;
import it.itcast.ssm.pojo.QueryVo;

public interface BaseDictService {
	List<BaseDict> findbasedictbyid(String typeCode);
	
	Page<Customer> findpagebyqueryvo(QueryVo queryVo);
	
	public Customer findcustomerbyid(Integer id);
}
