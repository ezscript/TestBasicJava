package ma.org.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestClass {

	
	public static void main(String[] args) throws Exception {
		
		testClass();
		
		Thread.sleep(500);
		
		reflectEnum();
	}
	
	
	private static void testClass() throws Exception{
		
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
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		
	}

	//测试enum;
	private static void reflectEnum() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<AEnum> ac = AEnum.class;
		Constructor<?>[] constructors = ac.getDeclaredConstructors();
		constructors[0].setAccessible(true);
		
		AEnum e1 =(AEnum) constructors[0].newInstance(50);
		System.out.println(e1.a);
		
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
