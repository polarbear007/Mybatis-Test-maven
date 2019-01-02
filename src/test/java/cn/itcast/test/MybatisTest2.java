package cn.itcast.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.entity.VO;
import cn.itcast.mapper.StudentMapper;


// 这个测试类必须配合《补充一下参数传递的参数名问题.doc》 笔记去看，这里的注释很少

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
	
	// 测试添加一个学生数组
	@Test
	public void test3() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		Student stu1 = new Student();
		stu1.setSname("张三");
		stu1.setAge(12);
		
		Student stu2 = new Student();
		stu2.setSname("李四");
		stu2.setAge(13);
		
		Student[] stus = {stu1, stu2};
		
		mapper.addStudents(stus);
	}
	
	
	// 测试添加一个学生List
	@Test
	public void test4() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
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
		
		mapper.addStudentList(stuList);
	}
	
	// 测试添加一个学生Collection
	@Test
	public void test5() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Collection<Student> col = new ArrayList<>();
		// 创建学生对象
		Student stu1 = new Student();
		stu1.setSname("张三");
		stu1.setAge(12);
		col.add(stu1);
		
		Student stu2 = new Student();
		stu2.setSname("李四");
		stu2.setAge(13);
		col.add(stu2);
		
		mapper.addStudentCollection(col);
	}
	
	// 测试传递一个 map 集合参数
	@Test
	public void test6() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Map<String, Object> map = new HashMap<>();
		map.put("sid", 1);
		map.put("sname", "Rose");
		map.put("age", 14);
		
		mapper.updateByMap(map);
	}
	
	// 测试传递一个 vo 对象参数 ， 纯粹只是为了演示，没有任何意义 
	@Test
	public void test7() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		// 创建vo 对象
		VO vo = new VO();
		// 创建学生对象，并添加
		Student stu = new Student();
		stu.setSname("Jack");
		stu.setAge(21);
		vo.setStu(stu);
		
		// 创建学生数组
		Student[] stus = {stu};
		vo.setStus(stus);
		
		// 创建学生对象的List集合
		List<Student> stuList = new ArrayList<>();
		stuList.add(stu);
		vo.setStuList(stuList);
		
		// 创建学生对象的 map 集合
		Map<String, Student> stuMap = new HashMap<>();
		stuMap.put("stu", stu);
		vo.setStuMap(stuMap);
		
		// 通过 mapper 代理对象执行方法
		mapper.addStudentVO(vo);
	}
	
	//传递多个参数，没有使用 @param 注解
	@Test
	public void test8() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		mapper.updateStudent1(1, "Tom", 25);
	}
	
	//传递多个参数，使用 @param 注解
	@Test
	public void test9() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		mapper.updateStudent2(1, "Tom", 25);
	}
}
