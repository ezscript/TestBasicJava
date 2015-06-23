package ma.org.obj;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class TesWeakReference {
	
	public static void main(String[] args) {
	//	testBasic();
	//	testEntry();
		int a = 10;
		 AtomicInteger nextHashCode = new AtomicInteger();
		 System.out.println(nextHashCode.getAndAdd(1));
		 System.out.println(nextHashCode.getAndAdd(1));
		 System.out.println(nextHashCode.getAndAdd(1));
		 System.out.println(nextHashCode.getAndAdd(1));
	}
	

	static class MyEntry  extends WeakReference<Object> {
        Object value;
        MyEntry(Object mark, Object v) {
            super(mark);
            value = v;
        }
    } 

	
	public static void testBasic(){
		Object abc =new Object();
		WeakReference<Object> ws = new WeakReference<Object>(abc);
		clear();
		System.out.println(abc);
		System.out.println(ws.get());
		
		abc = null;
		clear();
		
		System.out.println(ws.get());
		
		System.out.println(".........................");
		WeakReference<Object> ws2 = new WeakReference<Object>(new Object());
		
		clear();
		
		System.out.println(ws2.get());
		
	}

	private static void testEntry() {
		MyEntry m = new MyEntry(new Object(), new Object());
		System.out.println(m.get());
		System.out.println(m.value);
		clear();
		System.out.println(m.get());
		System.out.println(m.value);
		
	}
	
	public static void clear(){
		System.gc();
		for(int i = 0; i< 50; i++){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
