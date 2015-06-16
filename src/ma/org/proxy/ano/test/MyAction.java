package ma.org.proxy.ano.test;

import ma.org.proxy.BookFacade;
import ma.org.proxy.ano.Action;
import ma.org.proxy.ano.AutoField;

@Action(name = "helloWorld")
public class MyAction {
	
	@AutoField
	private BookFacade myBookFacadeService =null;
	
	
	public void JustDoIt(){
		System.out.println("start JustDoIt");
		
		myBookFacadeService.addBook();
		
		System.out.println("over JustDoIt");
	}
	
}
