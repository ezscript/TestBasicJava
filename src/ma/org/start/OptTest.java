package ma.org.start;

import java.util.HashMap;
import java.util.Map;

public class OptTest {
	public static Map<String,IOpt> optMap = new HashMap<String,IOpt>();
	
	static{
		IOpt optOne = new IOpt(){
			public void optPrint(Object o) {
				System.out.println("opt.. one...");
			}
		};
		
		IOpt optTwo = new IOpt(){
			public void optPrint(Object o) {
				System.out.println("opt.. two...");
			}
		};
		
		IOpt optThree = new IOpt(){
			public void optPrint(Object o) {
				System.out.println("opt.. three...");
			}
		};
		optMap.put("1", optOne);
		optMap.put("2", optTwo);
		optMap.put("3", optThree);
	}
	public static void main(String[] args) {
		
		for(String arg : args){
			IOpt opt = optMap.get(arg);
			opt.optPrint(arg);
		}
	}
	
	interface IOpt{
		void optPrint(Object o);
	}	
}

