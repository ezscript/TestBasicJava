package ma.org.proxy.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class TestFind {
	
	
	
	public static void main(String[] args) {
		List<Persion> ps = new ArrayList<Persion>();
		
		String [] ids = new String[2000];
		for(int i = 0; i< 300000; i++){
			Persion p = new Persion(UUID.randomUUID().toString(),"name" + i);
			ps.add(p);
			if(i< 2000){
				ids[i] = p.id;
			}
		}
		
		findByMap(ps,ids);
	}


	private static void findByMap(List<Persion> ps,String [] ids) {
		Map<String,Persion> map = new HashMap<String,Persion>();
		
		for(Persion p: ps){
			map.put(p.id, p);
		}
		
		for(String id : ids){
			System.out.println(map.get(id).name);
		}
		
	}
	
	
}
class Persion{
	public Persion(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String id;
	public String name;
}