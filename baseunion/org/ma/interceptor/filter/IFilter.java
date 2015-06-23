package org.ma.interceptor.filter;

public interface IFilter {
	void destroy();
	void doFilter(Request req, Response resp,IFilterChain filterChain);
	void init();
	
}
