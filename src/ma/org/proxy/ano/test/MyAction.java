package ma.org.proxy.ano.test;

import ma.org.proxy.BookFacade;
import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.AutoField;

@Action(name = "helloWorld")
public class MyAction {
	
	@AutoField
	private BookFacade myBookFacadeService =null;
	
	
	public void JustDoIt(){
		System.out.println("start JustDoIt");
		
		myBookFacadeService.addBook();
		
		System.out.println("over JustDoIt");
	}
	
	public void abc(){
		System.out.println("abc");
	}
	
}
