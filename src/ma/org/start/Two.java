package ma.org.start;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Two {

	
	public static void main(String[] args) {
		
		Properties properties = System.getProperties();
		Set<Entry<Object, Object>>  set = properties.entrySet();
		Iterator<Entry<Object, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<Object, Object> entry = it.next();
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		System.out.println(Integer.toString(133, 36));
	}
	

}
