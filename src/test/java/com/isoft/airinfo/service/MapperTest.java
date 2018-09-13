package com.isoft.airinfo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.mapper.CommonUserMapper;

/**
 * ����dao��
 * @author ws
 * Spring��Ŀ����ʹ��spring�ĵ�Ԫ���ԣ������Զ�ע��������Ҫ�����
 * 1.����SpringTestģ��spring-test-4.1.7.RELEASE.jar
 * 2.@ContextConfigurationָ�������ļ�λ��
 *   @RunWith(SpringJUnit4ClassRunner.class)ָ��ʹ��spring�ĵ�Ԫ����
 * 3.ֱ��autowiredҪʹ�õ��������
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/config/applicationContext-mybatis.xml")
public class MapperTest {
    @Autowired
    CommonUserMapper mapper;
    
    /*@Autowired
    SqlSession sqlSession;*/

    public static void main(String[] args) {
        /*// 1.����SpringIOC����
        ApplicationContext ioc = new ClassPathXmlApplicationContext("springContext.xml");
        // 2.�������л�ȡmapper
        UserMapper bean = ioc.getBean(UserMapper.class);*/
    }
    
    
    @Test
    public void testC(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + mapper);
        CommonUser user = new CommonUser();
    

    
        user.setUname("zzz");
        user.setUpassword("zzz456");
  
        mapper.insert(user);
        /*for(int i = 0;i < 500;i++){
            String uid = UUID.randomUUID().toString().substring(0, 5);
            userMapper.insertSelective(new User(uid, uid, uid, i));
            System.out.println("����ɹ���");
        }*/
        System.out.println("�������");
    }
    
    @Test
    public void testCs(){
        /*UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for(int i = 0;i < 1000;i++){
            String uid = UUID.randomUUID().toString().substring(0, 5);
            String id = UUID.randomUUID().toString().substring(5,10);
            userMapper.insertSelective(new User(id, uid, uid, i));
        }*/
    }
}
