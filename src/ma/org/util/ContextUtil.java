package ma.org.util;

public class ContextUtil {
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
