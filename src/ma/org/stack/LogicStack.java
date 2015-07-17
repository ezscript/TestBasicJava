package ma.org.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LogicStack<T> {
	private List<T> list = new ArrayList<T>();
	
	/**
	 * ��ջ
	 * @param t
	 * @return
	 */
	public T push(T t){
		list.add(t);
		return t;
	}
	
	/**
	 * ��ջ
	 * @return
	 */
	public T pop(){
		if(list.size() == 0)
			return null;
		else
			return list.remove(list.size() - 1);
	}
	
	public T top(){
		if(list.size() == 0)
			return null;
		else
			return list.get(list.size() -1);
	}	
	
	
	
	public static void main(String [] args){
		LogicStack<Integer> stack = new LogicStack<Integer>();
		System.out.println(stack.top());
		System.out.println(stack.pop());
		stack.push(1);
		stack.push(2);
		System.out.println(stack.top());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		Stack s;
	}
}
