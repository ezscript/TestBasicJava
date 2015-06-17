package ma.org.proxy.multiple;

import ma.org.proxy.BookFacade;
import ma.org.proxy.BookFacadeImpl;
import ma.org.proxy.ProxyUtil;
import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class TestMultiple {

	public static void main(String[] args) {
		BookFacadeImpl impl = new BookFacadeImpl();

        OneProxy one = new OneProxy();
        one.setTarget(impl);
        BookFacade oneProxy = (BookFacade) ProxyUtil.bind(BookFacadeImpl.class,one);  
        
        
        TwoProxy two = new TwoProxy();
        two.setTarget(oneProxy);
        
        Object twoProxy =  ProxyUtil.bind(BookFacadeImpl.class,two);  
        System.out.println(twoProxy);
        System.out.println(twoProxy.getClass());
        ((BookFacade)twoProxy).addBook();  
	}

	

}
