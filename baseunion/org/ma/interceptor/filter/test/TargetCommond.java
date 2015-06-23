package org.ma.interceptor.filter.test;

import org.ma.interceptor.Command;

public class TargetCommond implements Command<String> {

	public String execute() {
		System.out.println("execute ....");
		return null;
	}

}
