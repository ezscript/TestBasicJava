package ma.org.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class TestProxy {

	public static void main(String[] args) {
		
		BookFacadeImpl impl = new BookFacadeImpl();
		
		BookFacadeProxy proxy = new BookFacadeProxy(impl);  
		
        IBookFacade bookProxy = (IBookFacade) ProxyUtil.bind(BookFacadeImpl.class,proxy);  
        
        bookProxy.addBook();  
     //   impl.addBook();
	}


}

