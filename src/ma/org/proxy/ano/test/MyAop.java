package ma.org.proxy.ano.test;

import java.lang.reflect.Method;

import ma.org.proxy.ano.declare.Aop;
import ma.org.proxy.ano.declare.InvocationHandlerExt;

@Aop( targetBean = {"*Service"})
public class MyAop implements InvocationHandlerExt{
	
	private Object target = null;
	
	public void setTarget(Object target) {
		this.target = target;
	}

	public Object getTarget() {
		return target;
	}	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		System.out.println("MyAop ... ¿ªÊ¼");
		result = method.invoke(target, args);
		
		System.out.println("MyAop ... ½áÊø");
		return result;
	}


	

}
