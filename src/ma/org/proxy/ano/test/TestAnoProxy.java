package ma.org.proxy.ano.test;

import ma.org.proxy.BookFacade;
import ma.org.proxy.ano.BeanFactory;



public class TestAnoProxy {
	
	
	public static void main(String[] args) {
		BeanFactory.init("ma.org.proxy.ano");
		MyAction myAction = (MyAction)BeanFactory.getBean("helloWorld");
		myAction.JustDoIt();
		
		System.out.println("xxxxxxxxxxxxxxxxxxx");
		BookFacade bf = (BookFacade)BeanFactory.getBean("myBookFacadeService");
		bf.addBook();
	}

}

