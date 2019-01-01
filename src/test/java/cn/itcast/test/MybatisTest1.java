package cn.itcast.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Order;
import cn.itcast.entity.OrderDetail;
import cn.itcast.entity.User;

// 演示使用 SqlSession 对象的原生方法，对数据库进行增删改查。
// 在真实的开发中，这种方式需要自己写dao 层的实现类，然后在每个dao方法中去获取 SqlSession 对象，然后对数据库进行操作。
// 使用这种方式的开发的缺点：
//    1、 每次都要自己执行  select / insert / update / delete 方法，而这些方法其实跟映射文件里面的标签是对应的（冗余）。
//    2、 绑定的参数只能是一个 object ，不能对参数的类型进行约束，可能会出现类型转换错误。
//    3、 查询方法的返回值类型默认也是一个 Object, 不能对返回值类型进行约束，可能会出现类型转换错误。
public class MybatisTest1 {
	private SqlSessionFactory factory = null;
	private SqlSession session = null;
	
	@Before
	public void before() throws IOException {
		factory = new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
		session = factory.openSession();
	}
	
	@After
	public void after() {
		session.commit();
		session.close();
	}
	
	// 演示最基本的单表查询
	@Test
	public void test1() {
		User user = session.selectOne("user.findUserByUid", 1);
		System.out.println(user.getUsername() + "---" + user.getBirthday() + "---" + user.getAddress());
	}
	
	// 演示单表模糊查询
	@Test
	public void test2() {
		List<User> list = session.selectList("user.findUserByUsername", "王");
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user.getUsername() + "---" + user.getBirthday() + "---" + user.getAddress());
			}
		}
	}
	
	// 测试插入一条数据
	@Test
	public void test3() {
		User user = new User();
		user.setUsername("张三丰");
		user.setBirthday(new Date());
		user.setSex('男');
		user.setAddress("武当山");
		
		session.insert("user.addUser", user);
	}
	
	// 测试插入一条数据后，获取这条数据的自增 id 
	@Test
	public void test4() {
		User user = new User();
		user.setUsername("张君宝");
		user.setBirthday(new Date());
		user.setSex('男');
		user.setAddress("武当山");
		
		session.insert("user.addUser", user);
		
		System.out.println(user.getUid());
	}
	
	
	// 测试一下一对一关联查询
	// 通过user 表的主键查询相关的订单
	@Test
	public void test5() {
		List<Order> list = session.selectList("order.findByUserId", 1);
		if(list != null && list.size() > 0) {
			for (Order order : list) {
				System.out.println(order);
			}
		}
	}
	
	// 测试一下一对多关联查询
	// 通过orders 表的主键查询相关的用户信息
	@Test
	public void test6() {
		User user = session.selectOne("user.findUserByOid", 3);
		System.out.println(user);
	}
	
	// 测试一下多对多的关联查询
	// 通过 user 的主键查询买过的商品
	@Test
	public void test7() {
		User user = session.selectOne("user.findItemByUserId", 1);
		if(user != null && user.getOrderList() != null ) {
			for (Order order : user.getOrderList()) {
				if(order.getOrderDetailList() != null) {
					for (OrderDetail orderDetail : order.getOrderDetailList() ) {
						System.out.println(orderDetail.getItem());
					}
				}
			}
		}
	}
}
