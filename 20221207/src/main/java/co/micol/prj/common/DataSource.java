package co.micol.prj.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DataSource { //싱글톤 클래스로 만들어야한다  private로 만들어야함
	private static SqlSessionFactory sqlSessionFactory; 
	private DataSource() {};  //생성자: 내클래스명과 동일한 메소드명 
	
	public static SqlSessionFactory getInstance() {
		//자기가 인스턴스 한다 
		//인터페이스 반드시 알아야한다
		//추상클래스 :하나이상 원형메소드 ,자기자신 초기화 못함,구현체  
		String resource = "config/mybatis-config.xml";
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
}
