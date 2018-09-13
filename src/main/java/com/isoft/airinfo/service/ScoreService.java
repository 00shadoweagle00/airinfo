package com.isoft.airinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isoft.airinfo.db.entity.Consumption;
import com.isoft.airinfo.db.entity.Score;
import com.isoft.airinfo.db.entity.rank;
import com.isoft.airinfo.db.mapper.ScoreMapper;

@Service
public class ScoreService {

	
	@Autowired
	private ScoreMapper mapper;
	
	   public void save(Score t) {
	        // TODO Auto-generated method stub
	        mapper.insert(t);
	    }


	    public void update(Score t) {
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

	    public Score getByID(int id) {
	        // TODO Auto-generated method stub
	        return  mapper.selectByPrimaryKey(id);
	    }


	    public List<Score>  getUserList() {
	        // TODO Auto-generated method stub
	        return  mapper.findAll();
	    }
	    public List<Score> getRank(){
	    	
	    	return mapper.findRank();
	    }
  public List<rank> getRank1(){
	    	
	    	return mapper.findRank1();
	    }
}
