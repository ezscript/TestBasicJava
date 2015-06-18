package ma.org.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestProxyTwo {

	public static void main(String[] args) {
		MyHandler handler = new MyHandler();
		
		
		handler.setOptBegin(new OptBegin() {
			public void execute(Object obj, Method method, Object[] args) {
				
			}
		});
		handler.setOptEnd(new OptEnd() {
			public void execute(Object obj, Method method, Object result, Object[] args) {
				
			}
		});

	}

}//end TestProxyTwo



class MyHandler implements InvocationHandler{
	private Object target = null;
	
	private OptBegin optBegin= null;
	
	private OptEnd optEnd = null;
	
	public void setTarget(Object target){
		this.target = target;
	}
	public Object getTarget(){
		return target;
	}
	public OptBegin getOptBegin() {
		return optBegin;
	}
	public void setOptBegin(OptBegin optBegin) {
		this.optBegin = optBegin;
	}
	public OptEnd getOptEnd() {
		return optEnd;
	}
	public void setOptEnd(OptEnd optEnd) {
		this.optEnd = optEnd;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		Object target = this.getTarget();
		OptBegin optBegin = this.getOptBegin();
		OptEnd optEnd = this.getOptEnd();
		if(optBegin != null){
			optBegin.execute(target, method, args);
		}
		result = method.invoke(target, args);
		
		if(optEnd != null){
			optEnd.execute(target, method,result, args);
		}
		return result;
	}
	
}//end MyInvocationHandler


interface OptBegin{
	public void execute(Object obj, Method method, Object[] args);
}

interface OptEnd{
	public void execute(Object obj, Method method, Object result,Object[] args);
}