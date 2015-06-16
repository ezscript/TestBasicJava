package ma.org.proxy.ano.test;

import ma.org.proxy.BookFacade;
import ma.org.proxy.ano.Service;

@Service(name = "myBookFacadeService" )
public class MyBookFacadeService implements BookFacade{

	public void addBook() {
		System.out.println("MyBookFacadeService.addBook()");
		
	}

}
