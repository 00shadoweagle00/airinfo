package com.isoft.airinfo.service;

import java.util.List;
import java.util.UUID;

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

    public void delete(List<String> ids) {
    	
    	int i=mapper.deleteUserByUserIds(ids);
    	System.out.println("����ɾ��" + i + "������");

    }

    public CommonUser getByID(int id) {
        // TODO Auto-generated method stub
        return  mapper.selectByPrimaryKey(id);
    }


    public List<CommonUser> getUser() {
        // TODO Auto-generated method stub
        return  mapper.findAll();
    }
    
    
    public List<CommonUser> getUserList(){
        return mapper.findAll();
    }

    /**
     * �û�����
     * @param user
     */
    public  void addUser(CommonUser user) {
        // idΪ�Զ����ɵ�uuid
    /*    String uid = UUID.randomUUID().toString().replaceAll("-", "");
       
        user.setUid(uid);*/
        mapper.insert(user);
    }
    
    /**
     * ��֤�û����Ƿ����
     * @param userName
     * @return
     */
    public Boolean findRepeatUserName(String userName) {
        int count = mapper.selectCountByUserName(userName);
        if(count == 0){
            // ���û�в鵽��˵�����û������ã�����true
            return true;
        }else{
            // ����鵽�����˵�����û��������ã�����false
            return false;
        }
    }
    
    public int findidByUserName(String userName) {
    	
    	int a=mapper.selectId(userName);
    	return a;
    }
    public CommonUser selectByUserName(String uname) {
        // TODO Auto-generated method stub
        return  mapper.selectByUserName(uname);
    }

}
