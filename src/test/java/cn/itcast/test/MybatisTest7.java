package cn.itcast.test;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.entity.VO2;
import cn.itcast.mapper.StudentMapper;

// 演示一下如何调用存储过程
// 请配合 《补充一下存储过程的使用.doc》 来看
public class MybatisTest7 {
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
	
	// 调用一个不带参数的存储过程
	@Test
	public void test() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		List<Student> list = mapper.testProcedure1();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 调用一个带 in 类型参数的 存储过程
	@Test
	public void test2() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		List<Student> list = mapper.testProcedure2(10, 20);
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 调用一个带 out 类型参数的存储过程【错误示范】
	@Test
	public void test3() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Integer stu_count = null;
		Double age_avg = null;
		mapper.testProcedure3(stu_count, age_avg);
		
		System.out.println("学生人数： " + stu_count);   // 输出的结果是 null
		System.out.println("平均年龄：" + age_avg);      // 输出的结果是null
	}
	
	
	// 调用一个带 out 类型参数的存储过程【正确示范】
	@Test
	public void test4() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		VO2 vo = new VO2();
		mapper.testProcedure4(vo);
		
		System.out.println("学生人数： " + vo.getStu_count());   // 输出的结果是 null
		System.out.println("平均年龄：" + vo.getAge_avg());      // 输出的结果是null
	}
}
