package ma.org.proxy.ano.test;

import ma.org.proxy.BookFacade;
import ma.org.proxy.ProxyUtil;
import ma.org.proxy.ano.Bean;
import ma.org.proxy.ano.BeanFactory;



public class TestAnoProxy {
	
	
	public static void main(String[] args) {
		
		test();
	}
	
	public static void test(){
		BeanFactory.init("ma.org.proxy.ano");
		Object obj = BeanFactory.getBean("helloWorld");
		System.out.println(obj);
		System.out.println(obj.getClass());
		
		((MyAction)obj).JustDoIt();
		System.out.println("xxxxxxxxxxxxxxxxxxx");
		BookFacade bf = (BookFacade)BeanFactory.getBean("myBookFacadeService");
		bf.addBook();
	}
	

}

