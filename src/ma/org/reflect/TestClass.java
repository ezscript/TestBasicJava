package ma.org.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestClass {

	
	public static void main(String[] args) throws Exception {
		AccessibleObject ao;
		A a = new A();
		Class<A> c = A.class;
		Field field = c.getDeclaredField("a");
		field.setAccessible(true);
		field.set(a, "123456789");
		field.setAccessible(false);
		
		Method m = c.getMethod("getA", null);
		m.setAccessible(false);
		
		System.out.println(m.invoke(a, null));
		
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
