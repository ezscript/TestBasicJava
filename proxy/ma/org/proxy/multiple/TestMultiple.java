package ma.org.proxy.multiple;

import ma.org.proxy.IBookFacade;
import ma.org.proxy.BookFacadeImpl;
import ma.org.proxy.ProxyUtil;
import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class TestMultiple {

	public static void main(String[] args) {
		BookFacadeImpl impl = new BookFacadeImpl();

        OneProxy one = new OneProxy();
        one.setTarget(impl);
        IBookFacade oneProxy = (IBookFacade) ProxyUtil.bind(BookFacadeImpl.class,one);  
        
        
        TwoProxy two = new TwoProxy();
        two.setTarget(oneProxy);
        
        Object twoProxy =  ProxyUtil.bind(BookFacadeImpl.class,two);  
        System.out.println(twoProxy);
        System.out.println(twoProxy.getClass());
        ((IBookFacade)twoProxy).addBook();  
	}

	

}
