package cn.itcast.test;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.mapper.StudentMapper;

//演示一下一级缓存的数据什么时候保存到二级缓存中去
public class MybatisTest9 {
	@Test
	public void secondCacheTest() throws IOException {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				                     .build(Resources.getResourceAsReader("SqlMapConfig.xml"));
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		Student stu = mapper.findBysid(10);
		System.out.println(stu);
		// 刚查询出来的时候，缓存数据会先保存到一级缓存中，这是毫无疑问的
		// 一级缓存的位置就在sqlSession 对象中的  baseExecutor 里面的 localCache 里
		// 但是缓存数据并不会马上刷新到二级缓存中，而是先保存在事务管理器中
		// 等到事务commit 或者 session 关闭的时候，再统一刷新到二级缓存中去
		
		// 二级缓存的位置在： MapperStatement 中的 cache 里面
		
		// 【再次强调一下】 在mybatis 中，只有查询方法得到的结果才会保存到一级缓存和二级缓存
		//              增删改等操作，都不会保存到缓存中，甚至会强制清空所有相关的缓存。
		//              这一点跟  hibernate 和 jpa 是非常不一样的。
		session.close();
		
		SqlSession session2 = factory.openSession();
		StudentMapper mapper2 = session2.getMapper(StudentMapper.class);
		Student stu2 = mapper2.findBysid(10);
		System.out.println(stu2);
		session.close();
		
	}
}
