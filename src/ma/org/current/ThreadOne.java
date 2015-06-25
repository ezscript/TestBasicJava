package ma.org.current;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadOne {
		 
	public volatile boolean isRun = false;
	
	public volatile int inc = 0;
	
	public int abc = 0;
    
    public void increase() {
        abc++;
        int a = inc;
        try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        inc = a +1;
    }
    
    
     
    public static void main(String[] args) {
        final ThreadOne test = new ThreadOne();
        for(int i=0;i<100;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<10;j++)
                        test.increase();
                };
            }.start();
        }
         
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
        System.out.println(test.abc);
        
        AtomicInteger inc = new AtomicInteger();
        inc.getAndIncrement();
    }
}
