package ma.org.current;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadOne {
		 
	public volatile int inc = 0;
    
    public void increase() {
        inc++;
    }
     
    public static void main(String[] args) {
        final ThreadOne test = new ThreadOne();
        for(int i=0;i<100;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<10000;j++)
                        test.increase();
                };
            }.start();
        }
         
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
        
        
        AtomicInteger inc = new AtomicInteger();
        inc.getAndIncrement();
    }
}
