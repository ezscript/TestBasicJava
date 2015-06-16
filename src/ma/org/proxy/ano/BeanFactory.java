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

import ma.org.proxy.ano.test.MyAction;

public class BeanFactory {
	private static Map<String,Bean> beanMap = new HashMap<String,Bean>();
	

	public static void init(String pack){
		Set<Class<?>> set =  getClasses(pack);
		
		for(Class<?> c : set){
			boolean isService = c.isAnnotationPresent(Service.class);
			  
			boolean isAction = c.isAnnotationPresent(Action.class);
			
			if(isService){
				addService(c);
			//	System.out.println("find service... " + c.getName());
			}else if(isAction){
				addAction(c);
			//	System.out.println("find action... " + c.getName());
			}
		}
//		System.out.println(beanMap);
		
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
		if(beanMap.containsKey(name)){
			Bean bean =(Bean)beanMap.get(name);
			bean.setTarget(target,new AnyProxy());
		}else{
			Bean bean =Bean.createBean(name, target, new AnyProxy());
			beanMap.put(name, bean);
		}
		
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
					if(beanMap.containsKey(beanName)){
						f.setAccessible(true);
						f.set(target, getBean(beanName));
					}else{
						Bean bean = Bean.createBean(beanName);
						bean.addField(f, target);
						
						beanMap.put(beanName, bean);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
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
