package ma.org.err;

import java.lang.Thread.UncaughtExceptionHandler;

public class TestErrCatch implements UncaughtExceptionHandler {

	public TestErrCatch() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("TestErrCatch catch it...");
		System.out.println(t.getName() + "; errMsg:" + e.getMessage());
		throw new Error("����쳣û�˹�");
	}

	public static void main(String[] args) throws Exception {

		new TestErrCatch();

		new Thread() {
			public void run() {
				System.out.println(this.getName() + ":thread run");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				// try{
					throw new RuntimeException("�����߳��׳����쳣");
				// }catch(Exception err){
				// System.out.println("thread catch it");
				// }
			}
		}.start();

		Thread.sleep(2000);

		System.out.println("2���...");

		throw new RuntimeException("�����߳��׳����쳣");
	}
}
