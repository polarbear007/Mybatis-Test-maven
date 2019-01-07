package cn.itcast.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.itcast.entity.Student;

public class MybatisTest8 {
	
	// 直接多次调用一个 insert 语句，也会有批量操作的功能
	@Test
	public void test() throws IOException {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				                    .build(Resources.getResourceAsStream("SqlMapConfig.xml"));
		// openSession() 方法中显式指定执行器的类型，那么通过这个 SqlSession 对象执行的sql语句就会
		//  使用 BatchExecutor 去执行
		SqlSession session = factory.openSession(ExecutorType.BATCH);
		
		// 执行批量的操作
		for (int i = 0; i < 10000; i++) {
			Student stu = new Student();
			stu.setSname("batch_name" + i);
			stu.setAge(Integer.valueOf(String.valueOf(Math.round(Math.random() * 50 + 1))));
			
			session.insert("cn.itcast.mapper.StudentMapper.addStudent", stu);
		}
		
		session.commit();
		session.close();
	}
	
	
	// 调用insert 方法，保存一个集合
	@Test
	public void test2() throws IOException {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				                    .build(Resources.getResourceAsStream("SqlMapConfig.xml"));
		SqlSession session = factory.openSession(ExecutorType.BATCH);
		List<Student> stuList = new ArrayList<>();
		// 创建学生对象
		Student stu1 = new Student();
		stu1.setSname("张三");
		stu1.setAge(12);
		stuList.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSname("李四");
		stu2.setAge(13);
		stuList.add(stu2);
		
		// 插入一个学生对象集合
		session.insert("cn.itcast.mapper.StudentMapper.addBatch2", stuList);
		
		session.commit();
		session.close();
	}
}
