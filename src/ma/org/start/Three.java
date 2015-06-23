package ma.org.start;

import java.util.concurrent.atomic.AtomicBoolean;

public class Three {
	private static final Three three = new Three();
	
	private boolean isInit = false;
	
	private String name = "hello";
	
	public static  void init(){
		synchronized(three){
			if(three.isInit)
				return;
			three.isInit = true;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		

	}

}
