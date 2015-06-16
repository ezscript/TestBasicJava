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
	 * ���÷���
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		System.out.println("��ʼ");
		// ִ�з���
		result = method.invoke(target, args);
		System.out.println("����");
		return result;
	}
}
