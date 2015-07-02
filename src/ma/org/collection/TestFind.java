package ma.org.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class TestFind {
	
	
	
	public static void main(String[] args) {
		List<Persion> ps = new ArrayList<Persion>();
		int size = 300000;
		int find = 2000;
		String [] ids = new String[find];
		for(int i = 0; i< size; i++){
			Persion p = new Persion(UUID.randomUUID().toString(),"name" + i);
			ps.add(p);
		}
		
		Random random = new Random();
		for(int i = 0; i< ids.length; i++){
			ids[i] = ps.get(random.nextInt(ps.size())).id;
		}
		
		long l1 = System.currentTimeMillis();
		
		findByMap(ps,ids);  //
	//	findByList(ps,ids); //
		
		long l2 = System.currentTimeMillis();
		System.out.println(l2 - l1);
		
	}
	
	public static void testRandom(){
		Random random = new Random();
		for(int i = 0 ; i< 50 ; i++)
			System.out.println(random.nextInt(50));
	}


	private static void findByList(List<Persion> ps, String[] ids) {
		
		for(String id : ids){
			for(int i = 0 ; i< ps.size(); i++){
				Persion p = ps.get(i);
				if(p.id.equals(id)){
			//		System.out.println(p);
				}
			}
		}
	}


	private static void findByMap(List<Persion> ps,String [] ids) {
		Map<String,Persion> map = new HashMap<String,Persion>();
		
		for(Persion p: ps){
			map.put(p.id, p);
		}
		System.out.println(map.size() == ps.size());
		
		for(String id : ids){
			Persion p = map.get(id);
	//		System.out.println(p);
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
	
	public String toString(){
		return id + ":" + name;
	}
	
}