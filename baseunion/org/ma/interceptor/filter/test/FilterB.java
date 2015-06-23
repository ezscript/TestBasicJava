package org.ma.interceptor.filter.test;

import org.ma.interceptor.filter.IFilterChain;
import org.ma.interceptor.filter.IFilter;
import org.ma.interceptor.filter.Request;
import org.ma.interceptor.filter.Response;

public class FilterB implements IFilter {

	public void destroy() {

	}

	public void doFilter(Request req, Response resp, IFilterChain filterChain) {
		System.out.println(Thread.currentThread() + ": B start...");
		filterChain.doFilter(req, resp);
		System.out.println(Thread.currentThread() + ": B end...");
	}

	public void init() {

	}

}
