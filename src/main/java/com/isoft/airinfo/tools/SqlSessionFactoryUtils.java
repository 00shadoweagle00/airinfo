package com.isoft.airinfo.tools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//和spring整合时不需要此工具类的
public class SqlSessionFactoryUtils {

	
	private static SqlSessionFactory sqlSessionFactory;
	private static SqlSession sqlSession;
	
	static {
		
		if(sqlSessionFactory==null) {
			InputStream resourceAsStream;
			try {
				resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
				sqlSessionFactory=new SqlSessionFactoryBuilder().build(resourceAsStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("读取配置文件失败");
			}							
			
		}
		
	}
	
	public static SqlSession getSqlSession(boolean commit) {
		return sqlSessionFactory.openSession(commit);
		
	}
	
}
