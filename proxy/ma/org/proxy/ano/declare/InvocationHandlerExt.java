package ma.org.proxy.ano.declare;

import java.lang.reflect.InvocationHandler;

public interface InvocationHandlerExt extends InvocationHandler {
	void setTarget(Object target);
	Object getTarget();
}
