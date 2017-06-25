package it.itcast.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.crmcommon.Page;
import it.itcast.ssm.dao.BaseDictMapper;
import it.itcast.ssm.pojo.BaseDict;
import it.itcast.ssm.pojo.Customer;
import it.itcast.ssm.pojo.QueryVo;
@Service
public class BaseDictServiceImpl implements BaseDictService{
	@Autowired
	private BaseDictMapper baseDictMapper;
	@Override
	public List<BaseDict> findbasedictbyid(String typeCode) {
		List<BaseDict> list = baseDictMapper.findbasedictbyid(typeCode);
		return list;
	}
	@Override
	public Page<Customer> findpagebyqueryvo(QueryVo queryVo) {
		Page<Customer> page = new Page<>();
		if(queryVo.getPage()==null){
			queryVo.setPage(1);
		}
		page.setSize(5);
		queryVo.setSize(5);
		queryVo.setStart((queryVo.getPage() - 1) * queryVo.getSize());
		page.setPage(queryVo.getPage());
		page.setTotal(baseDictMapper.findcountbyid(queryVo));
		page.setRows(baseDictMapper.findcustomerbyqueryvo(queryVo));
		return page;
	}
	@Override
	public Customer findcustomerbyid(Integer id) {
		Customer customer = baseDictMapper.findcustomerbyid(id);
		return customer;
	}
	

}
