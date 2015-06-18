package ma.org.proxy.multiple;

import java.lang.reflect.Method;

import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class TwoProxy implements InvocationHandlerExt{
	
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
		System.out.println("two start ...");
		
		result = method.invoke(target, args);
		
		System.out.println("two end ...");
		return result;
	}
}
