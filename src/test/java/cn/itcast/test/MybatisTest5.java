package cn.itcast.test;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.mapper.StudentMapper;

// 测试一下 Mybatis 的插件要怎么去写
// 请配合《补充一下Mybatis插件原理.doc》 笔记去看， 测试类本身没多大意义
public class MybatisTest5 {
	@Test
	public void test1() throws IOException {
		// 创建工厂对象
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				                     .build(Resources.getResourceAsStream("SqlMapConfig.xml"));
		// 获取 session 对象
		SqlSession session = factory.openSession();
		// 获取mapper 接口的代理对象
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		// 使用 mapper 代理对象执行查询方法
		Student stu = mapper.findBysid(10);
		System.out.println(stu);
//		mapper.test();
		
		// 提交事务
		session.commit();
		// 关闭session 对象
		session.close();
	}
}
