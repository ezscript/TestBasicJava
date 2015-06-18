package ma.org.proxy.multiple;

import java.lang.reflect.Method;

import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class OneProxy implements InvocationHandlerExt{
	
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
		System.out.println("one start ...");
		
		result = method.invoke(target, args);
		
		System.out.println("one end ...");
		return result;
	}
}
