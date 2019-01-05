package cn.itcast.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts(value= {
						@Signature(type=StatementHandler.class, 
								   method="prepare", 
								   args= {Connection.class, Integer.class})
					})
public class MySecondInterceptor implements Interceptor{
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("MySecondInterceptor...拦截了：" + invocation.getTarget()  + "对象");
		Object result = invocation.proceed();
		System.out.println("MySecondInterceptor...没有对" + invocation.getMethod() + "方法进行任何修改...");
		return result;
	}
	
	@Override
	public Object plugin(Object target) {
		System.out.println("MySecondInterceptor正在对" + target.getClass().getName() + "进行wrap包装");
		Object wrap = Plugin.wrap(target, this);
		System.out.println("如果是注解上面配置的目标对象，就会用代理对象去替换，包装后的对象是：" + wrap.getClass().getName());
		return wrap;
	}

	
	@Override
	public void setProperties(Properties properties) {
	}
}
