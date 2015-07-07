package ma.org.reflect;

public class A {
	private String a= "a";
	
	
	private A(){
		
	}
	
	public String getA() {
		return a;
	}
	
}

enum AEnum  {
	A1(10),
	A2(20);
	
	public final int a;
	
	
	private AEnum(int a){
		this.a = a;
	}
	
	private Runnable t1 = new Runnable(){
		public void run() {
			
		}
	};
	
	
}