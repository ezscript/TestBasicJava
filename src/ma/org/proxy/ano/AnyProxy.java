package ma.org.proxy.ano;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ma.org.proxy.ProxyUtil;

public class AnyProxy implements InvocationHandlerExt{
	
	private Object target;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	public boolean isInit(){
		return null != target;
	}
	
	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		System.out.println("proxy start ...");
		
		result = method.invoke(target, args);
		
		System.out.println("proxy end ...");
		return result;
	}
}
