package ma.org.obj;

/**
 * 继承
 */
public class TestExtends {

	public static void main(String[] args) {
		ParentOne po = new ParentOne();
		System.out.println(po.add(1, 1));
		ChildOne co = new ChildOne();
		System.out.println(co.add(1, 1));
		
	}

	
	
}

class ParentOne{
	
	private int a;
	private int b;
	public int add(String a, int b){
		this.b  = b;
		return add();
	}
	
	public int add(int a, int b){
		this.a = a;
		this.b  = b;
		return add();
	}

	public int add(){
		return a +b;
	}

}
class ChildOne extends ParentOne{
	
	
	public int a(){
		return 36;
	}
}
