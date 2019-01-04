package cn.itcast.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.mapper.StudentMapper;

public class MybatisTest3 {
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
	
	// 演示 mysql 批量插入的两种思路
	@Test
	public void test1() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		List<Student> list = new ArrayList<>();
		
		Student stu1 = new Student();
		stu1.setSname("小明");
		stu1.setAge(12);
		list.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSname("小黑");
		stu2.setAge(12);
		list.add(stu2);
		
		mapper.addBatch1(list);
	}
	
	
	// 演示 mysql 批量插入的两种思路
	@Test
	public void test2() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		List<Student> list = new ArrayList<>();
		
		Student stu1 = new Student();
		stu1.setSname("小花");
		stu1.setAge(12);
		list.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSname("小白");
		stu2.setAge(12);
		list.add(stu2);
		
		mapper.addBatch2(list);
	}
	
	// 演示 批量更新操作
	@Test
	public void test3() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		List<Student> list = new ArrayList<>();
		
		Student stu1 = new Student();
		stu1.setSid(1);
		stu1.setSname("小花");
		stu1.setAge(12);
		list.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSid(2);
		stu2.setSname("小白");
		stu2.setAge(12);
		list.add(stu2);
		
		mapper.updateBatch(list);
	}
	
	// 演示 批量删除操作
	@Test
	public void test4() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		List<Student> list = new ArrayList<>();
		
		Student stu1 = new Student();
		stu1.setSid(100);
		list.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSid(200);
		list.add(stu2);
		
		Student stu3 = new Student();
		list.add(stu3);
		
		mapper.deleteBatch(list);
	}
}
