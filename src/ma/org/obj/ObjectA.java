package ma.org.obj;

public class ObjectA {
	
	private int a = 1;
	private String s = "";
	
	public static void main(String[] args) {
		String str = "A00012,B00123,H0021";
		
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
	
	public void test (){
		int o = 10;
		switch(o){
			case 0:
				System.out.println(0);
				break;
			case 1:
				System.out.println(1);
				break;
			case 2:
				System.out.println(2);
				break;
		}
	}
}
