package ma.org.current;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MyThread {
	private Map<String,Object> map = new HashMap<String,Object>();

	public Map<String, Object> getMap() {
		return map;
	}
	
	static class MyObj<Object> extends WeakReference<Object>{

		public MyObj(Object referent) {
			super(referent);
		}
		
	};
	
	SoftReference soft;
	
	private MyObj<Object> entry = null;
	
	
	public static void main(String [] args){
		Thread.currentThread();
	}


	@Override
	public String toString() {
		return "MyThread []";
	}
	
	
	
}
