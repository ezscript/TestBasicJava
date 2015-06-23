package org.ma.interceptor.filter;

public interface IFilterChain {
	void doFilter(Request req, Response resp);
}
