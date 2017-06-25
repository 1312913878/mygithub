package it.itcast.ssm.dao;

import java.util.List;

import it.itcast.ssm.pojo.BaseDict;
import it.itcast.ssm.pojo.Customer;
import it.itcast.ssm.pojo.QueryVo;


public interface BaseDictMapper {
	List<BaseDict> findbasedictbyid(String typeCode);
	
	public Integer findcountbyid(QueryVo queryVo);
	
	public List<Customer> findcustomerbyqueryvo(QueryVo queryVo);
	
	public Customer findcustomerbyid(Integer id);
}
