package ma.org.proxy.ano;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ma.org.proxy.ProxyUtil;

public class Bean {
	private String name =null;
	private boolean isInit = false;
	
	private Object handlerTarget = null;
	
	public List<BeanField> beanFields = new ArrayList<BeanField>();
	
	public void addField(Field f,Object obj){
		beanFields.add(new BeanField(f,obj));
	}
	
	public static Bean createBean(String name){
		Bean bean = new Bean();
		bean.name  = name;
		return bean;
	}
	
	public static Bean createBean(String name,Object target , InvocationHandlerExt handler){
		Bean bean =createBean(name);
		bean.setTarget(target);
		return bean;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isInit() {
		return isInit;
	}

	
	public Object getTarget() {
		return handlerTarget;
	}
	
	public void setTarget(Object target){
		if(target != null){
			isInit = true;
		}else{
			isInit = false;
		}
		this.handlerTarget = target;
	}
	
	public void setTarget(Object handlerObj , InvocationHandlerExt handler) {
		if(handlerObj != null){
			isInit = true;
		}else{
			isInit = false;
		}
		handler.setTarget(handlerObj);
		
		this.handlerTarget = ProxyUtil.bind(handlerObj.getClass(), handler);
		for(BeanField bf : beanFields){
			Object target = bf.target;
			bf.field.setAccessible(true);
			try {
				bf.field.set(target, handlerTarget );
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class BeanField{
		public Object target = null;
		public Field field = null;
		
		public BeanField(Field f, Object obj) {
			field = f;
			target = obj;
		}
	}
	
}
