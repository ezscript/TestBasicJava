package ma.org.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class TestClass {

	
	public static void main(String[] args) throws Exception {
		AccessibleObject ao;
		A a = new A();
		Class c = A.class;
		Field field = c.getDeclaredField("a");
	//	field.setAccessible(true);
		field.set(a, "123");
		
		System.out.println(a.getA());
	}
	
	
	
	/**
	 * Object 
	 *    -- Class
	 * 
	 * Object
	 *    -- AccessibleObject
	 *       -- Constructor<T>
	 *       -- Field
	 *       -- Method
	 */

}
