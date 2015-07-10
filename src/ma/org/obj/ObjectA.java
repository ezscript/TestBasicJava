package ma.org.obj;

public class ObjectA {
	
	private int a = 1;
	private String s = "";
	
	public static void main(String[] args) {
		
		ObjectA a = new ObjectA();
		
		System.out.println(a.add());
		
		System.out.println(a.getA());
		
	}

	
	public int add(){
		return a++;
	}
	
	public int getA(){
		return a;
	}

}
