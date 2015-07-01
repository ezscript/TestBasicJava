package ma.org.current;


public class ThreadTwo {

	public static void main(String[] args) {

		TestThread t = new TestThread(10);
		
		new Thread(t).start();
		new Thread(t).start();
		
		
	}
}

class TestThread extends Thread{
	private int c;

	public TestThread(int c) {
		this.c = c;
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c++;
		System.out.println(Thread.currentThread().getName() + ":" + c);
	}

}