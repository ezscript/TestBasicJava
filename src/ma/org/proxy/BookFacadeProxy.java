package ma.org.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookFacadeProxy implements InvocationHandler {
	
	private Object target;
	
	public BookFacadeProxy() {
		super();
	}

	
	public BookFacadeProxy(Object target) {
		super();
		this.target = target;
	}

	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	/**
	 * 调用方法
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		System.out.println("开始");
		// 执行方法
		result = method.invoke(target, args);
		System.out.println("结束");
		return result;
	}
}
