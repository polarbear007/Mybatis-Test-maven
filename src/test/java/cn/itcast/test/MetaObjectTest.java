package cn.itcast.test;

import java.util.Date;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.junit.Test;

import cn.itcast.entity.Order;
import cn.itcast.entity.User;

public class MetaObjectTest {
	// 测试赋值
	@Test
	public void test() {
		Order order = new Order();
		MetaObject metaObject = MetaObject.forObject(order,
							new DefaultObjectFactory(), 
							new DefaultObjectWrapperFactory(),
							new DefaultReflectorFactory());
		
		// 给order 的属性赋值
		metaObject.setValue("oid", 123);
		// 给order 的 user 对象的属性赋值
		metaObject.setValue("user.uid", 1);
		
		System.out.println(order.getOid());
		System.out.println(order.getUser());
		
	}
	
	// 测试取值
	@Test
	public void test2() {
		User user = new User();
		user.setUsername("rose");
		user.setSex('女');
		
		Order order = new Order();
		order.setOid(12);
		order.setCreateTime(new Date());
		order.setUser(user);
		// 把 order 对象封装成 metaObject 对象
		MetaObject metaObject = MetaObject.forObject(order,
				new DefaultObjectFactory(), 
				new DefaultObjectWrapperFactory(),
				new DefaultReflectorFactory());
		
		// 测试根据属性名从 metaObject 对象中取值
		System.out.println(metaObject.getValue("user.username"));
		System.out.println(metaObject.getValue("user.sex"));
		System.out.println(metaObject.getValue("oid"));
		
	}
}
