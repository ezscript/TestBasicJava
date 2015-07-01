package ma.org.proxy.ano;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ma.org.proxy.ano.declare.Action;
import ma.org.proxy.ano.declare.Aop;
import ma.org.proxy.ano.declare.AutoField;
import ma.org.proxy.ano.declare.InvocationHandlerExt;
import ma.org.proxy.ano.declare.Service;
import ma.org.util.CollectionUtil;
import ma.org.util.CreateUtil;
import ma.org.util.ReflectUtil;

public class BeanFactory {
	private static Map<String,Bean> beanMap = new HashMap<String,Bean>();
	
	private static Set<AopBean> aopBeanSet = CreateUtil.set();
	
	public static void init(String pack){
		Set<Class<?>> set = ReflectUtil.getClasses(pack);
		
		
		Set<Class<?>> serviceSet = CreateUtil.set();
		Set<Class<?>> actionSet = CreateUtil.set();
		Set<Class<?>> aopSet = CreateUtil.set();
		
		for(Class<?> c : set){
			boolean isService = c.isAnnotationPresent(Service.class);
			  
			boolean isAction = c.isAnnotationPresent(Action.class);
			if(isService){
			//	addService(c);
				serviceSet.add(c);
			}else if(isAction){
				actionSet.add(c);
			//	addAction(c);
			}else if(c.isAnnotationPresent(Aop.class)){
				aopSet.add(c);
			}
		}
		
		for(Class<?> c : aopSet){
			addAop(c);
		}
		
		for(Class<?> c : serviceSet){
			addService(c);
		}
		
		for(Class<?> c : actionSet){
			addAction(c);
		}
		
	
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMapEntry<String,Bean>() {
			public void opt(String beanName, Bean bean) {
				for(AopBean aopBean : aopBeanSet){
					if(aopBean.matches(beanName)){
						bean.addHandler(aopBean.getProxy());
					}
				}
				if(bean.isNeedDefaultAop()){
					bean.addHandler(new DefaultProxy());
				}
			}
		});
		
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMapEntry<String,Bean>() {
			public void opt(String beanName, Bean bean) {
				bean.initFields();
			}
		});
		
	}
	
	
	
	
	private static void addAop(Class<?> c) {
	
		AopBean aopBean = new AopBean();
		aopBean.setProxyClass(c);
		Aop aop = (Aop)c.getAnnotation(Aop.class);
		aopBean.setReg(aop.targetBean());
		aopBeanSet.add(aopBean);
	}




	public static Object getBean(String beanName){
		if(beanMap.containsKey(beanName)){
			return beanMap.get(beanName).getTarget();
		}else {
			return null;
		}
	}
	
	
	private static void addService(Class<?> c) {
		Service service = (Service)c.getAnnotation(Service.class);
		String name = service.name();
		Object target = null;
		try {
			target = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		addField(target);
		Bean bean = null;
		if(beanMap.containsKey(name)){
			bean = (Bean)beanMap.get(name);
			bean.setTarget(target);
			//,new DefaultProxy());
		//	bean.initFields();
		}else{
			//bean =Bean.createBean(name, target, new DefaultProxy());
			bean = Bean.createBean(name);
			bean.setTarget(target);
			beanMap.put(name, bean);
		}
		bean.setNeedDefaultAop(true);
		bean.setOrgClass(c);
		
	}




	private static void addAction(Class<?> c) {
		Action action = (Action)c.getAnnotation(Action.class);
		String name = action.name();
		Object target = null;
		try {
			target = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		addField(target);
		Bean bean = Bean.createBean(name);
		bean.setOrgClass(c);
		bean.setTarget(target);
		beanMap.put(name, bean);
		
	}

	private static void addField(Object target){
		Class<?> c = target.getClass();
		Field[] fields =c.getDeclaredFields();
		
		for(Field f : fields){
			if(f.isAnnotationPresent(AutoField.class)){
				AutoField af = f.getAnnotation(AutoField.class);
				String beanName = null;
				if(!AutoField.Default_Mark.equals(af.name())){
					beanName = af.name();
				}else{
					beanName = f.getName();
				}
				try {
					
					Bean bean = null;
					if(beanMap.containsKey(beanName)){
						bean = beanMap.get(beanName);
					}else{
						bean = Bean.createBean(beanName);
						beanMap.put(beanName, bean);
					}
					bean.addField(f, target);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}



}
