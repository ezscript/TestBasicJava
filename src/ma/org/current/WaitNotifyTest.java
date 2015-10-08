package ma.org.current;

public class WaitNotifyTest {
	
	
	public static void main(String[] args)throws Exception {
		
		Object t = new Object();
		
		Object to = new Object();
		for(int i = 0 ; i< 100; i++){
			Thread.sleep(50);
			new Th("n" + i,t).start();
		}
		System.out.println("start notify ");
		
		new Th("to",t).start();
		
		for(int i = 0 ; i< 100; i++){
			Thread.sleep(100);
			synchronized (t) {
				t.notify();
			}
		}
		
		synchronized (to) {
			to.notify();
		}
	}
	
	public static class Th extends Thread {
		public Object waitObj = null;
		public String name = "";
		
		public Th(String name,Object w){
			waitObj = w;
			this.name = name;
		}
		
		public void run(){
			try {
				synchronized (waitObj) {
					 waitObj.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name);
		}
	}

}
