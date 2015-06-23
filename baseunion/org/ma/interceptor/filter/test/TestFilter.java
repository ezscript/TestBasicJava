package org.ma.interceptor.filter.test;

import org.ma.interceptor.filter.FilterChainExecutor;

public class TestFilter {
	
	public static void main(String [] args){
		final FilterChainExecutor fc = new FilterChainExecutor();
		fc.addFilter(new FilterA());
		fc.addFilter(new FilterB());
		
		for(int i = 0 ; i< 100; i++){
			final String mark = String.valueOf(i);
			new Thread(){
				public void run(){
					fc.doCommond(new TargetCommond(mark));
				}
			}.start();
		}
		
	}
}
