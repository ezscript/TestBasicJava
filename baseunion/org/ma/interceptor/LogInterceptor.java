package org.ma.interceptor;

public class LogInterceptor extends CommandInterceptor {

	public <T> T execute(Command<T> command) {
		try {
			System.out.println("log ... start ");
			return next.execute(command);

		} finally {
			System.out.println("log ... end ");
		}
	}

}
