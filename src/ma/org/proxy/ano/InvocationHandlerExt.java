package ma.org.proxy.ano;

import java.lang.reflect.InvocationHandler;

public interface InvocationHandlerExt extends InvocationHandler {
	void setTarget(Object target);
	Object getTarget();
}
