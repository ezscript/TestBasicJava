package org.ma.interceptor.filter.test;

import org.ma.interceptor.Command;

public class TargetCommond implements Command<String> {
	private String mark = null;
	
	public TargetCommond(String mark) {
		super();
		this.mark = mark;
	}

	public String execute() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("execute ...." +mark);
		return null;
	}

}
