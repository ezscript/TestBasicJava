package org.ma.interceptor.filter;

import java.util.ArrayList;
import java.util.List;

import org.ma.interceptor.Command;

public class FilterChainExecutor implements IFilterChain{
	
	private static ThreadLocal<Integer> localIndex = new ThreadLocal<Integer>();
	private static ThreadLocal<Command<?>> localCommand = new ThreadLocal<Command<?>>();
	
	private List<IFilter> filters = new ArrayList<IFilter>();
	
	public void addFilter(IFilter f){
		filters.add(f);
	}
	
	public void doFilter(Request req, Response resp){
		int index = getNextIndex();
		if(index == -1){
			localCommand.get().execute();
			return;
		}
		IFilter f = filters.get(index);
		f.doFilter(req, resp, this);
	}
	
	public void doCommond(Command<?> commond){
		localIndex.set(-1);
		localCommand.set(commond);
		doFilter(new Request(),new Response());
		
	}
	
	public int getNextIndex(){
		Integer index = localIndex.get();
		index++;
		if(index == filters.size())
			index = -1;
		localIndex.set(index);
		return index;
	}
	
	public void setFilters(List<IFilter> filters) {
		this.filters = filters;
	}

	
}
