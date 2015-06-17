package ma.org.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 不想写泛�?防止编译器报�?
 * @author MaXin
 */
public class CreateUtil {
	public static <T,V> Map<T,V> map(){
		return new HashMap<T,V>();
	}
	
	public static <T> List<T> list(){
		return new ArrayList<T>();
	}
	
	public static <T> Set<T> set(){
		return new HashSet<T>();
	}
	
}
