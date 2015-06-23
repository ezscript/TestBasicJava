package ma.org.current;

import java.util.List;

import ma.org.util.ContextUtil;

public class ThreadLocalTest {
	public static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
	
	
	
	public static void main(String [] args){
		threadLocal.set("helloWorld");
		new Thread(){
			public void run(){
				threadLocal.set("one");
				System.out.println("set one");
				try {
					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				printMark();
			}
		}.start();
		
	
		
		new Thread(){
			
			public void run(){
				threadLocal.set("two");
				System.out.println("set two");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				printMark();
			}
		}.start();
		
		
		
		new Thread(new Runnable() {
			public void run() {
				threadLocal.set(new MyThread());
				ContextUtil.clear();
				System.out.println("set one");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(threadLocal.get());
			}
		}).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printMark();
		
	}



	private static void printMark() {
		System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
		
	}
}
