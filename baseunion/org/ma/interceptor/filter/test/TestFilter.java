package org.ma.interceptor.filter.test;

import org.ma.interceptor.filter.FilterChainExecutor;

public class TestFilter {
	
	public static void main(String [] args){
		FilterChainExecutor fc = new FilterChainExecutor();
		fc.addFilter(new FilterA());
		fc.addFilter(new FilterB());
		
		fc.doCommond(new TargetCommond());
	}
}
