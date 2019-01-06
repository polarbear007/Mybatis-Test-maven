package cn.itcast.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.entity.Student;
import cn.itcast.mapper.StudentMapper;

// 演示分页插件的使用
public class MybatisTest6 {
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
	
	// 测试原生的mybatis 的   sqlSession 接口如何使用分页查询
	// 不使用mapper 接口，不使用 pagehelper 插件
	// 添加一个 RowBounds() 参数，使用这个参数以后，从结果来看好像是有分页效果
	// 但是实际上是查询了所有的数据以后，只封装我们需要的数据，其他的数据全部扔掉。
	// 发送的 sql 语句仍然是：  select * from t_student
	// 【注意】 这种分页查询被称为逻辑分页。
	
	// 【注意】 如果我们使用了 pageHelper 插件的话，那么其实你这样子去用也是会有发送：  select * from t_student LIMIT ? 
	//       在查询的时候使用 limit 语法，数据库只会返回我们需要的数据，这种分页查询也被称为物理分页。
	@Test
	public void test() {
		List<Student> list = sqlSession.selectList("cn.itcast.mapper.StudentMapper.findAll", null, new RowBounds(0, 5));
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试一下分页插件的用法 
	// 使用 mapper 代理开发
	
	// 我们可以看到，使用了分页插件以后，我们发送的  sql 语句发生了变化 ：
	// select * from t_student LIMIT ? 
	//  这才是真正的分页查询
	@Test
	public void  test2() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		// 【注意】 startPage() 的第一个参数是页码，也就是我们生活中说的第几页，从1开始算
		//                     第二个参数是每页显示的数量
		PageHelper.startPage(1, 5);
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试  offsetPage() 方法
	// 使用  这个方法，同样好使：   select * from t_student LIMIT ? 
	@Test
	public void test3() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		// 【注意】  offsetPage() 的参数就跟数据库的 limit a, b   语法很像了
		//                      第一个参数是指从哪一条开始查，从0 开始 
		//                      第二个参数还是指每页显示的数量 
		PageHelper.offsetPage(0, 5);
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试 offsetPage() 和  startPage() 的返回值  Page 对象
	@Test
	public void test4() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Page<Student> page = PageHelper.offsetPage(0, 5);
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
		
		System.out.println("---------------");
		System.out.println("起始行索引： " + page.getStartRow());
		System.out.println("结束行索引：" + page.getEndRow());
		System.out.println("当前页码：" + page.getPageNum());
		System.out.println("每页显示的数量：" + page.getPageSize());
		System.out.println("总页数：" + page.getPages());
		System.out.println("总记录数：" + page.getTotal());
		
//		System.out.println(page.get(10));
	}
	
	// 测试 offsetPage() 和  startPage() 的返回值  Page 对象
	@Test
	public void test5() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Page<Student> page = PageHelper.startPage(1, 5);
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
		
