package ma.org.err;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;

public class TestErrCatch implements UncaughtExceptionHandler {

	public TestErrCatch() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("TestErrCatch catch it...");
		System.out.println(t.getName() + "; errMsg:" + e.getMessage());
		throw new Error("这个异常没人管");
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
					throw new RuntimeException("由子线程抛出的异常");
				// }catch(Exception err){
				// System.out.println("thread catch it");
				// }
			}
		}.start();

		Thread.sleep(2000);

		System.out.println("2秒后...");

		throw new RuntimeException("由主线程抛出的异常");
	}
}
