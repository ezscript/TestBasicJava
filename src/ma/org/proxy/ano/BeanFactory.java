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

public class BeanFactory {
	private static Map<String,Bean> beanMap = new HashMap<String,Bean>();
	
	private static Set<AopBean> aopBeanSet = CreateUtil.set();
	
	public static void init(String pack){
		Set<Class<?>> set =  getClasses(pack);
		
		
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
		
	
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMap<String,Bean>() {
			public void opt(String beanName, Bean bean) {
				for(AopBean aopBean : aopBeanSet){
					if(aopBean.matches(beanName)){
						bean.addHandler(aopBean.getProxy());
					}
				}
			}
		});
		
		CollectionUtil.optMapEntry(beanMap, new CollectionUtil.IOptMap<String,Bean>() {
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
		Bean bean = null;
		if(beanMap.containsKey(name)){
			bean = (Bean)beanMap.get(name);
			bean.setTarget(target,new DefaultProxy());
		//	bean.initFields();
		}else{
			bean =Bean.createBean(name, target, new DefaultProxy());
			beanMap.put(name, bean);
		}
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



	/**
     * �Ӱ�package�л�ȡ���е�Class
     * 
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {
 
        // ��һ��class��ļ���
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // �Ƿ�ѭ������
        boolean recursive = true;
        // ��ȡ�������� �������滻
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    packageDirName);
            // ѭ��������ȥ
            while (dirs.hasMoreElements()) {
                // ��ȡ��һ��Ԫ��
                URL url = dirs.nextElement();
                // �õ�Э�������
                String protocol = url.getProtocol();
                // ��������ļ�����ʽ�����ڷ�������
                if ("file".equals(protocol)) {
                    System.out.println("file���͵�ɨ��-------------------");
                    // ��ȡ��������·��
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // �����jar���ļ�
                    // ����һ��JarFile
                    System.out.println("jar���͵�ɨ��----------------------");
                    JarFile jar;
                    try {
                        // ��ȡjar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // �Ӵ�jar�� �õ�һ��ö����
                        Enumeration<JarEntry> entries = jar.entries();
                        // ͬ���Ľ���ѭ������
                        while (entries.hasMoreElements()) {
                            // ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // �������/��ͷ��
                            if (name.charAt(0) == '/') {
                                // ��ȡ������ַ���
                                name = name.substring(1);
                            }
                            // ���ǰ�벿�ֺͶ���İ�����ͬ
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // �����"/"��β ��һ����
                                if (idx != -1) {
                                    // ��ȡ���� ��"/"�滻��"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // ������Ե�����ȥ ������һ����
                                if ((idx != -1) || recursive) {
                                    // �����һ��.class�ļ� ���Ҳ���Ŀ¼
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // ȥ�������".class" ��ȡ����������
                                        String className = name.substring(
                                                packageName.length() + 1, name
                                                        .length() - 6);
                                        try {
                                            // ��ӵ�classes
                                        	addClass(classes,packageName + '.' + className);
                                        	
                                        } catch (ClassNotFoundException e) {
                                            // log
                                            // .error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("��ɨ���û�������ͼʱ��jar����ȡ�ļ�����");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return classes;
    }
    
    private static void addClass(Set<Class<?>> classes, String className) throws ClassNotFoundException {
    	classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
		
	}




	/**
     * ���ļ�����ʽ����ȡ���µ�����Class
     * 
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName,
            String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // ��ȡ�˰���Ŀ¼ ����һ��File
        File dir = new File(packagePath);
        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");
            return;
        }
        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // ѭ�������ļ�
        for (File file : dirfiles) {
            // �����Ŀ¼ �����ɨ��
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // �����java���ļ� ȥ�������.class ֻ��������
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                	addClass(classes,packageName + '.' + className);
                    // ��ӵ�������ȥ
                    //classes.add(Class.forName(packageName + '.' + className));
                                         //�����ظ�ͬѧ�����ѣ�������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�
                    //                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                    } catch (ClassNotFoundException e) {
                    // log.error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");
                    e.printStackTrace();
                }
            }
        }
    }
}
