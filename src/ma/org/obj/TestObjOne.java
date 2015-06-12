package ma.org.obj;

public class TestObjOne {

	public static void main(String[] args) {
		Object à¸;
		ParentOne po = new ParentOne();
		System.out.println(po.add(1, 1));
		
		ChildOne co = new ChildOne();
		
		System.out.println(co.add(1, 1));
	}

	
	
}

class ParentOne{
	
	private int a;
	private int b;
	
	
	public int add(int a, int b){
		this.a = a;
		this.b  = b;
		return a();
	}

	public int a(){
		return a +b;
	}

}
class ChildOne extends ParentOne{
	
	
	public int a(){
		return 36;
	}
}
