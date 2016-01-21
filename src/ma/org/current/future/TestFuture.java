package ma.org.current.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
  	FutureTask<String> future = new FutureTask<String>(
		new Callable<String>() {
			public String call() {
				return searcher.search(target);
			}
	});
	executor.execute(future);
	dosomething ...
	String result = future.get(); 
 *
 */
public class TestFuture {

	ExecutorService executor = Executors.newCachedThreadPool();

	ArchiveSearcher searcher = new ArchiveSearcher() {
		public String search(String target) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return target + "str";
		}
	};

	void showSearch(final String target) throws InterruptedException {
		/*
		 * Future<String> future = executor.submit(new Callable<String>() {
		 * public String call() { return searcher.search(target); } });
		 */
		FutureTask<String> future = new FutureTask<String>(
			new Callable<String>() {
				public String call() {
					return searcher.search(target);
				}
		});
		executor.execute(future);

		displayOtherThings(target); // do other things while searching
		try {
			String result = future.get(); // use future
			// future.get(10, TimeUnit.SECONDS);
			System.out.println("结果：" + result);
		} catch (ExecutionException ex) {
			ex.printStackTrace();
			return;
		} finally {
			// executor.shutdown();
		}
	}

	private void displayOtherThings(String target) {
		System.out.println(target + "displayOtherThings");
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		TestFuture t = new TestFuture();
		t.showSearch("abc");

	}

	interface ArchiveSearcher {
		String search(String target);
	}
}
