package com.isoft.airinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.mapper.CommonUserMapper;

@Service
public class CommonUserService {

	@Autowired
	private CommonUserMapper mapper;

    public void save(CommonUser t) {
        // TODO Auto-generated method stub
        mapper.insert(t);
    }


    public void update(CommonUser t) {
        // TODO Auto-generated method stub
        mapper.updateByPrimaryKey(t);
    }


    public void delete(int id) {
        // TODO Auto-generated method stub
    	 mapper.deleteByPrimaryKey(id);
    }


    public CommonUser getByID(int id) {
        // TODO Auto-generated method stub
        return  mapper.selectByPrimaryKey(id);
    }


    public List<CommonUser> findAll() {
        // TODO Auto-generated method stub
        return  mapper.findAll();
    }
}