		System.out.println("---------------");
		System.out.println("起始行索引： " + page.getStartRow());
		System.out.println("结束行索引：" + page.getEndRow());
		System.out.println("当前页码：" + page.getPageNum());
		System.out.println("每页显示的数量：" + page.getPageSize());
		System.out.println("总页数：" + page.getPages());
		System.out.println("总记录数：" + page.getTotal());
	}
	
	// 探究一下这个 Page 对象到底是什么？
	@Test
	public void test6() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		Page<Student> page = PageHelper.startPage(1, 5);
		List<Student> list = mapper.findAll();
		// 你会发现： 其实这个 Page 对象就是查询结果 list 对象
		//  如果你点开Page 源码查看的话，会发现这个 page 类其实是 ArrayList 集合的一个子类
		System.out.println(list == page);
	}
	
	// 测试一下 PageInfo 类
	// 这个类其实是一个工具类，提供比Page 对象更多的分页参数
	// 具有分页导航功能， 但是注意： 这个类本身并不保存具体的查询数据
	@Test
	public void test7() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(10, 3);
		List<Student> list = mapper.findAll();
		// 我们可以在封装 PageInfo 对象时候设置分页导航数
		// 如果我们不设置的话，默认的分页导航数为 8
		//PageInfo<Student> pageInfo = PageInfo.of(list, 6);
		PageInfo<Student> pageInfo = PageInfo.of(list);
		System.out.println("原生Page 类提供的api全部都有 ------------------------------");
		System.out.println("起始行索引： " + pageInfo.getStartRow());
		System.out.println("结束行索引：" + pageInfo.getEndRow());
		System.out.println("当前页码：" + pageInfo.getPageNum());
		System.out.println("每页显示的数量：" + pageInfo.getPageSize());
		System.out.println("总页数：" + pageInfo.getPages());
		System.out.println("总记录数：" + pageInfo.getTotal());
		
		System.out.println("PageInfo类特有的分页导航api-------------------------------");
		System.out.println("下一页：" + pageInfo.getNextPage());
		System.out.println("上一页：" + pageInfo.getPrePage());
		System.out.println("分页导航页数：" + pageInfo.getNavigatePages());
		// 这个方法很有用，我们可以直接拿到这个数组去前端页面填充数据即可，不需要自己算
		System.out.println("分页导航数组：" + Arrays.toString(pageInfo.getNavigatepageNums()));
		System.out.println("分页导航首页： " +  pageInfo.getNavigateFirstPage());
		System.out.println("分页导航最后页：" + pageInfo.getNavigateLastPage());
		System.out.println("是否有下一页：" + pageInfo.isHasNextPage());
		System.out.println("是否有上一页：" + pageInfo.isHasPreviousPage());
	}
	
	// 拿出分页的时候常用的参数
	@Test
	public void test8() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(10, 3);
		List<Student> list = mapper.findAll();
		PageInfo<Student> pageInfo = PageInfo.of(list);
		
		System.out.println("上一页：" + pageInfo.getPrePage());
		System.out.println("下一页：" + pageInfo.getNextPage());
		System.out.println("当前页码：" + pageInfo.getPageNum());
		System.out.println("总页数：" + pageInfo.getPages());
		System.out.println("是否有下一页：" + pageInfo.isHasNextPage());
		System.out.println("是否有上一页：" + pageInfo.isHasPreviousPage());
		System.out.println("分页导航数组：" + Arrays.toString(pageInfo.getNavigatepageNums()));
	} 
	
	// 测试PageHelper类的排序方法1
	//  SELECT * FROM t_student order by sname LIMIT ?, ? 
	@Test
	public void  test9() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(3, 5).setOrderBy("sname");
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试PageHelper类的排序方法2
	//  SELECT * FROM t_student order by sname desc LIMIT ?, ? 
	// 【注意】PageHelper 插件会把  setOrderBy() 方法里面的参数直接拼接到sql 语句中！！！
	//       我们设置的参数要直接能够够被数据库执行，这个排序字段必须写数据库对应的列名，而不要写pojo 的成员变量名
	@Test
	public void  test10() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(3, 5).setOrderBy("sname desc");
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试PageHelper类的排序方法3
	// SELECT * FROM t_student order by sid LIMIT ?, ? 
	// 【注意】 这个 setOrderBy() 方法如果执行两次，那么后面添加的参数会覆盖前面的参数
	//        如果你去看 PageHelper 的源码的话，就会发现这个类有一个 orderBy 成员变量，而我们调用的方法其实就是
	 //       这个成员变量的 setter 方法，第二次调用其实就是用一个新的值去替换原来的值
	@Test
	public void  test11() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(3, 5).setOrderBy("sname desc").setOrderBy("sid");
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 如果我们想根据多个字段进行排序的话，那么就直接用一个参数来表示
	// SELECT * FROM t_student order by sname desc, sid asc LIMIT ?, ? 
	@Test
	public void  test12() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(3, 5).setOrderBy("sname desc, sid asc");
		List<Student> list = mapper.findAll();
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// pageHelper 插件可能会出现的问题：
	// 如果我们使用 PageHelper的静态方法添加了分页参数，但是没有调用查询方法，或者是我们调用了，但是却半路上报异常了
	// 导致本地线程的那些参数没有被用到
	// 那么下次再查询的时候，可能就会莫名其妙地使用之前的分页参数
	@Test
	public void test13() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		PageHelper.startPage(3, 5);
		List<Student> list = null;
		// 其实下面的条件永远都不会成立，也就是说下面的查询语句其实就会被执行
		if(Math.random() > 1) {
			list = mapper.findAll();
		}
		
		// 因为上一个查询没有执行，所以我们保存到 本地线程的Page 对象参数并没有被使用
		// 所以留到了这次查询的时候再使用
		// 而本次查询根本就不需要这些参数，从而很可能就会出现奇怪的问题
		Student stu = mapper.findBysid(1);
		System.out.println(stu);
	}
	
	// 为了避免出现类似的问题，我们应该时刻记住PageHelper.startPage() 方法应该跟查询方法紧紧连接在一起
	@Test
	public void test14() {
		StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
		
		List<Student> list = null;
		// 我们应该把查询方法和设置参数放在一起，这样子要么一起执行，要么一起不执行，就不会出现上面的问题了
		if(Math.random() > 1) {
			PageHelper.startPage(3, 5);
			list = mapper.findAll();
		}
		
		// 因为上一个查询没有执行，所以我们保存到 本地线程的Page 对象参数并没有被使用
		// 所以留到了这次查询的时候再使用
		// 而本次查询根本就不需要这些参数，从而很可能就会出现奇怪的问题
		Student stu = mapper.findBysid(1);
		System.out.println(stu);
	}
}
