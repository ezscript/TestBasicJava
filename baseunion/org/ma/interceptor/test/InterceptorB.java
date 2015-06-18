package org.ma.interceptor.test;

import org.ma.interceptor.Command;
import org.ma.interceptor.CommandInterceptor;

public class InterceptorB extends CommandInterceptor{

	public <T> T execute(Command<T> command) {
		try{
			System.out.println("b start ..");
			return next.execute(command);
		}finally{
			System.out.println("b end ..");
		}
	}

}
