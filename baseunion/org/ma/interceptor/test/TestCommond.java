package org.ma.interceptor.test;

import org.ma.interceptor.CommandExecutorImpl;
import org.ma.interceptor.LogInterceptor;

public class TestCommond {
	
	public static void main(String [] args){
		LogInterceptor log = new LogInterceptor();
		InterceptorA a = new InterceptorA();
		InterceptorB b = new InterceptorB();
		log.setNext(a);
		a.setNext(b);
		b.setNext(new CommandExecutorImpl());
		
		String result = log.execute(new MyCommond("hello world"));
		System.out.println("result:" + result);
		
		
		result = log.execute(new MyCommond("abc"));
		System.out.println("result:" + result);
	}
}
