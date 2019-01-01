package cn.itcast.test;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.mapper.StudentMapper;

public class MybatisTest2 {
	private SqlSessionFactory factory = null;
	private SqlSession sqlSession = null;
	
	@Before
	public void before() throws IOException {
		factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
		sqlSession = factory.openSession();
	}
	
	@After
	public void after() {
		sqlSession.commit();
		sqlSession.close();
	}
	
	// 先插入几条测试用的数据
	@Test
	public void test() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		Student stu1 = new Student();
		stu1.setSname("张三");
		stu1.setAge(12);
		mapper.addStudent(stu1);
		
		Student stu2 = new Student();
		stu2.setSname("李四");
		stu2.setAge(13);
		mapper.addStudent(stu2);
	}
	
	// 使用 mapper 接口进行查询
	@Test
	public void test2() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Student student = mapper.findBysid(3);
		System.out.println(student);
	}
}
