package com.isoft.airinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.entity.Consumption;
import com.isoft.airinfo.db.mapper.CommonUserMapper;
import com.isoft.airinfo.db.mapper.ConsumptionMapper;

@Service
public class ConsumptionService {

	
	@Autowired
	private ConsumptionMapper mapper;

    public void save(Consumption t) {
        // TODO Auto-generated method stub
        mapper.insert(t);
    }


    public void update(Consumption t) {
        // TODO Auto-generated method stub
        mapper.updateByPrimaryKey(t);
    }


    public void delete(int id) {
        // TODO Auto-generated method stub
    	 mapper.deleteByPrimaryKey(id);
    }

 public void delete(List<String> ids) {
    	
    	int i=mapper.deleteUserByUserIds(ids);
    	System.out.println("批量删除" + i + "条数据");

    }
    

    public Consumption getByID(int cid) {
        // TODO Auto-generated method stub
        return  mapper.selectByPrimaryKey(cid);
    }


    public List<Consumption>  getUserList() {
        // TODO Auto-generated method stub
        return  mapper.findAll();
    }
}
