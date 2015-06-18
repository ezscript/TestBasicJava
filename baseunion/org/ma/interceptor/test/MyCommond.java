package org.ma.interceptor.test;

import org.ma.interceptor.Command;

public class MyCommond implements Command<String>{

	private String name = null;
	public MyCommond(String name){
		this.name = name;
	}
	public String execute() {
		System.out.println("execute..." + name);
		return name + " 123";
	}
	
}
