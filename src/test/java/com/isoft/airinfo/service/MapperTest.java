package com.isoft.airinfo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.isoft.airinfo.db.entity.CommonUser;
import com.isoft.airinfo.db.mapper.CommonUserMapper;

/**
 * 测试dao层
 * @author ws
 * Spring项目可以使用spring的单元测试，可以自动注入我们需要的组件
 * 1.导入SpringTest模块spring-test-4.1.7.RELEASE.jar
 * 2.@ContextConfiguration指定配置文件位置
 *   @RunWith(SpringJUnit4ClassRunner.class)指定使用spring的单元测试
 * 3.直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/config/applicationContext-mybatis.xml")
public class MapperTest {
    @Autowired
    CommonUserMapper mapper;
    
    /*@Autowired
    SqlSession sqlSession;*/

    public static void main(String[] args) {
        /*// 1.创建SpringIOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("springContext.xml");
        // 2.从容器中获取mapper
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
            System.out.println("插入成功！");
        }*/
        System.out.println("插入完成");
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
