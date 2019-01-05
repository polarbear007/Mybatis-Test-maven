package cn.itcast.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

// 对于拦截器，我们可以添加 @Intercepts 标签，告诉Mybatis 这是个拦截器我们想要拦截四个重要对象中的哪一个
// 要拦截目标对象的哪个方法
// 因为java 支持方法重载，所以一个类里面可能有多个方法名都是重复的，我们就需要明确参数列表，来确定是拦截哪个方法
@Intercepts(value= {
						@Signature(type=StatementHandler.class, 
								   method="prepare", 
								   args= {Connection.class, Integer.class})
					})
public class MyFirstInterceptor implements Interceptor{
	// 如果我们想要在拦截器中使用核心配置文件中给定的参数，那么就需要设置成员变量来接收参数
	private String hello;
	
	// 只有在执行我们拦截的目标对象的目标方法时，mybatis 才会帮我们执行   intercept() 方法
	// 【注意】 如果我们事先没有在  plugin() 方法生成代理对象的话，那么就不会执行 此方法！！！
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		//  proceed() 方法就是执行目标对象原来的方法，如果我们什么也不干，那么就是在这里执行proceed()方法
		System.out.println("MyFirstInterceptor...拦截了：" + invocation.getTarget()  + "对象");
		Object result = invocation.proceed();
		System.out.println("MyFirstInterceptor...没有对" + invocation.getMethod() + "方法进行任何修改...");
		return result;
	}
	
	// 【注意】如果我们想要使用拦截器，就必须生成目标对象的代理对象来替换目标对象
	// 【注意】 生成代理对象的方法我们可以自己去使用 cglig 动态代理， 也可以自己使用jdk 动态代理，但是不建议
	//        但是mybatis 框架帮我们提供了一个 Plugin.wrap() 方法，可以直接生成代理对象
	@Override
	public Object plugin(Object target) {
		System.out.println("MyFirstInterceptor正在对" + target.getClass().getName() + "进行wrap包装");
		Object wrap = Plugin.wrap(target, this);
		System.out.println("如果是注解上面配置的目标对象，就会用代理对象去替换，包装后的对象是：" + wrap.getClass().getName());
		return wrap;
	}

	// 这个方法会在加载核心配置文件、创建拦截器对象的时候执行
	// 我们可以在这个方法中使用核心配置文件的参数来初始化拦截器对象的成员变量hello
	@Override
	public void setProperties(Properties properties) {
		this.hello = properties.getProperty("hello");
	}

}
