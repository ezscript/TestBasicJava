package ma.org.proxy.ano;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ma.org.proxy.ProxyUtil;
import ma.org.proxy.ano.declare.InvocationHandlerExt;

public class Bean {
	private String name =null;
	
	private Class<?> orgClass = null;
	
	private Object handlerTarget = null;
	
	private boolean needDefaultAop = false;
	
	public List<BeanField> beanFields = new ArrayList<BeanField>();
	
	public void addField(Field f,Object obj){
		beanFields.add(new BeanField(f,obj));
	}
	
	public static Bean createBean(String name){
		Bean bean = new Bean();
		bean.name  = name;
		return bean;
	}
	
	/*public static Bean createBean(String name,Object target , InvocationHandlerExt handler){
		Bean bean =createBean(name);
		bean.setTarget(target);
		
		handler.setTarget(target);
		
		bean.handlerTarget = ProxyUtil.bind(target.getClass(), handler);
		return bean;
	}*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	
	public Object getTarget() {
		return handlerTarget;
	}
	
	public Class<?> getOrgClass() {
		return orgClass;
	}

	public void setOrgClass(Class<?> orgClass) {
		this.orgClass = orgClass;
	}

	public void setTarget(Object target){
		this.handlerTarget = target;
	}
	
/*	public void setTarget(Object handlerObj , InvocationHandlerExt handler) {
		handler.setTarget(handlerObj);
		
		this.handlerTarget = ProxyUtil.bind(handlerObj.getClass(), handler);
	}*/
	
	
	public void addHandler(InvocationHandlerExt handler){
		handler.setTarget(this.handlerTarget);
		this.handlerTarget = ProxyUtil.bind(orgClass, handler);
		
	}
	
	public boolean isNeedDefaultAop() {
		return needDefaultAop;
	}

	public void setNeedDefaultAop(boolean needDefaultAop) {
		this.needDefaultAop = needDefaultAop;
	}

	public void initFields(){
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
