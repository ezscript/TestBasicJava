package ma.org.obj;

/**
 * 重载
 */
public class TestOverload {
	
	public static void print(Object obj){
		System.out.println("print Obj");
	}
	
	public static void print(String str){
		System.out.println("print String");
	}
	
	public static void print(Integer i){
		System.out.println("print Integer");
	}
	
	public static void main(String [] args){
		Object[] oo= new Object[3];
		oo[0] = new Object();
		oo[1] = "11";
		oo[2] = 12;
		start(oo);
	}

	private static void start(Object[] oo) {
		for(Object o : oo){
			print(o);
		}
		
	}
}
